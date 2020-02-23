package com.example.Bar.converter;

import com.example.Bar.dto.order.MakeNewOrderRequestDTO;
import com.example.Bar.dto.order.Order;
import com.example.Bar.dto.order.OrderChoiceDTO;
import com.example.Bar.dto.order.OrderDTO;
import com.example.Bar.entity.MenuItemEntity;
import com.example.Bar.entity.OrderChoiceEntity;
import com.example.Bar.entity.OrderEntity;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderConverter {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemConverter menuItemConverter;

    public OrderDTO convertToDTO(OrderEntity orderEntity){
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderEntity.getId());
        orderDTO.setTableNumber(orderEntity.getTableNumber());
        orderDTO.setTimeClose(orderEntity.getTimeClose());
        orderDTO.setTimeOpen(orderEntity.getTimeOpen());
        orderDTO.setOrder(getOrderChoiceDTOs(orderEntity.getOrderChoiceEntities()));

        return orderDTO;
    }

    public OrderEntity convertToEntity(MakeNewOrderRequestDTO orderDTO) throws NoSuchElementException{
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTableNumber(orderDTO.getTableNumber());
        orderEntity.setTimeOpen(LocalDateTime.now());

        for (final Order order : orderDTO.getOrder()){
            final OrderChoiceEntity orderChoiceEntity = getOrderChoiceEntity(order);
            orderEntity.getOrderChoiceEntities().add(orderChoiceEntity);
        }
        orderEntity.getOrderChoiceEntities().forEach(orderChoiceEntity -> orderChoiceEntity.setOrderEntity(orderEntity));

        return orderEntity;
    }

    private List<OrderChoiceDTO> getOrderChoiceDTOs(final List<OrderChoiceEntity> orderChoiceEntities){
        return orderChoiceEntities.stream().map(this::getOrderChoiceDTO).collect(Collectors.toList());

    }

    private OrderChoiceDTO getOrderChoiceDTO(OrderChoiceEntity orderChoiceEntity) {
        final OrderChoiceDTO orderChoiceDTO = new OrderChoiceDTO();
        final MenuItemEntity menuItemEntity = orderChoiceEntity.getMenuItemEntity();

        orderChoiceDTO.setMenuItem(menuItemConverter.convertToDTO(menuItemEntity));
        orderChoiceDTO.setCount(orderChoiceEntity.getAmount());
        return orderChoiceDTO;
    }

    private OrderChoiceEntity getOrderChoiceEntity(Order order) throws NoSuchElementException {
        final OrderChoiceEntity orderChoiceEntity = new OrderChoiceEntity();
        orderChoiceEntity.setMenuItemEntity(menuItemRepository.findById(order.getId())
                .orElseThrow(() -> new NoSuchElementException("Such menuItem doesn't exist")));
        orderChoiceEntity.setAmount(order.getCount());
        return orderChoiceEntity;
    }
}
