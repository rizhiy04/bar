package com.example.Bar.service;

import com.example.Bar.converter.MenuItemConverter;
import com.example.Bar.converter.OrderConverter;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.menuItem.MenuItemDTO;
import com.example.Bar.dto.order.*;
import com.example.Bar.entity.MenuItemEntity;
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

    private final OrderRepository orderRepository;
    private final UserDiscountCardRepository userDiscountCardRepository;
    private final OrderConverter orderConverter;

    public List<OrderDTO> getOrders(){
        return orderRepository.findAll().stream().map(orderConverter::convertToDTO).collect(Collectors.toList());
    }

    public TextResponse makeOrder(final MakeNewOrderRequestDTO makeNewOrderRequestDTO) throws NoSuchElementException {
        orderRepository.save(orderConverter.convertToEntity(makeNewOrderRequestDTO));

        return new TextResponse("Заказ оформлен");
    }

    public TextResponse getRevenueByTime(final RevenueRequest revenueRequest){
        final double price = orderRepository.findAllByTimeCloseAfter(revenueRequest.getDate()).stream()
                .map(orderEntity -> calculateRevenue(orderEntity.getOrderChoiceEntities()))
                .reduce(0D, Double::sum);

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

    private double calculateRevenue(final List<OrderChoiceEntity> orderChoiceEntities){
        return orderChoiceEntities.stream()
                .map(orderChoiceEntity -> orderChoiceEntity.getAmount() * orderChoiceEntity.getMenuItemEntity().getPrice())
                .reduce(0D, Double::sum);
    }

    private OrderEntity findOrderToClose(final CloseOrderRequestDTO closeOrderRequestDTO) throws NoSuchElementException{
        return orderRepository.findByTableNumberAndTimeCloseIsNull(closeOrderRequestDTO.getTableNumber())
                .orElseThrow(() -> new NoSuchElementException("Such orderEntity doesn't exist"));
    }

    private double calculatePrice(final OrderEntity orderEntity){
        return orderEntity.getOrderChoiceEntities().stream()
                .map(orderChoiceEntity -> orderChoiceEntity.getAmount() * orderChoiceEntity.getMenuItemEntity().getPrice())
                .reduce(0D, Double::sum);
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
