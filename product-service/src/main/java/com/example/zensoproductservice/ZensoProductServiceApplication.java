package com.example.zensoproductservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ZensoProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZensoProductServiceApplication.class, args);
    }

}
