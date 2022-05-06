package com.kotiki.presentation.controllers;
import com.kotiki.core.entities.Owner;
import com.kotiki.dataAccess.tools.KotikiExeption;
import com.kotiki.infrastructure.entities.User;
import com.kotiki.infrastructure.services.InfrastructureOwnerService;
import com.kotiki.infrastructure.services.RoleService;
import com.kotiki.infrastructure.services.UserService;
import com.kotiki.presentation.models.RegistrationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;

@RestController
public class RegistrationController {

    @Autowired
    private InfrastructureOwnerService ownerService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegistrationModel model) {

        if (!uniqueUsername(model.getUsername())) throw new KotikiExeption("user name is not unique");

        var owner = new Owner(model.getUsername(), LocalDateTime.now().minus(1, ChronoUnit.DAYS));
        owner = ownerService.addToDatabase(owner);
        var role = roleService.findRoleByName("OWNER");
        var set = new HashSet<>(List.of(role));
        var user = new User(model.getUsername(), model.getPassword(), set, owner);
        user = userService.saveUser(user);

        return user.getId().toString();
    }

    private boolean uniqueUsername(String username) {
        return userService.allUsers().stream().map(User::getUsername).noneMatch(n -> n.equals(username));
    }
}

