package com.example.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    @GetMapping("/communication")
    public String communication() {
        return "communication";
    }

    @GetMapping("/ordered")
    public String ordered() {

        return "ordered";
    }

    @GetMapping("/category")
    public String category(HttpSession session, Model model) {
        return "category";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

}
