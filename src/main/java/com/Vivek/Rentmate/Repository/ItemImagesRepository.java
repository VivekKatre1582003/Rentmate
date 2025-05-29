package com.Vivek.Rentmate.Repository;

import com.Vivek.Rentmate.Model.ItemImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemImagesRepository extends JpaRepository<ItemImages, UUID> {
    List<ItemImages> findByItemId(UUID itemId);
}
