package com.feture.learnfilter.consts;

import java.util.HashSet;

/**
 * 请求常量
 */
public final class RequestConst {

    public final static String RouteDispatchPrefix = "/openapi";

    public final static HashSet<String> CharsetSet = new HashSet<String>() {{
        add("utf-8");
        add("UTF-8");
    }};


    public final static HashSet<String> VersionSet = new HashSet<String>() {{
        add("1.0");
    }};

    public final static HashSet<String> FormatSet = new HashSet<String>() {{
        add("json");
    }};

    public final static HashSet<String> SignTypeSet = new HashSet<String>() {{
        add("SHA256");
        add("MD5");
    }};

}
