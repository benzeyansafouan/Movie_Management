package com.example.movies_management;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MoviesManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesManagementApplication.class, args);
    }

    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create("mongodb://admin:APP_ADMIN@localhost/app_db");
    }

}
