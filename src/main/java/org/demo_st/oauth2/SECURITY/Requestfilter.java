    package org.demo_st.oauth2.SECURITY;

    import jakarta.servlet.http.HttpServletResponse;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.config.Customizer;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.demo_st.oauth2.JWTTOKENS.JwtAuthFilter;
    import org.springframework.stereotype.Component;

    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity
    public class Requestfilter {

        @Autowired
        private OAuth2LoginSuccessHandler successHandler;

        @Autowired
        private JwtAuthFilter jwtAuthFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http
                    .cors(Customizer.withDefaults())
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(
                                    "/authentication/**",
                                    "/token/**",
                                    "/oauth2/**"
                            ).permitAll()
                            .requestMatchers("/api/v1/**").authenticated()
//                            .requestMatchers("/api/v2").hasRole("ADMIN")
                            .anyRequest().denyAll()
                    )
                    .addFilterBefore(jwtAuthFilter,
                            UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling(ex -> ex
                            .authenticationEntryPoint((req, res, e) -> {
                                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                res.setContentType("application/json");
                                res.getWriter().write("{\"error\":\"Unauthorized\"}");
                            })
                            .accessDeniedHandler((req, res, e) -> {
                                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                res.setContentType("application/json");
                                res.getWriter().write("{\"error\":\"Forbidden\"}");
                            })
                    )

                    .oauth2Login(oauth -> oauth
                            .successHandler(successHandler)
                    );

            return http.build();
        }

    }
