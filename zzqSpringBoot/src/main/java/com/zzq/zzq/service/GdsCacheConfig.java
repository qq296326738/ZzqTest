package com.zzq.zzq.service;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GdsCacheConfig {
    String cacheNS();

    String cacheName();
}
