package com.spring.pfe.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
@Table(name = "Message")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ms_id")
    private Long ms_id;

    private Long chat;

    @Column(name = "sender")
    private String sender;

    @Column(name = "t_stamp")
    private String t_stamp;

    @Column(name = "content")
    private String content;


  /*  @ManyToOne
    @JsonSerialize(using = CustomSerializerChatEntity.class)
    private ChatEntity chatEntity;
*/
    public MessageEntity() {}



    public MessageEntity(String sender, String t_stamp, String content, Long chat) {
        this.sender = sender;
        this.t_stamp = t_stamp;
        this.content = content;
        this.chat = chat;

    }

    public Long getMs_id() {
        return ms_id;
    }

    public void setMs_id(Long ms_id) {
        this.ms_id = ms_id;
    }



    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getT_stamp() {
        return t_stamp;
    }

    public void setT_stamp(String t_stamp) {
        this.t_stamp = t_stamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public Long getChat() {
        return chat;
    }

    public void setChat(Long chat) {
        this.chat = chat;
    }


}
