package com.st1020.shuttermall.controller;

import com.st1020.shuttermall.domain.Cart;
import com.st1020.shuttermall.domain.Product;
import com.st1020.shuttermall.domain.User;
import com.st1020.shuttermall.service.CartService;
import com.st1020.shuttermall.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    final private CartService cartService;
    final private HttpServletRequest request;

    @Autowired
    public CartController(CartService cartService, HttpServletRequest request) {
        this.cartService = cartService;
        this.request = request;
    }

    @PostMapping("/getCartProducts")
    public Result<List<Product>> getCartProducts() {
        User user = ((User) request.getAttribute("user"));
        return cartService.getAllCartProducts(user.getId());
    }

    @PostMapping("/addCart")
    public Result<Cart> addCart(@RequestBody Product product) {
        User user = ((User) request.getAttribute("user"));
        return cartService.addCart(user.getId(), product.getId());
    }

    @PostMapping("/deleteCart")
    public Result<Cart> removeCart(@RequestBody Product product) {
        User user = ((User) request.getAttribute("user"));
        return cartService.deleteCart(user.getId(), product.getId());
    }
}
