package com.spring.pfe.repository;

import com.spring.pfe.models.Question;
import com.spring.pfe.models.Quiz;
import com.spring.pfe.models.QuizResultResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultResponseRepository extends JpaRepository<QuizResultResponse, Long> {

  @Query("Select d from QuizResultResponse d where d.quiz.id  = :quiz_id  and  d.user.id = :user_id")
    List<QuizResultResponse> getAllUserByQuiz(@Param("quiz_id") Long quiz_id,@Param("user_id") Long user_id);


  @Query("Select d from QuizResultResponse d where   d.user.id = :id  ")
  List<QuizResultResponse> getResultQuiz(@Param("id") Long id);

  @Query("Select d from QuizResultResponse d where   d.quiz.id  = :quiz_id  ")
  List<QuizResultResponse> getAllResultByQuiz(@Param("quiz_id") Long quiz_id);
}
