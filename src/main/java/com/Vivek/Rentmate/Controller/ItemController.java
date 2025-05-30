package com.Vivek.Rentmate.Controller;

import com.Vivek.Rentmate.Model.ItemImages;
import com.Vivek.Rentmate.Model.Items;
import com.Vivek.Rentmate.Model.User;
import com.Vivek.Rentmate.Repository.ItemImagesRepository;
import com.Vivek.Rentmate.Repository.ItemsRepository;
import com.Vivek.Rentmate.Repository.UserRepository;
import com.Vivek.Rentmate.dto.ItemCreateRequestDto;
import com.Vivek.Rentmate.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
@CrossOrigin
public class ItemController {

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private ItemImagesRepository itemImagesRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Items> createItem(@RequestBody ItemCreateRequestDto itemDto) {
        Optional<User> ownerOpt = userRepository.findById(itemDto.getOwnerId());
        if (ownerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Items item = new Items();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());
        item.setDailyRate(itemDto.getDailyRate());
        item.setCategory(itemDto.getCategory());
        item.setItemCondition(itemDto.getItemCondition());
        item.setLocation(itemDto.getLocation());
        item.setOwner(ownerOpt.get());  // ✅ set the actual User entity

        Items savedItem = itemsRepository.save(item);

        List<ItemImages> images = itemDto.getImageUrls().stream().map(url -> {
            ItemImages img = new ItemImages();
            img.setItemId(savedItem.getId());
            img.setImageUrl(url);
            img.setIsPrimary(Boolean.FALSE);
            img.setItem(savedItem);
            return img;
        }).collect(Collectors.toList());

        itemImagesRepository.saveAll(images);
        savedItem.setImages(images);
        return ResponseEntity.ok(savedItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItem(@PathVariable UUID id) {
        return itemsRepository.findById(id).map(item -> {
            List<ItemImages> images = itemImagesRepository.findByItemId(item.getId());
            String primaryImageUrl = images.isEmpty() ? null : images.get(0).getImageUrl();
            ItemDto itemDto = new ItemDto(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getPrice(),
                    item.getDailyRate(),
                    item.getCategory(),
                    item.getItemCondition(),
                    item.getLocation(),
                    item.getOwner().getId(),  // ✅ extract ownerId from User
                    primaryImageUrl
            );
            return ResponseEntity.ok(itemDto);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/owner/{ownerId}")
    public List<Items> getItemsByOwner(@PathVariable UUID ownerId) {
        return itemsRepository.findByOwnerId(ownerId);
    }

    @GetMapping("/category/{category}")
    public List<Items> getItemsByCategory(@PathVariable String category) {
        return itemsRepository.findByCategory(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable UUID id) {
        if (itemsRepository.existsById(id)) {
            itemsRepository.deleteById(id);
            return ResponseEntity.ok("Item deleted");
        }
        return ResponseEntity.notFound().build();
    }
}
