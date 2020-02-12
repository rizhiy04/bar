package com.example.Bar.repository;

import com.example.Bar.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {

    List<ReservationEntity> findAllByTimeAfterOrderById(LocalDateTime time);

    List<ReservationEntity> findAllByTimeAfterAndTimeBefore(LocalDateTime after, LocalDateTime before);
}
