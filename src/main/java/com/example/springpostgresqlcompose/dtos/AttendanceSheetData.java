package com.example.springpostgresqlcompose.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceSheetData {
    private String classId;
    private String roomNo;
    private String centre;
    private long startRollNo;
    private long endRollNo;
}
