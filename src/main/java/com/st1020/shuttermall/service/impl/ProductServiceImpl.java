package com.st1020.shuttermall.service.impl;

import com.st1020.shuttermall.domain.Product;
import com.st1020.shuttermall.exception.BusinessException;
import com.st1020.shuttermall.repository.ProductRepository;
import com.st1020.shuttermall.service.ProductService;
import com.st1020.shuttermall.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Result<List<Product>> findAll() {
        return new Result<>(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Result<Product> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new BusinessException("无法找到商品！");
        } else {
            return new Result<>(product.get());
        }
    }

    @Override
    @Transactional
    public Result<Product> addProduct(Product product) {
        if (productRepository.findByName(product.getName()).isEmpty()) {
            return new Result<>(productRepository.saveAndFlush(product));
        } else {
            throw new BusinessException("商品名称已经存在！");
        }
    }

    @Override
    @Transactional
    public Result<Product> setProductInfo(Product product) {
        if (productRepository.findById(product.getId()).isEmpty()) {
            throw new BusinessException("商品不存在！");
        } else {
            return new Result<>(productRepository.saveAndFlush(product));
        }
    }

    @Override
    @Transactional
    public Result<Product> deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new BusinessException("商品不存在！");
        } else {
            productRepository.deleteById(id);
            return new Result<>(product.get());
        }
    }

    @Override
    @Transactional
    public Result<List<Product>> searchProduct(String name) {
        return new Result<>(productRepository.findByNameLike("%" + name + "%"));
    }
}
