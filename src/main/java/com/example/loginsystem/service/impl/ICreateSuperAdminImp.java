package com.example.loginsystem.service.impl;


import com.example.loginsystem.model.entity.Role;
import com.example.loginsystem.model.entity.User;
import com.example.loginsystem.model.entity.UserRole;
import com.example.loginsystem.repo.RolesRepo;
import com.example.loginsystem.repo.UserRepo;
import com.example.loginsystem.repo.UserRoleRepo;
import com.example.loginsystem.service.ICreateSuperAdmin;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ICreateSuperAdminImp implements ICreateSuperAdmin {

    private final UserRepo userRepository;
    private final UserRoleRepo userRoleRepo;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepo rolesRepo;

    public ICreateSuperAdminImp(UserRepo userRepository, UserRoleRepo userRoleRepo, PasswordEncoder passwordEncoder, RolesRepo rolesRepo) {
        this.userRepository = userRepository;
        this.userRoleRepo = userRoleRepo;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepo = rolesRepo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createASuperAdmin(String studentNum, String password, String email, String roleName) {
        if (!userRepository.existsByStudentNumber(Integer.parseInt(studentNum))) {
            User admin = new User();
            admin.setStudentNumber(Integer.parseInt(studentNum));
            admin.setPassword(passwordEncoder.encode(password));
            admin.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
            admin.setEmail(email); // SuperAdmin Email
            admin.setFirstName("Super");
            admin.setLastName("Admin");

            // set properties...
            userRepository.save(admin);

            Role superAdminRole = rolesRepo.findByRoleName(roleName)
                    .orElseGet(() -> rolesRepo.save(new Role(roleName)));

            userRoleRepo.save(new UserRole(admin, superAdminRole));
        }
    }
}
