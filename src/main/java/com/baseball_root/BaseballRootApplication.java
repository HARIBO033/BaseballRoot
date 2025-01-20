package com.baseball_root;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;

@SpringBootApplication
@EnableJpaAuditing
public class BaseballRootApplication {
    public static void main(String[] args) throws IOException {

        SpringApplication.run(BaseballRootApplication.class, args);
        System.out.println("Hello, World!");
    }
}
