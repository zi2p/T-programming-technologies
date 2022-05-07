package com.kotiki.presentation;
import com.kotiki.core.entities.Cat;
import com.kotiki.infrastructure.daos.UserDao;
import com.kotiki.infrastructure.entities.Role;
import com.kotiki.presentation.controllers.CatController;
import com.kotiki.dataAccess.daos.CatDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.kotiki.core.services.CatService;
import com.kotiki.infrastructure.services.InfrastructureCatService;
import com.kotiki.core.services.OwnerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackageClasses = { InfrastructureCatService.class, CatController.class })
@EntityScan(basePackageClasses = { Cat.class, Role.class })
@EnableJpaRepositories(basePackageClasses = { CatDao.class, UserDao.class })
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class Main {
    public static void main(String[] args) {
        var app = new SpringApplication(Main.class);
        app.run(args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public DataSource datasource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/kotiki-java")
                .username("postgres")
                .password("1234567890")
                .build();
    }

    @Bean
    public CatService catService() { return new CatService(); }

    @Bean
    public OwnerService ownerService() { return new OwnerService(); }

    @Bean
    public BCryptPasswordEncoder encoder() { return new BCryptPasswordEncoder(); }
}
