package com.Vivek.Rentmate.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemCreateRequestDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean dailyRate;
    private String category;
    private String itemCondition;
    private String location;
    private UUID ownerId;
    private List<String> imageUrls;
}
