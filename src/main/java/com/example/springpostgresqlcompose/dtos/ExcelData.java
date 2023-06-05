package com.example.springpostgresqlcompose.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExcelData {
    private String sheetName;
    private String[] headerRow;
    private List<Object[]> otherRowList;
}
