package com.zzq.zzq.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;

import java.nio.charset.Charset;

public class FastJson2JsonRedisSerializer {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public FastJson2JsonRedisSerializer() {
    }

    public byte[] serialize(Object object) throws SerializationException {
        return object == null ? new byte[0] : JSON.toJSONString(object, new SerializerFeature[]{SerializerFeature.WriteClassName}).getBytes(DEFAULT_CHARSET);
    }

    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes != null && bytes.length > 0) {
            String str = new String(bytes, DEFAULT_CHARSET);
            return JSON.parseObject(str, Object.class);
        } else {
            return null;
        }
    }

}
