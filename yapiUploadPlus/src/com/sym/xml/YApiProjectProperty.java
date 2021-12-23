package com.sym.xml;

import java.util.Objects;

public class YApiProjectProperty {

    private final static String DEFAULT_URL = "http://127.0.0.1:3000/";
    private final static int DEFAULT_PROJECT_ID = 1;
    private final static String DEFAULT_TOKEN = "";
    private final static int DEFAULT_STRATEGY = 0;
    private final static int DEFAULT_DATA_MODE = 0;
    private final static int DEFAULT_STATUS_MODE = 0;

    private String url = DEFAULT_URL;
    private int projectId = DEFAULT_PROJECT_ID;
    private String token = DEFAULT_TOKEN;
    private int strategy = DEFAULT_STRATEGY;
    private int dataMode = DEFAULT_DATA_MODE;

    private int statusMode = DEFAULT_STATUS_MODE;

    private String tag = "";
    private boolean enableBasicScope;

    public YApiProjectProperty() {

    }

    public YApiProjectProperty(String url, int projectId, String token, int strategy,
                               int dataMode,int statusMode, String tag) {
        this.projectId = projectId;
        this.token = token;
        this.strategy = strategy;
        this.dataMode = dataMode;
        this.statusMode = statusMode;
        this.setUrl(url);
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url.matches(".*/+$")) {
            this.url = url.replaceAll("/+$", "");
        } else {
            this.url = url;
        }
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getToken() {
        return token;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    public int getDataMode() {
        return dataMode;
    }

    public int getStatusMode() {
        return statusMode;
    }

    public void setStatusMode(int statusMode) {
        this.statusMode = statusMode;
    }

    public void setDataMode(int dataMode) {
        this.dataMode = dataMode;
    }

    public boolean isEnableBasicScope() {
        return enableBasicScope;
    }

    public void setEnableBasicScope(boolean enableBasicScope) {
        this.enableBasicScope = enableBasicScope;
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(this.url, this.projectId, this.token, this.strategy, this.enableBasicScope,this.tag);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        YApiProjectProperty that = (YApiProjectProperty) obj;
        return Objects.equals(url, that.url) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(token, that.token) &&
                Objects.equals(strategy, that.strategy) &&
                Objects.equals(dataMode, that.dataMode) &&
                Objects.equals(statusMode, that.statusMode) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(enableBasicScope, that.enableBasicScope);
    }
}
