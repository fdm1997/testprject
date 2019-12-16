package com.fdm.testpoject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@MapperScan(basePackages = "com.fdm")
@Configuration
public class  TestpojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestpojectApplication.class, args);

    }


}
