package com.spring.pfe.models;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz")
public class Quiz {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    private String numberOfQuestions;

    private String maxMarks;

    private boolean active=false;



    @ManyToOne
    @JsonSerialize(using = CustomSerializerEtape.class)
    private Etape etape;

    @ManyToOne
    @JsonSerialize(using = CustomSerializerFormation.class)
    private Formation formation;

    @JsonSerialize(using = CustomListSerializerQuestion.class)
    @OneToMany(targetEntity = Question.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "quiz")
    private List<Question> questions = new ArrayList<>();


    @JsonSerialize(using = CustomListSerializerQuizResultResponse.class)
    @OneToMany(targetEntity = QuizResultResponse.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "quiz")
    private List<QuizResultResponse> quizResultResponses = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(String numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(String maxMarks) {
        this.maxMarks = maxMarks;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Etape getEtape() {
        return etape;
    }

    public void setEtape(Etape etape) {
        this.etape = etape;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<QuizResultResponse> getQuizResultResponses() {
        return quizResultResponses;
    }

    public void setQuizResultResponses(List<QuizResultResponse> quizResultResponses) {
        this.quizResultResponses = quizResultResponses;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }
}
