package com.st1020.shuttermall.controller;

import com.st1020.shuttermall.annotation.Permission;
import com.st1020.shuttermall.domain.Order;
import com.st1020.shuttermall.domain.Product;
import com.st1020.shuttermall.domain.User;
import com.st1020.shuttermall.domain.vo.SetOrderStatusRequest;
import com.st1020.shuttermall.service.OrderService;
import com.st1020.shuttermall.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    final private OrderService orderService;
    final private HttpServletRequest request;

    @Autowired
    public OrderController(OrderService orderService, HttpServletRequest request) {
        this.orderService = orderService;
        this.request = request;
    }

    @PostMapping("/getAll")
    @Permission(admin = true)
    public Result<List<Order>> getAll() {
        return orderService.findAll();
    }

    @PostMapping("/getOrderInfo")
    public Result<Order> getProductInfo(@RequestBody Order order) {
        return orderService.findById(order.getId());
    }

    @PostMapping("/submitOrder")
    public Result<Order> submitOrder(@RequestBody Product product) {
        User user = ((User) request.getAttribute("user"));
        return orderService.submitOrder(user.getId(), product.getId());
    }

    @PostMapping("/submitOrders")
    public Result<List<Order>> submitOrders(@RequestBody List<Long> productIds) {
        User user = ((User) request.getAttribute("user"));
        return orderService.submitOrders(user.getId(), productIds);
    }

    @PostMapping("/getMyOrders")
    public Result<List<Order>> getAllOrders() {
        User user = ((User) request.getAttribute("user"));
        return orderService.getAllOrders(user.getId());
    }

    @PostMapping("/setOrderStatus")
    @Permission(admin = true)
    public Result<Order> setOrderStatus(@RequestBody SetOrderStatusRequest setOrderStatusRequest) {
        return orderService.setOrderStatus(
                setOrderStatusRequest.getOrderId(),
                setOrderStatusRequest.getOrderStatus()
        );
    }

    @PostMapping("/deleteOrder")
    @Permission(admin = true)
    public Result<Order> deleteOrder(@RequestBody Order order) {
        return orderService.deleteOrder(order);
    }
}
