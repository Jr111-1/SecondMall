package com.lqf.fleamarket.dao.repo;

import com.lqf.fleamarket.dao.entity.CarEntity;
import com.lqf.fleamarket.dao.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<CarEntity, Long> {
    List<CarEntity> findAllByBuyerId(long buyerId);
    List<CarEntity> findAllBySellerId(long sellerId);
    List<CarEntity> findAllById(long id);
}
