package com.definex.practicum.finalcase.repository;

import com.definex.practicum.finalcase.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);
    List<Role> findByRoleNameIn(List<String> roleNames);

}
