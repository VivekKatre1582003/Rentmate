package com.Vivek.Rentmate.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentalResponseDto {
    private UUID rentalId;
    private UUID itemId;
    private String itemName;
    private UUID renterId;
    private String renterName;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPrice;
    private String status;
    private String denialReason;
}
