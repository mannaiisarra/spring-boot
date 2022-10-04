package com.spring.pfe.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "quizResultResponse")
public class QuizResultResponse implements Serializable {



    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Integer correctAnswers;

    private double marksObtained;

    private Integer attempted;

    @ManyToOne
    @JsonSerialize(using = CustomSerializerFormation.class)
    private Formation formation;

    @ManyToOne
    @JsonSerialize(using = CustomSerializerQuiz.class)
    private Quiz quiz;
    @ManyToOne
    @JsonSerialize(using = CustomSerializerUser.class)
    private User user;

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public QuizResultResponse(Integer correctAnswers, double marksObtained, Integer attempted, Quiz quiz, User user) {
        this.correctAnswers = correctAnswers;
        this.marksObtained = marksObtained;
        this.attempted = attempted;
        this.quiz = quiz;
        this.user = user;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public double getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(double marksObtained) {
        this.marksObtained = marksObtained;
    }

    public Integer getAttempted() {
        return attempted;
    }

    public void setAttempted(Integer attempted) {
        this.attempted = attempted;
    }


    public QuizResultResponse() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }
}
