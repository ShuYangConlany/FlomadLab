package com.springboot.FlomadAIplanner.controller;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;

@RestController
public class FlightOfferController {        
    @Value("${amadeus.api.key}")
    private String apiKey;

    @Value("${amadeus.api.secret}")
    private String apiSecret;

    private final AmadeusAuthService amadeusAuthService;

    // 注入AmadeusAuthService
    @Autowired
    public FlightOfferController(AmadeusAuthService amadeusAuthService) {
        this.amadeusAuthService = amadeusAuthService;
    }

    @GetMapping("/flight-offers")
    public String getFlightOffers(@RequestParam String originLocationCode,
                                  @RequestParam String destinationLocationCode,
                                  @RequestParam String departureDate,
                                  @RequestParam String returnDate,
                                  @RequestParam int adults,
                                  @RequestParam int max) {
        // 获取访问令牌
        String accessToken = amadeusAuthService.getAccessToken();
        if (accessToken == null) {
            return "Failed to retrieve access token";
        }

        RestTemplate restTemplate = new RestTemplate();

        // 构建请求头，添加认证令牌
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(accessToken); // 使用访问令牌

        // 构建请求实体
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // 构建URL和参数
        String url = "https://test.api.amadeus.com/v2/shopping/flight-offers" +
                     "?originLocationCode={originLocationCode}" +
                     "&destinationLocationCode={destinationLocationCode}" +
                     "&departureDate={departureDate}" +
                     "&returnDate={returnDate}" +
                     "&adults={adults}" +
                     "&max={max}";

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class,
                originLocationCode, destinationLocationCode, departureDate, returnDate, adults, max);

        return response.getBody();
    }
    
}
