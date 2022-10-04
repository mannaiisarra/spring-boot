package com.spring.pfe.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "formation")
@JsonIgnoreProperties(value = {"createAt","updateAt"},allowGetters = true)

@EntityListeners(AuditingEntityListener.class)

public class Formation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "titre", nullable = false)
    private  String titre;
    @Column(name = "description", nullable = false)
    private  String description;
    @Column(name = "photo", nullable = false)
    private  String photo;

    @Column(name = "date_deDebut", nullable = false)
    private String date_deDebut;
    @Column(name = "resultProgres")
    private  String resultProgres;
    @Column(name = "date_defin", nullable = false)
    private String date_defin;
    private Boolean archiver;
    @JsonSerialize(using = CustomListtSerializerDemande.class)
    @OneToMany(targetEntity = Demande.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "formationn")
    private List<Demande> demandes;

    /*@ManyToMany
    @JoinTable( name = "users_formation",
            joinColumns = @JoinColumn( name = "id_User" ),
            inverseJoinColumns = @JoinColumn( name = "id_fomation" ) )
    private List<User> users = new ArrayList<>();*/

    @JsonSerialize(using = CustomListtSerializerTheme.class)
    @OneToMany(targetEntity = Theme.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "formation")
    private List<Theme> themes = new ArrayList<>();

    @JsonSerialize(using = CustomListtSerializerQuiz.class)
    @OneToMany(targetEntity = Quiz.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "formation")
    private List<Quiz> quizzes = new ArrayList<>();




    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate_deDebut() {
        return date_deDebut;
    }

    public void setDate_deDebut(String date_deDebut) {
        this.date_deDebut = date_deDebut;
    }

    public String getDate_defin() {
        return date_defin;
    }

    public void setDate_defin(String date_defin) {
        this.date_defin = date_defin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /*public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }*/

    public List<Theme> getThemes() {
        return themes;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    public Boolean getArchiver() {
        return archiver;
    }

    public void setArchiver(Boolean archiver) {
        this.archiver = archiver;
    }

    public String getResultProgres() {
        return resultProgres;
    }

    public void setResultProgres(String resultProgres) {
        this.resultProgres = resultProgres;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}