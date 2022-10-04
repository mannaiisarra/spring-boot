package com.spring.pfe.controllers;

import com.spring.pfe.models.*;
import com.spring.pfe.repository.CodeVerificationRepository;
import com.spring.pfe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/forgot")
@CrossOrigin("*")
public class ForgotPasswordController {


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

   @Autowired
    private CodeVerificationRepository codeVerification;

    @Autowired
    PasswordEncoder encoder;
    @GetMapping("/")
    public void findAllDemande () {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("labidii.najett@gmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);

        System.out.println("email done");
    }


    @RequestMapping(value="/send", method= RequestMethod.POST)
    public Response<User> sendPWToEmail(@RequestBody User u){
        try {
        User userOptional = userRepository.findUserByEmail(u.getEmail());
        if(userOptional != null){
            User user =userRepository.findUserByEmail(u.getEmail());
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(u.getEmail());

            msg.setSubject("Testing from Spring Boot");
            Random randomGenerator = new Random();
            int rand =randomGenerator.nextInt(10_000);
            msg.setText("code : " + rand);

            user.setCode(rand);


            System.out.println(u.getEmail());
            javaMailSender.send(msg);

            System.out.println("email done");
            //userRepository.save(user);

            return new Response<User>("200","Send Code",    userRepository.save(user) );

        }
        }


        catch (Exception e){
            return new Response<User>("406","Not found user",   null );

        }
        return new Response<User>("406","Not found user",   null );

    }

    @RequestMapping(value="/add/{userss_id}", method= RequestMethod.POST)
    public Response<CodeVerification> addEtape(@RequestBody CodeVerification c,@PathVariable Long userss_id) {
        try {



            User t = userRepository.findById(userss_id).orElse(null);
            c.setUsers(t);

            //  p.setNameEtapes(n);

            return new Response<CodeVerification>("200", "Creat Etape", codeVerification.save(c));

        } catch (Exception e) {
            return new Response<CodeVerification>("406", e.getMessage(), null);
        }

    }

    @GetMapping("/Allformation")
    public Response<List<CodeVerification>> findAllFormation () {
        return new Response<List<CodeVerification>>("200", "Get all Formation", codeVerification.findAll());
    }


    @PostMapping(value="/findByiD")
    public Response<User> findByIdCode(@RequestBody User user) {


                User u = userRepository.findUserByCode(user.getCode());

            // System.out.println(u.getEmail());
            return new Response<User>("200", "Find Code By id", userRepository.findById(u.getId()).orElse(null));






    }



    @PostMapping(value="/Rest/{id}")
    public Response<User> RestPassword( @PathVariable("id") Long id,@RequestBody User u ) {
        try {



            User userOp = userRepository.findById(id).orElse(null);

                userOp.setPassword(encoder.encode(u.getPassword()) == null ? encoder.encode(userOp.getPassword()) : encoder.encode(u.getPassword()));
                encoder.encode(encoder.encode(u.getPassword()) == null ? encoder.encode(userOp.getPassword()) : encoder.encode(u.getPassword()));
                //  p.setNameEtapes(n);


                return new Response<User>("200", "Rest Password", userRepository.save(userOp));

        } catch (Exception e) {
            return new Response<User>("406", e.getMessage(), null);
        }
    }

@PutMapping("/updateUser/{id}")
public Response<User> updateUsers(@PathVariable("id") Long id, @RequestBody User c) {
    try {

        User oldUser = userRepository.findById(id).orElse(null);
        c.setUsername(c.getUsername() == null ? oldUser.getUsername() : c.getUsername());
        c.setEmail(c.getEmail() == null ? oldUser.getEmail() : c.getEmail());
        c.setAdress(c.getAdress() == null ? oldUser.getAdress() : c.getAdress());
        c.setAdress(c.getPhone() == null ? oldUser.getPhone() : c.getPhone());
        c.setPassword(c.getUsername() == null ? oldUser.getUsername() : c.getUsername());
        c.setRoles(oldUser.getRoles());
        c.setPhoto(c.getPhoto() == null ? oldUser.getPhoto() : c.getPhoto());
        c.setId(id);
        return new Response<User>("200","User updated", userRepository.save(c));




    }catch (Exception e){

        return new Response<User>("406", e.getMessage(), null);

    }
}




}
