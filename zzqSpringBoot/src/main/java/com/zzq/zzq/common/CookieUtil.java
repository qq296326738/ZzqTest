package com.zzq.zzq.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CookieUtil {
    private CookieUtil() {
    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge, String path, String domain, Boolean secure) {
        Assert.notNull(request, "request is null");
        Assert.notNull(response, "response is null");
        Assert.hasText(name, "name is null");

        try {
            name = URLEncoder.encode(name, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);
            if (maxAge != null) {
                cookie.setMaxAge(maxAge.intValue());
            }

            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }

            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }

            if (secure != null) {
                cookie.setSecure(secure.booleanValue());
            }

            response.addCookie(cookie);
        } catch (UnsupportedEncodingException var9) {
            var9.printStackTrace();
        }

    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge) {
        addCookie(request, response, name, value, maxAge, "/", "", (Boolean) null);
    }

    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        addCookie(request, response, name, value, (Integer) null, "/", "", (Boolean) null);
    }

    public static String getCookie(HttpServletRequest request, String name) {
        Assert.notNull(request, "request is null!");
        Assert.hasText(name, "name is null");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            try {
                name = URLEncoder.encode(name, "UTF-8");
                Cookie[] var3 = cookies;
                int var4 = cookies.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    Cookie cookie = var3[var5];
                    if (name.equals(cookie.getName())) {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    }
                }
            } catch (UnsupportedEncodingException var7) {
                var7.printStackTrace();
            }
        }

        return null;
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String path, String domain) {
        Assert.notNull(request, "request is null");
        Assert.notNull(response, "response is null");
        Assert.hasText(name, "name is null");

        try {
            name = URLEncoder.encode(name, "UTF-8");
            Cookie cookie = new Cookie(name, (String) null);
            cookie.setMaxAge(0);
            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }

            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }

            response.addCookie(cookie);
        } catch (UnsupportedEncodingException var6) {
            var6.printStackTrace();
        }

    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        removeCookie(request, response, name, "/", "");
    }

}
