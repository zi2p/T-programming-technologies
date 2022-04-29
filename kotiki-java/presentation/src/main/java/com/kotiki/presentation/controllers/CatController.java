package com.kotiki.presentation.controllers;
import com.kotiki.presentation.dtos.CatDto;
import com.kotiki.core.models.Color;
import com.kotiki.presentation.dtos.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kotiki.infrastructure.services.InfrastructureCatService;
import java.util.List;

@RestController
@RequestMapping("cats")
public class CatController {
    @Autowired
    private InfrastructureCatService catService;

    @GetMapping("all")
    public List<CatDto> getAllCats() { return catService.getAll().stream().map(CatDto::new).toList(); }

    @GetMapping("{id}")
    public CatDto getCatWithId(@PathVariable Long id) { return new CatDto(catService.getById(id)); }

    @GetMapping("color")
    public List<CatDto> getCatsWithColor(@RequestParam String color) {
        var colorEnum = Color.valueOf(color);
        return catService.getAll().stream().filter(c -> c.getColor() == colorEnum).map(CatDto::new).toList();
    }

    @GetMapping("{id}/friends")
    public List<CatDto> getCatsFriends(@PathVariable Long id) {
        var cat = catService.getById(id);
        return cat.getFriends().stream().map(CatDto::new).toList();
    }

    @GetMapping("{id}/owner")
    public OwnerDto getOwner(@PathVariable Long id) {
        var cat = catService.getById(id);
        return new OwnerDto(cat.getOwner());
    }
}
