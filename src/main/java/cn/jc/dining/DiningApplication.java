package cn.jc.dining;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.jc.dining.mapper")
@SpringBootApplication
public class DiningApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiningApplication.class, args);
    }

}
