package com.springboot.FlomadAIplanner.DatabaseManagement;

import java.time.LocalDateTime;

public class MessagesDto {
    private Speaker speaker;
    private String message;
    private LocalDateTime timestamp;
    private String id;
    
    public MessagesDto() {
    }

    public MessagesDto(Speaker speaker, String message,LocalDateTime timestamp, String id) {
        this.speaker = speaker;
        this.message = message;
        this.timestamp = timestamp;
        this.id = id;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }
}
