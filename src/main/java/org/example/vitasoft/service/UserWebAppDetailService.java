package org.example.vitasoft.service;

import org.example.vitasoft.entity.UserWebApp;
import org.example.vitasoft.repository.UsersRepository;
import org.example.vitasoft.security.UserWebAppDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserWebAppDetailService extends UserWebApp implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Autowired
    public UserWebAppDetailService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserWebApp> userWebAppOptional = Optional.ofNullable(usersRepository.findByUsername(username));

        if(userWebAppOptional.isEmpty()){
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new UserWebAppDetails(userWebAppOptional.get());
    }
}
