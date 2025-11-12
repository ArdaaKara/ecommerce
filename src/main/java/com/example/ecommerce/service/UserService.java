package com.example.ecommerce.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;


@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor injection 
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean save(User user) {
        if (userRepository.existsByName(user.getName())) {
            return false; 
        }
        userRepository.save(user);
        return true;
    }
    public List<User> getallUsers() {
        return userRepository.findAll();
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
    


}

    

