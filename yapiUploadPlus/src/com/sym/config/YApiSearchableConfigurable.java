package com.sym.config;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.jgoodies.common.base.Strings;
import com.sym.config.impl.ProjectConfigReader;
import com.sym.constant.YApiConstants;
import com.sym.ui.YApiConfigurationForm;
import com.sym.xml.YApiProjectProperty;
import com.sym.xml.YApiPropertyConvertHolder;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class YApiSearchableConfigurable implements SearchableConfigurable {

    private YApiConfigurationForm yApiConfigurationForm;

    private YApiProjectProperty yApiProjectProperty;

    private final YApiProjectPersistentState persistent;

    private final Project project;

    YApiSearchableConfigurable(Project project) {
        this.project = project;
        this.persistent = YApiProjectPersistentState.getInstance(project);
    }

    @NotNull
    @Override
    public String getId() {
        return "YApiUploadPlus";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return YApiConstants.name;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (yApiConfigurationForm == null) {
            yApiConfigurationForm = new YApiConfigurationForm();
        }
        return yApiConfigurationForm.getPanel();
    }

    @Override
    public boolean isModified() {
        String idStr = yApiConfigurationForm.getProjectIdField().getText();
        int projectId = 1;
        if (Strings.isNotBlank(idStr)) {
            projectId = Integer.parseInt(idStr);
        }
        this.yApiProjectProperty = new YApiProjectProperty(
                yApiConfigurationForm.getUrlField().getText(),
                projectId,
                yApiConfigurationForm.getTokenField().getText(),
                yApiConfigurationForm.getNamingStrategyComboBox().getSelectedIndex(),
                yApiConfigurationForm.getDataModeComboBox().getSelectedIndex(),
                yApiConfigurationForm.getStatusComboBox().getSelectedIndex(),
                yApiConfigurationForm.getTagField().getText());
        return !this.yApiProjectProperty
                .equals(ProjectConfigReader.read(this.project));
    }

    @Override
    public void apply() {
        persistent.loadState(
                YApiPropertyConvertHolder.getConvert().serialize(this.yApiProjectProperty));
    }

    @Override
    public void reset() {
        this.loadValue();
    }

    private void loadValue() {
        YApiProjectProperty property = ProjectConfigReader.read(this.project);
        String url = property.getUrl();
        if (Strings.isNotBlank(url)) {
            yApiConfigurationForm.getUrlField().setText(url);
        }
        yApiConfigurationForm.getProjectIdField().setText(String.valueOf(property.getProjectId()));
        int strategy = property.getStrategy();
        yApiConfigurationForm.getNamingStrategyComboBox().setSelectedIndex(strategy);
        int dataMode = property.getDataMode();
        yApiConfigurationForm.getDataModeComboBox().setSelectedIndex(dataMode);
        int statusMode = property.getStatusMode();
        yApiConfigurationForm.getStatusComboBox().setSelectedIndex(statusMode);
        String token = property.getToken();
        if (Strings.isNotBlank(token)) {
            yApiConfigurationForm.getTokenField().setText(token);
        }
        String tag = property.getTag();
        if(Strings.isNotBlank(tag)){
            yApiConfigurationForm.getTagField().setText(tag);
        }

    }

}
