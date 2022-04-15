package com.kotiki.dataAccess.tools;
import com.kotiki.core.entities.Cat;
import com.kotiki.core.entities.Owner;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import java.util.HashMap;
import java.util.Map;

public class SessionFactoryBuilder {
    public SessionFactory build(String url, String username, String password, String dialect, String driverClass) {
        Map<String, Object> settings = new HashMap<>();
        settings.put("connection.driver_class", driverClass);
        settings.put("dialect", "org.hibernate.dialect." + dialect);
        settings.put("hibernate.connection.url", url);
        settings.put("hibernate.connection.username", username);
        settings.put("hibernate.connection.password", password);
        settings.put("hibernate.current_session_context_class", "thread");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.format_sql", "true");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(Owner.class);
        metadataSources.addAnnotatedClass(Cat.class);
        Metadata metadata = metadataSources.buildMetadata();

        return metadata.getSessionFactoryBuilder().build();
    }
}
