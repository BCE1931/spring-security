package org.demo_st.oauth2.SECURITY;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173" , "https://0auth2-frontend.vercel.app" , "https://www.revise.codes")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE" ,"OPTIONS")
                .allowCredentials(true);
    }
}
