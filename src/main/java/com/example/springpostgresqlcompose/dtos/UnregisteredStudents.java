package com.example.springpostgresqlcompose.dtos;

import com.example.springpostgresqlcompose.db.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnregisteredStudents {
    private List<Student> tenStudents;
    private List<Student> eightStudents;
    private List<Student> fiveStudents;
}
