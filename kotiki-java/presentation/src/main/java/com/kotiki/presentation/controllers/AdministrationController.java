package com.kotiki.presentation.controllers;
import com.kotiki.infrastructure.services.InfrastructureCatService;
import com.kotiki.infrastructure.services.InfrastructureOwnerService;
import com.kotiki.infrastructure.services.RoleService;
import com.kotiki.infrastructure.services.UserService;
import com.kotiki.presentation.dtos.CatDto;
import com.kotiki.presentation.dtos.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdministrationController {
    @Autowired
    private InfrastructureOwnerService ownerService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private InfrastructureCatService catService;

    @PostMapping("set")
    public void setAdmin(@RequestParam Long userId) {
        var user = userService.getUser(userId);
        var role = roleService.findRoleByName("ADMIN");

        user.addRoles(role);
    }

    @GetMapping("owners/all")
    public List<OwnerDto> getAllOwners() { return ownerService.getAll().stream().map(OwnerDto::new).toList(); }

    @GetMapping("owners/{id}")
    public OwnerDto getOwnerWithId(@PathVariable Long id) { return new OwnerDto(ownerService.getById(id)); }

    @GetMapping("owners/{id}/cats")
    public List<CatDto> getCats(@PathVariable Long id) {
        return catService.getAll().stream()
                .filter(c -> c.getOwner() != null && c.getOwner().getId().equals(id))
                .map(CatDto::new)
                .toList();
    }
}
