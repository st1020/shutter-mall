package com.st1020.shuttermall.controller;

import com.st1020.shuttermall.annotation.Permission;
import com.st1020.shuttermall.domain.Product;
import com.st1020.shuttermall.service.ProductService;
import com.st1020.shuttermall.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    final private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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
    @Permission(admin = true)
    public Result<Product> addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PostMapping("/setProductInfo")
    @Permission(admin = true)
    public Result<Product> updateProductInfo(@RequestBody Product productInfo) {
        return productService.setProductInfo(productInfo);
    }

    @PostMapping("/deleteProduct")
    @Permission(admin = true)
    public Result<Product> deleteProduct(@RequestBody Product productInfo) {
        return productService.deleteProduct(productInfo.getId());
    }
}
