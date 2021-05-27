package com.community.aspn.util;

import lombok.Data;

@Data
public class AjaxResponse {

    private boolean isok;  //请求是否处理成功
    private int code; //请求响应状态码（200、400、500）
    private String message;  //请求结果描述信息
    private Object data; //请求结果数据（通常用于查询操作）

    private AjaxResponse() {
    }

    //请求成功的响应，不带查询数据（用于删除、修改、新增接口）
    public static AjaxResponse success() {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setIsok(true);
        ajaxResponse.setCode(200);
        ajaxResponse.setMessage("success");
        return ajaxResponse;
    }

    //请求成功的响应，带有查询数据（用于数据查询接口）
    public static AjaxResponse success(Object obj) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setIsok(true);
        ajaxResponse.setCode(200);
        ajaxResponse.setMessage("success");
        ajaxResponse.setData(obj);
        return ajaxResponse;
    }


    //请求成功的响应，带有查询数据（用于数据查询接口）
    public static AjaxResponse success(Object obj, String message) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setIsok(true);
        ajaxResponse.setCode(200);
        ajaxResponse.setMessage(message);
        ajaxResponse.setData(obj);
        return ajaxResponse;
    }


    public static AjaxResponse error() {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setIsok(true);
        ajaxResponse.setCode(500);
        ajaxResponse.setMessage("service error");
        return ajaxResponse;
    }

    public static AjaxResponse error(Object obj) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setIsok(true);
        ajaxResponse.setCode(500);
        ajaxResponse.setMessage("service error");
        ajaxResponse.setData(obj);
        return ajaxResponse;
    }


    public static AjaxResponse error(Object obj, String message) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setIsok(true);
        ajaxResponse.setCode(500);
        ajaxResponse.setMessage(message);
        ajaxResponse.setData(obj);
        return ajaxResponse;
    }


}