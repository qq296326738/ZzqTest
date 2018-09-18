package com.zzq.zhuce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.zzq.zhuce"})
public class ZhuceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhuceApplication.class, args);
    }
}
