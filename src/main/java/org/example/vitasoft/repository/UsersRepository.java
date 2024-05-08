package org.example.vitasoft.repository;

import org.example.vitasoft.entity.UserWebApp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsersRepository extends JpaRepository<UserWebApp, Integer> {
    UserWebApp findByUsername(String username);
    List<UserWebApp> findByUsernameContainingIgnoreCase(String username);
}
