package com.sym.ui;

import com.intellij.openapi.ui.ComboBox;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * 配置面板
 */
public class YApiConfigurationForm {

    private JTextField urlField;
    private JTextField tokenField;
    private JPanel panel;
    private JFormattedTextField projectIdField;
    private ComboBox<String> namingStrategyComboBox;
    private ComboBox<String> dataModeComboBox;
    private JTextField tagField;
    private ComboBox<String> statusComboBox;

    public JTextField getTagField() {
        return tagField;
    }

    public ComboBox<String> getStatusComboBox() {
        return statusComboBox;
    }

    public void setStatusComboBox(ComboBox<String> statusComboBox) {
        this.statusComboBox = statusComboBox;
    }

    public void setTagField(JTextField tagField) {
        this.tagField = tagField;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getUrlField() {
        return urlField;
    }

    public JFormattedTextField getProjectIdField() {
        return projectIdField;
    }

    public JTextField getTokenField() {
        return tokenField;
    }

    public ComboBox<String> getNamingStrategyComboBox() {
        return namingStrategyComboBox;
    }

    public ComboBox<String> getDataModeComboBox() {
        return dataModeComboBox;
    }

    private void createUIComponents() {
        this.projectIdField = new JFormattedTextField(new DecimalFormat("#0"));
        this.projectIdField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                String old = projectIdField.getText();
                JFormattedTextField.AbstractFormatter formatter = projectIdField.getFormatter();
                if (!old.equals("")) {
                    if (formatter != null) {
                        String str = projectIdField.getText();
                        try {
                            long page = (Long) formatter.stringToValue(str);
                            projectIdField.setText(page + "");
                        } catch (ParseException pe) {
                            projectIdField.setText("1");//解析异常直接将文本框中值设置为1
                        }
                    }
                }
            }
        });
        this.namingStrategyComboBox = new ComboBox<>(
                new String[]{"None", "KebabCase", "SnakeCase", "LowerCase", "UpperCamelCase"});
        this.statusComboBox = new ComboBox<>(
                new String[]{"未完成", "已完成", "待更新", "已废弃"});
        this.dataModeComboBox = new ComboBox<>(new String[]{"dubbo", "api"});
    }

}
