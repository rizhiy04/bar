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

    public TextResponse makeNewOrder(final MakeNewOrderRequestDTO makeNewOrderRequestDTO) throws NoSuchElementException {
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTableNumber(makeNewOrderRequestDTO.getTableNumber());
        orderEntity.setTimeOpen(LocalDateTime.now());

        for (final Order order : makeNewOrderRequestDTO.getOrder()){
            final OrderChoiceEntity orderChoiceEntity = new OrderChoiceEntity();
            orderChoiceEntity.setMenuItemEntity(menuItemRepository.findById(order.getId()).orElseThrow(() -> new NoSuchElementException("Such menuItem doesn't exist")));
            orderChoiceEntity.setCount(order.getCount());
            orderEntity.getOrderChoiceEntities().add(orderChoiceEntity);
        }

        orderRepository.save(orderEntity);

        return new TextResponse("Заказ оформлен");
    }

    //TODO discount system
    public TextResponse closeOrder(final CloseOrderRequestDTO closeOrderRequestDTO) throws NoSuchElementException{

        final OrderEntity orderEntity = orderRepository.findByTableNumberAndTimeCloseIsNull(closeOrderRequestDTO.getTableNumber())
                .orElseThrow(() -> new NoSuchElementException("Such orderEntity doesn't exist"));

        double totalPrice = 0D;
        for (final OrderChoiceEntity orderChoiceEntity : orderEntity.getOrderChoiceEntities()) {
            totalPrice += orderChoiceEntity.getCount() * orderChoiceEntity.getMenuItemEntity().getPrice();
        }

        if (closeOrderRequestDTO.getClientId() != null){
            final UserDiscountCardEntity userDiscountCardEntity = userDiscountCardRepository.findById(closeOrderRequestDTO.getClientId())
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
