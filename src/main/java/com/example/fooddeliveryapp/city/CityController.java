package com.example.fooddeliveryapp.city;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Endpoint for updating RBF for a specific city and vehicle type.
     * @param cityName Name of the city.
     * @param vehicle Type of the vehicle.
     * @param newRBF New value of RBF.
     */
    @PutMapping(path = "/updateRBF/{cityName}/{vehicle}/{newRBF}")
    public void updateRBF(@PathVariable String cityName, @PathVariable String vehicle, @PathVariable String newRBF) {
        cityService.updateRBF(cityName, vehicle, newRBF);
    }
}
