package com.st1020.shuttermall.repository;

import com.st1020.shuttermall.domain.Cart;
import com.st1020.shuttermall.domain.CartKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, CartKey> {
    @Query("select c from Cart c where c.user.id = ?1")
    List<Cart> findAllByUserId(Long userId);
}
