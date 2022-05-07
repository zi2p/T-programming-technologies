package com.kotiki.infrastructure.daos;
import com.kotiki.infrastructure.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleDao extends JpaRepository<Role, Long> {
    List<Role> findByName(String name);
}