package com.example.Bar.service;

import com.example.Bar.converter.OrderConverter;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.order.*;
import com.example.Bar.entity.OrderChoiceEntity;
import com.example.Bar.entity.OrderEntity;
import com.example.Bar.entity.UserDiscountCardEntity;
import com.example.Bar.enumeration.Currency;
import com.example.Bar.exception.BarNoSuchElementException;
import com.example.Bar.repository.OrderRepository;
import com.example.Bar.repository.UserDiscountCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserDiscountCardRepository userDiscountCardRepository;
    private final OrderConverter orderConverter;

    public List<OrderDTO> getOrders(){
        return orderRepository.findAll().stream().map(orderConverter::convertToDTO).collect(Collectors.toList());
    }

    public void makeOrder(final MakeNewOrderRequestDTO makeNewOrderRequestDTO) throws BarNoSuchElementException {
        orderRepository.save(orderConverter.convertToEntity(makeNewOrderRequestDTO));
    }

    public MoneyResponse getRevenueByTime(final RevenueRequest revenueRequest){
        final BigDecimal price = orderRepository.findAllByTimeCloseAfter(revenueRequest.getDate()).stream()
                .map(orderEntity -> calculateRevenue(orderEntity.getOrderChoiceEntities()))
                .reduce(new BigDecimal(0), BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        return new MoneyResponse(price.toString(), Currency.BYN.name());
    }

    public MoneyResponse closeOrder(final CloseOrderRequestDTO closeOrderRequestDTO) throws BarNoSuchElementException {
        final OrderEntity orderEntity = findOrderToClose(closeOrderRequestDTO);
        BigDecimal orderPrice = calculatePrice(orderEntity)
                .setScale(2, RoundingMode.HALF_UP);

        if (closeOrderRequestDTO.getClientId() != null){
            orderPrice = applyUserDiscountCard(closeOrderRequestDTO.getClientId(), orderPrice);
        }

        orderEntity.setTimeClose(LocalDateTime.now());
        orderRepository.save(orderEntity);

        return new MoneyResponse(orderPrice.toString(), Currency.BYN.name());
    }

    private BigDecimal calculateRevenue(final List<OrderChoiceEntity> orderChoiceEntities){
        return orderChoiceEntities.stream()
                .map(orderChoiceEntity ->  {
                    final BigDecimal menuItemPrice = orderChoiceEntity.getMenuItemEntity().getPrice();
                    final BigDecimal menuItemCount = BigDecimal.valueOf(orderChoiceEntity.getAmount());
                    return menuItemPrice.multiply(menuItemCount);
                })
                .reduce(new BigDecimal(0), BigDecimal::add);
    }

    private OrderEntity findOrderToClose(final CloseOrderRequestDTO closeOrderRequestDTO) throws BarNoSuchElementException {
        return orderRepository.findByTableNumberAndTimeCloseIsNull(closeOrderRequestDTO.getTableNumber())
                .orElseThrow(() -> new BarNoSuchElementException("Such orderEntity doesn't exist"));
    }

    private BigDecimal calculatePrice(final OrderEntity orderEntity){
        return orderEntity.getOrderChoiceEntities().stream()
                .map(orderChoiceEntity -> {
                    final BigDecimal menuItemPrice = orderChoiceEntity.getMenuItemEntity().getPrice();
                    final BigDecimal menuItemCount = BigDecimal.valueOf(orderChoiceEntity.getAmount());
                    return menuItemPrice.multiply(menuItemCount);
                })
                .reduce(new BigDecimal(0), BigDecimal::add);
    }

    private BigDecimal applyUserDiscountCard(final Integer clientId, BigDecimal price) throws BarNoSuchElementException {
        final UserDiscountCardEntity userDiscountCardEntity = userDiscountCardRepository.findById(clientId)
                .orElseThrow(() -> new BarNoSuchElementException("Such discountCard doesn't exist"));

        final BigDecimal discount = BigDecimal.valueOf(userDiscountCardEntity.getClientDiscount());
        price = price.subtract(price.multiply(discount))
                .setScale(2, RoundingMode.HALF_UP);

        userDiscountCardEntity.setAllSpentMoney(userDiscountCardEntity.getAllSpentMoney().add(price));
        applyDiscount(userDiscountCardEntity);
        userDiscountCardRepository.save(userDiscountCardEntity);

        return price;
    }

    private void applyDiscount(final UserDiscountCardEntity userDiscountCardEntity){
        final BigDecimal money = userDiscountCardEntity.getAllSpentMoney();

        if (money.compareTo(BigDecimal.valueOf(2000)) > 0)
            userDiscountCardEntity.setClientDiscount(0.20D);
        else
            userDiscountCardEntity.setClientDiscount(500D % money.doubleValue() * 0.05D);
    }

}
