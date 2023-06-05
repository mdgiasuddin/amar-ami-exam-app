package com.example.springpostgresqlcompose.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolWiseStudent {
    private int count;
    private List<StudentDTO> studentDTOList;
}
