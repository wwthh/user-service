package com.wwt.userservice.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Success
 */
public class Success {

    public static JSONObject successResponse(JSONObject content){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", true);
        jsonObject.put("content", content);
        return jsonObject;
    }
}