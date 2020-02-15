package com.example.Bar.service;

import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.order.CloseOrderRequestDTO;
import com.example.Bar.dto.order.MakeNewOrderRequestDTO;
import com.example.Bar.dto.order.Order;
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

@AllArgsConstructor
@Service
public class OrderService {

    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final UserDiscountCardRepository userDiscountCardRepository;

    public TextResponse makeOrder(final MakeNewOrderRequestDTO makeNewOrderRequestDTO) throws NoSuchElementException {
        final OrderEntity orderEntity = new OrderEntity();
        makeOrderEntity(orderEntity, makeNewOrderRequestDTO);

        orderRepository.save(orderEntity);

        return new TextResponse("Заказ оформлен");
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

    private void makeOrderEntity(final OrderEntity orderEntity, final MakeNewOrderRequestDTO makeNewOrderRequestDTO) throws NoSuchElementException{
        orderEntity.setTableNumber(makeNewOrderRequestDTO.getTableNumber());
        orderEntity.setTimeOpen(LocalDateTime.now());

        for (final Order order : makeNewOrderRequestDTO.getOrder()){
            final OrderChoiceEntity orderChoiceEntity = new OrderChoiceEntity();
            orderChoiceEntity.setMenuItemEntity(menuItemRepository.findById(order.getId()).orElseThrow(() -> new NoSuchElementException("Such menuItem doesn't exist")));
            orderChoiceEntity.setCount(order.getCount());
            orderEntity.getOrderChoiceEntities().add(orderChoiceEntity);
        }
    }

    private OrderEntity findOrderToClose(final CloseOrderRequestDTO closeOrderRequestDTO) throws NoSuchElementException{
        return orderRepository.findByTableNumberAndTimeCloseIsNull(closeOrderRequestDTO.getTableNumber())
                .orElseThrow(() -> new NoSuchElementException("Such orderEntity doesn't exist"));
    }

    private double calculatePrice(final OrderEntity orderEntity){
        double price = 0D;

        for (final OrderChoiceEntity orderChoiceEntity : orderEntity.getOrderChoiceEntities()) {
            price += orderChoiceEntity.getCount() * orderChoiceEntity.getMenuItemEntity().getPrice();
        }

        return price;
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
