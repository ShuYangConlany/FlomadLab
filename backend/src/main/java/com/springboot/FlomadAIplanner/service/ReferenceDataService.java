package com.springboot.FlomadAIplanner.service;

import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.springboot.FlomadAIplanner.controller.AmadeusAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

@Service
public class ReferenceDataService {


    private AmadeusAuthService amadeusAuthService = null;

    @Autowired
    public ReferenceDataService(AmadeusAuthService amadeusAuthService) {
        this.amadeusAuthService = amadeusAuthService;
    }


    @Value("${amadeus.api.key}")
    private String apiKey;

    @Value("${amadeus.api.secret}")
    private String apiSecret;

    public  JsonNode getAirportCode(String cityName) {
        final String url = "https://test.api.amadeus.com/v1/reference-data/locations?keyword=" + cityName + "&subType=CITY,AIRPORT";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String accessToken = amadeusAuthService.getAccessToken();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
        // JsonNode responseBody = response.getBody();
        // if (responseBody != null && responseBody.has("data") && responseBody.get("data").isArray() && responseBody.get("data").size() > 0) {
        //     JsonNode firstMatch = responseBody.get("data").get(0);
        //     if (firstMatch.has("iataCode")) {
        //         return firstMatch.get("iataCode").asText();
        //     }
        // }
        // return null; 
        return response.getBody();
    }
}
