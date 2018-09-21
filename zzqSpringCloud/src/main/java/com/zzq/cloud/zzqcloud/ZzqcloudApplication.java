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

    /*#打包
mvn clean package
# 分别以peer1和peeer2 配置信息启动eureka
java -jar spring-cloud-eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1
java -jar spring-cloud-eureka-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2*/
}
