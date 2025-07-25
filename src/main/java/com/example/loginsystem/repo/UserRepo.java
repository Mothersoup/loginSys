package com.example.loginsystem.repo;

import com.example.loginsystem.model.entity.User;
import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.annotation.RetentionPolicy;
import java.util.Optional;

/*
@Repository
this is optional Jap will automatically generate sql command
 */

// 依然可以自己寫sql command 當需要處理複雜邏輯時
public interface UserRepo extends JpaRepository<User, Integer> {
    //@Query("SELECT u FROM User u WHERE u.studentNumber = :userId")
    // student number 就是userId 因為怕跟id 搞混改用student number
<<<<<<< HEAD
    @Query("SELECT u FROM User u WHERE u.studentNumber = :user_id")
    Optional<User> findByStudentNumber( @Param("user_id") Integer studentNumber);

=======
    Optional<User> findByStudentNumber(String studentNumber);
    Optional<User> findUserByEmail(String email );
>>>>>>> 44cc6f89f907b34ea580ca5525ff27e69c424180

    boolean existsByStudentNumber(  Integer studentNumber);


    boolean existsByEmail(String email);
}
