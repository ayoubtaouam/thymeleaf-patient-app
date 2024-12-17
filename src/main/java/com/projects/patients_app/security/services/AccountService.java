package com.projects.patients_app.security.services;

import com.projects.patients_app.security.dtos.AppUserDto;
import com.projects.patients_app.security.entities.AppRole;
import com.projects.patients_app.security.entities.AppUser;
import com.projects.patients_app.security.repositories.AppRoleRepository;
import com.projects.patients_app.security.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AccountService {
    private final AppUserRepository appUserRepository;
    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user != null) throw new RuntimeException("This User already exists!");
        if (!password.equals(confirmPassword)) throw new RuntimeException("Password doesn't match");
        user = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        appUserRepository.save(user);
        return user;
    }

    public AppRole addNewRole(String role){
        if (appRoleRepository.findById(role).isPresent()) throw new RuntimeException("Role already exists");
        return appRoleRepository.save(AppRole.builder().role(role).build());
    }

    public void addRoleToUser(String username, String role){
        AppUser user = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).orElse(null);

        if (appRole == null) throw new RuntimeException("Role does not exists");
        if (user.getRoles() == null) user.setRoles(new ArrayList<>());

        user.getRoles().add(appRole);
        // No need to appUserRepository.save(user), because it is already handled by @Transactional
    }
    public void removeRoleFromUser(String username, String role){
        AppUser user = appUserRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findById(role).get();
        user.getRoles().remove(appRole);
    }
    public AppUser loadUserByUsername(String username){
        return appUserRepository.findByUsername(username);
    }
}
