package com.zzq.zzq.common;

import java.util.HashMap;
import java.util.Map;

public interface Constants {
    Map<Class<?>, String> cacheKeyMap = new HashMap();
    String Exception_Head = "OH,MY GOD! SOME ERRORS OCCURED! AS FOLLOWS :";
    String CURRENT_USER = "CURRENT_USER";
    String CACHE_NAMESPACE = "QMXGDS:";
    String PASSWORD_ERROR_COUNT = "PASSWORD_ERROR_COUNT";
    String PREREQUEST = "PREREQUEST";
    String PREREQUEST_TIME = "PREREQUEST_TIME";
    String MALICIOUS_REQUEST_TIMES = "MALICIOUS_REQUEST_TIMES";
    String API_LOG_REQ_CONTENT_ATTRIBUTE_NAME = "API_LOG_REQ_CONTENT";
    String API_LOG_RESP_CONTENT_ATTRIBUTE_NAME = "API_LOG_RESP_CONTENT";
}
