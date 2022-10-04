package com.spring.pfe.models;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String username;


    private String email;

    private String phone;
    private String photo;
    private String adress;


    private String password;

    private int code;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonSerialize(using = CustomListtSerializerChatEntity.class)
    @OneToMany(targetEntity = ChatEntity.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private List<ChatEntity> chatEntities;

    @JsonSerialize(using = CustomListtSerializerDemande.class)
    @OneToMany(targetEntity = Demande.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "users")
    private List<Demande> demandes;

    @JsonSerialize(using = CustomListSerializerQuizResultResponse.class)
    @OneToMany(targetEntity = QuizResultResponse.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user")
    private List<QuizResultResponse> quizResultResponses = new ArrayList<>();


    @JsonSerialize(using = CustomListtSerializerCodeverification.class)
    @OneToMany(targetEntity = CodeVerification.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "users")
    private List<CodeVerification> codeVerifications;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User( String username, String email, String phone, String adress, String password,String photo) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
        this.password = photo;


    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Demande> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }

    public User() {

    }


    public List<QuizResultResponse> getQuizResultResponses() {
        return quizResultResponses;
    }

    public void setQuizResultResponses(List<QuizResultResponse> quizResultResponses) {
        this.quizResultResponses = quizResultResponses;
    }

    public List<CodeVerification> getCodeVerifications() {
        return codeVerifications;
    }

    public void setCodeVerifications(List<CodeVerification> codeVerifications) {
        this.codeVerifications = codeVerifications;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
