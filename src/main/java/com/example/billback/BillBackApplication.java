package com.example.billback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.billback.mapper")
public class BillBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(BillBackApplication.class, args);
    }
} 