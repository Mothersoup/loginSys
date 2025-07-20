package com.example.loginsystem.config;

import com.example.loginsystem.service.ICreateSuperAdmin;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class AdminAccountInitializer {



    private final ICreateSuperAdmin createSuperAdminService;

    public AdminAccountInitializer(ICreateSuperAdmin createSuperAdminService) {

        this.createSuperAdminService = createSuperAdminService;
    }
    @PostConstruct
    public void initSuperAdmin() {
        this.createSuperAdminService.createASuperAdmin("10000","SuperAdmin", "SuperAdmin@email.com", "ROLE_SUPER_ADMIN" );

        }
    }
