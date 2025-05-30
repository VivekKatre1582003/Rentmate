package com.Vivek.Rentmate.Service;

import com.Vivek.Rentmate.Model.ItemImages;
import com.Vivek.Rentmate.Model.Items;
import com.Vivek.Rentmate.Model.User;
import com.Vivek.Rentmate.Repository.ItemImagesRepository;
import com.Vivek.Rentmate.Repository.ItemsRepository;
import com.Vivek.Rentmate.Repository.UserRepository;
import com.Vivek.Rentmate.dto.ItemCreateRequestDto;
import com.Vivek.Rentmate.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemsService {

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private ItemImagesRepository itemImagesRepository;

    @Autowired
    private UserRepository userRepository;

    public Items createItem(ItemCreateRequestDto itemCreateRequestDto) {
        Optional<User> optionalUser = userRepository.findById(itemCreateRequestDto.getOwnerId());
        if (optionalUser.isPresent()) {
            User owner = optionalUser.get();
            Items item = new Items();
            item.setName(itemCreateRequestDto.getName());
            item.setDescription(itemCreateRequestDto.getDescription());
            item.setPrice(itemCreateRequestDto.getPrice());
            item.setDailyRate(itemCreateRequestDto.getDailyRate());
            item.setCategory(itemCreateRequestDto.getCategory());
            item.setItemCondition(itemCreateRequestDto.getItemCondition());
            item.setLocation(itemCreateRequestDto.getLocation());
            item.setOwner(owner); // ✅ Get UUID from the User object
            Items savedItem = itemsRepository.save(item);

            List<ItemImages> images = new ArrayList<>();
            for (int i = 0; i < itemCreateRequestDto.getImageUrls().size(); i++) {
                String imageUrl = itemCreateRequestDto.getImageUrls().get(i);
                ItemImages image = new ItemImages();
                image.setItem(savedItem);
                image.setItemId(savedItem.getId());
                image.setImageUrl(imageUrl);
                image.setIsPrimary(i == 0); // First image as primary
                images.add(image);
            }
            itemImagesRepository.saveAll(images);
            return savedItem;
        } else {
            throw new RuntimeException("Owner not found with id: " + itemCreateRequestDto.getOwnerId());
        }
    }

    public Optional<Items> getItemById(UUID id) {
        return itemsRepository.findById(id);
    }

    public List<Items> getItemsByOwnerId(UUID ownerId) {
        return itemsRepository.findByOwnerId(ownerId);
    }

    public List<Items> getItemsByCategory(String category) {
        return itemsRepository.findByCategory(category);
    }

    public void deleteItem(UUID id) {
        itemsRepository.deleteById(id);
    }

    public ItemDto convertToDto(Items item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setPrice(item.getPrice());
        dto.setDailyRate(item.getDailyRate());
        dto.setCategory(item.getCategory());
        dto.setItemCondition(item.getItemCondition());
        dto.setLocation(item.getLocation());
        dto.setOwnerId(item.getOwner().getId());
        List<ItemImages> images = itemImagesRepository.findByItemId(item.getId());
        images.stream()
                .filter(ItemImages::getIsPrimary)
                .findFirst()
                .ifPresent(image -> dto.setPrimaryImageUrl(image.getImageUrl()));
        return dto;
    }
}
