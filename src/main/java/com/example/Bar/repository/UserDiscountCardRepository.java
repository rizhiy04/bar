package com.example.Bar.repository;

import com.example.Bar.entity.UserDiscountCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDiscountCardRepository extends JpaRepository<UserDiscountCard, Integer> {
}
