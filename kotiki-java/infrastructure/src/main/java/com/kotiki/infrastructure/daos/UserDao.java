package com.kotiki.infrastructure.daos;
import com.kotiki.infrastructure.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
