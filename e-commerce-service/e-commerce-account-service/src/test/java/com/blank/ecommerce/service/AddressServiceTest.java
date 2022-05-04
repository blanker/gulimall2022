package com.blank.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.account.AddressInfo;
import com.blank.ecommerce.common.TableId;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@SpringBootTest
@Slf4j
public class AddressServiceTest extends BaseTest{
    @Autowired
    private IAddressService addressService;

    @Test
    public void testCreateAddressInfo(){
        AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();
        addressItem.setAddressDetail("北京故宫");
        addressItem.setCity("北京");
        addressItem.setPhone("1388888888");
        addressItem.setProvince("北京");
        addressItem.setUsername("blank");

        log.info("create address info : [{}]", addressService.createAddressInfo(
                new AddressInfo(null, Collections.singletonList(addressItem))
        ));
    }

    @Test void testGetCurrentAddressInfo(){
        log.info(JSON.toJSONString(addressService.getCurrentAddressInfo()));
    }

    @Test void testGetAddressInfoById(){
        log.info(JSON.toJSONString(addressService.getAddressInfoById(1L)));
    }

    @Test void testGetAddressInfoByTableId(){
        TableId tableId = new TableId();
        tableId.setIds(
                Arrays.asList(1L, 2L).stream().map(TableId.Id::new).collect(Collectors.toList())
        );
        log.info(JSON.toJSONString(addressService.getAddressInfoByTableId(tableId)));
    }


}
