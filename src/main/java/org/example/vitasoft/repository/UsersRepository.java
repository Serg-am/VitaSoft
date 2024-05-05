package org.example.vitasoft.repository;

import org.example.vitasoft.entity.UserWebApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserWebApp, Integer> {
    UserWebApp findByUsername(String username);
}
