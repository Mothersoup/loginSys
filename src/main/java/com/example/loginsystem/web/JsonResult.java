package com.example.loginsystem.web;

import com.example.loginsystem.ex.ServiceException;
import lombok.Data;


@Data
public class JsonResult {
    private Integer code;

    /**
     * 錯誤時消息
     */
    private String message;

    /**
     * 處理成功時，需要響應的數據
     */
    private Object data;


    public static JsonResult ok() {
        return ok(null);
    }

    public static JsonResult ok(Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.code = ServerCode.OK;
        jsonResult.data = data;
        return jsonResult;
    }

    public static JsonResult fail(ServiceException e){
        return fail(e.getServiceCode(),e.getMessage());
    }

    public static JsonResult  fail(Integer code, String message){
        JsonResult jsonResult = new JsonResult();
        jsonResult.code = code;
        jsonResult.message = message;
        return jsonResult;
    }
}
