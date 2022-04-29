package com.kotiki.dataAccess.daos;
import com.kotiki.core.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerDao extends JpaRepository<Owner, Long> {
}
