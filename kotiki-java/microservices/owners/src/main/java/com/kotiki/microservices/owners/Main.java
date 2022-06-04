package com.kotiki.microservices.owners;
import com.kotiki.core.entities.Cat;
import com.kotiki.core.services.CatService;
import com.kotiki.core.services.OwnerService;
import com.kotiki.dataAccess.daos.CatDao;
import com.kotiki.dataAccess.daos.OwnerDao;
import com.kotiki.infrastructure.services.InfrastructureCatService;
import com.kotiki.infrastructure.services.InfrastructureOwnerService;
import com.kotiki.microservices.shared.configurations.DataSourceConfiguration;
import com.kotiki.microservices.shared.configurations.OwnersQueueConfiguration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackageClasses = {Cat.class})
@EnableJpaRepositories(basePackageClasses = {CatDao.class})
@Import({OwnersQueueConfiguration.class, DataSourceConfiguration.class})
@EnableRabbit
public class Main {
    public static void main(String[] args) {
        var app = new SpringApplication(Main.class);
        app.run(args);
    }

    @Bean
    public OwnerService ownerService() { return new OwnerService(); }

    @Bean
    public CatService catService() { return new CatService(); }

    @Bean
    public InfrastructureCatService infrastructureCatService(CatDao catDao) {
        return new InfrastructureCatService(catService(), catDao);
    }

    @Bean
    public InfrastructureOwnerService infrastructureOwnerService(CatDao catDao, OwnerDao ownerDao) {
        return new InfrastructureOwnerService(ownerService(), catDao, ownerDao);
    }

    @Bean
    public ConnectionFactory connectionFactory() { return new CachingConnectionFactory("localhost"); }
}
