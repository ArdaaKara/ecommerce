package com.example.ecommerce.controller;

import com.example.ecommerce.model.Review;          
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.ReviewService;
import com.example.ecommerce.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;                                 

@Controller
public class ProfileController {
    private final UserService userService;
    private final ReviewService reviewService;

    public ProfileController(UserService userService, ReviewService reviewService) {
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping("/my-profile")
    public String myProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        User user = userService.getUserByName(userDetails.getUsername());
        if (user == null) {
            return "redirect:/login";
        }

        return "redirect:/profile/" + user.getId();
    }

    @GetMapping("/profile/{id}")
    public String profilePage(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        if (user == null) {
            return "error/404";
        }

        model.addAttribute("user", user);

        List<Review> userReviews = reviewService.findByUserId(Math.toIntExact(user.getId()));
        model.addAttribute("userReviews", userReviews);

        return "profile";
    }


}