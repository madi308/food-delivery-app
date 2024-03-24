package com.example.fooddeliveryapp.city;

import com.example.fooddeliveryapp.exception.CityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * Gets city from the repository by name.
     * @param cityName Name of the city.
     * @return City object.
     */
    public City getCityByName(String cityName) {
        Optional<City> optionalCity = cityRepository.findById(cityName.toLowerCase());
        if (optionalCity.isEmpty()) throw new CityNotFoundException("Could not find a city with given name");
        return optionalCity.get();
    }

    /**
     * Gets all cities.
     * @return List of City objects.
     */
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}
