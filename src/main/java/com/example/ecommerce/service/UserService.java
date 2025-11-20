package com.example.ecommerce.service;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + email));
    }

    public Optional<User> findOptionalByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public User getLoggedInUser(Principal principal) {
        String username = principal.getName();

        Optional<User> userOpt = userRepository.findByEmail(username);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }

        User user = userRepository.findByName(username);
        if (user != null) {
            return user;
        }

        throw new RuntimeException("Kullanıcı bulunamadı: " + username);
    }

    public boolean existsByName(String name) {
        return userRepository.existsByName(name);
    }
}