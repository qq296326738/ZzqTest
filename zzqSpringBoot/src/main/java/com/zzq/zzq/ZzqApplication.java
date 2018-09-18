package com.zzq.zzq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = {"com.zzq.zzq"})
@SpringBootApplication
@EnableScheduling//增加支持定时任务的注解
public class ZzqApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzqApplication.class, args);
    }
}
