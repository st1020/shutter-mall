package com.st1020.shuttermall.service;

import com.st1020.shuttermall.domain.Order;
import com.st1020.shuttermall.enums.OrderStatus;
import com.st1020.shuttermall.utils.Result;

import java.util.List;

public interface OrderService {
    Result<List<Order>> findAll();

    Result<Order> findById(Long id);

    Result<Order> submitOrder(Long userId, Long productId);

    Result<List<Order>> submitOrders(Long userId, List<Long> productIds);

    Result<List<Order>> getAllOrders(Long userId);

    Result<List<Order>> getAllOrdersByOrderStatus(Long userId, OrderStatus orderStatus);

    Result<List<Order>> getAllOrdersByShopId(Long shopId);

    Result<Order> setOrderStatus(Long orderId, OrderStatus orderStatus);

    Result<Order> deleteOrder(Order order);
}
