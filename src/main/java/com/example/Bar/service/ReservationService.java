package com.example.Bar.service;

import com.example.Bar.converter.ReservationConverter;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.reservation.FreeTablesDTO;
import com.example.Bar.dto.reservation.ReservationDTO;
import com.example.Bar.dto.reservation.ReservationRequestDTO;
import com.example.Bar.entity.OrderEntity;
import com.example.Bar.entity.ReservationEntity;
import com.example.Bar.exception.BarNoSuchElementException;
import com.example.Bar.repository.InventoryRepository;
import com.example.Bar.repository.OrderRepository;
import com.example.Bar.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;
    private final ReservationConverter reservationConverter;

    public List<ReservationDTO> getReservations(){
        return reservationRepository.findAllByTimeAfterOrderById(LocalDateTime.now()).stream()
                .map(reservationConverter::convertToDTO).collect(Collectors.toList());
    }

    public FreeTablesDTO getFreeTables(final Integer hours) throws BarNoSuchElementException {
        final List<OrderEntity> unclosedOrders = orderRepository.findAllByTimeCloseIsNull();
        final List<ReservationEntity> nearestReservation = getNearestReservation(hours);
        final Set<Integer> unfreeTables = getUnfreeTables(unclosedOrders, nearestReservation);

        return getFreeTables(unfreeTables, getTablesCount());
    }

    public TextResponse reserveTable(final ReservationRequestDTO reserve) throws BarNoSuchElementException {
        final Integer tablesCount = getTablesCount();
        final List<ReservationEntity> reservedTables = getReservationsByTime(reserve.getTime());

        if (reservedTables.size() == tablesCount)
            return new TextResponse("Свободных мест на это время нет");

        final ReservationEntity reservation = getReservation(reservedTables, tablesCount, reserve);
        reservationRepository.save(reservation);

        return new TextResponse("№" + reservation.getTableNumber());
    }

    private Integer getTablesCount() throws BarNoSuchElementException {
        return inventoryRepository.findByCategory("table")
                .orElseThrow(() -> new BarNoSuchElementException("Tables don't exist")).getAmount();
    }

    private List<ReservationEntity> getNearestReservation(final Integer hours){
        final LocalDateTime now = LocalDateTime.now();
        return reservationRepository.findAllByTimeAfterAndTimeBefore(now, now.plusHours(hours));
    }

    private Set<Integer> getUnfreeTables(final List<OrderEntity> unclosedOrders,
                                         final List<ReservationEntity> reservation){
        final Set<Integer> unfreeTables = new HashSet<>();
        unclosedOrders.forEach(orderEntity -> unfreeTables.add(orderEntity.getTableNumber()));
        reservation.forEach(reservationEntity -> unfreeTables.add(reservationEntity.getTableNumber()));

        return unfreeTables;
    }

    private FreeTablesDTO getFreeTables(final Set<Integer> unfreeTables, final Integer tablesCount){
        final FreeTablesDTO freeTablesDTO = new FreeTablesDTO(new ArrayList<>());

        IntStream.rangeClosed(1, tablesCount).filter(i -> !unfreeTables.contains(i))
                .forEach(freeTablesDTO.getTableNumbers()::add);

        return freeTablesDTO;
    }

    private List<ReservationEntity> getReservationsByTime(final LocalDateTime time){
        return reservationRepository.findAllByTimeAfterAndTimeBefore(time.minusHours(3), time.plusHours(3));
    }

    private ReservationEntity getReservation(final List<ReservationEntity> reservedTables, final Integer tablesCount,
                                             final ReservationRequestDTO reservationRequestDTO){
        final ReservationEntity reservation = new ReservationEntity();
        reservation.setTableNumber(findFreeTable(reservedTables, tablesCount));
        reservation.setName(reservationRequestDTO.getName());
        reservation.setTime(reservationRequestDTO.getTime());

        return reservation;
    }

    private Integer findFreeTable(final List<ReservationEntity> reservedTables, final Integer tablesCount){
        final List<Integer> reservedTable = new ArrayList<>();
        reservedTables.forEach(reservationEntity -> reservedTable.add(reservationEntity.getTableNumber()));

        return IntStream.rangeClosed(1, tablesCount).filter(i -> !reservedTable.contains(i))
                .limit(1).findFirst().orElse(0);

    }
}
