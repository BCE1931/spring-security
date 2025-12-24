package org.demo_st.oauth2.CONTROLLER;

import org.demo_st.oauth2.ENTITY.Permissions;
import org.demo_st.oauth2.ENTITY.Role;
import org.demo_st.oauth2.ENTITY.User;
import org.demo_st.oauth2.JWTTOKENS.TokenGeneration;
import org.demo_st.oauth2.JWTTOKENS.Validatetoken;
import org.demo_st.oauth2.REPO.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/authentication")
public class Controller {

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private Validatetoken validatetoken;

    @Autowired
    private TokenGeneration tokenGeneration;

    @GetMapping("/public")
    public String hello() {
        return "hello";
    }

    @GetMapping("/private")
    public String hello1() {
        return "hello1 private";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        Optional<User> email = userrepo.findByEmail(user.getEmail());
        if(email.isPresent()) {
            return ResponseEntity.ok("user already registered");
        }
        else{
            User user1 = new User();
            user1.setEmail(user.getEmail());
            user1.setPassword(user.getPassword());
            user1.setLogintype("manual");
            user1.setUsername(user.getUsername());
            user1.setRole(Role.USER);
            userrepo.save(user1);
            String token = tokenGeneration.generateToken(user.getEmail());
            String refreshtoken = tokenGeneration.generaterefreshtoken(user.getEmail());
            HashMap<String , String> map = new HashMap<>();
            map.put("token", token);
            map.put("refreshtoken", refreshtoken);
            map.put("msg" , "user registered successfully");
            return ResponseEntity.ok(map);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        Optional<User> optionalUser = userrepo.findByEmail(user.getEmail());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.ok("Email does not exist");
        }
        User dbUser = optionalUser.get();
        if("google".equalsIgnoreCase(dbUser.getLogintype()) && (dbUser.getPassword() == null || dbUser.getPassword().isBlank())) {
            return ResponseEntity.ok("Try Google Login and create password");
        }
        if(dbUser.getPassword() != null && dbUser.getPassword().equals(user.getPassword())) {
            String token = tokenGeneration.generateToken(user.getEmail());
            String refreshtoken = tokenGeneration.generaterefreshtoken(user.getEmail());
            HashMap<String , String> map = new HashMap<>();
            map.put("token", token);
            map.put("refreshtoken", refreshtoken);
            map.put("msg" , "User logged in successfully");
            return ResponseEntity.ok(map);
        }
        if("google".equalsIgnoreCase(dbUser.getLogintype())) {
            return ResponseEntity.ok("Try Google Login");
        }
        return ResponseEntity.ok("Check password or email");
    }

}
