package cn.springboot.pioneermall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("cn.springboot.pioneermall.dao")
@SpringBootApplication
public class PioneerMallAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(PioneerMallAPIApplication.class, args);
    }

}
