package com.mstennie.rezdy.interview.mylunch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class MylunchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MylunchApplication.class, args);
    }

}
