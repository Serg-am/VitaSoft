package org.example.vitasoft.controller;

import org.example.vitasoft.DTO.RequestDTO;
import org.example.vitasoft.entity.Request;
import org.example.vitasoft.entity.RequestStatus;
import org.example.vitasoft.entity.UserWebApp;
import org.example.vitasoft.security.UserWebAppDetails;
import org.example.vitasoft.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/requests")
public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping()
    public String createRequest(@ModelAttribute("requestDTO") @Valid RequestDTO requestDTO) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserWebAppDetails userDetails = (UserWebAppDetails) authentication.getPrincipal();
            UserWebApp user = userDetails.getUser();

            Request request = new Request();
            request.setUser(user);
            request.setText(requestDTO.getText());
            request.setStatus(RequestStatus.DRAFT);

            requestService.createRequest(request);

            //return ResponseEntity.ok().build();
            return "redirect:/requests";
    }


    @GetMapping()
    public String showCreateRequestForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserWebAppDetails userDetails = (UserWebAppDetails) authentication.getPrincipal();
        UserWebApp user = userDetails.getUser();

        List<Request> requests = requestService.getUserRequests(user);
        System.out.println("1:");
        System.out.println(requests.toString());
        model.addAttribute("requests", requests);
        model.addAttribute("requestDTO", new RequestDTO());
        System.out.println("2:");
        return "requests"; // Возвращаем имя представления (шаблона Thymeleaf)
    }

}

