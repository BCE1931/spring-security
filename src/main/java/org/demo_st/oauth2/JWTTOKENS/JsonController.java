package org.demo_st.oauth2.JWTTOKENS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class JsonController {
    @Autowired
    private TokenGeneration tokenGeneration;

    @Autowired
    private Validatetoken validatetoken;

    @GetMapping("/tokengen/{username}")
    public ResponseEntity<?> tokengenlogin(@PathVariable String username) {
        System.out.println("Token generation requested for: " + username);
        String token = tokenGeneration.generateToken(username);
        String refreshtoken = tokenGeneration.generaterefreshtoken(username);
        return ResponseEntity.ok(Map.of("token", token, "refreshtoken", refreshtoken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshtoken");
        String email = validatetoken.extractEmail(refreshToken.substring(7));
        System.out.println("Refresh Token generation requested for: " + email);
        String token = tokenGeneration.generateToken(email);
        String refreshtoken = tokenGeneration.generaterefreshtoken(email);
        return ResponseEntity.ok(Map.of("token", token, "refreshtoken", refreshtoken));
    }
}
