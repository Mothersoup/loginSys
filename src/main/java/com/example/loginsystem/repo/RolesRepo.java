package com.example.loginsystem.repo;

import com.example.loginsystem.model.entity.Role;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;

import java.util.Optional;

public interface RolesRepo extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(String roleName);

    @NotNull Optional<Role> findById(@NotNull Integer id );
    boolean existsByRoleName(String roleName);

}
