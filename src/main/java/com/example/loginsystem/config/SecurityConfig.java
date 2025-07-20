package com.example.loginsystem.config;


<<<<<<< HEAD

import com.example.loginsystem.filter.JwtAuthorizationFilter;
import jakarta.annotation.PostConstruct;
=======
import com.example.loginsystem.service.UserService;
import com.example.loginsystem.service.imp.UserDetailsServiceImpl;
>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
<<<<<<< HEAD
import org.springframework.security.core.userdetails.UserDetails;
=======
>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;


@Slf4j
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig   {

    /*
    1. SecurityFilterChain     springboot auto configure ProxyFilterChain use it
    2. PasswordEncoder          Password encode
    3. Authentication Provider  return Authentication in bean and Authentication could use it
    4. AuthenticationManager    this would use UserDetails to get authentication
    5. 	UserDetailsService      search user from DB

<<<<<<< HEAD

private final JwtAuthorizationFilter jwtAuthenticationFilter;


    @Autowired
    public SecurityConfig(JwtAuthorizationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


=======
     */
>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180

    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorizeRequests ->
                        {
                            authorizeRequests
                                    .requestMatchers("/api/auth/login").permitAll()
                                    .requestMatchers("/api/auth/signUp/**").permitAll()
                                    .requestMatchers("/api/auth/createAdmin").hasAuthority("ROLE_SUPER_ADMIN")
                                    .requestMatchers("/api/auth/invite-teacher").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                                    .requestMatchers("/api/auth/forget-password").hasAnyAuthority("ROLE_STUDENT","ROLE_TEACHER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                                    .requestMatchers("/api/auth/change-password").hasAnyAuthority("ROLE_STUDENT","ROLE_TEACHER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                                    .requestMatchers("/api/auth/**").authenticated()
                                    .anyRequest().authenticated(); // the others request need auth
                        });
                http.httpBasic( Customizer.withDefaults())
                        .sessionManagement( httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy( SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-CSRF-TOKEN"));
        config.setAllowCredentials(true);

<<<<<<< HEAD
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
=======
    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new UserDetailsServiceImpl(userService);
>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180
    }

    /****
     *   this would search authentication type automatically in bean
     *
     ****/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider authProvider  = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
