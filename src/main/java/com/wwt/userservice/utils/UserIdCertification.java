package com.wwt.userservice.utils;

import com.alibaba.fastjson.JSONObject;

public class UserIdCertification {

    public static JSONObject run(String userId, String id) {
        if(userId==null || userId.equals(""))
            return Error.errorResponse(3);
        if(!userId.equals(id))
            return Error.errorResponse(2);
        return null;
    }
}
