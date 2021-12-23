package com.sym.dto;

import java.io.Serializable;

/**
 * @author zhangyunfan
 * @version 1.0
 * @ClassName: ConfigDTO
 * @Description: 配置实体
 * @date 2020/12/25
 */
public class ConfigDTO implements Serializable {

    String projectName;

    String moduleName;

    String projectToken;

    String projectId;

    String yapiUrl;

    String projectType;

    String returnClass;

    String attachUpload;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getProjectToken() {
        return projectToken;
    }

    public void setProjectToken(String projectToken) {
        this.projectToken = projectToken;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getYapiUrl() {
        return yapiUrl;
    }

    public void setYapiUrl(String yapiUrl) {
        this.yapiUrl = yapiUrl;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getReturnClass() {
        return returnClass;
    }

    public void setReturnClass(String returnClass) {
        this.returnClass = returnClass;
    }

    public String getAttachUpload() {
        return attachUpload;
    }

    public void setAttachUpload(String attachUpload) {
        this.attachUpload = attachUpload;
    }
}
