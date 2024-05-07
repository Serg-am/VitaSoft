package org.example.vitasoft.controller;

import org.example.vitasoft.entity.Request;
import org.example.vitasoft.service.RequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/operator_panel")
public class OperatorPanelController {

    private final RequestService requestService;

    public OperatorPanelController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping()
    public String showAllRequests(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "createdAt_desc") String sort,
                                  @RequestParam(required = false) String userName) {
        Page<Request> pageRequests;
        if (userName != null && !userName.isEmpty()) {
            // Если указано имя пользователя, ищем заявки только для этого пользователя
            pageRequests = requestService.getUserRequestsByName(userName, PageRequest.of(page, 5, Sort.by(parseSortDirection(sort), "createdAt")));
        } else {
            // Иначе отображаем все заявки
            pageRequests = requestService.getAllRequests(PageRequest.of(page, 5, Sort.by(parseSortDirection(sort), "createdAt")));
        }

        model.addAttribute("requests", pageRequests);
        model.addAttribute("currentPage", pageRequests.getNumber());
        model.addAttribute("totalPages", pageRequests.getTotalPages());
        model.addAttribute("sort", sort);
        model.addAttribute("userName", userName); // Передача значения параметра userName в модель
        return "operator_panel";
    }


    private Sort.Direction parseSortDirection(String sort) {
        return sort.endsWith("_desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

}
