package com.zzq.zzq.common;

import org.springframework.util.Base64Utils;

public class Base64Util {
    public Base64Util() {
    }

    public static byte[] encode(byte[] src) {
        return Base64Utils.encode(src);
    }

    public static byte[] decode(byte[] src) {
        return Base64Utils.decode(src);
    }

    public static byte[] encodeUrlSafe(byte[] src) {
        return Base64Utils.encodeUrlSafe(src);
    }

    public static byte[] decodeUrlSafe(byte[] src) {
        return Base64Utils.decodeUrlSafe(src);
    }

    public static String encodeToString(byte[] src) {
        return Base64Utils.encodeToString(src);
    }

    public static byte[] decodeFromString(String src) {
        return Base64Utils.decodeFromString(src);
    }

    public static String encodeToUrlSafeString(byte[] src) {
        return Base64Utils.encodeToUrlSafeString(src);
    }

    public static byte[] decodeFromUrlSafeString(String src) {
        return Base64Utils.decodeFromUrlSafeString(src);
    }

}
