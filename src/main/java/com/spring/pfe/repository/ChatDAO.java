package com.spring.pfe.repository;

import com.spring.pfe.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatDAO extends JpaRepository<ChatEntity, Long> {

    @Query("Select d from ChatEntity d where d.user.id  = :user_id  ")
    List<ChatEntity> findByPartecipant(@Param("user_id ") Long user_id );

    @Query("Select d from ChatEntity d where d.name = :name ")
    ChatEntity findByName(@Param("name") String name);


    @Query("Select d from ChatEntity d where d.user.id = :user_id ")
    List<ChatEntity> getAllchatByUsers(@Param("user_id") Long user_id);

}
