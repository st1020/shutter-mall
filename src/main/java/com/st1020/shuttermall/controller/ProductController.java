package com.st1020.shuttermall.controller;

import com.st1020.shuttermall.domain.Product;
import com.st1020.shuttermall.domain.User;
import com.st1020.shuttermall.service.ProductService;
import com.st1020.shuttermall.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    final private ProductService productService;
    final private HttpServletRequest request;

    @Autowired
    public ProductController(ProductService productService, HttpServletRequest request) {
        this.productService = productService;
        this.request = request;
    }

    @PostMapping("/getAll")
    public Result<List<Product>> getAll() {
        return productService.findAll();
    }

    @PostMapping("/getProductInfo")
    public Result<Product> getProductInfo(@RequestBody Product product) {
        return productService.findById(product.getId());
    }

    @PostMapping("/addProduct")
    public Result<Product> addProduct(@RequestBody Product product) {
        if (((User) request.getAttribute("user")).isAdmin()) {
            return productService.addProduct(product);
        } else {
            return new Result<>("权限不足！");
        }
    }

    @PostMapping("/setProductInfo")
    public Result<Product> updateProductInfo(@RequestBody Product productInfo) {
        User user = (User) request.getAttribute("user");
        if (user.isAdmin()) {
            return productService.setProductInfo(productInfo);
        } else {
            return new Result<>("权限不足！");
        }
    }

    @PostMapping("/deleteProduct")
    public Result<Product> deleteProduct(@RequestBody Product productInfo) {
        User user = (User) request.getAttribute("user");
        if (user.isAdmin()) {
            return productService.deleteProduct(productInfo.getId());
        } else {
            return new Result<>("权限不足！");
        }
    }
}
