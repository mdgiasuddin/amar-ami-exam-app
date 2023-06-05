package com.example.springpostgresqlcompose.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetailsDTO {
    private Long id;
    private Long studentId;
    private String address;
    private String upazila;
    private String district;
}
