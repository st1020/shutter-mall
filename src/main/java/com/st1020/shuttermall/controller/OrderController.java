package com.st1020.shuttermall.controller;

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
    public Result<List<Order>> getAll() {
        if (((User) request.getAttribute("user")).isAdmin()) {
            return orderService.findAll();
        } else {
            return new Result<>("权限不足！");
        }
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
    public Result<Order> setOrderStatus(@RequestBody SetOrderStatusRequest setOrderStatusRequest) {
        if (((User) request.getAttribute("user")).isAdmin()) {
            return orderService.setOrderStatus(
                    setOrderStatusRequest.getOrderId(),
                    setOrderStatusRequest.getOrderStatus()
            );
        } else {
            return new Result<>("权限不足！");
        }
    }

    @PostMapping("/deleteOrder")
    public Result<Order> deleteOrder(@RequestBody Order order) {
        if (((User) request.getAttribute("user")).isAdmin()) {
            return orderService.deleteOrder(order);
        } else {
            return new Result<>("权限不足！");
        }
    }
}
