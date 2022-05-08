package com.blank.ecommerce.service;

import com.blank.ecommerce.common.TableId;
import com.blank.ecommerce.order.OrderInfo;
import com.blank.ecommerce.vo.PageSimpleOrderDetail;

public interface IOrderService {
    TableId createOrder(OrderInfo orderInfo);

    PageSimpleOrderDetail getPageSimpleOrderDetail(int page);
}
