package org.example.vitasoft.service;

import org.example.vitasoft.entity.UserWebApp;
import org.example.vitasoft.entity.Role;
import org.example.vitasoft.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final Role defaultRole = Role.USER; // Роль пользователя по умолчанию

    @Autowired
    public RegistrationService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(UserWebApp userWebApp){
        userWebApp.setPassword(passwordEncoder.encode(userWebApp.getPassword()));
        // Добавляем роль в коллекцию roles
        userWebApp.getRoles().add(defaultRole);
        usersRepository.save(userWebApp);
    }
}
