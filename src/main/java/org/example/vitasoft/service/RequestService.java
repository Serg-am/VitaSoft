package org.example.vitasoft.service;

import org.example.vitasoft.entity.Request;
import org.example.vitasoft.entity.UserWebApp;
import org.example.vitasoft.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RequestService {
    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public void createRequest(Request request) {
        requestRepository.save(request);
    }

    public List<Request> getUserRequests(UserWebApp user) {
        return requestRepository.findByUser(user);
    }
}

