package com.example.fooddeliveryapp.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, String> {

    @Query("SELECT c FROM City c WHERE LOWER(c.name) = LOWER(?1)")
    Optional<City> findCityByName(String name);
}
