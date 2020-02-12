package com.example.Bar.repository;

import com.example.Bar.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findAllByTimeAfterOrderByIdAsc(LocalDateTime time);
}
