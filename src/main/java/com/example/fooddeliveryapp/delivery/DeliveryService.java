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

    /**
     * Calculates the delivery fee based on regional base fees and fees based on weather conditions.
     * @param cityName Name of the city.
     * @param vehicle Vehicle type.
     * @return Delivery fee.
     */
    public double getDeliveryFee(String cityName, String vehicle) {
        City city = cityService.getCityByName(cityName);
        Observation observation = observationService.getLatestObservation(city.getStationName());

        double RBF = getRBF(city, vehicle);

        double[] ATEFandWPEF = getATEFandWPEF(vehicle, observation);
        double ATEF = ATEFandWPEF[0];
        double WPEF = ATEFandWPEF[1];

        double WSEF = getWSEF(vehicle, observation);

        return RBF + ATEF + WSEF + WPEF;
    }

    private static double getRBF(City city, String vehicle) {
        return switch (vehicle.toLowerCase()) {
            case "car" -> city.getCarRBF();
            case "scooter" -> city.getScooterRBF();
            case "bike" -> city.getBikeRBF();
            default -> throw new UnsupportedVehicleException("Vehicle not found");
        };
    }

    private static double[] getATEFandWPEF(String vehicle, Observation observation) {
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
            if (phenomenon.contains("rain") || phenomenon.contains("shower"))
                WPEF = 0.5;
            if (phenomenon.contains("glaze") || phenomenon.contains("hail") || phenomenon.contains("thunder"))
                throw new UnsupportedVehicleException("Usage of selected vehicle type is forbidden");
        }
        return new double[]{ATEF, WPEF};
    }

    private static double getWSEF(String vehicle, Observation observation) {
        if (vehicle.equalsIgnoreCase("bike")) {
            double windSpeed = observation.getWindspeed();
            if (windSpeed > 20)
                throw new UnsupportedVehicleException("Usage of selected vehicle type is forbidden");
            if (windSpeed >= 10)
                return 0.5;
        }
        return 0;
    }
}
