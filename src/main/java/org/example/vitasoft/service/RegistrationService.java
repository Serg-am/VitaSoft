package org.example.vitasoft.service;

import org.example.vitasoft.entity.UserWebApp;
import org.example.vitasoft.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RegistrationService {
    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(UserWebApp userWebApp){
        userWebApp.setPassword(passwordEncoder.encode(userWebApp.getPassword()));
        userWebApp.setRole("ROLE_USER");
        usersRepository.save(userWebApp);
    }
}