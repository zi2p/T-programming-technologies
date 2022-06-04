package com.kotiki.microservices.external.presentation.controllers;
import com.kotiki.core.models.Color;
import com.kotiki.dtos.CatDto;
import com.kotiki.dtos.OwnerDto;
import com.kotiki.microservices.shared.messages.cats.*;
import com.kotiki.microservices.shared.models.CreateCatModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("cats")
public class CatController {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CatController(RabbitTemplate rabbitTemplate) { this.rabbitTemplate = rabbitTemplate; }

    @GetMapping("all")
    public List<CatDto> getAllCats() {
        var message = new GetAllCats();
        Object response = rabbitTemplate.convertSendAndReceive(GetAllCats.NAME, message);

        return (List<CatDto>) response;
    }

    @GetMapping("{id}")
    public CatDto getCatWithId(@PathVariable Long id) {
        var message = new GetCatById(id);
        Object response = rabbitTemplate.convertSendAndReceive(GetCatById.NAME, message);

        return (CatDto) response;
    }

    @GetMapping("color")
    public List<CatDto> getCatsWithColor(@RequestParam String color) {
        var message = new GetCatsWithColor(Color.valueOf(color));
        Object response = rabbitTemplate.convertSendAndReceive(GetCatsWithColor.NAME, message);

        return (List<CatDto>) response;
    }

    @GetMapping("{id}/friends")
    public List<CatDto> getCatsFriends(@PathVariable Long id) {
        var message = new GetCatsFriends(id);
        Object response = rabbitTemplate.convertSendAndReceive(GetCatsFriends.NAME, message);

        return (List<CatDto>) response;
    }

    @GetMapping("{id}/owner")
    public OwnerDto getOwner(@PathVariable Long id) {
        var message = new GetCatOwner(id);
        Object response = rabbitTemplate.convertSendAndReceive(GetCatOwner.NAME, message);

        return (OwnerDto) response;
    }

    @PostMapping("create")
    public void createCat(@RequestBody CreateCatModel model) {
        var message = new CreateCat(model);
        rabbitTemplate.convertAndSend(CreateCat.NAME, message);
    }
}
