package com.example.ecommerce.controller;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    /*  */
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestParam String name,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        User user = userRepository.findByName(name);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Kullanıcı adı veya şifre hatalı!");
            return "login"; // login sayfasına geri dön
        }

        // Başarılı giriş
        session.setAttribute("name", name); // sessiona kaydet
        session.setAttribute("role", user.getRole());
        return "redirect:/"; // ana sayfaya yönlendir
    }

    @ModelAttribute
    public void addAttributes(Model model, HttpSession session) {
        model.addAttribute("name", session.getAttribute("name"));
        model.addAttribute("role", session.getAttribute("role"));

    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        String name = (String) session.getAttribute("name");
        if (name != null) {
            model.addAttribute("name", name); // ana sayfada gösterebilirsin
        }
        return "index"; // index.html
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // oturumu temizle
        return "redirect:/"; // ana sayfaya yönlendir
    }

    /*  */
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        boolean isSaved = userService.save(user);
        if (!isSaved) {
            model.addAttribute("error", "Kullanıcı zaten mevcut!");
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    
    
}
