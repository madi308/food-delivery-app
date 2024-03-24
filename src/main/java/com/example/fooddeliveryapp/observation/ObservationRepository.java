package com.example.fooddeliveryapp.observation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {

    @Query("SELECT w FROM Observation w WHERE w.name=?1 ORDER BY timestamp DESC LIMIT 1")
    Optional<Observation> findLatestByName(String name);
}
