package org.demo_st.oauth2.SECURITY;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.demo_st.oauth2.ENTITY.Role;
import org.demo_st.oauth2.ENTITY.User;
import org.demo_st.oauth2.JWTTOKENS.TokenGeneration;
import org.demo_st.oauth2.REPO.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler
        implements AuthenticationSuccessHandler {

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private TokenGeneration tokenGeneration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User user = token.getPrincipal();
        String email = user.getAttribute("email");
        String name  = user.getAttribute("name");
        Optional<User> emailoptional = userrepo.findByEmail(email);
        if(emailoptional.isPresent()) {
            User user1 = emailoptional.get();
            String token1 = tokenGeneration.generateToken(email);
            String refreshtoken = tokenGeneration.generaterefreshtoken(email);
            response.sendRedirect("http://localhost:5173/middle?token=" + token1 + "&refreshtoken=" + refreshtoken);
        }
        else {
            User user1 = new User();
            user1.setEmail(email);
            user1.setPassword(null);
            user1.setUsername(name);
            user1.setLogintype("google");
            user1.setRole(Role.USER);
            userrepo.save(user1);
            String token1 = tokenGeneration.generateToken(email);
            String refreshtoken = tokenGeneration.generaterefreshtoken(email);
            response.sendRedirect("http://localhost:5173/middle?token=" + token1 + "&refreshtoken=" + refreshtoken);
        }



    }
}
