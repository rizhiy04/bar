package com.example.Bar.service;

import com.example.Bar.converter.MenuItemConverter;
import com.example.Bar.dto.TextResponse;
import com.example.Bar.dto.menuItem.AddNewMenuItemRequestDTO;
import com.example.Bar.dto.menuItem.MenuItemDTO;
import com.example.Bar.entity.MenuItemEntity;
import com.example.Bar.exception.NoSuchElementException;
import com.example.Bar.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemConverter menuItemConverter;

    public List<MenuItemDTO> getMenu() {
        return menuItemRepository.findAll().stream().map(menuItemConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MenuItemDTO> getMenuByCategory(final String category) {
        return menuItemRepository.findAllByCategory(category).stream().map(menuItemConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public TextResponse addMenuItem(final AddNewMenuItemRequestDTO menuItemDTO) {
        menuItemRepository.save(menuItemConverter.convertToEntity(menuItemDTO));

        return new TextResponse("Позиция добавлена");
    }

    public TextResponse deleteMenuItem(final Integer productId) throws NoSuchElementException {
        final MenuItemEntity menuItemEntity = menuItemRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Such menuItem doesn't exist"));

        menuItemRepository.delete(menuItemEntity);

        return new TextResponse("Позиция удалена");
    }

}
