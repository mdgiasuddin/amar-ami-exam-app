package com.example.springpostgresqlcompose.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentAddressDTO {
    private Long studentId;
    private String studentName;
    private Long studentRollNo;
    private String address;
    private String upazila;
    private String district;
}
