package com.zzq.xiaofei.controller;

import com.zzq.xiaofei.api.HelloRemote;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign Hystrix回调类
 */
@Component
public class HelloRemoteHystrix implements HelloRemote {

    @Override
    public String hello(@RequestParam(value = "name") String name) {
        return "hello" + name + ", 服务出错了!!!!!!!!!!! ";
    }
}
