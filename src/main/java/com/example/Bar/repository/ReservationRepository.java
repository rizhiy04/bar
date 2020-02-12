package com.example.Bar.repository;

import com.example.Bar.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findAllByTimeAfterOrderById(LocalDateTime time);

    List<Reservation> findAllByTimeAfterAndTimeBefore(LocalDateTime after, LocalDateTime before);
}
