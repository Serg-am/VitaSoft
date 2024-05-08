package org.example.vitasoft.controller;

import org.example.vitasoft.entity.Request;
import org.example.vitasoft.entity.RequestStatus;
import org.example.vitasoft.service.RequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
            pageRequests = requestService.getUserRequestsByNameAndStatusNot(userName, RequestStatus.DRAFT, PageRequest.of(page, 5, Sort.by(parseSortDirection(sort), "createdAt")));
        } else {
            // Иначе отображаем все заявки, исключая те, которые имеют статус DRAFT
            pageRequests = requestService.getAllRequestsByStatusNot(RequestStatus.DRAFT, PageRequest.of(page, 5, Sort.by(parseSortDirection(sort), "createdAt")));
        }

        for (Request request : pageRequests.getContent()) {
            request.setText(replaceCharsWithDash(request.getText()));
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

    @PostMapping("/accept")
    public String acceptRequest(@RequestParam("requestId") Long requestId) {
        requestService.acceptRequest(requestId);
        return "redirect:/operator_panel";
    }

    @PostMapping("/reject")
    public String rejectRequest(@RequestParam("requestId") Long requestId) {
        requestService.rejectRequest(requestId);
        return "redirect:/operator_panel";
    }

    private String replaceCharsWithDash(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            result.append(currentChar).append('-');
        }

        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }







}
