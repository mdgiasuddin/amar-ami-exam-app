package com.example.springpostgresqlcompose.db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "room_distribution")
@NoArgsConstructor
public class RoomDistribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String classId;
    private String centre;
    private String roomNumber;
    private Long startRoll;
    private Long endRoll;

}
