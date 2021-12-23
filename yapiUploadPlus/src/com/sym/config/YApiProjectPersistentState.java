package com.sym.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.project.Project;
import com.sym.xml.YApiProjectProperty;
import com.sym.xml.YApiPropertyConvertHolder;
import com.sym.xml.YApiPropertyXmlConvert;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

@State(name = "ProjectYApiUploadPlus")
public class YApiProjectPersistentState implements PersistentStateComponent<Element> {

    private final YApiPropertyXmlConvert<YApiProjectProperty> convert = YApiPropertyConvertHolder
            .getConvert();
    private YApiProjectProperty yApiProjectProperty;

    YApiProjectPersistentState() {
    }

    public static YApiProjectPersistentState getInstance(Project project) {
        return ServiceManager.getService(project, YApiProjectPersistentState.class);
    }

    @Override
    public Element getState() {
        return yApiProjectProperty == null ? null : this.convert.serialize(yApiProjectProperty);
    }

    @Override
    public void loadState(@NotNull Element element) {
        this.yApiProjectProperty = this.convert.deserialize(element);
    }

}
