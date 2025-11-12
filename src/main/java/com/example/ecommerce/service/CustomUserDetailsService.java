package com.example.ecommerce.service;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name); // artık doğru metod
        if (user == null) {
            throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + name);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(user.getPassword()) // şifre encode edilmedi
                .roles(user.getRole())        
                .build();
    }
}
