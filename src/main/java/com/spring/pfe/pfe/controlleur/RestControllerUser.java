package com.spring.pfe.pfe.controlleur;

import com.spring.pfe.pfe.common.DeviceProvider;
import com.spring.pfe.pfe.dao.IAuthority;
import com.spring.pfe.pfe.dao.IUser;
import com.spring.pfe.pfe.models.Authority;
import com.spring.pfe.pfe.models.User;
import com.spring.pfe.pfe.models.UserTokenState;
import com.spring.pfe.pfe.security.TokenHelper;
import com.spring.pfe.pfe.security.auth.JwtAuthenticationRequest;
import com.spring.pfe.pfe.utils.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestControllerUser {
    private static final Logger logger = LoggerFactory.getLogger(RestController.class);


    TokenHelper tokenHelper;
    @Autowired
    private StorageService storage;

    private AuthenticationManager authenticationManager;


    private UserDetailsService userDetailsService;

    @Autowired
    private DeviceProvider deviceProvider;

    @Autowired
    private IUser iUser;

    @Autowired
    private IAuthority iAuthority;

    @RequestMapping("/")
    public void home(Device device) {
        if (device.isMobile()) {
            logger.info("Hello mobile user!");
        } else if (device.isTablet()) {
            logger.info("Hello tablet user!");
        } else {
            logger.info("Hello desktop user!");
        }
    }

    @RequestMapping("/register")
    public ResponseEntity<?> register(User user, @RequestParam("file") MultipartFile file )
    {
        Authority authority=new Authority();
        authority.setName("USER");
        iAuthority.save(authority);

        user.setEnabled(true);
        user.setAuthorities(authority);
        user.setPassword(hash(user.getPassword()));
        String fileName = storage.fileName(file);
        storage.store(file,fileName);
        user.setImage(file.getOriginalFilename());

        iUser.save(user);

        return ResponseEntity.ok(new UserTokenState(null, 0, user));

    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
                                                       HttpServletResponse response, Device device
    ) throws AuthenticationException, IOException {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        // Inject into security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // token creation
        User user = (User) authentication.getPrincipal();

        String jws = tokenHelper.generateToken(user.getUsername(), device);
        int expiresIn = tokenHelper.getExpiredIn(device);
        // Add cookie to response
        response.addCookie(createAuthCookie(jws, expiresIn));
        // Return the token
        return ResponseEntity.ok(new UserTokenState(jws, expiresIn, user));
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response, Principal principal) {

        String authToken = tokenHelper.getToken(request);
        Device device = deviceProvider.getCurrentDevice(request);
        if (authToken != null && principal != null) {

            // TODO check user password last update
            String refreshedToken = tokenHelper.refreshToken(authToken, device);
            int expiresIn = tokenHelper.getExpiredIn(device);
            //  User user = (User) authentication.getPrincipal();
            // Add cookie to response
            response.addCookie(createAuthCookie(refreshedToken, expiresIn));
            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn, null));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.accepted().body(userTokenState);
        }
    }

    private Cookie createAuthCookie(String jwt, int expiresIn) {
        Cookie authCookie = new Cookie(tokenHelper.AUTH_COOKIE, (jwt));
        authCookie.setPath("/");
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(expiresIn);
        return authCookie;
    }

    String hash(String password) {


        String hashedPassword = null;
        int i = 0;
        while (i < 5) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            hashedPassword = passwordEncoder.encode(password);
            i++;
        }

        return hashedPassword;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storage.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}


