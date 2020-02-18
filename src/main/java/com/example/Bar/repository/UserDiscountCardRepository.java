package com.example.Bar.repository;

import com.example.Bar.entity.UserDiscountCardEntity;
import com.example.Bar.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDiscountCardRepository extends JpaRepository<UserDiscountCardEntity, Integer> {

    Optional<UserDiscountCardEntity> findByUserEntity(UserEntity userEntity);
}
