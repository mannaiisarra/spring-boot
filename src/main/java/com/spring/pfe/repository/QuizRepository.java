package com.spring.pfe.repository;

import com.spring.pfe.models.ChatEntity;
import com.spring.pfe.models.Quiz;
import com.spring.pfe.models.QuizResultResponse;
import com.spring.pfe.models.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {


    @Query("Select d from Quiz d where   d.id = :id  ")
    Quiz getAllEtapeByQuiz(@Param("id") Long id);
    @Query("Select d from Quiz d where   d.formation.id = :id  ")
    List<Quiz> getAllEtapeByFormation(@Param("id") Long id);
}
