package com.springboot.FlomadAIplanner.controller;
import java.util.Collections;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PricingController {
    @Value("${amadeus.api.key}")
    private String apiKey;

    @Value("${amadeus.api.secret}")
    private String apiSecret;

    private final AmadeusAuthService amadeusAuthService;

    // 注入AmadeusAuthService
    @Autowired
    public PricingController(AmadeusAuthService amadeusAuthService) {
        this.amadeusAuthService = amadeusAuthService;
    }

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/pricing")
    public String verifyFlightOffer(@RequestBody String offer) {
        try {
            System.out.println("Received offer: " + offer);
            String accessToken = amadeusAuthService.getAccessToken();
            if (accessToken == null) {
                return "Failed to retrieve access token";
            }
            System.out.println("Access Token: " + accessToken);
    
            String verifyUrl = "https://test.api.amadeus.com/v1/shopping/flight-offers/pricing";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + accessToken); // 在这里添加accessToken
            headers.set("Content-Type", "application/json");
            headers.set("X-HTTP-Method-Override", "GET");
    
            HttpEntity<String> request = new HttpEntity<>(offer, headers);
            System.out.println("Headers: " + request.getHeaders().toSingleValueMap());
            System.out.println("Body: " + request.getBody());
    
            ResponseEntity<String> response = restTemplate.postForEntity(verifyUrl, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace(); // 在生产环境中，考虑使用日志框架记录异常
            return "Error processing the request: " + e.getMessage();
        }
    }
}
