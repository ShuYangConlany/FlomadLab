package com.springboot.FlomadAIplanner.controller;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ChatSession;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.PartMaker;
import com.springboot.FlomadAIplanner.service.GeminiService;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeminiController {
    /////////////////////////
    //todo: implement Gemini Vertex pro here

    // @PostMapping("/generate-content")
    // public void generateContent() {
    //     // TODO: Replace these variables with your actual Google Cloud project ID and model name
    //     String projectId = "flomadaiplanner";   // If you don't manage your projects in Google Cloud Dashboard, it expires
    //     String location = "us-central1";
    //     String modelName = "gemini-1.0-pro-vision";

    //     try (VertexAI vertexAI = new VertexAI(projectId, location)) {
    //         GenerativeModel model = new GenerativeModel(modelName, vertexAI);
    //         ChatSession chatSession = new ChatSession(model);
    //         GenerateContentResponse response = chatSession.sendMessage("Hello.");
    //         System.out.println(response.toString());  // 打印响应内容到控制台
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/generate-content")
    public ResponseEntity<String> generateContent(@RequestBody String text) {
        String response = geminiService.callGeminiApi(text);
        return ResponseEntity.ok(response);
    }
}