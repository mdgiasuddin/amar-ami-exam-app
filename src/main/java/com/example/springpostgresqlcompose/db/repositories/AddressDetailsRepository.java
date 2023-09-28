package com.example.springpostgresqlcompose.db.repositories;

import com.example.springpostgresqlcompose.db.model.AddressDetails;
import com.example.springpostgresqlcompose.dtos.AddressView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressDetailsRepository extends JpaRepository<AddressDetails, Long> {

    List<AddressView> findAllByStudentIsNotNull();
}
