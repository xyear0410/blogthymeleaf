package com.tgxyear;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tgxyear.mapper")
public class ThrxyearApplication {

    public static void main(String[] args) { SpringApplication.run(ThrxyearApplication.class, args);
        System.setProperty("spring.devtools.restart.enabled", "false");
    }

}
