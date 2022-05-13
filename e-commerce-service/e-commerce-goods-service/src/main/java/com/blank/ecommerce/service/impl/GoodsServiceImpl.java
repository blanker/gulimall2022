package com.blank.ecommerce.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.constant.GoodsConstant;
import com.blank.ecommerce.dao.EcommerceGoodsDao;
import com.blank.ecommerce.entity.EcommerceGoods;
import com.blank.ecommerce.goods.DeductGoodsInventory;
import com.blank.ecommerce.goods.GoodsInfo;
import com.blank.ecommerce.goods.SimpleGoodsInfo;
import com.blank.ecommerce.service.IGoodsService;
import com.blank.ecommerce.vo.PageSimpleGoodsInfo;
import io.seata.tm.api.GlobalTransaction;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl  implements IGoodsService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private EcommerceGoodsDao goodsDao;

    @Override
    public List<GoodsInfo> getGoodsInfoByTableId(TableId tableId) {
        final List<Long> ids
                = tableId.getIds().stream().map(TableId.Id::getId)
                    .collect(Collectors.toList());
        final List<EcommerceGoods> ecommerceGoods = IterableUtils.toList(goodsDao.findAllById(ids));
        return ecommerceGoods.stream().map(EcommerceGoods::toGoodsInfo)
                .collect(Collectors.toList());
    }

    @Override
    public PageSimpleGoodsInfo getPageSimpleGoodsInfoByPage(int page) {
        if (page < 0) {
            page = 0;
        }
        Pageable pageable =
                PageRequest.of(page, 10, Sort.by("id").descending());

        final Page<EcommerceGoods> orderPage = goodsDao.findAll(pageable);
        return new PageSimpleGoodsInfo(
                orderPage.getContent().stream().map(EcommerceGoods::toSimple).collect(Collectors.toList()),
                orderPage.getTotalPages() > page + 1
        );
    }

    @Override
    public List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId) {
        List<Object> goodsIds = tableId.getIds().stream()
                .map(id -> id.getId().toString())
                .collect(Collectors.toList());
        final List<Object> cached = redisTemplate.opsForHash()
                .multiGet(GoodsConstant.ECOMMERCE_GOODS_DICT_KEY, goodsIds)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
                ;
        if (CollectionUtil.isNotEmpty(cached)) {
            if (tableId.getIds().size() == cached.size()) {
                log.info("get simple goods info (from cache): [{}]", JSON.toJSONString(goodsIds));
                return getCachedGoodsInfo(cached);
            } else {
                List<SimpleGoodsInfo> left = getCachedGoodsInfo(cached);
                Collection<Long> subtractIds = CollectionUtil.subtract(
                        tableId.getIds().stream().map(TableId.Id::getId).collect(Collectors.toList()),
                        left.stream().map(SimpleGoodsInfo::getId).collect(Collectors.toList())
                );
                List<SimpleGoodsInfo> right =
                        queryGoodsFromDBAndCacheToRedis(
                                new TableId(
                                        subtractIds.stream().map(TableId.Id::new).collect(Collectors.toList())));
                log.info("get simple goods info (from db and cache): [{}]", JSON.toJSONString(subtractIds));
                return new ArrayList(CollectionUtil.union(left, right));
            }
        } else {
            return queryGoodsFromDBAndCacheToRedis(tableId);
        }

    }

    @Override
    public Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories) {
        GlobalTransaction tx = GlobalTransactionContext.getCurrent();
        log.info("deductGoodsInventory xid: [{}]", tx == null? "null" : tx.getXid());
        deductGoodsInventories.forEach(d -> {
            if (d.getCount() <= 0 ) {
                throw new RuntimeException("purchase goods count need > 0");
            }
        });
        final List<EcommerceGoods> ecommerceGoods = IterableUtils.toList(
                goodsDao.findAllById(
                        deductGoodsInventories.stream()
                                .map(DeductGoodsInventory::getGoodsId)
                                .collect(Collectors.toList())
                )
        );

        if (CollectionUtil.isEmpty(ecommerceGoods)) {
            throw new RuntimeException("cannot find any goods");
        }
        if (ecommerceGoods.size() != deductGoodsInventories.size()){
            throw new RuntimeException("request invalid");
        }
        Map<Long, DeductGoodsInventory> goodsId2DeductGoodsInventory
                = deductGoodsInventories.stream().collect(
                        Collectors.toMap(DeductGoodsInventory::getGoodsId,
                                Function.identity())
        );

        ecommerceGoods.forEach(g -> {
            Long currentInventory = g.getInventory();
            Integer needDeductInventory = goodsId2DeductGoodsInventory.get(g.getId()).getCount();
            if (currentInventory < needDeductInventory) {
                log.warn("goods inventory is not enough: [{}, {}]", currentInventory, needDeductInventory);
                throw new RuntimeException("goods inventory is not enough" + g.getId());
            }
            g.setInventory(currentInventory - needDeductInventory);
            log.info("deduct goods inventory: [{}, {}, {}]", g.getId(), currentInventory, g.getInventory());
        });

        goodsDao.saveAll(ecommerceGoods);
        log.info("deduct goods success");
        return true;
    }

    private List<SimpleGoodsInfo> getCachedGoodsInfo(List<Object> cachedSimpleGoodsInfos) {
        return cachedSimpleGoodsInfos.stream()
                .map(obj -> JSON.parseObject(obj.toString(), SimpleGoodsInfo.class))
                .collect(Collectors.toList());
    }

    private List<SimpleGoodsInfo> queryGoodsFromDBAndCacheToRedis(TableId tableId){
        final List<Long> ids = tableId.getIds().stream().map(TableId.Id::getId).collect(Collectors.toList());
        log.info("get simple goods info by ids (from db): [{}]",
                JSON.toJSONString(ids));
        final List<EcommerceGoods> ecommerceGoods = IterableUtils.toList( goodsDao.findAllById(ids));
        final List<SimpleGoodsInfo> simpleGoodsInfoList = ecommerceGoods.stream().map(EcommerceGoods::toSimple).collect(Collectors.toList());
        log.info("cache simple goods info [{}]", JSON.toJSONString(ids));

        Map<String, String> id2JsonObject = new HashMap<>(simpleGoodsInfoList.size());
        simpleGoodsInfoList.forEach(g -> id2JsonObject.put(
                g.getId().toString(),
                JSON.toJSONString(g)
        ));

        redisTemplate.opsForHash().putAll(GoodsConstant.ECOMMERCE_GOODS_DICT_KEY, id2JsonObject);
        return simpleGoodsInfoList;
    }
}
