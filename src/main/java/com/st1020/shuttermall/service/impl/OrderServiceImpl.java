package com.st1020.shuttermall.service.impl;

import com.st1020.shuttermall.domain.*;
import com.st1020.shuttermall.enums.OrderStatus;
import com.st1020.shuttermall.repository.OrderRepository;
import com.st1020.shuttermall.repository.ProductRepository;
import com.st1020.shuttermall.repository.UserRepository;
import com.st1020.shuttermall.service.OrderService;
import com.st1020.shuttermall.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Result<List<Order>> findAll() {
        return new Result<>(orderRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Result<Order> findById(Long id) {
        Optional<Order> product = orderRepository.findById(id);
        return product.map(Result::new).orElseGet(() -> new Result<>("无法找到订单！"));
    }

    @Override
    @Transactional
    public Result<Order> submitOrder(Long userId, Long productId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);
        if (user.isEmpty() || product.isEmpty()) {
            return new Result<>("添加失败！");
        } else {
            Order order = new Order(user.get(), product.get());
            return new Result<>(orderRepository.saveAndFlush(order));

        }
    }

    @Override
    @Transactional
    public Result<List<Order>> submitOrders(Long userId, List<Long> productIds) {
        Optional<User> user = userRepository.findById(userId);
        List<Product> products = productRepository.findAllById(productIds);
        if (user.isEmpty() || products.isEmpty()) {
            return new Result<>("添加失败！");
        } else {
            return new Result<>(orderRepository.saveAllAndFlush(
                    products.stream().map(
                            (product -> new Order(user.get(), product))
                    ).toList()
            ));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Result<List<Order>> getAllOrders(Long userId) {
        return new Result<>(orderRepository.findAllByUserId(userId));
    }

    @Override
    @Transactional
    public Result<Order> setOrderStatus(Long orderId, OrderStatus orderStatus) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            return new Result<>("订单不存在！");
        } else {
            order.get().setOrderStatus(orderStatus);
            return new Result<>(orderRepository.saveAndFlush(order.get()));
        }
    }

    @Override
    @Transactional
    public Result<Order> deleteOrder(Order order) {
        orderRepository.delete(order);
        return new Result<>(order);
    }
}
