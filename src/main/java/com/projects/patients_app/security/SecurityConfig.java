package com.projects.patients_app.security;

import com.projects.patients_app.security.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
    private UserDetailsServiceImpl userDetailsService;
    //@Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder){
//        return new InMemoryUserDetailsManager(
//                User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build(),
//                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN").build()
//        );
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(f -> f.loginPage("/login").permitAll())
                .authorizeHttpRequests(ar -> ar.requestMatchers("/webjars/**", "/signup/**", "/saveAccount/**").permitAll())
                .authorizeHttpRequests(ar -> ar.requestMatchers("/deletePatient/**", "/editPatient/**", "/formPatient/**").hasRole("ADMIN"))
                .authorizeHttpRequests(ar -> ar.anyRequest().authenticated())
                .exceptionHandling(exc -> exc.accessDeniedPage("/notAuthorized"))
                .rememberMe(rm -> rm.key("uniqueAndSecret").tokenValiditySeconds(2*24*60*60))
                .userDetailsService(userDetailsService)
                .build();
    }
}
