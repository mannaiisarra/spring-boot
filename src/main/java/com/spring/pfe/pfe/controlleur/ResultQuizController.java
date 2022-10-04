package com.spring.pfe.controllers;

import com.spring.pfe.models.Etape;
import com.spring.pfe.models.Quiz;
import com.spring.pfe.models.QuizResultResponse;
import com.spring.pfe.models.Response;
import com.spring.pfe.repository.QuizResultResponseRepository;
import com.spring.pfe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ResultQuiz")
@CrossOrigin("*")
public class ResultQuizController {
    @Autowired
    private QuizResultResponseRepository quizResultResponseRepository;


    @GetMapping("/")
    public Response<List<QuizResultResponse>> findAllQuizResult () {
        System.out.println("affichage");
        return new Response<List<QuizResultResponse>>("200", "Get all Quiz result ", quizResultResponseRepository.findAll());
    }

    @GetMapping("/getAllresultByQuiz/{quiz_id}")
    public Response<List<QuizResultResponse>> getAllEtapeByFotmation(@PathVariable Long quiz_id) {


        try {
            return new Response<List<QuizResultResponse>>("200", "quiz", quizResultResponseRepository.getAllResultByQuiz(quiz_id));

        } catch (Exception e) {
            return new Response<List<QuizResultResponse>>("406", e.getMessage(), null);
        }

    }
}
