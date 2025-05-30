package com.Vivek.Rentmate.Service;

import com.Vivek.Rentmate.Model.Items;
import com.Vivek.Rentmate.Model.Rentals;
import com.Vivek.Rentmate.Model.User;
import com.Vivek.Rentmate.Repository.ItemsRepository;
import com.Vivek.Rentmate.Repository.RentalsRepository;
import com.Vivek.Rentmate.Repository.UserRepository;
import com.Vivek.Rentmate.dto.RentalRequestDto;
import com.Vivek.Rentmate.dto.RentalResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RentalsService {

    @Autowired
    private RentalsRepository rentalsRepository;

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private UserRepository userRepository;

    public Rentals createRental(RentalRequestDto rentalRequestDto) {
        Optional<Items> optionalItem = itemsRepository.findById(rentalRequestDto.getItemId());
        Optional<User> optionalUser = userRepository.findById(rentalRequestDto.getRenterId());
        if (optionalItem.isPresent() && optionalUser.isPresent()) {
            Items item = optionalItem.get();
            User renter = optionalUser.get();
            Rentals rental = new Rentals();
            rental.setItem(item);
            rental.setRenter(renter);
            rental.setStartDate(rentalRequestDto.getStartDate());
            rental.setEndDate(rentalRequestDto.getEndDate());
            rental.setTotalPrice(rentalRequestDto.getTotalPrice());
            rental.setStatus("PENDING");
            return rentalsRepository.save(rental);
        } else {
            throw new RuntimeException("Item or Renter not found");
        }
    }

    public Optional<Rentals> getRentalById(UUID id) {
        return rentalsRepository.findById(id);
    }

    public List<Rentals> getRentalsByRenterId(UUID renterId) {
        return rentalsRepository.findByRenter_Id(renterId);
    }

    public List<Rentals> getRentalsByItemId(UUID itemId) {
        return rentalsRepository.findByItem_Id(itemId);
    }

    public Rentals updateRentalStatus(UUID id, String status, String denialReason) {
        Optional<Rentals> optionalRental = rentalsRepository.findById(id);
        if (optionalRental.isPresent()) {
            Rentals rental = optionalRental.get();
            rental.setStatus(status);
            rental.setDenialReason(denialReason);
            return rentalsRepository.save(rental);
        } else {
            throw new RuntimeException("Rental not found with id: " + id);
        }
    }

    public void deleteRental(UUID id) {
        rentalsRepository.deleteById(id);
    }

    public RentalResponseDto convertToDto(Rentals rental) {
        RentalResponseDto dto = new RentalResponseDto();
        dto.setRentalId(rental.getId());
        dto.setItemId(rental.getItem().getId());
        dto.setItemName(rental.getItem().getName());
        dto.setRenterId(rental.getRenter().getId());
        dto.setRenterName(rental.getRenter().getFullName());
        dto.setStartDate(rental.getStartDate());
        dto.setEndDate(rental.getEndDate());
        dto.setTotalPrice(rental.getTotalPrice());
        dto.setStatus(rental.getStatus());
        dto.setDenialReason(rental.getDenialReason());
        return dto;
    }
}
