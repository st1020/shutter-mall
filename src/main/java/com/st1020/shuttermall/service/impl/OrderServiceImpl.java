package com.st1020.shuttermall.service.impl;

import com.st1020.shuttermall.domain.*;
import com.st1020.shuttermall.enums.OrderStatus;
import com.st1020.shuttermall.exception.BusinessException;
import com.st1020.shuttermall.repository.OrderRepository;
import com.st1020.shuttermall.repository.ProductRepository;
import com.st1020.shuttermall.repository.UserRepository;
import com.st1020.shuttermall.service.MailService;
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
    @Resource
    private MailService mailService;

    @Override
    @Transactional(readOnly = true)
    public Result<List<Order>> findAll() {
        return new Result<>(orderRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Result<Order> findById(Long id) {
        Optional<Order> product = orderRepository.findById(id);
        if (product.isEmpty()) {
            throw new BusinessException("无法找到订单！");
        } else {
            return new Result<>(product.get());
        }
    }

    @Override
    @Transactional
    public Result<Order> submitOrder(Long userId, Long productId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);
        if (user.isEmpty() || product.isEmpty() || product.get().getStock() == 0) {
            throw new BusinessException("下单失败！");
        } else if (product.get().getStock() == 0) {
            throw new BusinessException("库存不足！");
        } else {
            product.get().setStock(product.get().getStock() - 1);
            productRepository.saveAndFlush(product.get());
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
            throw new BusinessException("下单失败！");
        } else {
            for (Product product : products) {
                product.setStock(product.getStock() - 1);
            }
            productRepository.saveAllAndFlush(products);
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
    @Transactional(readOnly = true)
    public Result<List<Order>> getAllOrdersByOrderStatus(Long userId, OrderStatus orderStatus) {
        return new Result<>(orderRepository.findAllByUserIdAndOrderStatus(userId, orderStatus));
    }

    @Override
    public Result<List<Order>> getAllOrdersByShopId(Long shopId) {
        return new Result<>(orderRepository.findAllByProductShopInfoId(shopId));
    }

    @Override
    @Transactional
    public Result<Order> setOrderStatus(Long orderId, OrderStatus orderStatus) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new BusinessException("订单不存在！");
        } else {
            if (orderStatus == OrderStatus.FINISH) {
                mailService.sendMail(
                        order.get().getUser().getEmail(),
                        "您的订单已经完成！",
                        "订单号：" + order.get().getProduct().getId() +
                                "\n商品名：" + order.get().getProduct().getName() +
                                "\n商品名称：" + order.get().getProduct().getCreateDate()
                );
            }
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
