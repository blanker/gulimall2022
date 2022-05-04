package com.blank.ecommerce.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.account.AddressInfo;
import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.dao.EcommerceAddressDao;
import com.blank.ecommerce.entity.EcommerceAddress;
import com.blank.ecommerce.filter.AccessContext;
import com.blank.ecommerce.service.IAddressService;
import com.blank.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private EcommerceAddressDao ecommerceAddressDao;

    @Override
    public TableId createAddressInfo(AddressInfo addressInfo) {
        final LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        final List<EcommerceAddress> list = addressInfo.getAddressItems().stream()
                .map(a -> EcommerceAddress.to(loginUserInfo.getId(), a))
                .collect(Collectors.toList());

        final List<EcommerceAddress> saved = ecommerceAddressDao.saveAll(list);
        final List<Long> ids = saved.stream().map(EcommerceAddress::getId).collect(Collectors.toList());
        log.info("create address info : [{}, {}]", loginUserInfo.getId(),
                JSON.toJSONString(ids));
        return TableId.builder().ids(
                ids.stream().map(TableId.Id::new).collect(Collectors.toList())
                ).build();
    }

    @Override
    public AddressInfo getCurrentAddressInfo() {
        final LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        final List<EcommerceAddress> addresses = ecommerceAddressDao.findAllByUserId(loginUserInfo.getId());

        final List<AddressInfo.AddressItem> list = addresses.stream().map(EcommerceAddress::toAddressItem).collect(Collectors.toList());

        return new AddressInfo(loginUserInfo.getId(), list);
    }

    @Override
    public AddressInfo getAddressInfoById(Long id) {
        final EcommerceAddress address = ecommerceAddressDao.findById(id).orElse(null);
        if (null == address) {
            throw new RuntimeException("address is not exist");
        }
        return new AddressInfo(address.getUserId(),
                Collections.singletonList(EcommerceAddress.toAddressItem(address)));
    }

    @Override
    public AddressInfo getAddressInfoByTableId(TableId tableId) {
        final List<Long> ids = tableId.getIds().stream().map(TableId.Id::getId).collect(Collectors.toList());
        final List<EcommerceAddress> addresses = ecommerceAddressDao.findAllById(ids);

        if (CollectionUtil.isEmpty(addresses)) {
            return new AddressInfo(-1L, Collections.emptyList());
        }
        final List<AddressInfo.AddressItem> list = addresses.stream().map(EcommerceAddress::toAddressItem).collect(Collectors.toList());

        return new AddressInfo(addresses.get(0).getUserId(), list);
    }
}
