package com.kotiki.microservices.shared.configurations;
import com.kotiki.microservices.shared.messages.cats.*;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatsQueueConfiguration {

    @Bean
    public Queue queueCreateCat() { return new Queue(CreateCat.NAME); }

    @Bean
    public Queue queueGetAllCats() { return new Queue(GetAllCats.NAME); }

    @Bean
    public Queue queueGetCatById() { return new Queue(GetCatById.NAME); }

    @Bean
    public Queue queueGetCatOwner() { return new Queue(GetCatOwner.NAME); }

    @Bean
    public Queue queueGetCatsFriends() { return new Queue(GetCatsFriends.NAME); }

    @Bean
    public Queue queueGetCatsWithColor() { return new Queue(GetCatsWithColor.NAME); }
}
