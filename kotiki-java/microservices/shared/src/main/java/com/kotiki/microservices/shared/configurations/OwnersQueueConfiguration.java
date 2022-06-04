package com.kotiki.microservices.shared.configurations;
import com.kotiki.microservices.shared.messages.owners.*;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OwnersQueueConfiguration {
    @Bean
    public Queue queueGetUserCats() { return new Queue(GetUserCats.NAME); }

    @Bean
    public Queue queueGetUserCatsWithBreed() { return new Queue(GetUserCatsWithBreed.NAME); }

    @Bean
    public Queue queueGetUserCatsWithColor() { return new Queue(GetUserCatsWithColor.NAME); }

    @Bean
    public Queue queueGetAllOwners() { return new Queue(GetAllOwners.NAME); }

    @Bean
    public Queue queueGetOwnerWithId() { return new Queue(GetOwnerWithId.NAME); }
}