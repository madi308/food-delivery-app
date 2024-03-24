package com.example.fooddeliveryapp.city;

import com.example.fooddeliveryapp.exception.CityNotFoundException;
import com.example.fooddeliveryapp.exception.NotAFloatException;
import com.example.fooddeliveryapp.exception.UnsupportedVehicleException;
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

    /**
     * Updates RBF for the specified city.
     * @param cityName The name of the city to be updated.
     * @param vehicle The vehicle type.
     * @param newRBF New RBF value.
     */
    public void updateRBF(String cityName, String vehicle, String newRBF) {
        City city = getCityByName(cityName);
        float floatRBF;
        try {
            floatRBF = Float.parseFloat(newRBF);
        } catch (NumberFormatException | NullPointerException e) {
            throw new NotAFloatException("The inserted RBF value is not a float");
        }

        switch (vehicle.toLowerCase()) {
            case "car" -> city.setCarRBF(floatRBF);
            case "scooter" -> city.setScooterRBF(floatRBF);
            case "bike" -> city.setBikeRBF(floatRBF);
            default -> throw new UnsupportedVehicleException("Vehicle not found");
        };
        cityRepository.save(city);
    }
}
