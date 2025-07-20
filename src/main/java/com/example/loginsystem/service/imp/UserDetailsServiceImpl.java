package com.example.loginsystem.service.imp;

import com.example.loginsystem.model.entity.User;
import com.example.loginsystem.service.UserService;
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
    public UserDetailsServiceImpl(UserService userService){
        this.userService = userService;
    }

    @Override
    // because interface define can't change args here
    //  parameter user_id
public UserDetails loadUserByUsername(String user_id ) throws UsernameNotFoundException {
        return toUserDetails(userService.findStudentByStudentNum( user_id ));
    }

    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.withUsername( user.getStudentNumber())
                .password(user.getPassword())
                .build();
    }


}
