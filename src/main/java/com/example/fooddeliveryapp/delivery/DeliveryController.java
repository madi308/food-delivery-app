package com.example.fooddeliveryapp.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    /**
     * Delivery fee endpoint.
     * Returns delivery fee using DeliveryService.
     * @param location Name of the city.
     * @param vehicle Vehicle type.
     * @return Delivery fee.
     */
    @GetMapping(path = "/{location}/{vehicle}")
    public ResponseEntity<Object> getDeliveryFee(@PathVariable String location, @PathVariable String vehicle) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body("{\"deliveryFee\": \"" + deliveryService.getDeliveryFee(location, vehicle) + "\"}");
    }
}
