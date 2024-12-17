package com.projects.patients_app.security.services;

import com.projects.patients_app.security.entities.AppRole;
import com.projects.patients_app.security.entities.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = accountService.loadUserByUsername(username);
        if(user==null) throw new UsernameNotFoundException(String.format("User %s not found", username));

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(AppRole::getRole).toArray(String[]::new))
//                .authorities(user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList()))
                .build();
    }
}
