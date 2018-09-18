package com.zzq.xiaofei.api;

import com.zzq.xiaofei.controller.HelloRemoteHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "spring-cloud-producer", fallback = HelloRemoteHystrix.class)
//name:远程服务名，及spring.application.name配置的名称
public interface HelloRemote {

    //    此类中的方法和远程服务中contoller中的方法名和参数需保持一致。
    @RequestMapping(value = "/hello")
    public String hello(@RequestParam(value = "name") String name);

}
