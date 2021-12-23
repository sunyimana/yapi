package com.sym.interaction;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.sym.builder.BuildJsonForDubbo;
import com.sym.builder.BuildJsonForYapi;
import com.sym.config.impl.ProjectConfigReader;
import com.sym.constant.ProjectTypeConstant;
import com.sym.constant.StatusEnum;
import com.sym.constant.YapiConstant;
import com.sym.dto.*;
import com.sym.upload.UploadYapi;
import com.sym.xml.YApiProjectProperty;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @description: 入口
 * @author: 543426555@qq.com
 * @date: 2019/5/15
 */
public class UploadToYapi extends AnAction {

    private static NotificationGroup notificationGroup;
    private Gson gson=new Gson();
    static {
        notificationGroup = new NotificationGroup("Java2Json.NotificationGroup", NotificationDisplayType.BALLOON, true);
    }


    @Override
    public void actionPerformed(AnActionEvent e) {
     //   Editor editor = (Editor) e.getDataContext().getData(CommonDataKeys.EDITOR);

        Project project = e.getData(CommonDataKeys.PROJECT);

        YApiProjectProperty property = ProjectConfigReader.read(project);

     //   Project project = editor.getProject();
        String projectToken = property.getToken();;
        String projectId = property.getProjectId()+"";
        String yapiUrl = property.getUrl();
        String tag = property.getTag();
        String projectType = ProjectTypeConstant.api;
        int statusMode = property.getStatusMode();

        String status = StatusEnum.enumByType(statusMode).name();
        if(0==property.getDataMode()){
            projectType=ProjectTypeConstant.dubbo;

        }
        String returnClass = null;
        String attachUpload = null;
        java.util.List<String> tagList = new ArrayList<>();
        if(null!=tag){

            tagList = Arrays.asList(tag.split(","));
        }

        // 判断项目类型
        if (ProjectTypeConstant.dubbo.equals(projectType)) {
            // 获得dubbo需上传的接口列表 参数对象
            ArrayList<YapiDubboDTO> yapiDubboDTOs = new BuildJsonForDubbo().actionPerformedList(e);
            if (yapiDubboDTOs != null) {
                for (YapiDubboDTO yapiDubboDTO : yapiDubboDTOs) {
                    YapiSaveParam yapiSaveParam = new YapiSaveParam(projectToken, yapiDubboDTO.getTitle(), yapiDubboDTO.getPath(), yapiDubboDTO.getRequestBody(), yapiDubboDTO.getResponse(), Integer.valueOf(projectId), yapiUrl, yapiDubboDTO.getDesc());

                    if(StringUtils.isBlank(yapiDubboDTO.getStatus())){
                        yapiSaveParam.setStatus(status);
                    }else{
                        yapiSaveParam.setStatus(yapiDubboDTO.getStatus());
                    }
                    if (!Strings.isNullOrEmpty(yapiDubboDTO.getMenu())) {
                        yapiSaveParam.setMenu(yapiDubboDTO.getMenu());
                    } else {
                        yapiSaveParam.setMenu(YapiConstant.menu);
                    }
                    yapiSaveParam.setMethod(ProjectTypeConstant.dubbo);
                    yapiSaveParam.setDubbo_service(yapiDubboDTO.getDubbo_service());
                    yapiSaveParam.setDubbo_method(yapiDubboDTO.getDubbo_method());
                    yapiSaveParam.setReq_body_is_json_schema(true);
                    if(null!=tagList){
                        yapiSaveParam.setTag(tagList);
                    }
                    try {
                        // 上传
                        YapiResponse yapiResponse = new UploadYapi().uploadSave(yapiSaveParam, null, project.getBasePath());
                        if (yapiResponse.getErrcode() != 0) {
                            Messages.showErrorDialog("上传失败！异常:  " + yapiResponse.getErrmsg(),"上传失败！");
                        } else {
                            String url = yapiUrl + "/project/" + projectId + "/interface/api/cat_" + yapiResponse.getCatId();
                            this.setClipboard(url);
                            Messages.showInfoMessage("上传成功！接口文档url地址:  " + url,"上传成功！");
                        }
                    } catch (Exception e1) {
                        Messages.showErrorDialog("上传失败！异常:  " + e1,"上传失败！");
                    }
                }
            }
        } else if (ProjectTypeConstant.api.equals(projectType)) {
            //获得api 需上传的接口列表 参数对象
            ArrayList<YapiApiDTO> yapiApiDTOS = new BuildJsonForYapi().actionPerformedList(e, attachUpload, returnClass);
            if (yapiApiDTOS != null) {
                for (YapiApiDTO yapiApiDTO : yapiApiDTOS) {
                    YapiSaveParam yapiSaveParam = new YapiSaveParam(projectToken, yapiApiDTO.getTitle(), yapiApiDTO.getPath(), yapiApiDTO.getParams(), yapiApiDTO.getRequestBody(), yapiApiDTO.getResponse(), Integer.valueOf(projectId), yapiUrl, true, yapiApiDTO.getMethod(), yapiApiDTO.getDesc(), yapiApiDTO.getHeader());
                    yapiSaveParam.setReq_body_form(yapiApiDTO.getReq_body_form());
                    yapiSaveParam.setReq_body_type(yapiApiDTO.getReq_body_type());
                    yapiSaveParam.setReq_params(yapiApiDTO.getReq_params());
                    if(StringUtils.isBlank(yapiApiDTO.getStatus())){
                        yapiSaveParam.setStatus(status);
                    }else{
                        yapiSaveParam.setStatus(yapiApiDTO.getStatus());
                    }
                    if (!Strings.isNullOrEmpty(yapiApiDTO.getMenu())) {
                        yapiSaveParam.setMenu(yapiApiDTO.getMenu());
                    } else {
                        yapiSaveParam.setMenu(YapiConstant.menu);
                    }
                    if(null!=tagList){
                        yapiSaveParam.setTag(tagList);
                    }
                    try {
                        // 上传
                        YapiResponse yapiResponse = new UploadYapi().uploadSave(yapiSaveParam, attachUpload, project.getBasePath());
                        if (yapiResponse.getErrcode() != 0) {
                            Messages.showInfoMessage("上传失败，原因:  " + yapiResponse.getErrmsg(),"上传失败！");
                        } else {
                            String url = yapiUrl + "/project/" + projectId + "/interface/api/cat_" + yapiResponse.getCatId();
                            this.setClipboard(url);
                            Messages.showInfoMessage("上传成功！接口文档url地址:  " + url,"上传成功！");
                        }
                    } catch (Exception e1) {
                        Messages.showErrorDialog("上传失败！异常:  " + e1,"上传失败！");
                    }
                }
            }
        }
    }

    /**
     * @description: 设置到剪切板
     * @param: [content]
     * @return: void
     * @author: 543426555@qq.com
     * @date: 2019/7/3
     */
    private void setClipboard(String content) {
        //获取系统剪切板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //构建String数据类型
        StringSelection selection = new StringSelection(content);
        //添加文本到系统剪切板
        clipboard.setContents(selection, null);
    }
}
