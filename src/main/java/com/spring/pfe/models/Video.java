package com.spring.pfe.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
@Entity
@Table(name = "video")
@EntityListeners(AuditingEntityListener.class)
public class Video implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String photo;

    @Column(name = "progress")
    private  Boolean progress;

    @ManyToOne
    @JsonSerialize(using = CustomSerializerEtape.class)
    private Etape etape;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Etape getEtape() {
        return etape;
    }

    public void setEtape(Etape etape) {
        this.etape = etape;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getProgress() {
        return progress;
    }

    public void setProgress(Boolean progress) {
        this.progress = progress;
    }
}
