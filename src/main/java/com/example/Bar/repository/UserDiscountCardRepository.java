package com.example.Bar.repository;

import com.example.Bar.entity.UserDiscountCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDiscountCardRepository extends JpaRepository<UserDiscountCardEntity, Integer> {
}
