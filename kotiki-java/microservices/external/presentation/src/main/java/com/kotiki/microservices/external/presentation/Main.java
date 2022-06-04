package com.kotiki.microservices.external.presentation;
import com.kotiki.core.entities.Cat;
import com.kotiki.dataAccess.daos.CatDao;
import com.kotiki.dataAccess.daos.OwnerDao;
import com.kotiki.infrastructure.services.InfrastructureOwnerService;
import com.kotiki.microservices.external.infrastructure.configurations.SecurityConfiguration;
import com.kotiki.microservices.external.infrastructure.daos.UserDao;
import com.kotiki.microservices.external.infrastructure.entities.Role;
import com.kotiki.microservices.external.infrastructure.services.UserService;
import com.kotiki.microservices.external.presentation.controllers.CatController;
import com.kotiki.microservices.shared.configurations.CatsQueueConfiguration;
import com.kotiki.microservices.shared.configurations.DataSourceConfiguration;
import com.kotiki.microservices.shared.configurations.OwnersQueueConfiguration;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.kotiki.core.services.OwnerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EntityScan(basePackageClasses = {Cat.class, Role.class})
@EnableJpaRepositories(basePackageClasses = {UserDao.class, CatDao.class})
@ComponentScan(basePackageClasses = {UserService.class, CatController.class})
@Import({
        CatsQueueConfiguration.class,
        OwnersQueueConfiguration.class,
        DataSourceConfiguration.class,
        SecurityConfiguration.class
})
@EnableRabbit
public class Main {
    public static void main(String[] args) {
        var app = new SpringApplication(Main.class);
        app.run(args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public OwnerService ownerService() { return new OwnerService(); }

    @Bean
    public BCryptPasswordEncoder encoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public ConnectionFactory connectionFactory() { return new CachingConnectionFactory("localhost"); }

    @Bean
    public AmqpAdmin amqpAdmin() { return new RabbitAdmin(connectionFactory()); }

    @Bean
    public RabbitTemplate rabbitTemplate() { return new RabbitTemplate(connectionFactory()); }

    @Bean
    public InfrastructureOwnerService infrastructureOwnerService(CatDao catDao, OwnerDao ownerDao) {
        return new InfrastructureOwnerService(ownerService(), catDao, ownerDao);
    }
}
