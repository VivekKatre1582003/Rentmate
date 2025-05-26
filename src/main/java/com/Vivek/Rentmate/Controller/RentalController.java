package com.Vivek.Rentmate.Controller;

import com.Vivek.Rentmate.Model.Items;
import com.Vivek.Rentmate.Model.Rentals;
import com.Vivek.Rentmate.Model.User;
import com.Vivek.Rentmate.Repository.ItemsRepository;
import com.Vivek.Rentmate.Repository.RentalsRepository;
import com.Vivek.Rentmate.Repository.UserRepository;
import com.Vivek.Rentmate.dto.RentalRequestDto;
import com.Vivek.Rentmate.dto.RentalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rentals")
@CrossOrigin
public class RentalController {

    @Autowired
    private RentalsRepository rentalsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemsRepository itemsRepository;

    @PostMapping
    public ResponseEntity<Rentals> createRental(@RequestBody RentalRequestDto dto) {
        Optional<User> renter = userRepository.findById(dto.getRenterId());
        Optional<Items> item = itemsRepository.findById(dto.getItemId());

        if (renter.isEmpty() || item.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Rentals rental = new Rentals();
        rental.setItemId(dto.getItemId());
        rental.setRenterId(dto.getRenterId());
        rental.setStartDate(dto.getStartDate());
        rental.setEndDate(dto.getEndDate());
        rental.setTotalPrice(dto.getTotalPrice());
        rental.setStatus("pending");
        rental.setRenter(renter.get());
        rental.setItem(item.get());

        return ResponseEntity.ok(rentalsRepository.save(rental));
    }

    @GetMapping("/renter/{renterId}")
    public List<RentalResponseDto> getRentalsByRenter(@PathVariable UUID renterId) {
        return rentalsRepository.findByRenterId(renterId).stream().map(r -> new RentalResponseDto(
                r.getId(),
                r.getItemId(),
                r.getItem().getName(),
                r.getRenterId(),
                r.getRenter().getFullName(),
                r.getStartDate(),
                r.getEndDate(),
                r.getTotalPrice(),
                r.getStatus(),
                r.getDenialReason()
        )).collect(Collectors.toList());
    }

    @GetMapping("/item/{itemId}")
    public List<RentalResponseDto> getRentalsByItem(@PathVariable UUID itemId) {
        return rentalsRepository.findByItemId(itemId).stream().map(r -> new RentalResponseDto(
                r.getId(),
                r.getItemId(),
                r.getItem().getName(),
                r.getRenterId(),
                r.getRenter().getFullName(),
                r.getStartDate(),
                r.getEndDate(),
                r.getTotalPrice(),
                r.getStatus(),
                r.getDenialReason()
        )).collect(Collectors.toList());
    }

    @PutMapping("/{rentalId}/status")
    public ResponseEntity<Rentals> updateRentalStatus(
            @PathVariable UUID rentalId,
            @RequestParam String status,
            @RequestParam(required = false) String denialReason) {
        return rentalsRepository.findById(rentalId).map(rental -> {
            rental.setStatus(status);
            rental.setDenialReason(denialReason);
            return ResponseEntity.ok(rentalsRepository.save(rental));
        }).orElse(ResponseEntity.notFound().build());
    }
}
