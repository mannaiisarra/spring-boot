package com.spring.pfe.repository;


import com.spring.pfe.models.Demande;
import com.spring.pfe.models.Question;
import com.spring.pfe.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("Select d from Question d where d.quiz.id = :id ")
    List<Question> getAllQuestionByQuiz(@Param("id") Long id);
}
