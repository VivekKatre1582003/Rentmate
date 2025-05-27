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
public class RentalRequestDto {
    private UUID itemId;
    private UUID renterId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPrice;
}
