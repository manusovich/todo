package com.mashape.testtask.todo.plugin.searchbox;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Searchly Spring Configuration
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
@Configuration
public class SearchlyConfiguration {
    public static final String SEARCHLY_URL = System.getenv("SEARCHLY_URL");

    @Bean
    public JestClient jestClient() throws Exception {
        /**
         * Construct a new Jest client according to configuration via factory
         */
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(SEARCHLY_URL)
                .multiThreaded(true)
                .build());
        return factory.getObject();
    }
}
