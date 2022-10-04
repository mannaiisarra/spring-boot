package com.spring.pfe.repository;

import com.spring.pfe.models.ChatEntity;
import com.spring.pfe.models.MessageEntity;
import com.spring.pfe.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDAO extends JpaRepository<MessageEntity, Long> {

    @Query("Select d from MessageEntity d where d.sender = :sender ")
    MessageEntity findByNamef(@Param("sender") String sender);

    @Query("Select d from MessageEntity d where d.chat = :chat ")
    MessageEntity findByChat(@Param("chat") Long chat);
}
