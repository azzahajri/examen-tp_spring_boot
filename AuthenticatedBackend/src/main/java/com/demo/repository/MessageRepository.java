package com.demo.repository;

import com.demo.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
//    List<Message> findByContenuContainingAndTypeAndEmetteurId(String keyword, MessageType type, Long emetteurId);
//
//    List<Message> searchMessagesWithFilters(String keyword, MessageType type, Long emetteurId);
//

//
//    List<Message> findByRecepteursIsNull();
}

