package org.example.vitasoft.controller;

import org.example.vitasoft.DTO.RequestDTO;
import org.example.vitasoft.entity.Request;
import org.example.vitasoft.entity.RequestStatus;
import org.example.vitasoft.entity.UserWebApp;
import org.example.vitasoft.security.UserWebAppDetails;
import org.example.vitasoft.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

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

    @GetMapping
    public String showRequestsPage(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "createdAt_desc") String sort) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserWebAppDetails userDetails = (UserWebAppDetails) authentication.getPrincipal();
        UserWebApp user = userDetails.getUser();

        Sort.Direction direction = sort.endsWith("_desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        String sortBy = sort.split("_")[0];

        // Получить список заявок пользователя с учетом пагинации и сортировки
        Page<Request> pageRequests = requestService.getUserRequests(user,
                PageRequest.of(page, 5, Sort.by(direction, sortBy)));

        model.addAttribute("requests", pageRequests);
        model.addAttribute("requestDTO", new RequestDTO());
        model.addAttribute("currentPage", pageRequests.getNumber());
        model.addAttribute("totalPages", pageRequests.getTotalPages());
        model.addAttribute("sort", sort);
        return "requests";
    }




}

