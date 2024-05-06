package org.example.vitasoft.controller;

import org.example.vitasoft.DTO.RequestDTO;
import org.example.vitasoft.entity.Request;
import org.example.vitasoft.entity.RequestStatus;
import org.example.vitasoft.entity.UserWebApp;
import org.example.vitasoft.security.UserWebAppDetails;
import org.example.vitasoft.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Метод для создания заявки
    @PostMapping("/create")
    public String createRequest(@ModelAttribute("requestDTO") @Valid RequestDTO requestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserWebAppDetails userDetails = (UserWebAppDetails) authentication.getPrincipal();
        UserWebApp user = userDetails.getUser();

        Request request = new Request();
        request.setUser(user);
        request.setText(requestDTO.getText());
        request.setStatus(RequestStatus.DRAFT);

        requestService.createRequest(request);

        return "redirect:/requests";
    }

    // Метод для редактирования заявки
    @PostMapping("/edit/{requestId}")
    public String editRequest(@PathVariable("requestId") Long requestId, @RequestParam String newText) {
        requestService.editRequest(requestId, newText);
        return "redirect:/requests";
    }

    // Метод для отправки заявки на рассмотрение оператору
    @PostMapping("/submit/{requestId}")
    public String submitRequest(@PathVariable("requestId") Long requestId) {
        requestService.submitRequest(requestId);
        return "redirect:/requests";
    }

    // Метод для отображения страницы создания заявки и списка заявок пользователя
    @GetMapping()
    public String showCreateRequestForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserWebAppDetails userDetails = (UserWebAppDetails) authentication.getPrincipal();
        UserWebApp user = userDetails.getUser();

        List<Request> requests = requestService.getUserRequests(user);
        model.addAttribute("requests", requests);
        model.addAttribute("requestDTO", new RequestDTO());
        return "requests";
    }

}

