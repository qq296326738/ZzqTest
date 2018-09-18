package com.zzq.cloud.zzqcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class ZzqcloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzqcloudApplication.class, args);
    }
}
