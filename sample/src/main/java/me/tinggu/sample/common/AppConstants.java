package me.tinggu.sample.common;

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : wangjia_bi
 * Date       : 2015/6/8 20:09
 */
public interface AppConstants {

    //---------------OkHttp配置-----------------------
    String RESPONSE_CACHE = "netCache";

    long RESPONSE_CACHE_SIZE = 10 * 1024 * 1024;

    long HTTP_CONNECT_TIMEOUT = 1000 * 30;

    long HTTP_READ_TIMEOUT = HTTP_CONNECT_TIMEOUT;

    //-------------项目配置------------------------
    String PLATFORM_VALUE = "android";

    /**
     * 项目名字做为资源访问路径
     */
    String PROJECT_NAME = "socialComment";

    String IMAGE_SERVER_URL = "http://10.12.5.21:8080/socialWeb";

    String SCRET_KEY = "9cb5ee00b6a87c24a2fabf87c6dba2e1";

    String DEFAULT_BIRTHDAY = "1940-01-01";
}
