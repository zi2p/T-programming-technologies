package com.kotiki.microservices.cats.consumers;
import com.kotiki.core.entities.Cat;
import com.kotiki.dtos.CatDto;
import com.kotiki.dtos.OwnerDto;
import com.kotiki.infrastructure.services.InfrastructureCatService;
import com.kotiki.microservices.shared.messages.cats.*;
import com.kotiki.microservices.shared.models.CreateCatModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CatConsumer {
    private final InfrastructureCatService catService;

    @Autowired
    public CatConsumer(InfrastructureCatService catService) {
        this.catService = catService;
    }

    @RabbitListener(queues = CreateCat.NAME)
    public void CreateCat(@Payload CreateCat message) {
        CreateCatModel model = message.Model();
        var cat = new Cat(model.getCatName(), LocalDateTime.now(), model.getBreed(), model.getColor());
        catService.addToDatabase(cat);
    }

    @RabbitListener(queues = GetAllCats.NAME)
    public List<CatDto> GetAllCats(@Payload GetAllCats message) {
        return catService.getAll().stream().map(CatDto::new).toList();
    }

    @RabbitListener(queues = GetCatById.NAME)
    public CatDto GetCatById(@Payload GetCatById message) { return new CatDto(catService.getById(message.Id())); }

    @RabbitListener(queues = GetCatOwner.NAME)
    public OwnerDto GetCatOwner(@Payload GetCatOwner message) {
        var cat = catService.getById(message.Id());
        return new OwnerDto(cat.getOwner());
    }

    @RabbitListener(queues = GetCatsFriends.NAME)
    public List<CatDto> GetCatsFriends(@Payload GetCatsFriends message) {
        var cat = catService.getById(message.Id());
        return cat.getFriends().stream().map(CatDto::new).toList();
    }

    @RabbitListener(queues = GetCatsWithColor.NAME)
    public List<CatDto> GetCatsWithColor(@Payload GetCatsWithColor message) {
        return catService.getAll().stream().filter(c -> c.getColor() == message.Color()).map(CatDto::new).toList();
    }
}
