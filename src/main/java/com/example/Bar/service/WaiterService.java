package com.example.Bar.service;

import com.example.Bar.dto.order.CloseOrderRequestDTO;
import com.example.Bar.dto.order.MakeNewOrderRequestDTO;
import com.example.Bar.dto.order.Order;
import com.example.Bar.dto.reservation.FreeTablesDTO;
import com.example.Bar.dto.reservation.ReservationDTO;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.entity.*;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WaiterService {

    private final ReservationRepository reservationRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserDiscountCardRepository userDiscountCardRepository;

    public List<ReservationDTO> getReservation(){
        LocalDateTime now = LocalDateTime.now();
        return reservationRepository.findAllByTimeAfterOrderById(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute())).stream().map(
                reservation -> new ReservationDTO(reservation.getId(), reservation.getName(), reservation.getTableNumber(), reservation.getTime()))
                .collect(Collectors.toList());
    }

    public FreeTablesDTO getFreeTable(final Integer hours) throws NoSuchElementException{
        LocalDateTime now = LocalDateTime.now();
        InventoryEntity tables = inventoryRepository.findByCategory("table").orElseThrow(() -> new NoSuchElementException("Tables don't exist"));
        List<OrderEntity> unclosedOrders = orderRepository.findAllByTimeCloseIsNull();
        List<ReservationEntity> reservation = reservationRepository.findAllByTimeAfterAndTimeBefore(LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute()), now.plusHours(hours));

        Set<Integer> reservedTable = new HashSet<>();
        unclosedOrders.forEach(orderEntity -> reservedTable.add(orderEntity.getTableNumber()));
        reservation.forEach(reservationEntity -> reservedTable.add(reservationEntity.getTableNumber()));

        FreeTablesDTO freeTablesDTO = new FreeTablesDTO(new ArrayList<>());

        for (int i = 1; i <= tables.getCount(); i++){
            if (!reservedTable.contains(i)){
                freeTablesDTO.getTableNumbers().add(i);
            }
        }

        return freeTablesDTO;
    }

    public TextResponse makeNewOrder(final MakeNewOrderRequestDTO makeNewOrderRequestDTO) throws NoSuchElementException{
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTableNumber(makeNewOrderRequestDTO.getTableNumber());
        orderEntity.setTimeOpen(LocalDateTime.now());

        for (Order order : makeNewOrderRequestDTO.getOrder()){
            OrderChoiceEntity orderChoiceEntity = new OrderChoiceEntity();
            orderChoiceEntity.setMenuItemEntity(menuItemRepository.findById(order.getId()).orElseThrow(() -> new NoSuchElementException("Such menuItem doesn't exist")));
            orderChoiceEntity.setCount(order.getCount());
            orderEntity.getOrderChoiceEntities().add(orderChoiceEntity);
        }

        orderRepository.save(orderEntity);

        return new TextResponse("Заказ оформлен");
    }

    //TODO discount system
    public TextResponse closeOrder(final CloseOrderRequestDTO closeOrderRequestDTO) throws NoSuchElementException{

        OrderEntity orderEntity = orderRepository.findByTableNumberAndTimeCloseIsNull(closeOrderRequestDTO.getTableNumber())
                .orElseThrow(() -> new NoSuchElementException("Such orderEntity doesn't exist"));

        double totalPrice = 0D;
        for (OrderChoiceEntity orderChoiceEntity : orderEntity.getOrderChoiceEntities()) {
            totalPrice += orderChoiceEntity.getCount() * orderChoiceEntity.getMenuItemEntity().getPrice();
        }

        if (closeOrderRequestDTO.getClientId() != null){
            UserDiscountCardEntity userDiscountCardEntity = userDiscountCardRepository.findById(closeOrderRequestDTO.getClientId())
                    .orElseThrow(() -> new NoSuchElementException("Such discountCard doesn't exist"));

            totalPrice -= totalPrice * userDiscountCardEntity.getClientDiscount();

            userDiscountCardEntity.setAllSpentMoney(userDiscountCardEntity.getAllSpentMoney() + totalPrice);
            userDiscountCardRepository.save(userDiscountCardEntity);
        }

        orderEntity.setTimeClose(LocalDateTime.now());
        orderRepository.save(orderEntity);

        return new TextResponse("Заказ закрыт, к оплате " + totalPrice + "р");
    }

}
