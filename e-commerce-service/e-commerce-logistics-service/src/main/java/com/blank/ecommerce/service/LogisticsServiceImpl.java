package com.blank.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.blank.ecommerce.dao.EcommerceLogisticsDao;
import com.blank.ecommerce.entity.EcommerceLogistics;
import com.blank.ecommerce.order.LogisticsMessage;
import com.blank.ecommerce.sink.LogisticsSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableBinding(LogisticsSink.class)
public class LogisticsServiceImpl {
    @Autowired
    private EcommerceLogisticsDao logisticsDao;

    @StreamListener(LogisticsSink.INPUT)
    public void consumeLogisticsMessage(@Payload Object payload){
        log.info("consume logistics message : [{}]", payload.toString());
        final LogisticsMessage logisticsMessage = JSON.parseObject(payload.toString(), LogisticsMessage.class);

        EcommerceLogistics ecommerceLogistics = logisticsDao.save(
                new EcommerceLogistics(
                        logisticsMessage.getUserId(),
                        logisticsMessage.getOrderId(),
                        logisticsMessage.getAddressId(),
                        logisticsMessage.getExtraInfo()
                        )
        );
        log.info("consume logistics message success: [{}]", ecommerceLogistics.getId());
    }
}
