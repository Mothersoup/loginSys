package com.example.loginsystem.security;

import com.example.loginsystem.ex.ServiceException;
import com.example.loginsystem.model.entity.User;
import com.example.loginsystem.repo.RolesRepo;
import com.example.loginsystem.service.IUserRoleRepo;
import com.example.loginsystem.service.IUserService;
import com.example.loginsystem.web.ServerCode;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final IUserService iUserService;

    private final IUserRoleRepo iUserRoleRepo;

    private final RolesRepo rolesRepo;

    // private static List<User> users = new ArrayList<>();
    @Autowired
    public UserDetailsServiceImpl(IUserService iUserService, IUserRoleRepo iUserRoleRepo, RolesRepo rolesRepo){
        this.iUserService = iUserService;
        this.iUserRoleRepo = iUserRoleRepo;
        this.rolesRepo = rolesRepo;
    }



    @Override
    // because interface define can't change args here
    // 這邊的 userID 這邊裡面的參數是 studentNumber
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Integer studentNumber = Integer.parseInt(username);
            User user = iUserService.findStudentByStudentNum(studentNumber);
            if (user == null) {
                throw new ServiceException(ServerCode.ERR_NOT_FOUND, "User not found：" + username);
            }
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add( new SimpleGrantedAuthority (  iUserRoleRepo.getUserRoleByUserId(studentNumber).getRole().getRoleName() ));
            return new AdminDetails(user.getStudentNumber().toString(), user.getPassword(), authorities  );
        } catch (NumberFormatException e) {
            logger.warn("Not Number Form: {}", username);
            throw new UsernameNotFoundException("User not found：" + username);
        }


    }

    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.withUsername( String.valueOf(user.getStudentNumber()))
                .password(user.getPassword())
                .build();
    }



}
