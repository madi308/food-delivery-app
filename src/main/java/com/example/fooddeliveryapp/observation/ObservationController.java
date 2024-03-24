package com.example.fooddeliveryapp.observation;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/observation")
public class ObservationController {

    private final ObservationService observationService;

    @Autowired
    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    @GetMapping(path = "/{location}")
    public Observation getLatestObservationByLocation(@PathVariable String location) {
        return observationService.getLatestObservation(location);
    }

}
