package com.example.springpostgresqlcompose.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRoomData {

    private String studentName;
    private String classId;
    private String schoolName;
    private Long schoolRollNo;
    private String centre;
    private String roomNo;
}
