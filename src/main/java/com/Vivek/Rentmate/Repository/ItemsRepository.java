package com.Vivek.Rentmate.Repository;

import com.Vivek.Rentmate.Model.Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemsRepository extends JpaRepository<Items, UUID> {
    List<Items> findByOwnerId(UUID ownerId);
    List<Items> findByCategory(String category);
}
