package com.kotiki.microservices.cats;
import com.kotiki.core.entities.Cat;
import com.kotiki.core.services.CatService;
import com.kotiki.dataAccess.daos.CatDao;
import com.kotiki.infrastructure.services.InfrastructureCatService;
import com.kotiki.microservices.shared.configurations.CatsQueueConfiguration;
import com.kotiki.microservices.shared.configurations.DataSourceConfiguration;
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
@Import({CatsQueueConfiguration.class, DataSourceConfiguration.class})
@EnableRabbit
public class Main {
    public static void main(String[] args) {
        var app = new SpringApplication(Main.class);
        app.run(args);
    }

    @Bean
    public CatService catService() { return new CatService(); }

    @Bean
    public InfrastructureCatService infrastructureCatService(CatDao catDao) {
        return new InfrastructureCatService(catService(), catDao);
    }

    @Bean
    public ConnectionFactory connectionFactory() { return new CachingConnectionFactory("localhost"); }
}
