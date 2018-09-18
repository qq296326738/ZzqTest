package com.zzq.zzq.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisLock {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    private static final int DEFAULT_SINGLE_EXPIRE_TIME = 3;
    private Logger logger = LoggerFactory.getLogger(RedisLock.class);

    public RedisLock() {
    }

    public boolean tryLock(String lockKey, long timeout, TimeUnit unit) {
        try {
            long nano = System.nanoTime();

            do {
                this.logger.debug("try lock key: " + lockKey);
                Boolean flag = this.redisTemplate.opsForValue().setIfAbsent(lockKey, lockKey);
                if (flag.booleanValue()) {
                    this.redisTemplate.expire(lockKey, 3L, unit);
                    this.logger.debug("get lock, key: " + lockKey + " , expire in " + 3 + " seconds.");
                    return Boolean.TRUE.booleanValue();
                }

                String desc = (String) this.redisTemplate.opsForValue().get(lockKey);
                this.logger.debug("key: " + lockKey + " locked by another business：" + desc);
                if (timeout == 0L) {
                    break;
                }

                Thread.sleep(200L);
            } while (System.nanoTime() - nano < unit.toNanos(timeout));

            return Boolean.FALSE.booleanValue();
        } catch (Exception var9) {
            this.logger.error(var9.getMessage(), var9);
            return false;
        }
    }

    public void unlock(String lockKey) {
        try {
            this.redisTemplate.delete(lockKey);
            this.logger.debug("unlock key:" + lockKey);
        } catch (Exception var3) {
            this.logger.error(var3.getMessage(), var3);
        }

    }
}
