package com.springboot.FlomadAIplanner.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.*;


@Service
public class AmadeusAuthService {
    @Value("${amadeus.api.key}")
    private String clientId;

    @Value("${amadeus.api.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    public AmadeusAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // public String getAccessToken() {
    //     String url = "https://test.api.amadeus.com/v1/security/oauth2/token";
        
    //     // 构建请求体
    //     Map<String, String> requestBody = new HashMap<>();
    //     requestBody.put("client_id", clientId);
    //     requestBody.put("client_secret", clientSecret);
    //     requestBody.put("grant_type", "client_credentials");

    //     // 发送POST请求
    //     Map<String, String> response = restTemplate.postForObject(url, requestBody, Map.class);
        
    //     // 返回访问令牌
    //     return response != null ? response.get("access_token") : null;
    // }

    public String getAccessToken() {
        String url = "https://test.api.amadeus.com/v1/security/oauth2/token";

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 构建请求体
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("grant_type", "client_credentials");

        // 创建HttpEntity对象，包含请求头和请求体
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // 发送POST请求，并接收响应
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, requestEntity, Map.class);

        // 从响应体中提取access_token
        if (responseEntity.getBody() != null && responseEntity.getBody().containsKey("access_token")) {
            return responseEntity.getBody().get("access_token").toString();
        } else {
            return null;
        }
    }

}
