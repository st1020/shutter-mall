package com.st1020.shuttermall.domain.vo;

import com.st1020.shuttermall.enums.OrderStatus;

public class SetOrderStatusRequest {
    private Long orderId;
    private OrderStatus orderStatus;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
