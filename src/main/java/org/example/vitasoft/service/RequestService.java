package org.example.vitasoft.service;

import org.example.vitasoft.entity.Request;
import org.example.vitasoft.entity.RequestStatus;
import org.example.vitasoft.entity.UserWebApp;
import org.example.vitasoft.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public void editRequest(Long requestId, String newText) {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            if (request.getStatus() == RequestStatus.DRAFT) {
                request.setText(newText);
                requestRepository.save(request);
            } else {
                // Handle error: Request is not in draft status
                throw new IllegalStateException("Unable to edit request with ID " + requestId + " because it is not in draft status.");
            }
        } else {
            // Handle error: Request not found
            throw new IllegalArgumentException("Request with ID " + requestId + " not found.");
        }
    }

    public void submitRequest(Long requestId) {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            request.setStatus(RequestStatus.SENT);
            requestRepository.save(request);
        } else {
            // Handle error: Request not found
            throw new IllegalArgumentException("Request with ID " + requestId + " not found.");
        }
    }

}

