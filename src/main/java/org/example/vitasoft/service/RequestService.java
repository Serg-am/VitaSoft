package org.example.vitasoft.service;

import org.example.vitasoft.entity.Request;
import org.example.vitasoft.entity.RequestStatus;
import org.example.vitasoft.entity.UserWebApp;
import org.example.vitasoft.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RequestService {
    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Page<Request> getAllRequests(Pageable pageable) {
        return requestRepository.findAll(pageable);
    }

    public void createRequest(Request request) {
        request.setCreatedAt(LocalDateTime.now());
        requestRepository.save(request);
    }

    public Page<Request> getUserRequests(UserWebApp user, Pageable pageable) {
        return requestRepository.findByUser(user, pageable);
    }

    public Page<Request> getUserRequestsByName(String userName, Pageable pageable) {
        return requestRepository.findByUserUsernameContainingIgnoreCase(userName, pageable);
    }

    public void editRequest(Long requestId, String newText) {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            if (request.getStatus() == RequestStatus.DRAFT) {
                request.setText(newText);
                requestRepository.save(request);
            } else {
                throw new IllegalStateException("Невозможно редактировать заявку с ID: " + requestId + ", поскольку ее статус не является черновым.");
            }
        } else {
            throw new IllegalArgumentException("Request with ID " + requestId + " not found.");
        }
    }

    public void submitRequest(Long requestId) {
        updateRequestStatus(requestId, RequestStatus.SENT);
    }

    public void acceptRequest(Long requestId) {
        updateRequestStatus(requestId, RequestStatus.ACCEPTED);
    }

    public void rejectRequest(Long requestId) {
        updateRequestStatus(requestId, RequestStatus.REJECTED);
    }

    private void updateRequestStatus(Long requestId, RequestStatus status) {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            request.setStatus(status);
            requestRepository.save(request);
        } else {
            throw new IllegalArgumentException("Заявка с ID: " + requestId + " не найдена.");
        }
    }

    public Page<Request> getAllRequestsByStatusNot(RequestStatus status, Pageable pageable) {
        return requestRepository.findByStatusNot(status, pageable);
    }

    public Page<Request> getUserRequestsByNameAndStatusNot(String userName, RequestStatus status, Pageable pageable) {
        return requestRepository.findByUserUsernameContainingIgnoreCaseAndStatusNot(userName, status, pageable);
    }

}

