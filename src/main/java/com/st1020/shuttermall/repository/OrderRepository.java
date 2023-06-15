package com.st1020.shuttermall.repository;

import com.st1020.shuttermall.domain.Order;
import com.st1020.shuttermall.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.user.id = ?1")
    List<Order> findAllByUserId(Long userId);

    @Query("select o from Order o where o.user.id = ?1 and o.orderStatus = ?2")
    List<Order> findAllByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    List<Order> findAllByProductShopInfoId(Long shopId);
}
