package com.kotiki.presentation.controllers;
import com.kotiki.core.models.Color;
import com.kotiki.infrastructure.entities.User;
import com.kotiki.infrastructure.services.InfrastructureCatService;
import com.kotiki.presentation.dtos.CatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("owner")
public class OwnerController {
    @Autowired
    private InfrastructureCatService catService;

    @GetMapping("cats")
    public List<CatDto> getCats(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        var owner = user.getOwner();
        return catService.getAll().stream().filter(c -> owner.equals(c.getOwner())).map(CatDto::new).toList();
    }

    @GetMapping("cats/{color}")
    public List<CatDto> getColoredCats(@PathVariable String color, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        var owner = user.getOwner();
        var colorEnum = Color.valueOf(color);

        return catService.getAll().stream()
                .filter(c -> owner.equals(c.getOwner()) && c.getColor().equals(colorEnum))
                .map(CatDto::new)
                .toList();
    }

    @GetMapping("{breed}_cats")
    public List<CatDto> getCatsByBreed(@PathVariable String breed, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        var owner = user.getOwner();

        return catService.getAll().stream()
                .filter(c -> owner.equals(c.getOwner()) && c.getBreed().equals(breed))
                .map(CatDto::new)
                .toList();
    }
}
