package com.springboot.FlomadAIplanner.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.springboot.FlomadAIplanner.service.ReferenceDataService;

@RestController
public class ReferenceDataController {

    private final ReferenceDataService referenceDataService;

    public ReferenceDataController(ReferenceDataService referenceDataService) {
        this.referenceDataService = referenceDataService;
    }

    @GetMapping("/reference_data")
    public JsonNode getAirportCode(@RequestParam String cityName) {
        return referenceDataService.getAirportCode(cityName);
    }
}

