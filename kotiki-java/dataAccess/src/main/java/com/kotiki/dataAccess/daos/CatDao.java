package com.kotiki.dataAccess.daos;
import com.kotiki.core.entities.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatDao extends JpaRepository<Cat, Long> {
}
