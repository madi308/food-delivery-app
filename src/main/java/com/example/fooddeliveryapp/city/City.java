package com.example.fooddeliveryapp.city;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "city")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class City {
    @Id
    private String name;
    private double carRBF;
    private double scooterRBF;
    private double bikeRBF;
    private String stationName;
}
