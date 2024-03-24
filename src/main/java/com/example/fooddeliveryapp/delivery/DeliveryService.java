package com.example.fooddeliveryapp.delivery;

import com.example.fooddeliveryapp.city.City;
import com.example.fooddeliveryapp.city.CityService;
import com.example.fooddeliveryapp.exception.UnsupportedVehicleException;
import com.example.fooddeliveryapp.observation.Observation;
import com.example.fooddeliveryapp.observation.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final ObservationService observationService;
    private final CityService cityService;

    @Autowired
    public DeliveryService(ObservationService observationService, CityService cityService) {
        this.observationService = observationService;
        this.cityService = cityService;
    }

    public double getDeliveryFee(String cityName, String vehicle) {
        City city = cityService.getCityByName(cityName);
        Observation observation = observationService.getLatestObservation(city.getStationName());
        double RBF;
        RBF = switch (vehicle.toLowerCase()) {
            case "car" -> city.getCarRBF();
            case "scooter" -> city.getScooterRBF();
            case "bike" -> city.getBikeRBF();
            default -> throw new UnsupportedVehicleException("Vehicle not found");
        };
        double ATEF = 0;
        double WPEF = 0;
        if (vehicle.equalsIgnoreCase("scooter") || vehicle.equalsIgnoreCase("bike")) {
            double airTemp = observation.getAirtemperature();
            if (airTemp < -10)
                ATEF = 1;
            else if (airTemp < 0) {
                ATEF = 0.5;
            }
            String phenomenon = observation.getPhenomenon().toLowerCase();
            if (phenomenon.contains("snow") || phenomenon.contains("sleet"))
                WPEF = 1;
            if (phenomenon.contains("rain"))
                WPEF = 0.5;
            if (phenomenon.contains("glaze") || phenomenon.contains("hail") || phenomenon.contains("thunder"))
                throw new UnsupportedVehicleException("Usage of selected vehicle type is forbidden");
        }
        double WSEF = 0;
        if (vehicle.equalsIgnoreCase("bike")) {
            double windSpeed = observation.getWindspeed();
            if (windSpeed > 20)
                throw new UnsupportedVehicleException("Usage of selected vehicle type is forbidden");
            if (windSpeed >= 10)
                WSEF = 0.5;
        }
        return RBF + ATEF + WSEF + WPEF;
    }

}
