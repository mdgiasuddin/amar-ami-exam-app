package com.example.springpostgresqlcompose.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSheetData {
    private String classId;
    private String centre;
    private String roomNo;
    private long startRollNo;
    private long endRollNo;
}
