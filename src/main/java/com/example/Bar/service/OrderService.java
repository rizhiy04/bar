package com.example.Bar.service;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.menuItem.MenuItemDTO;
import com.example.Bar.dto.order.*;
import com.example.Bar.entity.OrderChoiceEntity;
import com.example.Bar.entity.OrderEntity;
import com.example.Bar.entity.UserDiscountCardEntity;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.repository.MenuItemRepository;
import com.example.Bar.repository.OrderRepository;
import com.example.Bar.repository.UserDiscountCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {

    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final UserDiscountCardRepository userDiscountCardRepository;

    public List<OrderDTO> getOrders(){
        return createOrderDTO();
    }

    public TextResponse makeOrder(final MakeNewOrderRequestDTO makeNewOrderRequestDTO) throws NoSuchElementException {
        orderRepository.save(makeOrderEntity(makeNewOrderRequestDTO));

        return new TextResponse("Заказ оформлен");
    }

    public TextResponse getRevenueByTime(final RevenueRequest revenueRequest){
        final double price = orderRepository.findAllByTimeCloseAfter(revenueRequest.getDate()).stream()
                .map(orderEntity -> calculateRevenue(orderEntity.getOrderChoiceEntities()))
                .reduce(0D, (total, x) -> total + x);

        return new TextResponse("Выручка: " + price + "р.");
    }

    public TextResponse closeOrder(final CloseOrderRequestDTO closeOrderRequestDTO) throws NoSuchElementException{
        final OrderEntity orderEntity = findOrderToClose(closeOrderRequestDTO);
        double orderPrice = calculatePrice(orderEntity);

        if (closeOrderRequestDTO.getClientId() != null){
            orderPrice = applyUserDiscountCard(closeOrderRequestDTO.getClientId(), orderPrice);
        }

        orderEntity.setTimeClose(LocalDateTime.now());
        orderRepository.save(orderEntity);

        return new TextResponse("Заказ закрыт, к оплате " + orderPrice + "р");
    }

    private List<OrderDTO> createOrderDTO(){
        final List<OrderEntity> allOrders = orderRepository.findAll();

        return allOrders.stream().map(orderEntity -> {
            final OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(orderEntity.getId());
            orderDTO.setTableNumber(orderEntity.getTableNumber());
            orderDTO.setTimeClose(orderEntity.getTimeClose());
            orderDTO.setTimeOpen(orderEntity.getTimeOpen());
            orderDTO.setOrder(createOrderChoiceEntities(orderEntity.getOrderChoiceEntities()));
            return orderDTO;
        }).collect(Collectors.toList());
    }

    private List<OrderChoiceDTO> createOrderChoiceEntities(final List<OrderChoiceEntity> orderChoiceEntities){
        return orderChoiceEntities.stream().map(orderChoiceEntity -> {
            final OrderChoiceDTO orderChoiceDTO = new OrderChoiceDTO();
            orderChoiceDTO.setMenuItem(new MenuItemDTO(orderChoiceEntity.getMenuItemEntity().getId(), orderChoiceEntity.getMenuItemEntity().getName(), orderChoiceEntity.getMenuItemEntity().getCategory(), orderChoiceEntity.getMenuItemEntity().getDescription(), orderChoiceEntity.getMenuItemEntity().getPrice()));
            orderChoiceDTO.setCount(orderChoiceEntity.getCount());
            return orderChoiceDTO;
        }).collect(Collectors.toList());

    }

    private double calculateRevenue(final List<OrderChoiceEntity> orderChoiceEntities){
        return orderChoiceEntities.stream()
                .map(orderChoiceEntity -> orderChoiceEntity.getCount() * orderChoiceEntity.getMenuItemEntity().getPrice())
                .reduce(0D, (total, x) -> total + x);
    }

    private OrderEntity makeOrderEntity(final MakeNewOrderRequestDTO makeNewOrderRequestDTO) throws NoSuchElementException{
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTableNumber(makeNewOrderRequestDTO.getTableNumber());
        orderEntity.setTimeOpen(LocalDateTime.now());

        for (final Order order : makeNewOrderRequestDTO.getOrder()){
            final OrderChoiceEntity orderChoiceEntity = new OrderChoiceEntity();
            orderChoiceEntity.setMenuItemEntity(menuItemRepository.findById(order.getId())
                    .orElseThrow(() -> new NoSuchElementException("Such menuItem doesn't exist")));
            orderChoiceEntity.setCount(order.getCount());
            orderEntity.getOrderChoiceEntities().add(orderChoiceEntity);
        }

        return orderEntity;
    }

    private OrderEntity findOrderToClose(final CloseOrderRequestDTO closeOrderRequestDTO) throws NoSuchElementException{
        return orderRepository.findByTableNumberAndTimeCloseIsNull(closeOrderRequestDTO.getTableNumber())
                .orElseThrow(() -> new NoSuchElementException("Such orderEntity doesn't exist"));
    }

    private double calculatePrice(final OrderEntity orderEntity){
        return orderEntity.getOrderChoiceEntities().stream()
                .map(orderChoiceEntity -> orderChoiceEntity.getCount() * orderChoiceEntity.getMenuItemEntity().getPrice())
                .reduce(0D, (total, x) -> total + x);
    }

    private double applyUserDiscountCard(final Integer clientId, double price) throws NoSuchElementException{
        final UserDiscountCardEntity userDiscountCardEntity = userDiscountCardRepository.findById(clientId)
                .orElseThrow(() -> new NoSuchElementException("Such discountCard doesn't exist"));

        price -= price * userDiscountCardEntity.getClientDiscount();

        userDiscountCardEntity.setAllSpentMoney(userDiscountCardEntity.getAllSpentMoney() + price);
        calculateDiscount(userDiscountCardEntity);
        userDiscountCardRepository.save(userDiscountCardEntity);

        return price;
    }

    //TODO discount system
    private void calculateDiscount(final UserDiscountCardEntity userDiscountCardEntity){
    }

}
