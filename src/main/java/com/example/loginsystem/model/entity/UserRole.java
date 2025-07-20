package com.example.loginsystem.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Table( name = "userroles")
@Data
@Entity
@NoArgsConstructor
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_userrole_user"))
    private User user;



    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;  // 關聯到 Role 實體

    public UserRole( User user, Role role ) {
        this.user = user;
        this.role = role;
    }
}