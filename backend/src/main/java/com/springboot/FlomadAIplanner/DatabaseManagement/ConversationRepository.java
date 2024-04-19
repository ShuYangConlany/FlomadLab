package com.springboot.FlomadAIplanner.DatabaseManagement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<NewMessage, Integer> {
    List<NewMessage> findBySessionIdOrderByTimestampAsc(String session_id);
}
