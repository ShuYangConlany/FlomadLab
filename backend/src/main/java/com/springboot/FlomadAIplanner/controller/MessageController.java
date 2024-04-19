package com.springboot.FlomadAIplanner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.FlomadAIplanner.DatabaseManagement.NewMessage;
import com.springboot.FlomadAIplanner.DatabaseManagement.MessagesDto;
import com.springboot.FlomadAIplanner.service.ConversationService;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/messagesPersistence")
public class MessageController {
    @Autowired
    private ConversationService service;

    @PostMapping
    public ResponseEntity<NewMessage> createMessage(@RequestBody MessagesDto dto) {
        NewMessage newMessage = service.saveMessage( dto.getID(),dto.getMessage(),dto.getTimestamp(), dto.getSpeaker());
        return ResponseEntity.ok(newMessage);
    }

    @GetMapping("/getSessionMessages")
    public ResponseEntity<List<NewMessage>> getSessionMessages(@RequestParam String sessionId) {
        List<NewMessage> messages = service.getMessagesBySessionId(sessionId);
        return ResponseEntity.ok(messages);
    }
}