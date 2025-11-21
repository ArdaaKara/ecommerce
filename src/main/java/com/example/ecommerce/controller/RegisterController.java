package com.example.ecommerce.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserService;

@Controller
public class RegisterController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(User user, Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        boolean saved = userService.save(user);
        if (!saved) {
            model.addAttribute("error", "Kullanıcı adı zaten mevcut!");
            return "register";
        }
        if (userService.existsByName(user.getName())) {
            model.addAttribute("error", "Bu kullanıcı adı zaten alınmış!");
            return "register";
        }
        
        return "redirect:/login?register=true";
    }
}