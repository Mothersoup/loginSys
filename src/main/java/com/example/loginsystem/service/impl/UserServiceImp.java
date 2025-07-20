package com.example.loginsystem.service.impl;


import com.example.loginsystem.ex.ServiceException;
import com.example.loginsystem.pojo.mapper.UserMapper;
import com.example.loginsystem.pojo.dto.SignUpDTO;
import com.example.loginsystem.model.entity.Role;
import com.example.loginsystem.model.entity.TeacherCodes;
import com.example.loginsystem.model.entity.User;
import com.example.loginsystem.model.entity.UserRole;
import com.example.loginsystem.repo.RolesRepo;
import com.example.loginsystem.repo.TeacherCodesRepo;
import com.example.loginsystem.repo.UserRepo;
import com.example.loginsystem.repo.UserRoleRepo;
import com.example.loginsystem.service.IUserService;
import com.example.loginsystem.service.UserValidationService;
import com.example.loginsystem.web.ServerCode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class UserServiceImp implements IUserService {
    private final UserRepo userRepo;

    private final RolesRepo roleRepo;

    private final UserRoleRepo userRoleRepo;
    private final UserValidationService userValidationService;
    private final UserMapper userMapper;

    private final TeacherCodesRepo teacherCodesRepo;


    @Autowired
    public UserServiceImp(UserRepo userRepo, RolesRepo roleRepo, UserRoleRepo userRoleRepo, UserMapper userMapper, UserValidationService
                       userValidationService, TeacherCodesRepo teacherCodesRepo){
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
        this.roleRepo = roleRepo;
        this.userMapper = userMapper;
        this.userValidationService = userValidationService;
        this.teacherCodesRepo = teacherCodesRepo;
    }



    @Transactional
    public User findStudentByStudentNum(Integer studentNum ){
        return this.userRepo.findByStudentNumber(studentNum)
            .orElseThrow(() -> new ServiceException(ServerCode.ERR_NOT_FOUND, "Student not found with Student Number: " + studentNum));

    }

    /*
    transactional interfere with db operation if cause error this operation would roll back 
     */
    @Transactional
    public User updateStudent(int id, User user) {
        if (!this.userRepo.existsById(id)) {
            throw new ServiceException(ServerCode.ERR_NOT_FOUND, "User not found with ID: " + id);
        }
        user.setId(id);
        return this.userRepo.save(user);
    }
    @Transactional
    public void RegisterNewUser(SignUpDTO signUpDTO){
        userValidationService.validateEmailUniqueness(signUpDTO.getEmail());
        userValidationService.validateStudentNumberUniqueness(Integer.parseInt(signUpDTO.getStudentNumber()));
        User user = userMapper.toEntity(signUpDTO);
        if (signUpDTO.getTeacherUniqueCode() == null || signUpDTO.getTeacherUniqueCode().isBlank() || signUpDTO.getTeacherUniqueCode().isEmpty()) {
            Role role = roleRepo.findByRoleName("ROLE_STUDENT")
                    .orElseGet(() -> roleRepo.save(new Role("ROLE_STUDENT")));

            UserRole userRole = new UserRole(user, role);
            saveStudent(user);
            userRoleRepo.save(userRole);

        }else {
            // input teacher code is not null or blank
            Role role = roleRepo.findByRoleName("ROLE_TEACHER")
                    .orElseGet(() -> roleRepo.save(new Role("ROLE_TEACHER")));
            UserRole userRole = new UserRole(user, role);
            saveStudent(user);
            teacherCodesRepo.updateUserByTeacherCode(signUpDTO.getTeacherUniqueCode(), user);
            userRoleRepo.save(userRole);
        }



    }

    @Transactional
    public void saveStudent( User user){
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        this.userRepo.save(user);
    }

    @Transactional
    public void deleteStudent( User user){
        this.userRepo.delete(user);

    }

    public boolean checkIfEmailExists( String email ){
        return this.userRepo.existsByEmail(email);

    }


    public User mapSignUpDTOToUser(SignUpDTO signUpDTO) {

        return userMapper.toEntity(signUpDTO);
    }

    @Override
    public void RegisterNewAdmin(SignUpDTO signUpDTO) {
        userValidationService.validateEmailUniqueness(signUpDTO.getEmail());
        User user = userMapper.toEntity(signUpDTO);
        Role role = roleRepo.findByRoleName("ROLE_ADMIN")
                .orElseGet(() -> roleRepo.save(new Role("ROLE_ADMIN")));

        UserRole userRole = new UserRole(user, role);
        saveStudent(user);
        userRoleRepo.save(userRole);
    }

    @Override
    public void RegisterNewTeacher(SignUpDTO signUpDTO) {
        userValidationService.validateEmailUniqueness(signUpDTO.getEmail());
        User user = userMapper.toEntity(signUpDTO);
        Role role = roleRepo.findByRoleName("ROLE_TEACHER")
                .orElseGet(() -> roleRepo.save(new Role("ROLE_TEACHER")));

        if ( teacherCodesRepo.existsByTeacherCode(signUpDTO.getTeacherUniqueCode()) ){
            throw new ServiceException(ServerCode.ERR_TEACHER_CODE_HAS_BEEN_USED, "Teacher code already exists: " + signUpDTO.getTeacherUniqueCode());
        }
        UserRole userRole = new UserRole(user, role);
        saveStudent(user);
        userRoleRepo.save(userRole);
    }

    @Override
    public String generateTeacherCode() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final int CODE_LENGTH = 15;
        final SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        teacherCodesRepo.save( new TeacherCodes( sb.toString()));
        return sb.toString();
    }


}
