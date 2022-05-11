package com.blank.ecommerce.controller;

import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.order.OrderInfo;
import com.blank.ecommerce.service.IOrderService;
import com.blank.ecommerce.vo.PageSimpleOrderDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name="订单服务")
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @Operation(
            summary = "创建",
            description = "购买（分布式事务），创建订单，扣减库存，扣减账户，物流信息",
            method = "POST"
    )
    @PostMapping("/create-order")
    public TableId createOrder(@RequestBody OrderInfo orderInfo) {
        return orderService.createOrder(orderInfo);
    }

    @GetMapping("/page-simple-order-detail")
    public PageSimpleOrderDetail getPageSimpleOrderDetail(
            @RequestParam(required = false, defaultValue = "1") int page){
        return orderService.getPageSimpleOrderDetail(page);
    }
}
