package com.st1020.shuttermall.service;

import com.st1020.shuttermall.domain.Product;
import com.st1020.shuttermall.utils.Result;

import java.util.List;

public interface ProductService {
    Result<List<Product>> findAll();

    Result<Product> findById(Long id);

    Result<Product> addProduct(Product product);

    Result<Product> setProductInfo(Product product);

    Result<Product> deleteProduct(Long id);

    Result<List<Product>> searchProduct(String name);
}
