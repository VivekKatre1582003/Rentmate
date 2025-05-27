package com.Vivek.Rentmate.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemImageDto {
    private UUID itemId;
    private String imageUrl;
    private Boolean isPrimary;
}
