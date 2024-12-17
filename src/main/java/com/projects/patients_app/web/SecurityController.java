package com.projects.patients_app.web;

import com.projects.patients_app.security.dtos.AppUserDto;
import com.projects.patients_app.security.entities.AppUser;
import com.projects.patients_app.security.services.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller @AllArgsConstructor
public class SecurityController {
    private final AccountService accountService;
    @GetMapping("notAuthorized")
    public String notAuthorized(){
        return "notAuthorized";
    }
    @GetMapping("login")
    public String login(){
        return "login";
    }
    @GetMapping("signup")
    public String signup(Model model) {
        model.addAttribute("user", new AppUserDto());
        return "signup";
    }

    @PostMapping("saveAccount")
    public String saveAccount(@Valid @ModelAttribute("user") AppUserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("nigga");
            return "signup";
        }

        try {
            AppUser user = accountService.addNewUser(userDto.getUsername(), userDto.getPassword(), userDto.getEmail(), userDto.getConfirmPassword());
            accountService.addRoleToUser(user.getUsername(), "USER");
        }
        catch(Exception e) {
            //System.out.println(e.getMessage());
            bindingResult.rejectValue("username", "error.userDto", e.getMessage());
            return "signup";
        }

        return "redirect:/login";
    }
}
