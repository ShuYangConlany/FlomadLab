package com.springboot.FlomadAIplanner.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class TimeController {

    @CrossOrigin // allow all sources
    @GetMapping("/time")
    public String getTime() {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://worldtimeapi.org/api/timezone/Europe/London";
        return restTemplate.getForObject(uri, String.class);
    }
}