package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute
    public void addAttributes(Model model, HttpSession session) {
        model.addAttribute("name", session.getAttribute("name"));
        model.addAttribute("role", session.getAttribute("role"));
    }
}
