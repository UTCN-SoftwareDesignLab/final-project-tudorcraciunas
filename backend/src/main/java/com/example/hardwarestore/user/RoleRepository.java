package com.example.hardwarestore.user;

import com.example.hardwarestore.user.model.ERole;
import com.example.hardwarestore.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
