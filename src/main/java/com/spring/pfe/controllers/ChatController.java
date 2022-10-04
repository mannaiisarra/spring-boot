package com.spring.pfe.controllers;

import com.spring.pfe.models.*;
import com.spring.pfe.repository.ChatDAO;
import com.spring.pfe.repository.MessageDAO;
import com.spring.pfe.repository.MessageEntityRepository;
import com.spring.pfe.repository.UserRepository;
import com.spring.pfe.utils.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)

public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatDAO chatDAO;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageEntityRepository messageDAO;

    @Autowired
    private StorageService storageService;

    @MessageMapping("/chat/{to}") //to = nome canale
    public void sendMessage(@DestinationVariable String to , MessageEntity message) {

        System.out.println("handling send message: " + message + " to: " + to);
        message.setChat(createAndOrGetChat(to));
        message.setT_stamp(generateTimeStamp());
       

        message = messageDAO.save(message);

        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);


    }
    @PostMapping("/getChats/{chat}")
    public List<ChatEntity> getChats(@PathVariable Long chat){
        return chatDAO.findByPartecipant(chat);
    }
    @GetMapping("/findchat/{name}")
    public ChatEntity finddChatByName(@PathVariable String name){


            return chatDAO.findByName(name);


    }

    //returns an empty list if the chat doesn't exist
  /*  @PostMapping("/getMessages")
    public List<MessageEntity> getMessages(@RequestBody String chat) {
        ChatEntity ce = chatDAO.findByName(chat);

        if(ce != null) {
            return messageDAO.findAllByChat(ce.getChat_id());
        }
        else{
            return new ArrayList<MessageEntity>();
        }
    }*/
    //returns an empty list if the chat doesn't exist
    @PostMapping("/getMessages/{username}/{other}")
    public List<MessageEntity> getMessages(@PathVariable("username") String username,@PathVariable("other") String other,@RequestBody String chat) {


        ChatEntity ce = chatDAO.findByName(chat);
        if(ce!=null) {
            User ces = userRepository.getUserByNickname(username);
            User ot = userRepository.getUserByNickname(other);
            ce.setOther(ot.getUsername());
            ce.setPhoto(ot.getPhoto());
            ce.setUser(ces);

            chatDAO.save(ce);
            return messageDAO.findAllByChat(ce.getChat_id());

        }
        return null;


    }
    //finds the chat whose name is the parameter, if it doesn't exist it gets created, the ID gets returned either way
    private Long createAndOrGetChat(String name) {
        ChatEntity ce = chatDAO.findByName(name);




        if (ce != null) {

            return ce.getChat_id();
        }
        else {


            ChatEntity newChat = new ChatEntity(name);


            System.out.println(newChat);
            return chatDAO.save(newChat).getChat_id();
        }
    }

    private String generateTimeStamp() {
        Instant i = Instant.now();
        String date = i.toString();
        System.out.println("Source: " + i.toString());
        int endRange = date.indexOf('T');
        date = date.substring(0, endRange);
        date = date.replace('-', '/');
        System.out.println("Date extracted: " + date);
        String time = Integer.toString(i.atZone(ZoneOffset.UTC).getHour() + 1);
        time += ":";

        int minutes = i.atZone(ZoneOffset.UTC).getMinute();
        if (minutes > 9) {
            time += Integer.toString(minutes);
        } else {
            time += "0" + Integer.toString(minutes);
        }

        System.out.println("Time extracted: " + time);
        String timeStamp = date + "-" + time;
        return timeStamp;
    }

    @GetMapping("/findByid/{users_id}")
    public Response<List<ChatEntity>> GetAllchatByUser( @PathVariable("users_id") Long users_id) {
        try {
            return new Response<List<ChatEntity>>("200", "Get all chat by id", chatDAO.getAllchatByUsers(users_id));

        } catch (Exception e) {
            return new Response<List<ChatEntity>>("406", e.getMessage(), null);
        }

    }

    @GetMapping("/findChatById/{chat}")
    public Response<List<MessageEntity>> GetAllMessageByChat( @PathVariable("chat") Long chat) {
        try {
            return new Response<List<MessageEntity>>("200", "Get all message by chat", messageDAO.findAllByChat(chat));

        } catch (Exception e) {
            return new Response<List<MessageEntity>>("406", e.getMessage(), null);
        }

    }

}

