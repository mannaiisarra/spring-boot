package com.spring.pfe.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Chat")
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private long chat_id;

    @Column(name = "name")
    private String name;
    @Column(name = "other")
    private String other;
    @Column(name = "photo")
    private String photo;
    @ManyToOne
    @JsonSerialize(using = CustomSerializerUser.class)
    private User user;
/*
    @JsonSerialize(using = CustomListtSerializerMessageEntity.class)
    @OneToMany(targetEntity = MessageEntity.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "chatEntity")
    private List<MessageEntity> messageEntities;

*/
    public ChatEntity() {}

    public ChatEntity(String name) {
        this.name = name;
    }

    public long getChat_id() {
        return chat_id;
    }

    public void setChat_id(long chat_id) {
        this.chat_id = chat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
