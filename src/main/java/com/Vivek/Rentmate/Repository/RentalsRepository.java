package com.Vivek.Rentmate.Repository;

import com.Vivek.Rentmate.Model.Rentals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RentalsRepository extends JpaRepository<Rentals, UUID> {
    List<Rentals> findByRenter_Id(UUID renterId);
    List<Rentals> findByItem_Id(UUID itemId);
}
