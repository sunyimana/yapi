package com.sym.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.sym.xml.YApiApplicationProperty;
import com.sym.xml.YApiPropertyConvertHolder;
import com.sym.xml.YApiPropertyXmlConvert;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

@State(name = "ApplicationYApiUploadPlus", storages = @Storage("sym-yapi.xml"))
public class YApiApplicationPersistentState implements PersistentStateComponent<Element> {

    private final YApiPropertyXmlConvert<YApiApplicationProperty> convert = YApiPropertyConvertHolder
            .getApplicationConvert();
    private YApiApplicationProperty yApiApplicationProperty;

    YApiApplicationPersistentState() {
    }

    public static YApiApplicationPersistentState getInstance() {
        return ServiceManager.getService(YApiApplicationPersistentState.class);
    }

    @Override
    public Element getState() {
        return yApiApplicationProperty == null ? null
                : this.convert.serialize(yApiApplicationProperty);
    }

    @Override
    public void loadState(@NotNull Element element) {
        this.yApiApplicationProperty = this.convert.deserialize(element);
    }

}
