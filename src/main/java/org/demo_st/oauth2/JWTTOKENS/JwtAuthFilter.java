package org.demo_st.oauth2.JWTTOKENS;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.demo_st.oauth2.ENTITY.User;
import org.demo_st.oauth2.REPO.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private Validatetoken validatetoken;

    @Autowired
    private Userrepo userrepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("URI: " + request.getRequestURI());
        System.out.println("Auth Header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            System.out.println("Token: " + token);


            if (validatetoken.validate(token)) {
                System.out.println("JWT VALID");

                String email = validatetoken.extractEmail(token);
                System.out.println("Email from token: " + email);
                Optional<User> userOptional = userrepo.findByEmail(email);
//                if(!userOptional.isPresent()) {
//                    System.out.println("NO USER FOUND");
//                    return;
//                }
                //HERE WE CAN CHECK WEATHER THE EMAIL EXIST IN DB OR NOT
                //WE CAN CHECK TOKEN EXPIRATION ALSO MEANS VALIDATION ALREADY DONE
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email, null, userOptional.get().getAuthorities()
                        );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("JWT INVALID");
            }
        } else {
            System.out.println("NO AUTH HEADER");
        }

        filterChain.doFilter(request, response);
    }

}