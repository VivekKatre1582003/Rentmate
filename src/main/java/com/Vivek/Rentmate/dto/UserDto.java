package com.Vivek.Rentmate.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private String fullName;
    private String avatarUrl;
    private String location;
    private String phoneNumber;
    private String college;
    private String department;
    private String yearOfStudy;
    private String hostelRoom;
    private String bio;
}
