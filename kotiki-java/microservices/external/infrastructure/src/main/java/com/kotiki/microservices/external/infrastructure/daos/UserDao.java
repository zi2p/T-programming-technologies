package com.kotiki.microservices.external.infrastructure.daos;
import com.kotiki.microservices.external.infrastructure.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
