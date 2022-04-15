package com.kotiki.presentation.controllers;
import com.kotiki.presentation.dtos.CatDto;
import com.kotiki.presentation.dtos.OwnerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kotiki.infrastructure.services.InfrastructureOwnerService;
import java.util.List;

@RestController
@RequestMapping("owners")
public class OwnerController {
    @Autowired
    private InfrastructureOwnerService ownerService;

    @GetMapping("all")
    public List<OwnerDto> getAllOwners() { return ownerService.getAll().stream().map(OwnerDto::new).toList(); }

    @GetMapping("{id}")
    public OwnerDto getOwnerWithId(@PathVariable Long id) { return new OwnerDto(ownerService.getById(id)); }

    @GetMapping("{id}/cats")
    public List<CatDto> getCats(@PathVariable Long id) {
        var owner = ownerService.getById(id);
        return owner.getCats().stream().map(CatDto::new).toList();
    }
}
