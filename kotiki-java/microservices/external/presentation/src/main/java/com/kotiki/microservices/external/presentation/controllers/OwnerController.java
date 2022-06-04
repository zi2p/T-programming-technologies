package com.kotiki.microservices.external.presentation.controllers;
import com.kotiki.core.models.Color;
import com.kotiki.dtos.CatDto;
import com.kotiki.microservices.external.infrastructure.entities.User;
import com.kotiki.microservices.shared.messages.owners.GetUserCats;
import com.kotiki.microservices.shared.messages.owners.GetUserCatsWithBreed;
import com.kotiki.microservices.shared.messages.owners.GetUserCatsWithColor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("owner")
public class OwnerController {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public OwnerController(RabbitTemplate rabbitTemplate) { this.rabbitTemplate = rabbitTemplate; }

    @GetMapping("cats")
    public List<CatDto> getCats(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        var owner = user.getOwner();

        var message = new GetUserCats(owner.getId());
        Object response = rabbitTemplate.convertSendAndReceive(GetUserCats.NAME, message);

        return (List<CatDto>) response;
    }

    @GetMapping("cats/{color}")
    public List<CatDto> getColoredCats(@PathVariable String color, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        var owner = user.getOwner();

        var message = new GetUserCatsWithColor(owner.getId(), Color.valueOf(color));
        Object response = rabbitTemplate.convertSendAndReceive(GetUserCatsWithColor.NAME, message);

        return (List<CatDto>) response;
    }

    @GetMapping("cats/{breed}")
    public List<CatDto> getCatsByBreed(@PathVariable String breed, Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        var owner = user.getOwner();

        var message = new GetUserCatsWithBreed(owner.getId(), breed);
        Object response = rabbitTemplate.convertSendAndReceive(GetUserCatsWithBreed.NAME, message);

        return (List<CatDto>) response;
    }
}
