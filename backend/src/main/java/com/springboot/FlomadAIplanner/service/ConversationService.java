package com.springboot.FlomadAIplanner.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.FlomadAIplanner.DatabaseManagement.ConversationRepository;
import com.springboot.FlomadAIplanner.DatabaseManagement.NewMessage;
import com.springboot.FlomadAIplanner.DatabaseManagement.Speaker;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository repository;

    public NewMessage saveMessage(String id, String message,LocalDateTime timestamp,Speaker speaker ) {
        NewMessage messages = new NewMessage();
        // messages.setMessageId(10000);
        messages.setSpeaker(speaker);
        messages.setContent(message);
        messages.setTimestamp(timestamp);
        messages.setSessionId(id);

        return repository.save(messages);
    }

    public List<NewMessage> getMessagesBySessionId(String sessionId) {
        return repository.findBySessionIdOrderByTimestampAsc(sessionId);
    }
}
