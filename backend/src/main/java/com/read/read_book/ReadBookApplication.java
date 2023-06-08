package com.read.read_book;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.read.read_book.Mapper")
@SpringBootApplication
public class ReadBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadBookApplication.class, args);
    }

}
