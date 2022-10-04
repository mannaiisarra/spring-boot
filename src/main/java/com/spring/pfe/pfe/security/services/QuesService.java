package com.spring.pfe.security.services;

import com.spring.pfe.models.Question;
import com.spring.pfe.models.Quiz;
import com.spring.pfe.repository.QuestionRepository;
import com.spring.pfe.repository.QuizRepository;
import com.spring.pfe.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class QuesService {

    @Autowired
    private QuestionRepository iquestion;

    @Autowired
    private QuizRepository iquiz;

    public Question getQuestionById(Long quesId) throws Exception {
        Question question=this.iquestion.findById(quesId).get();
        if(question==null) {
            throw new Exception("Question is not found please enter valid ques id");
        }
        return question;
    }
    public Quiz getQuizById(Long id) throws Exception {
        Quiz quiz=this.iquiz.findById(id).get();
        if(quiz==null) {
            throw new Exception("Question is not found please enter valid ques id");
        }
        return quiz;
    }
}
