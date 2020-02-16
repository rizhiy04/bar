package com.example.Bar.service;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.reservation.FreeTablesDTO;
import com.example.Bar.dto.reservation.ReservationDTO;
import com.example.Bar.dto.reservation.ReservationRequestDTO;
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

    public List<ReservationDTO> getReservations(){
        return reservationRepository.findAllByTimeAfterOrderById(LocalDateTime.now()).stream().map(
                reservation -> new ReservationDTO(reservation.getId(), reservation.getName(), reservation.getTableNumber(), reservation.getTime()))
                .collect(Collectors.toList());
    }

    public FreeTablesDTO getFreeTables(final Integer hours) throws NoSuchElementException{
        final List<OrderEntity> unclosedOrders = getUnclosedOrders();
        final List<ReservationEntity> reservation = getNearestReservation(hours);
        final Set<Integer> unfreeTables = getUnfreeTables(unclosedOrders, reservation);

        return getFreeTables(unfreeTables, getTablesCount());
    }

    public TextResponse reserveTable(final ReservationRequestDTO reserve) throws NoSuchElementException {
        final Integer tablesCount = getTablesCount();
        final List<ReservationEntity> reservedTables = getReservationsByTime(reserve.getTime());

        if (reservedTables.size() == tablesCount)
            return new TextResponse("Свободных мест на это время нет");

        final ReservationEntity reservation = makeReservation(reservedTables, tablesCount, reserve);
        reservationRepository.save(reservation);

        return new TextResponse("Ваш столик №" + reservation.getTableNumber());
    }

    private Integer getTablesCount() throws NoSuchElementException{
        return inventoryRepository.findByCategory("table").orElseThrow(() -> new NoSuchElementException("Tables don't exist")).getCount();
    }

    private List<OrderEntity> getUnclosedOrders(){
        return orderRepository.findAllByTimeCloseIsNull();
    }

    private List<ReservationEntity> getNearestReservation(final Integer hours){
        final LocalDateTime now = LocalDateTime.now();
        return reservationRepository.findAllByTimeAfterAndTimeBefore(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute()), now.plusHours(hours));
    }

    private Set<Integer> getUnfreeTables(final List<OrderEntity> unclosedOrders, final List<ReservationEntity> reservation){
        final Set<Integer> unfreeTables = new HashSet<>();
        unclosedOrders.forEach(orderEntity -> unfreeTables.add(orderEntity.getTableNumber()));
        reservation.forEach(reservationEntity -> unfreeTables.add(reservationEntity.getTableNumber()));

        return unfreeTables;
    }

    private FreeTablesDTO getFreeTables(final Set<Integer> unfreeTables, final Integer tablesCount){
        final FreeTablesDTO freeTablesDTO = new FreeTablesDTO(new ArrayList<>());

        for (int i = 1; i <= tablesCount; i++){
            if (!unfreeTables.contains(i)){
                freeTablesDTO.getTableNumbers().add(i);
            }
        }

        return freeTablesDTO;
    }

    private List<ReservationEntity> getReservationsByTime(final LocalDateTime time){
        return reservationRepository.findAllByTimeAfterAndTimeBefore(time.minusHours(3), time.plusHours(3));
    }

    private ReservationEntity makeReservation(final List<ReservationEntity> reservedTables, final Integer tablesCount, final ReservationRequestDTO reservationRequestDTO){
        final ReservationEntity reservation = new ReservationEntity();
        reservation.setTableNumber(findFreeTable(reservedTables, tablesCount));
        reservation.setName(reservationRequestDTO.getName());
        reservation.setTime(reservationRequestDTO.getTime());

        return reservation;
    }

    private Integer findFreeTable(final List<ReservationEntity> reservedTables, final Integer tablesCount){
        final List<Integer> reservedTable = new ArrayList<>();
        reservedTables.forEach(reservationEntity -> reservedTable.add(reservationEntity.getTableNumber()));

        for (int i = 1; i<= tablesCount; i++){
            if (!reservedTable.contains(i)){
                return i;
            }
        }

        return null;
    }
}
