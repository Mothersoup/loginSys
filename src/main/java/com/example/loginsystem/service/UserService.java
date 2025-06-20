package com.example.loginsystem.service;


import com.example.loginsystem.exception.NotFoundException;
import com.example.loginsystem.mapper.UserMapper;
import com.example.loginsystem.dto.SignUpDTO;
import com.example.loginsystem.model.entity.User;
import com.example.loginsystem.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    private final UserValidationService userValidationService;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, UserMapper userMapper, UserValidationService
                       userValidationService){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userValidationService = userValidationService;
    }

    // id means type of primary key
    // leave exception for controller
    public User findStudentById(int id ){
        return this.userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with ID: " + id) );

    }


    public User findStudentByStudentNum( String studentNum ){
        return this.userRepo.findByStudentNumber(studentNum)
            .orElseThrow(() -> new NotFoundException("Student not found with Student Number: " + studentNum));
    }

    /*
    transactional interfere with db operation if cause error this operation would roll back 
     */
    @Transactional
    public User updateStudent(int id, User user) {
        if (!this.userRepo.existsById(id)) {
            throw new NotFoundException("Student not found with ID: " + id);
        }
        user.setId(id);
        return this.userRepo.save(user);
    }
    @Transactional
    public void RegisterNewStudent(SignUpDTO signUpDTO){
        int year = LocalDate.now().getYear();
        userValidationService.validateEmailUniqueness(signUpDTO.getEmail());
        User user = userMapper.toEntity(signUpDTO);
        saveStudent(user);

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
    /*
       handle null in Service
       public Optional<Student> findStudentById( int id ){
        return this.studentRepo.findById( id )
                                .orElseThrow(() -> new NotFoundException(id));;
    }

     */

    public User mapSignUpDTOToUser(SignUpDTO signUpDTO) {

        return userMapper.toEntity(signUpDTO);
    }




}
