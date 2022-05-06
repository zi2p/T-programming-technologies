package com.kotiki.presentation.controllers;
import com.kotiki.core.models.Color;
import com.kotiki.infrastructure.entities.User;
import com.kotiki.infrastructure.services.InfrastructureCatService;
import com.kotiki.presentation.dtos.CatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("owners")
public class OwnerController {
    @Autowired
    private InfrastructureCatService catService;

    @GetMapping("cats")
    public List<CatDto> getCats(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        var owner = user.getOwner();
        return catService.getAll().stream().filter(c -> owner.equals(c.getOwner())).map(CatDto::new).toList();
    }
}
