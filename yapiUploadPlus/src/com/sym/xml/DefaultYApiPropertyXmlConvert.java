package com.sym.xml;

import com.jgoodies.common.base.Strings;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class DefaultYApiPropertyXmlConvert implements YApiPropertyXmlConvert<YApiProjectProperty> {

    private static final String KEY_URL = "url";
    private static final String KEY_PROJECT_ID = "project-id";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_TAG = "tag";
    private static final String KEY_HEADER_NAME = "header-name";

    private static final String KEY_HEADER_VALUE = "header-value";
    private static final String KEY_DATA_MODE = "data-mode";
    private static final String KEY_STATUS_MODE = "status-mode";
    private static final String KEY_STRATEGY = "property-naming-strategy";
    private static final String KEY_ENABLE_BASIC_SCOPE = "enable-basic-scope";

    @Override
    public Element serialize(@NotNull YApiProjectProperty property) {
        String url = property.getUrl();
        int projectId = property.getProjectId();
        String token = property.getToken();
        int strategy = property.getStrategy();
        int dataMode = property.getDataMode();
        int statusMode = property.getStatusMode();
        String tag = property.getTag();
        String headerName = property.getHeaderName();
        String headerValue = property.getHeaderValue();
        boolean enableBasicScope = property.isEnableBasicScope();
        Element element = new Element("symYApiUpload");
        if (Strings.isNotBlank(url)) {
            element.setAttribute(KEY_URL, url);
        }
        element.setAttribute(KEY_PROJECT_ID, Integer.toString(projectId));
        if (Strings.isNotBlank(token)) {
            element.setAttribute(KEY_TOKEN, token);
        }
        if (Strings.isNotBlank(tag)) {
            element.setAttribute(KEY_TAG, tag);
        }
        if (Strings.isNotBlank(headerName)) {
            element.setAttribute(KEY_HEADER_NAME, headerName);
        }
        if (Strings.isNotBlank(headerValue)) {
            element.setAttribute(KEY_HEADER_VALUE, headerValue);
        }
        element.setAttribute(KEY_STATUS_MODE, Integer.toString(statusMode));
        element.setAttribute(KEY_DATA_MODE, Integer.toString(dataMode));
        element.setAttribute(KEY_STRATEGY, Integer.toString(strategy));
        element.setAttribute(KEY_ENABLE_BASIC_SCOPE, Boolean.toString(enableBasicScope));
        return element;
    }

    @Override
    public YApiProjectProperty deserialize(@NotNull Element element) {
        YApiProjectProperty property = new YApiProjectProperty();
        String url = element.getAttributeValue(KEY_URL);
        if (Strings.isNotBlank(url)) {
            property.setUrl(url);
        }
        String p = element.getAttributeValue(KEY_PROJECT_ID);
        int projectId = 1;
        if (Strings.isNotBlank(p)) {
            projectId = Integer.parseInt(p);
        }
        property.setProjectId(projectId);
        String token = element.getAttributeValue(KEY_TOKEN);
        if (Strings.isNotBlank(token)) {
            property.setToken(token);
        }
        String tag = element.getAttributeValue(KEY_TAG);
        if (Strings.isNotBlank(tag)) {
            property.setTag(tag);
        }

        String headerName = element.getAttributeValue(KEY_HEADER_NAME);
        if (Strings.isNotBlank(headerName)) {
            property.setHeaderName(headerName);
        }

        String headerValue = element.getAttributeValue(KEY_HEADER_VALUE);
        if (Strings.isNotBlank(headerValue)) {
            property.setHeaderValue(headerValue);
        }
        String s = element.getAttributeValue(KEY_STRATEGY);
        int strategy = 0;
        if (Strings.isNotBlank(s)) {
            strategy = Integer.parseInt(s);
        }
        property.setStrategy(strategy);
        String d = element.getAttributeValue(KEY_DATA_MODE);
        int dataMode = 0;
        if (Strings.isNotBlank(d)) {
            dataMode = Integer.parseInt(d);
        }
        property.setDataMode(dataMode);

        String status = element.getAttributeValue(KEY_STATUS_MODE);
        int statusMode = 0;
        if (Strings.isNotBlank(status)) {
            statusMode = Integer.parseInt(status);
        }
        property.setStatusMode(statusMode);
        String e = element.getAttributeValue(KEY_ENABLE_BASIC_SCOPE);
        boolean enableBasicScope = Boolean.parseBoolean(e);
        property.setEnableBasicScope(enableBasicScope);
        return property;
    }
}
