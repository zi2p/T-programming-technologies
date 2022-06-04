package com.kotiki.microservices.external.presentation.controllers;
import com.kotiki.dtos.CatDto;
import com.kotiki.dtos.OwnerDto;
import com.kotiki.microservices.external.infrastructure.services.RoleService;
import com.kotiki.microservices.external.infrastructure.services.UserService;
import com.kotiki.microservices.shared.messages.owners.GetAllOwners;
import com.kotiki.microservices.shared.messages.owners.GetOwnerWithId;
import com.kotiki.microservices.shared.messages.owners.GetUserCats;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdministrationController {
    private final RabbitTemplate rabbitTemplate;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdministrationController(RabbitTemplate rabbitTemplate, UserService userService, RoleService roleService) {
        this.rabbitTemplate = rabbitTemplate;
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("set")
    public void setAdmin(@RequestParam Long userId) {
        var user = userService.getUser(userId);
        var role = roleService.findRoleByName("ADMIN");

        user.addRoles(role);
    }

    @GetMapping("owners/all")
    public List<OwnerDto> getAllOwners() {
        var message = new GetAllOwners();
        Object response = rabbitTemplate.convertSendAndReceive(GetAllOwners.NAME, message);

        return (List<OwnerDto>) response;
    }

    @GetMapping("owners/{id}")
    public OwnerDto getOwnerWithId(@PathVariable Long id) {
        var message = new GetOwnerWithId(id);
        Object response = rabbitTemplate.convertSendAndReceive(GetOwnerWithId.NAME, message);

        return (OwnerDto) response;
    }

    @GetMapping("owners/{id}/cats")
    public List<CatDto> getCats(@PathVariable Long id) {
        var message = new GetUserCats(id);
        Object response = rabbitTemplate.convertSendAndReceive(GetUserCats.NAME, message);

        return (List<CatDto>) response;
    }
}
