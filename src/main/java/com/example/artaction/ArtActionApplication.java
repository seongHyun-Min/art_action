package com.example.artaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ArtActionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtActionApplication.class, args);
    }
}
