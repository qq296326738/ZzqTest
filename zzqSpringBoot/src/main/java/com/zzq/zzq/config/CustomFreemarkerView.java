package com.zzq.zzq.config;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class CustomFreemarkerView extends FreeMarkerView {
    public CustomFreemarkerView() {
    }

    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        model.put("base", getContextPath(request));
        super.exposeHelpers(model, request);
    }

    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        return basePath;
    }

    public static String getContextPath(HttpServletRequest request) {
        String path = request.getContextPath();
        return path;
    }
}
