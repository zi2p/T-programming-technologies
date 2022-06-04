package com.kotiki.microservices.external.infrastructure.services;
import com.kotiki.microservices.external.infrastructure.daos.RoleDao;
import com.kotiki.microservices.external.infrastructure.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role findRoleByName(String name) { return roleDao.findByName(name).stream().findFirst().orElse(null); }
}
