package com.blank.ecommerce.service.impl;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.dao.EcommerceOrderDao;
import com.blank.ecommerce.account.AddressInfo;
import com.blank.ecommerce.account.BalanceInfo;
import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.entity.EcommerceOrder;
import com.blank.ecommerce.feign.AddressClient;
import com.blank.ecommerce.feign.NotSecuredBalanceClient;
import com.blank.ecommerce.feign.NotSecuredGoodsClient;
import com.blank.ecommerce.feign.SecuredGoodsClient;
import com.blank.ecommerce.filter.AccessContext;
import com.blank.ecommerce.goods.DeductGoodsInventory;
import com.blank.ecommerce.goods.SimpleGoodsInfo;
import com.blank.ecommerce.order.LogisticsMessage;
import com.blank.ecommerce.order.OrderInfo;
import com.blank.ecommerce.service.IOrderService;
import com.blank.ecommerce.source.LogisticsSource;
import com.blank.ecommerce.vo.CommonResponse;
import com.blank.ecommerce.vo.PageSimpleOrderDetail;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@EnableBinding(LogisticsSource.class)
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private NotSecuredBalanceClient balanceClient;
    @Autowired
    private NotSecuredGoodsClient goodsClient;
    @Autowired
    private SecuredGoodsClient securedGoodsClient;
    @Autowired
    private AddressClient addressClient;
    @Autowired
    private LogisticsSource logisticsSource;
    @Autowired
    private EcommerceOrderDao orderDao;

    /**
     * 1 校验合法性
     * 2 创建订单
     * 3 扣减商品库存
     * 4 扣减用户余额
     * 5 发送订单物流消息
     * @param orderInfo
     * @return
     */
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public TableId createOrder(OrderInfo orderInfo) {
        // 1


        // 2
        EcommerceOrder order = new EcommerceOrder();
        order.setUserId(AccessContext.getLoginUserInfo().getId());
        order.setAddressId(orderInfo.getUserAddress());
        order.setOrderDetail(JSON.toJSONString(orderInfo.getOrderItems()));
        final EcommerceOrder savedOrder = orderDao.save(order);

        // 3 扣减商品库存
        List<DeductGoodsInventory> deductGoodsInventories =
                orderInfo.getOrderItems().stream()
                        .map(OrderInfo.OrderItem::toDeductGoodsInventory)
                        .collect(Collectors.toList());
        goodsClient.deductGoodsInventory(deductGoodsInventories);

        // 4 扣减用户余额
        BalanceInfo balanceInfo = new BalanceInfo();
        balanceInfo.setUserId(AccessContext.getLoginUserInfo().getId());
        balanceInfo.setBalance(calcAmountFromOrderInfo(orderInfo));
        balanceClient.deductBalance(balanceInfo);


        // 5 发送订单物流消息
        LogisticsMessage logisticsMessage = new LogisticsMessage();
        logisticsMessage.setOrderId(order.getId());
        logisticsMessage.setUserId(AccessContext.getLoginUserInfo().getId());
        logisticsMessage.setAddressId(orderInfo.getUserAddress());
        logisticsMessage.setExtraInfo(JSON.toJSONString(orderInfo.getOrderItems()));
        logisticsSource.logisticsOutput().send(
                MessageBuilder.withPayload(JSON.toJSONString(logisticsMessage))
                        .build()
        );

        return new TableId(Arrays.asList(new TableId.Id(savedOrder.getId())));
    }

    private Long calcAmountFromOrderInfo(OrderInfo orderInfo){

        final List<SimpleGoodsInfo> simpleGoodsInfos = goodsClient.getSimpleGoodsInfoByTableId(
                new TableId(
                        orderInfo.getOrderItems().stream()
                                .map(OrderInfo.OrderItem::getGoodsId)
                                .map(TableId.Id::new)
                                .collect(Collectors.toList())
                )
        ).getData();
        final Map<Long, Integer> id2Price = simpleGoodsInfos.stream()
                .collect(Collectors.toMap(SimpleGoodsInfo::getId, SimpleGoodsInfo::getPrice));

        AtomicInteger totalAmount = new AtomicInteger(0);
        orderInfo.getOrderItems().forEach(item -> {
            totalAmount.set( totalAmount.get() +
                    item.getCount() * id2Price.get(item.getGoodsId()));
        });
        return totalAmount.longValue();
    }

    @Override
    public PageSimpleOrderDetail getPageSimpleOrderDetail(int page) {
        if (page <= 0) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page - 1, 10,
                Sort.by("id").descending())
                ;
        Long userId = AccessContext.getLoginUserInfo().getId();
        final Page<EcommerceOrder> ecommerceOrderPage = orderDao.findByUserId(userId, pageable);
        final List<EcommerceOrder> content = ecommerceOrderPage.getContent();

        if (CollectionUtils.isEmpty(content)) {
            return new PageSimpleOrderDetail(Collections.emptyList(), false);
        }
        Set<Long> goodsIdsInOrders = new HashSet<>();
        content.forEach(o -> {
            final List<DeductGoodsInventory> deductGoodsInventories = JSON.parseArray(o.getOrderDetail(), DeductGoodsInventory.class);
            goodsIdsInOrders.addAll(deductGoodsInventories.stream().map(DeductGoodsInventory::getGoodsId).collect(Collectors.toSet()));
        });

        assert !CollectionUtils.isEmpty(goodsIdsInOrders);
        boolean hasMore = ecommerceOrderPage.getTotalPages() > page;
        final List<SimpleGoodsInfo> simpleGoodsInfos = securedGoodsClient.getSimpleGoodsInfoByTableId(
                new TableId(goodsIdsInOrders.stream().map(TableId.Id::new).collect(Collectors.toList()))
        ).getData();

        final AddressInfo addressInfo = addressClient.getAddressInfoByTableId(
                new TableId(
                        content.stream().map(o -> new TableId.Id(o.getAddressId())).distinct().collect(Collectors.toList())
                )
        ).getData();
        return new PageSimpleOrderDetail(
                assembleSingleOrderDetail(content, simpleGoodsInfos, addressInfo),
                hasMore
        );
    }

    private List<PageSimpleOrderDetail.SingleOrderItem> assembleSingleOrderDetail(
            List<EcommerceOrder> orders, List<SimpleGoodsInfo> simpleGoodsInfos,
            AddressInfo addressInfo
    ) {
        Map<Long, SimpleGoodsInfo> id2SimpleGoodsInfo =
                simpleGoodsInfos.stream().collect(
                        Collectors.toMap(SimpleGoodsInfo::getId, Function.identity())
                );
        Map<Long, AddressInfo.AddressItem> id2AddressItem =
                addressInfo.getAddressItems().stream().collect(
                        Collectors.toMap(AddressInfo.AddressItem::getId, Function.identity())
                );
        List<PageSimpleOrderDetail.SingleOrderItem> result = new ArrayList<>(orders.size());
        orders.forEach(o -> {
            PageSimpleOrderDetail.SingleOrderItem orderItem = new PageSimpleOrderDetail.SingleOrderItem();
            orderItem.setId(o.getId());
            orderItem.setUserAddress(id2AddressItem.getOrDefault(
                    o.getAddressId(), new AddressInfo.AddressItem()
                ).toUserAddress()
            );
            orderItem.setGoodsItems(buildGoodsItem(o, id2SimpleGoodsInfo));
            result.add(orderItem);
        });
        return result;
    }

    private List<PageSimpleOrderDetail.SingleOrderGoodsItem> buildGoodsItem(
            EcommerceOrder order, Map<Long, SimpleGoodsInfo> id2SimpleGoodsInfo
    ){
        List<PageSimpleOrderDetail.SingleOrderGoodsItem> result = new ArrayList<>();
        final List<DeductGoodsInventory> deductGoodsInventories = JSON.parseArray(order.getOrderDetail(), DeductGoodsInventory.class);
        deductGoodsInventories.forEach( dgi -> {
            PageSimpleOrderDetail.SingleOrderGoodsItem goodsItem = new PageSimpleOrderDetail.SingleOrderGoodsItem();
            goodsItem.setSimpleGoodsInfo(id2SimpleGoodsInfo.getOrDefault(dgi.getGoodsId(), new SimpleGoodsInfo(-1L)));
            goodsItem.setCount(dgi.getCount());
        });
        return result;
    }
}
