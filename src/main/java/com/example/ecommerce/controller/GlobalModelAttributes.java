package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@ControllerAdvice
public class GlobalModelAttributes {
    @ModelAttribute
    public void addUserAttributes(Model model, @AuthenticationPrincipal UserDetails user) {
        if (user != null) {
            model.addAttribute("name", user.getUsername());
            model.addAttribute("role", user.getAuthorities().iterator().next().getAuthority());
        }
    }
}