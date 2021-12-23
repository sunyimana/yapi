package com.sym.dto;

import java.io.Serializable;

/**
 * yapi dubbo 对象
 *
 * @author 543426555@qq.com
 * @date 2019/1/31 5:36 PM
 */
public class YapiDubboDTO implements Serializable {
    /**
     * 路径
     */
    private String path;
    /**
     * 请求参数
     */
    private String params;
    /**
     * title
     */
    private String title;
    /**
     * 响应
     */
    private String response;
    /**
     * 描述
     */
    private String desc;
    /**
     * 菜单
     */
    private String menu;
    /**
     * 状态
     */
    private String status;

    /**
     * 服务名
     */
    private String dubbo_service;

    /**
     * 方法名
     */
    private String dubbo_method;

    /**
     * 请求体
     */
    private String requestBody;



    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public YapiDubboDTO() {
    }

    public String getDubbo_service() {
        return dubbo_service;
    }

    public void setDubbo_service(String dubbo_service) {
        this.dubbo_service = dubbo_service;
    }

    public String getDubbo_method() {
        return dubbo_method;
    }

    public void setDubbo_method(String dubbo_method) {
        this.dubbo_method = dubbo_method;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}
