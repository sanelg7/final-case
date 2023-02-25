package com.definex.practicum.finalcase.repository;

import com.definex.practicum.finalcase.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
    List<Role> findByRoleNameIn(List<String> roleNames);

}
