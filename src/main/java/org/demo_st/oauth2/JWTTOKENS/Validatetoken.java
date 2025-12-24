package org.demo_st.oauth2.JWTTOKENS;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class Validatetoken {

    private Key Key;

    @Value("${jwt-secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    @Value("${jwt.refresh-expiration}")
    private long refreshexpiry;



    @PostConstruct
    public void init() {
        this.Key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    //both expiration and signature check done here it self
    public boolean validate(String token){
        try {
            Jwts.parserBuilder().setSigningKey(Key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


}
