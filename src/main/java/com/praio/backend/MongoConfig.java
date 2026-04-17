package com.praio.backend;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.FileInputStream;
import java.util.Properties;

@Configuration
public class MongoConfig {

    private String loadMongoUri() {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(".env"));
            return props.getProperty("MONGODB_URI");
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível carregar o .env", e);
        }
    }

    @Bean
    public MongoClient mongoClient() {
        String uri = loadMongoUri();
        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "praio");
    }
}