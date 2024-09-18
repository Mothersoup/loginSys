package com.example.loginsystem.service;

import com.example.loginsystem.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private static List<User> users = new ArrayList<>();
    @Autowired
    public UserDetailsServiceImpl(UserService userService){
        this.userService = userService;
    }

    @Override
    // because interface define can't change args here
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        int userId = Integer.parseInt(username); // Convert the username to an int
        User user = userService.findStudentById( userId );
        return toUserDetails(user);

    }

    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.withUsername( String.valueOf(user.getId()))
                .password(user.getPassword())
                .build();
    }
}
