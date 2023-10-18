package com.sydney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.sydney.dao")
public class Cs311ParadisecDevelopmentForPublicEngagementWithArchivesApplication {

    public static void main(String[] args) {
        SpringApplication.run(Cs311ParadisecDevelopmentForPublicEngagementWithArchivesApplication.class, args);
    }

}
