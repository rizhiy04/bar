package com.example.Bar.service;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.reservation.FreeTablesDTO;
import com.example.Bar.dto.reservation.ReservationDTO;
import com.example.Bar.dto.reservation.ReservationRequestDTO;
import com.example.Bar.entity.InventoryEntity;
import com.example.Bar.entity.OrderEntity;
import com.example.Bar.entity.ReservationEntity;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.repository.InventoryRepository;
import com.example.Bar.repository.OrderRepository;
import com.example.Bar.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;

    public List<ReservationDTO> getReservation(){
        final LocalDateTime now = LocalDateTime.now();
        return reservationRepository.findAllByTimeAfterOrderById(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute())).stream().map(
                reservation -> new ReservationDTO(reservation.getId(), reservation.getName(), reservation.getTableNumber(), reservation.getTime()))
                .collect(Collectors.toList());
    }

    public FreeTablesDTO getFreeTable(final Integer hours) throws NoSuchElementException{
        final LocalDateTime now = LocalDateTime.now();
        final InventoryEntity tables = inventoryRepository.findByCategory("table").orElseThrow(() -> new NoSuchElementException("Tables don't exist"));
        final List<OrderEntity> unclosedOrders = orderRepository.findAllByTimeCloseIsNull();
        final List<ReservationEntity> reservation = reservationRepository.findAllByTimeAfterAndTimeBefore(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute()), now.plusHours(hours));

        final Set<Integer> reservedTable = new HashSet<>();
        unclosedOrders.forEach(orderEntity -> reservedTable.add(orderEntity.getTableNumber()));
        reservation.forEach(reservationEntity -> reservedTable.add(reservationEntity.getTableNumber()));

        final FreeTablesDTO freeTablesDTO = new FreeTablesDTO(new ArrayList<>());

        for (int i = 1; i <= tables.getCount(); i++){
            if (!reservedTable.contains(i)){
                freeTablesDTO.getTableNumbers().add(i);
            }
        }

        return freeTablesDTO;
    }

    public TextResponse reserveTable(final ReservationRequestDTO reserve) throws NoSuchElementException {
        final ReservationEntity reservation = new ReservationEntity();
        final InventoryEntity tables = inventoryRepository.findByCategory("table").orElseThrow(() -> new NoSuchElementException("Tables don't exist"));
        final List<ReservationEntity> reservationEntityList = reservationRepository.findAllByTimeAfterAndTimeBefore(reserve.getTime().minusHours(3), reserve.getTime().plusHours(3));

        if (reservationEntityList.size() == tables.getCount())
            return new TextResponse("Свободных мест на это время нет");

        final List<Integer> reservedTable = new ArrayList<>();
        reservationEntityList.forEach(reservationEntity -> reservedTable.add(reservationEntity.getTableNumber()));

        for (int i = 1; i<= tables.getCount(); i++){
            if (!reservedTable.contains(i)){
                reservation.setTableNumber(i);
                break;
            }
        }

        reservation.setName(reserve.getName());
        reservation.setTime(reserve.getTime());
        reservationRepository.save(reservation);

        return new TextResponse("Ваш столик №" + reservation.getTableNumber());
    }
}
