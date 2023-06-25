package com.example.springpostgresqlcompose.db.repositories;

import com.example.springpostgresqlcompose.db.model.RoomDistribution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomDistributionRepository extends JpaRepository<RoomDistribution, Long> {

    List<RoomDistribution> findByClassIdOrderByStartRoll(String classId);
}
