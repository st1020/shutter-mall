package com.st1020.shuttermall.service.impl;

import com.st1020.shuttermall.domain.Product;
import com.st1020.shuttermall.repository.ProductRepository;
import com.st1020.shuttermall.service.ProductService;
import com.st1020.shuttermall.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductRepository productRepository;

    @Override
    public Result<List<Product>> findAll() {
        return new Result<>(productRepository.findAll());
    }

    @Override
    public Result<Product> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(Result::new).orElseGet(() -> new Result<>("无法找到商品！"));
    }

    @Override
    public Result<Product> addProduct(Product product) {
        if (productRepository.findByName(product.getName()).isEmpty()) {
            return new Result<>(productRepository.saveAndFlush(product));
        } else {
            return new Result<>("商品名称已经存在！");
        }
    }

    @Override
    public Result<Product> setProductInfo(Product product) {
        if (productRepository.findById(product.getId()).isEmpty()) {
            return new Result<>("商品不存在！");
        } else {
            return new Result<>(productRepository.saveAndFlush(product));
        }
    }

    @Override
    public Result<Product> deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return new Result<>("商品不存在！");
        } else {
            productRepository.deleteById(id);
            return new Result<>(product.get());
        }
    }
}
