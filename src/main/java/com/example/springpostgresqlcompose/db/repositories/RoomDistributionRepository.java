package com.example.springpostgresqlcompose.db.repositories;

import com.example.springpostgresqlcompose.db.model.RoomDistribution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDistributionRepository extends JpaRepository<RoomDistribution, Long> {
}
