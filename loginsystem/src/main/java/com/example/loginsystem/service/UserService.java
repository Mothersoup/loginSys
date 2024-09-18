package com.example.loginsystem.service;


import com.example.loginsystem.exception.NotFoundException;
import com.example.loginsystem.model.entity.User;
import com.example.loginsystem.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    // id means type of primary key
    // leave exception for controller
    public User findStudentById(int id ){
        return this.userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found with ID: " + id) );
                
    }

    @Transactional
    public User updateStudent(int id, User user) {
        if (!this.userRepo.existsById(id)) {
            throw new NotFoundException("Student not found with ID: " + id);
        }
        user.setId(id);
        return this.userRepo.save(user);
    }


    @Transactional
    public void saveStudent( User user){
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





}
