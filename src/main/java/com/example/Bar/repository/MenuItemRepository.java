package com.example.Bar.repository;

import com.example.Bar.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Integer> {

    List<MenuItemEntity> findAllByCategoryAndExistIsTrue(String category);
    List<MenuItemEntity> findAllByExistIsTrueOrderByCategory();
    Optional<MenuItemEntity> findByIdAndExistIsTrue(Integer id);
}
