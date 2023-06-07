package com.st1020.shuttermall.service.impl;

import com.st1020.shuttermall.domain.Cart;
import com.st1020.shuttermall.domain.Product;
import com.st1020.shuttermall.domain.User;
import com.st1020.shuttermall.repository.CartRepository;
import com.st1020.shuttermall.repository.ProductRepository;
import com.st1020.shuttermall.repository.UserRepository;
import com.st1020.shuttermall.service.CartService;
import com.st1020.shuttermall.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Resource
    private CartRepository cartRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Result<List<Product>> getAllCartProducts(Long userId) {
        return new Result<>(cartRepository.findAllByUserId(userId).stream().map(Cart::getProduct).toList());
    }

    @Override
    @Transactional
    public Result<Cart> addCart(Long userId, Long productId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);
        if (user.isEmpty() || product.isEmpty()) {
            return new Result<>("添加失败！");
        } else {
            Cart cart = new Cart(user.get(), product.get());
            return new Result<>(cartRepository.saveAndFlush(cart));
        }
    }

    @Override
    @Transactional
    public Result<Cart> deleteCart(Long userId, Long productId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);
        if (user.isEmpty() || product.isEmpty()) {
            return new Result<>("删除失败！");
        } else {
            Cart cart = new Cart(user.get(), product.get());
            cartRepository.delete(cart);
            return new Result<>(cart);
        }
    }
}
