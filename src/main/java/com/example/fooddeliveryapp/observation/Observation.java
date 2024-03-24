package com.example.fooddeliveryapp.observation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "weather_data")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Observation {

    @Id
    @SequenceGenerator(
          name = "observation_sequence",
          sequenceName = "observation_sequence",
          allocationSize = 1
    )
    @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "observation_sequence"
    )
    private long id;
    private String name;
    private int wmocode;
    private float airtemperature;
    private float windspeed;
    private String phenomenon;
    private long timestamp;

    public Observation(String name, int wmocode, float airtemperature, float windspeed, String phenomenon, long timestamp) {
        this.name = name;
        this.wmocode = wmocode;
        this.airtemperature = airtemperature;
        this.windspeed = windspeed;
        this.phenomenon = phenomenon;
        this.timestamp = timestamp;
    }
}
