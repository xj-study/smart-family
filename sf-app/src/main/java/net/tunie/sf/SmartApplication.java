package net.tunie.sf;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@MapperScan(value = SmartApplication.COMPONENT_SCAN)
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class SmartApplication {
    public static final String COMPONENT_SCAN = "net.tunie.sf";

    public static void main(String[] args) {
        SpringApplication.run(SmartApplication.class, args);
    }
}
