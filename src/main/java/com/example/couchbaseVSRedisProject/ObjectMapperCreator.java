package com.example.couchbaseVSRedisProject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperCreator {

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
