package com.st1020.shuttermall.service;

import com.st1020.shuttermall.domain.Cart;
import com.st1020.shuttermall.domain.Product;
import com.st1020.shuttermall.utils.Result;

import java.util.List;

public interface CartService {
    Result<List<Product>> getAllCartProducts(Long userId);

    Result<Cart> addCart(Long userId, Long productId);

    Result<Cart> deleteCart(Long userId, Long productId);
}
