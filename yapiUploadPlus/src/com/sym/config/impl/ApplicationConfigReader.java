package com.sym.config.impl;

import com.sym.config.ConfigurationReader;
import com.sym.config.YApiApplicationPersistentState;
import com.sym.xml.YApiApplicationProperty;
import com.sym.xml.YApiPropertyConvertHolder;
import org.jdom.Element;

public class ApplicationConfigReader {

    private ApplicationConfigReader() {
    }

    private final static ConfigurationReader<YApiApplicationProperty> reader = () -> {
        Element element = YApiApplicationPersistentState.getInstance().getState();
        YApiApplicationProperty property = null;
        if (element != null) {
            property = YApiPropertyConvertHolder.getApplicationConvert().deserialize(element);
        }
        return property;
    };

    public static YApiApplicationProperty read() {
        return reader.read();
    }
}
