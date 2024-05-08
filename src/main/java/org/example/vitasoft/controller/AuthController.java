package org.example.vitasoft.controller;

import org.example.vitasoft.entity.UserWebApp;
import org.example.vitasoft.service.RegistrationService;
import org.example.vitasoft.service.UserService;
import org.example.vitasoft.util.UserWebAppValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;

    private final UserWebAppValidator userWebAppValidator;

    private final UserService userService;


    @Autowired
    public AuthController(RegistrationService registrationService, UserWebAppValidator userWebAppValidator, UserService userService) {
        this.registrationService = registrationService;
        this.userWebAppValidator = userWebAppValidator;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("userwebapp") UserWebApp userWebApp) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("userwebapp") @Valid UserWebApp userWebApp, BindingResult bindingResult, Model model){

        userWebAppValidator.validate(userWebApp, bindingResult);

        if(bindingResult.hasErrors())
            return "auth/registration";

        registrationService.register(userWebApp);

        return "redirect:/auth/login";
    }
}

