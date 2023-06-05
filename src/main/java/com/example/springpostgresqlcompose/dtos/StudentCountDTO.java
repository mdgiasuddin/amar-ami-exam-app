package com.example.springpostgresqlcompose.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCountDTO {
    private String schoolName;
    private Long count;
}
