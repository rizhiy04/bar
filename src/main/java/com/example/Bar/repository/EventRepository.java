package com.example.Bar.repository;

import com.example.Bar.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Integer> {

    List<EventEntity> findAllByTimeAfterOrderByIdAsc(LocalDateTime time);
}
