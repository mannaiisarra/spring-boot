package com.spring.pfe.controllers;


import com.spring.pfe.models.*;
import com.spring.pfe.payload.request.LoginRequest;
import com.spring.pfe.payload.request.SignupRequest;
import com.spring.pfe.payload.response.JwtResponse;
import com.spring.pfe.payload.response.MessageResponse;
import com.spring.pfe.repository.RoleRepository;
import com.spring.pfe.repository.UserRepository;
import com.spring.pfe.security.jwt.JwtUtils;
import com.spring.pfe.security.services.UserDetailsImpl;
import com.spring.pfe.security.services.UserDetailsServiceImpl;
import com.spring.pfe.utils.StorageService;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.core.io.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private StorageService storageService;
    @Autowired
    private RoleRepository irole;
    private UserDetailsServiceImpl userService;

    private final Path rootLocation = Paths.get("uploads");



    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getAdress(),
                    userDetails.getPhone(),
                    userDetails.getPhoto(),
                    roles));



    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestParam("file") MultipartFile file, SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }



        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getPhone(),
                signUpRequest.getAdress(),
                signUpRequest.getPhoto(),

                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        System.out.println("here roles string : " + strRoles );
        System.out.println("here roles : " + signUpRequest.getRole() );
        Set<Role> roles = new HashSet<>();
        System.out.println("here roles string : " + roles );
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "2":
                        Role adminRole = roleRepository.findByName(ERole.APPRENANT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "1":
                        Role modRole = roleRepository.findByName(ERole.FORMATEUR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.APPRENANT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        String fileName =storageService.createNameImage(file);
        storageService.store(file,fileName);
     /*   user.setPhoto(file.getOriginalFilename());
        storageService.store(file,file.getOriginalFilename());*/

        user.setPhoto(fileName);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/")
    public Response<List<User>> findAllUsers (){
        // return /*iCategory.findAll();*/

        try {

            return new Response<List<User>>("200", "Get all Categories", userRepository.findAll());
        }catch (Exception e){
            return new Response<List<User>>("406", e.getMessage(), null);

        }
    }

    @GetMapping("/findByName/{username}")
    public Response<User> findByName (@PathVariable("username") String username){
        // return /*iCategory.findAll();*

        try {

            return new Response<User>("200", "Get all users", userRepository.getUserByNickname(username));
        }catch (Exception e){
            return new Response<User>("406", e.getMessage(), null);

        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public Response<User> deleteUser (@PathVariable("id") Long  id){

        userRepository.deleteById(id);
        try{
            return new Response<User>("200", "User deleted", null);
        } catch (Exception e){
            return new Response<User> ("406",e.getMessage(),null);
        }
    }

    @GetMapping("/findById/{id}")
    public Response<User> findById(@PathVariable Long id){
        try {

            return new Response<User>("200", "Get all users", userRepository.findById(id).orElse(null));
        }catch (Exception e){
            return new Response<User>("406", e.getMessage(), null);

        }
    }

    @PutMapping("/updateUser/{id}")
    public Response<User> updateUsers(@PathVariable("id") Long id, @RequestBody User c) {
        try {
            // System.out.println("here update  id is "+id+" aadress is  "+c.getAdress());
            //User oldUser = new User();
            User oldUser = userRepository.findById(id).orElse(null);
            c.setUsername(c.getUsername() == null ? oldUser.getUsername() : c.getUsername());
            c.setEmail(c.getEmail() == null ? oldUser.getEmail() : c.getEmail());
            c.setAdress(c.getAdress() == null ? oldUser.getAdress() : c.getAdress());
            c.setPhone(c.getPhone() == null ? oldUser.getPhone() : c.getPhone());
            c.setPassword(oldUser.getPassword());
            c.setRoles(oldUser.getRoles());
            c.setPhoto(c.getPhoto() == null ? oldUser.getPhoto() : c.getPhoto());
            c.setId(id);
            return new Response<User>("200","User updated", userRepository.save(c));




        }catch (Exception e){

            return new Response<User>("406", e.getMessage(), null);

        }
    }
    @PostMapping("/add")
    public Response<User> addUser(User c, @RequestParam("file") MultipartFile file) {

        Date d=new Date();
        String date=""+d.getDay()+d.getMonth()+d.getYear()+d.getHours()+d.getMinutes()+d.getSeconds();
        c.setPhoto(date+file.getOriginalFilename());
        storageService.store(file,date+file.getOriginalFilename());
        return  new Response<User>("200","create user",userRepository.save(c));
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
    @GetMapping("/roles")
    public Response<List<Role>> findAllRoles (){
        // return /*iCategory.findAll();*/

        try {

            return new Response<List<Role>>("200", "Get all Categories", irole.findAll());
        }catch (Exception e){
            return new Response<List<Role>>("406", e.getMessage(), null);

        }
    }


    @PutMapping("/updatePhoto/{id}")
    public Response<User> updatePhoto(@PathVariable("id") Long id, User f, @RequestParam("file") MultipartFile file) {
        try {

            User oldUser = userRepository.findById(id).orElse(null);


            oldUser.setPhoto(file.getOriginalFilename());
            storageService.store(file,file.getOriginalFilename());
            return new Response<User>("200","user photo updated", userRepository.save(oldUser));
        }catch (Exception e){
            return new Response<User>("406", e.getMessage(), null);

        }
    }
/*
    @PostMapping(path="/updateUser/{id}")
    public Response<User> updatePassword(@PathVariable("id") Long id,@RequestBody User c) {



        User oldUser = userRepository.findById(id).orElse(null);

       c.setUsername(c.getUsername() == null ? oldUser.getUsername() : c.getUsername());
        c.setEmail(c.getEmail() == null ? oldUser.getEmail() : c.getEmail());
      c.setAdress(c.getAdress() == null ? oldUser.getAdress() : c.getAdress());
      c.setPhone(c.getPhone() == null ? oldUser.getPhone() : c.getPhone());

        c.setPassword(c.getNewPassword());
        c.setNewPassword(c.getNewPassword());

       c.setRoles(oldUser.getRoles());
      c.setPhoto(c.getPhoto() == null ? oldUser.getPhoto() : c.getPhoto());
        c.setId(id);
        passwordEncoder.encode(c.getPassword());
        passwordEncoder.encode(c.getPassword());

        return new Response<User>("200","User updated", userRepository.save(c));

    }


*/



    @GetMapping("/videos/{fileName}")
    public ResponseEntity<ResourceRegion> getVideos(@PathVariable String fileName, @RequestHeader HttpHeaders headers) {
        try {
          //  Resource file = storageService.loadFile(fileName);

           // Logger log = LoggerFactory.getLogger(this.getClass().getName());
            Path file = rootLocation.resolve(fileName);
            //Resource resource = new UrlResource(file.toUri());
            UrlResource video = new UrlResource(file.toUri());
            Tika tika = new Tika();
            ResourceRegion region = resourceRegion(video, headers);

            ResponseEntity<ResourceRegion> response = ResponseEntity
                    .status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(MediaType.valueOf(tika.detect(video.getFile())))
                    .body(region);

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResourceRegion resourceRegion(UrlResource video, HttpHeaders headers) throws IOException {
        Long rangeLength;
        Long contentLength = video.contentLength();
        HttpRange range = headers.getRange().isEmpty() ? null : headers.getRange().get(0);
        if (null != range) {
            Long start = range.getRangeStart(contentLength);
            Long end = range.getRangeEnd(contentLength);
            rangeLength = Math.min(1024 * 1024L, end - start + 1);
            return new ResourceRegion(video, start, rangeLength);
        } else {
            rangeLength = Math.min(1024 * 1024L, contentLength);
            return new ResourceRegion(video, 0, rangeLength);
        }
    }



}
