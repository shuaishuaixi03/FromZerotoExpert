package com.wcx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wcx.mapper")
public class FromZerotoExpertApplication {

    public static void main(String[] args) {
        SpringApplication.run(FromZerotoExpertApplication.class, args);
    }

}
