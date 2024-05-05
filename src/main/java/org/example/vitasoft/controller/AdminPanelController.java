package org.example.vitasoft.controller;

import org.example.vitasoft.entity.UserWebApp;
import org.example.vitasoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminPanelController {

    private final UserService userService;

    @Autowired
    public AdminPanelController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin_panel")
    public String userList(Model model) {
        List<UserWebApp> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin_panel"; // Убран лишний слеш перед именем шаблона
    }

    @PostMapping("/admin/users/search")
    public String searchUser(@RequestParam("username") String username, Model model) {
        List<UserWebApp> users = userService.findByUsernameContaining(username);
        model.addAttribute("users", users);
        return "admin_panel"; // Убран редирект, чтобы модель осталась на той же странице
    }

    @PostMapping("/admin/users/assignOperatorRole")
    public String assignOperatorRole(@RequestParam("userId") Integer userId) { // Использован Long для ID пользователя
        userService.assignOperatorRole(userId);
        return "redirect:/admin_panel"; // Возвращается редирект на список пользователей
    }
}

