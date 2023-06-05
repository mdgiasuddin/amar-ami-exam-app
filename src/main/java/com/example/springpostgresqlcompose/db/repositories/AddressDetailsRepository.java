package com.example.springpostgresqlcompose.db.repositories;

import com.example.springpostgresqlcompose.db.model.AddressDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDetailsRepository extends JpaRepository<AddressDetails, Long> {
}
