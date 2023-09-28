package com.example.springpostgresqlcompose.dtos;

public interface AddressView {

    Long getId();

    String getAddress();

    StudentView2 getStudent();

    interface StudentView2 {
        String getName();
    }
}
