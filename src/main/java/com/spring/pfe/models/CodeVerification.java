package com.spring.pfe.models;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
@Table(name = "CodeVerification")

public class CodeVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private  int code;

    @ManyToOne
    @JsonSerialize(using = CustomSerializerUserr.class)
    private User users;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public CodeVerification() {

    }
}
