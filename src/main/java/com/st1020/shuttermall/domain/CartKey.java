package com.st1020.shuttermall.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class CartKey implements Serializable {
    Long userId;
    Long productId;

    public CartKey() {
    }

    public CartKey(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
