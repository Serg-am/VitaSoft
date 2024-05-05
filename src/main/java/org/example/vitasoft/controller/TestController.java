package org.example.vitasoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/operator")
    public String operatorPage() {
        return "operator";
    }

    @GetMapping("/user")
    public String userPage() {
    return "user";
}

}
