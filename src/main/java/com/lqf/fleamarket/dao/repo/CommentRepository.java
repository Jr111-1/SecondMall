package com.lqf.fleamarket.dao.repo;

import com.lqf.fleamarket.dao.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByCommodityId(long id);
}
