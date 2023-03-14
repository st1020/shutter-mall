package com.st1020.shuttermall.repository;

import com.st1020.shuttermall.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    List<Product> findAllByName(String name);
}
