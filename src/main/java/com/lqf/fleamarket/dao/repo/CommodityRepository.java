package com.lqf.fleamarket.dao.repo;

import com.lqf.fleamarket.dao.entity.CommodityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CommodityRepository extends JpaRepository<CommodityEntity, Long> {
    List<CommodityEntity> findByNameLikeAndStatusNot(String name, int status);
    List<CommodityEntity> findAllByOwnerId(long ownerId);
    List<CommodityEntity> findAllByStatus(int status);
    List<CommodityEntity> findByStatusAndIsselling(int status, int isselling);
    @Query(value = "SELECT * FROM tbl_commodity t WHERE t.status = :status AND t.id > :id LIMIT :limit", nativeQuery = true)
    List<CommodityEntity> findAllByStatusLimit(@Param("status")int status, @Param("id") long id, @Param("limit") int limit);
}
