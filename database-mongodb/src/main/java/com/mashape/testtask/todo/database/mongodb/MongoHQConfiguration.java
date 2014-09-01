package com.mashape.testtask.todo.database.mongodb;

import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;

/**
 * MongoDB Configurator
 * <p/>
 * Created by Alex Manusovich on 8/31/14.
 */
@Configuration
public class MongoHQConfiguration {

    public @Bean MongoDbFactory mongoDbFactory() throws MongoException, UnknownHostException {
        // "mongodb://localhost:27017/mashape_todo"
        return new SimpleMongoDbFactory(new MongoURI(System.getenv("MONGOHQ_URL")));
    }

    public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }
}