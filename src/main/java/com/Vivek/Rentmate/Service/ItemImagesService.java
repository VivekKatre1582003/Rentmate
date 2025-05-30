package com.Vivek.Rentmate.Service;

import com.Vivek.Rentmate.Model.ItemImages;
import com.Vivek.Rentmate.Model.Items;
import com.Vivek.Rentmate.Repository.ItemImagesRepository;
import com.Vivek.Rentmate.Repository.ItemsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemImagesService {

    private final ItemImagesRepository itemImagesRepository;
    private final ItemsRepository itemsRepository;

    // ✅ SINGLE constructor to initialize both repositories
    public ItemImagesService(ItemImagesRepository itemImagesRepository, ItemsRepository itemsRepository) {
        this.itemImagesRepository = itemImagesRepository;
        this.itemsRepository = itemsRepository;
    }

    public ItemImages addImage(UUID itemId, String imageUrl, boolean isPrimary) {
        Items item = itemsRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (isPrimary) {
            List<ItemImages> existingImages = itemImagesRepository.findByItemId(itemId);
            for (ItemImages img : existingImages) {
                img.setIsPrimary(false);
            }
            itemImagesRepository.saveAll(existingImages);
        }

        ItemImages image = new ItemImages();
        image.setItem(item);
        image.setItemId(itemId);
        image.setImageUrl(imageUrl);
        image.setIsPrimary(isPrimary);

        return itemImagesRepository.save(image);
    }

    public List<ItemImages> getImagesByItemId(UUID itemId) {
        return itemImagesRepository.findByItemId(itemId);
    }

    public ItemImages updatePrimaryImage(UUID imageId) {
        ItemImages image = itemImagesRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        List<ItemImages> itemImages = itemImagesRepository.findByItemId(image.getItemId());
        for (ItemImages img : itemImages) {
            img.setIsPrimary(false);
        }

        image.setIsPrimary(true);
        itemImagesRepository.saveAll(itemImages);

        return itemImagesRepository.save(image);
    }

    public void deleteImage(UUID imageId) {
        if (!itemImagesRepository.existsById(imageId)) {
            throw new RuntimeException("Image not found");
        }
        itemImagesRepository.deleteById(imageId);
    }
}
