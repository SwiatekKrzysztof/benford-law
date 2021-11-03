package com.swiatek.benford;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableR2dbcRepositories
@SpringBootApplication
public class BenfordApplication {

    public static void main(String[] args) {
//        BlockHound.install();
        SpringApplication.run(BenfordApplication.class, args);
    }

}
