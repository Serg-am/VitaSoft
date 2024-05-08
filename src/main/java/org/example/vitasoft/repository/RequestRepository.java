package org.example.vitasoft.repository;

import org.example.vitasoft.entity.Request;
import org.example.vitasoft.entity.RequestStatus;
import org.example.vitasoft.entity.UserWebApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Page<Request> findByUser(UserWebApp user, Pageable pageable);
    Page<Request> findByUserUsernameContainingIgnoreCase(String userName, Pageable pageable);
    Page<Request> findByUserUsernameContainingIgnoreCaseAndStatusNot(String userName, RequestStatus status, Pageable pageable);
    Page<Request> findByStatusNot(RequestStatus status, Pageable pageable);
}

