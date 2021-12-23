package com.sym.component;

import com.intellij.notification.NotificationListener.UrlOpeningListener;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.sym.config.YApiApplicationPersistentState;
import com.sym.config.impl.ApplicationConfigReader;
import com.sym.constant.NotificationConstants;
import com.sym.constant.PluginConstants;
import com.sym.constant.YApiConstants;
import com.sym.xml.YApiApplicationProperty;
import com.sym.xml.YApiPropertyConvertHolder;
import org.jetbrains.annotations.NotNull;

public class YApiInitComponent implements ProjectComponent {

    private final Project project;

    public YApiInitComponent(Project project) {
        this.project = project;
    }

    @Override
    public void projectOpened() {
        YApiApplicationProperty property = ApplicationConfigReader.read();
        if (property == null || !PluginConstants.currentVersion.equals(property.getVersion())) {
            String changeLogTitle = "<h4>2.0.6版本，兼容IDEA 2021.2版本</h4>";
            String changeLogContent = "<ol>\n"
                    + "        <li>兼容IDEA 2021.2版本</li>\n"
                    + "        <li>代码和注释模板优化</li>\n"
                    + "     </ol>";
            NotificationConstants.NOTIFICATION_GROUP_WINDOW.createNotification(YApiConstants.name,
                            "更新内容",
                            changeLogTitle + "\n" + changeLogContent
                                    ,
                            NotificationType.INFORMATION, new UrlOpeningListener(false))
                    .setImportant(true)
                    .notify(this.project);
            property = new YApiApplicationProperty();
            property.setVersion(PluginConstants.currentVersion);
            YApiApplicationPersistentState.getInstance().loadState(
                    YApiPropertyConvertHolder.getApplicationConvert().serialize(property));
        }
    }

    @Override
    public void projectClosed() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return "YApiInitComponent";
    }

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }
}
