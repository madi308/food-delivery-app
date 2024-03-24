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


    /*@GetMapping(path = "/{location}/{vehicle}")
    public double getDeliveryFee(@PathVariable String location, @PathVariable String vehicle) {
        return deliveryService.getDeliveryFee(location, vehicle);
    }*/

    @GetMapping(path = "/{location}/{vehicle}")
    public ResponseEntity<Object> getDeliveryFee(@PathVariable String location, @PathVariable String vehicle) {
        //return deliveryService.getDeliveryFee(location, vehicle);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body("{\"deliveryFee\": \"" + deliveryService.getDeliveryFee(location, vehicle) + "\"}");
    }
}
