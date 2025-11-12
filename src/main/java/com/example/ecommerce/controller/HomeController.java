package com.example.ecommerce.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
public class HomeController {

    /* @GetMapping("/")
    public String index(Model model) {

        return "index"; // index.html
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    } */
    
    @GetMapping("/communication")
    public String communication() {
        return "communication";
    }
    @GetMapping("/ordered")
    public String ordered() {
        
        return "ordered";
    }
    @GetMapping("/profile")
    public String profile() {
        
        return "profile";
    }
    @GetMapping("/category")
    public String category(HttpSession session,Model model) {
        return "category";
    }
}
