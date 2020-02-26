package com.example.Bar.service;

import com.example.Bar.converter.MenuItemConverter;
import com.example.Bar.dto.menuItem.AddNewMenuItemRequestDTO;
import com.example.Bar.dto.menuItem.MenuItemDTO;
import com.example.Bar.entity.MenuItemEntity;
import com.example.Bar.exception.BarNoSuchElementException;
import com.example.Bar.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemConverter menuItemConverter;

    @Cacheable("menu")
    public List<MenuItemDTO> getMenu() {
        return menuItemRepository.findAll().stream().map(menuItemConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MenuItemDTO> getMenuByCategory(final String category) {
        return menuItemRepository.findAllByCategory(category).stream().map(menuItemConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "menu", allEntries = true)
    public void addMenuItem(final AddNewMenuItemRequestDTO menuItemDTO) {
        menuItemRepository.save(menuItemConverter.convertToEntity(menuItemDTO));
    }

    @CacheEvict(value = "menu", allEntries = true)
    public void deleteMenuItem(final Integer productId) throws BarNoSuchElementException {
        final MenuItemEntity menuItemEntity = menuItemRepository.findById(productId)
                .orElseThrow(() -> new BarNoSuchElementException("Such menuItem doesn't exist"));

        menuItemRepository.delete(menuItemEntity);
    }

}
