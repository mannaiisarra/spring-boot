package com.spring.pfe.repository;

import com.spring.pfe.models.Formation;
import com.spring.pfe.models.MessageEntity;
import com.spring.pfe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageEntityRepository extends JpaRepository<MessageEntity, Long> {


    @Query("Select d from MessageEntity d where d.sender = :sender ")
    List<MessageEntity> findByNamef(@Param("sender") String sender);


    @Query("Select d from MessageEntity d where d.chat = :chat ")
    List<MessageEntity> findAllByChat(@Param("chat") Long chat);
}
