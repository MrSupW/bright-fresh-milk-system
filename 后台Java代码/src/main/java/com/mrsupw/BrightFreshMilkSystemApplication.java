package com.mrsupw;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.mrsupw.mapper")
@SpringBootApplication
public class BrightFreshMilkSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(BrightFreshMilkSystemApplication.class, args);
    }
}
