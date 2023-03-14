package com.st1020.shuttermall.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Cart {
    @EmbeddedId
    private CartKey id;
    @ManyToOne
    @MapsId("userId")
    private User user;
    @ManyToOne
    @MapsId("productId")
    private Product product;
    @CreatedDate
    private Date createDate;

    public Cart() {
    }

    public Cart(User user, Product product) {
        this.id = new CartKey();
        this.id.userId = user.getId();
        this.id.productId = product.getId();
        setUser(user);
        setProduct(product);
    }

    public CartKey getId() {
        return id;
    }

    public void setId(CartKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
