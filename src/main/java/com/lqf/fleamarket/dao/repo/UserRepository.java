package com.lqf.fleamarket.dao.repo;

import com.lqf.fleamarket.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmailAndPassword(@Param("email") String email,@Param("password") String password);
    UserEntity findByEmail(@Param("email") String email);
    UserEntity findAllById(long id);
    List<UserEntity> findAllByRole(int role);
    List<UserEntity> findAllByIsLicense(int license);
}
