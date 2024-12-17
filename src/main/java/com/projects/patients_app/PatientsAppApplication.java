package com.projects.patients_app;

import com.projects.patients_app.entities.Patient;
import com.projects.patients_app.repositories.PatientRepository;
import com.projects.patients_app.security.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class PatientsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientsAppApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner start(PatientRepository patientRepository) {
//		return args -> {
//			Patient p1 = Patient.builder()
//					.firstName("Bob")
//					.lastName("mala")
//					.birthDate(new Date())
//					.ill(true)
//					.score(120)
//					.build();
//			Patient p2 = Patient.builder()
//					.firstName("Pipo")
//					.lastName("Mobster")
//					.birthDate(new Date())
//					.ill(false)
//					.score(657)
//					.build();
//			Patient p3 = Patient.builder()
//					.firstName("George")
//					.lastName("Chao")
//					.birthDate(new Date())
//					.ill(true)
//					.score(132)
//					.build();
//			patientRepository.save(p1);
//			patientRepository.save(p2);
//			patientRepository.save(p3);
//        };
//    }

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	//@Bean
	public CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager) {
		return args -> {
			UserDetails u1 = jdbcUserDetailsManager.loadUserByUsername("user1");
			UserDetails u2 = jdbcUserDetailsManager.loadUserByUsername("admin");

			if (u1 == null) jdbcUserDetailsManager.createUser(User.withUsername("user1").password(passwordEncoder().encode("1234")).roles("USER").build());
			if (u2 == null) jdbcUserDetailsManager.createUser(User.withUsername("admin").password(passwordEncoder().encode("1234")).roles("USER", "ADMIN").build());
		};
	}

    //@Bean
    public CommandLineRunner commandLineRunnerUserDetails(AccountService accountService) {
        return args -> {
            try {
//                accountService.addNewRole("USER");
//                accountService.addNewRole("ADMIN");

                accountService.addNewUser("user1", "1234", "user1@gmail.com", "1234");
                accountService.addNewUser("admin", "1234", "admin@gmail.com", "1234");

                accountService.addRoleToUser("user1", "USER");
                accountService.addRoleToUser("admin", "ADMIN");
                accountService.addRoleToUser("admin", "USER");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        };
    }

}
