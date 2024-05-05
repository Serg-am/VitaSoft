package org.example.vitasoft.service;

import org.example.vitasoft.entity.Role;
import org.example.vitasoft.entity.UserWebApp;
import org.example.vitasoft.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UsersRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserWebApp user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    /*public List<UserWebApp> findUsersByUsername(String username) {
        return userRepo.findByUsername(username);
    }*/

    public List<UserWebApp> findAll() {
        return userRepo.findAll();
    }

    public List<UserWebApp> findByUsernameContaining(String username) {
        return userRepo.findByUsernameContainingIgnoreCase(username);
    }

    public UserWebApp findById(Integer userId) {
        Optional<UserWebApp> userOptional = userRepo.findById(userId);
        return userOptional.orElse(null);
    }

    public void assignOperatorRole(Integer userId) {
        UserWebApp user = findById(userId);
        if (user != null) {
            user.getRoles().add(Role.OPERATOR);
            userRepo.save(user);
        }
    }

    public void saveUser(UserWebApp user) {
        userRepo.save(user);
    }

    public void deleteUser(Integer userId) {
        userRepo.deleteById(userId);
    }
}
