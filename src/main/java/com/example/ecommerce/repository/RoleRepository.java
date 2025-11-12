package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecommerce.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
