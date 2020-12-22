package com.community.aspn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.community.aspn.*.mapper")
public class AspnApplication {
    public static void main(String[] args) {
        SpringApplication.run(AspnApplication.class, args);
    }
}
