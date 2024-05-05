package org.example.vitasoft.repository;

import org.example.vitasoft.entity.Request;
import org.example.vitasoft.entity.UserWebApp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByUser(UserWebApp user);
}

