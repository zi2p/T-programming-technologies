package com.kotiki.microservices.owners.consumers;
import com.kotiki.dtos.CatDto;
import com.kotiki.dtos.OwnerDto;
import com.kotiki.infrastructure.services.InfrastructureCatService;
import com.kotiki.infrastructure.services.InfrastructureOwnerService;
import com.kotiki.microservices.shared.messages.owners.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class OwnerConsumer {

    private final InfrastructureCatService catService;
    private final InfrastructureOwnerService ownerService;

    @Autowired
    public OwnerConsumer(InfrastructureCatService catService, InfrastructureOwnerService ownerService) {
        this.catService = catService;
        this.ownerService = ownerService;
    }

    @RabbitListener(queues = GetUserCats.NAME)
    public List<CatDto> GetUserCats(@Payload GetUserCats message) {
        var owner = ownerService.getById(message.UserId());
        return catService.getAll().stream().filter(c -> owner.equals(c.getOwner())).map(CatDto::new).toList();
    }

    @RabbitListener(queues = GetUserCatsWithBreed.NAME)
    public List<CatDto> GetUserCatsWithBreed(@Payload GetUserCatsWithBreed message) {
        var owner = ownerService.getById(message.UserId());
        return catService.getAll().stream()
                .filter(c -> owner.equals(c.getOwner()) && c.getBreed().equals(message.Breed()))
                .map(CatDto::new)
                .toList();
    }

    @RabbitListener(queues = GetUserCatsWithColor.NAME)
    public List<CatDto> GetUserCatsWithColor(@Payload GetUserCatsWithColor message) {
        var owner = ownerService.getById(message.UserId());
        return catService.getAll().stream()
                .filter(c -> owner.equals(c.getOwner()) && c.getColor().equals(message.Color()))
                .map(CatDto::new)
                .toList();
    }

    @RabbitListener(queues = GetAllOwners.NAME)
    public List<OwnerDto> GetAllOwners(@Payload GetAllOwners message) {
        return ownerService.getAll().stream().map(OwnerDto::new).toList();
    }

    @RabbitListener(queues = GetOwnerWithId.NAME)
    public OwnerDto GetOwnerWithId(@Payload GetOwnerWithId message) {
        return new OwnerDto(ownerService.getById(message.Id()));
    }
}
