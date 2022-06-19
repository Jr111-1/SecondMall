package com.lqf.fleamarket.dao.repo;

import com.lqf.fleamarket.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByBuyerId(long buyerId);
    List<OrderEntity> findByBuyerIdAndStatus(long buyerId, int status);
    List<OrderEntity> findAllBySellerId(long sellerId);
}
