package com.example.loginsystem.config;


import com.example.loginsystem.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Slf4j
@Configuration
public class SecurityConfig   {






    public SecurityConfig() {
    }








    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors( AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorizeRequests ->
                        {
                            authorizeRequests
                                    .requestMatchers("/api/**", "/api/auth/register", "/signup/").permitAll()
                                    .requestMatchers("/api/auth/login").permitAll()
                                    .anyRequest().authenticated(); // the others request need auth
                        });
                http.httpBasic( Customizer.withDefaults())
                        .sessionManagement( httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy( SessionCreationPolicy.STATELESS));


        return http.build();
    }




    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
