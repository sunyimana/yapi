package com.sym.interaction;

import com.google.common.base.Strings;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.sym.builder.BuildJsonForDubbo;
import com.sym.builder.BuildJsonForYapi;
import com.sym.component.ConfigPersistence;
import com.sym.config.impl.ProjectConfigReader;
import com.sym.constant.ProjectTypeConstant;
import com.sym.constant.StatusEnum;
import com.sym.constant.YapiConstant;
import com.sym.dto.*;
import com.sym.upload.UploadYapi;
import com.sym.xml.YApiProjectProperty;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 描述信息
 * @Author dongyue.liu
 * @Create 2023-07-24-18:26
 */
public class BatchUploadToYapi extends AnAction {

    private static NotificationGroup notificationGroup;

    static {
        notificationGroup = new NotificationGroup("Java2Json.NotificationGroup", NotificationDisplayType.BALLOON, true);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (null == project) {
            Messages.showErrorDialog("请重新基于project视图选择", "获取project失败！");
            return;
        }

        /*
         * 从Action中得到一个虚拟文件
         */
        VirtualFile virtualFile = e.getData(DataKeys.VIRTUAL_FILE);
        if (null == virtualFile) {
            return;
        }

        // 获取yapi插件配置
        String projectToken = null;
        String projectId = null;
        String yapiUrl = null;
        String tag = null;
        String headerName = null;
        String headerValue = null;
        String projectType = null;
        int statusMode = 0;
        String status = null;
        String returnClass = null;
        String attachUpload = null;
        // 获取配置
        try {
            YApiProjectProperty property = ProjectConfigReader.read(project);
            projectToken = property.getToken();
            projectId = property.getProjectId() + "";
            yapiUrl = property.getUrl();
            tag = property.getTag();
            headerName = property.getHeaderName();
            headerValue = property.getHeaderValue();
            projectType = ProjectTypeConstant.api;
            statusMode = property.getStatusMode();

            status = StatusEnum.enumByType(statusMode).name();
            if (0 == property.getDataMode()) {
                projectType = ProjectTypeConstant.dubbo;

            }
            java.util.List<String> tagList = new ArrayList<>();
            if (null != tag) {
                tagList = Arrays.asList(tag.split(","));
            }
        } catch (Exception ex) {
            Messages.showErrorDialog("获取配置失败，异常:  " + ex.getMessage(), "获取配置失败！");
            return;
        }

        if (!virtualFile.isDirectory()) {
            virtualFile = virtualFile.getParent();
        }

        /*
         * Module module = ModuleUtil.findModuleForFile(virtualFile, project);
         * String moduleRootPath = ModuleRootManager.getInstance(module).getContentRoots()[0].getPath();
         * String actionDir = virtualFile.getPath();
         * String str = StringUtils.substringAfter(actionDir, moduleRootPath + "/src/main/java/");
         * String basePackage = StringUtils.replace(str, "/", ".");
         */

        PsiDirectory dir = PsiManager.getInstance(project).findDirectory(virtualFile);
        if (null == dir) {
            return;
        }
        // 查找所有文件
        Map<String, PsiFile> fileMap = new HashMap<>();
        Queue<PsiFile> queue = new LinkedList<>(Arrays.asList(dir.getFiles()));

        while (!queue.isEmpty()) {
            PsiFile current = queue.poll();

            if (!current.isDirectory() && current.getName().endsWith(".java")) {
                // 如果不是目录
                fileMap.put(current.getName(), current);
            } else {
                // 如果是目录，就入队
                queue.add(current);
            }
        }

        if (ProjectTypeConstant.dubbo.equals(projectType)) {
            for (String key : fileMap.keySet()) {
                // 从key中提取file的名字，得到类名
                String fileName = key.substring(0, key.indexOf("."));
                ArrayList<YapiDubboDTO> yapiDubboDTOs = new BuildJsonForDubbo().actionPerformedList(fileName, fileMap.get(key), project);
                if (yapiDubboDTOs != null) {
                    for (YapiDubboDTO yapiDubboDTO : yapiDubboDTOs) {
                        YapiSaveParam yapiSaveParam = new YapiSaveParam(projectToken, yapiDubboDTO.getTitle(), yapiDubboDTO.getPath(), yapiDubboDTO.getParams(), yapiDubboDTO.getResponse(), Integer.valueOf(projectId), yapiUrl, yapiDubboDTO.getDesc());

                        if (StringUtils.isBlank(yapiDubboDTO.getStatus())) {
                            yapiSaveParam.setStatus(status);
                        } else {
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
                        if (null != tagList) {
                            yapiSaveParam.setTag(tagList);
                        }

                        try {
                            // 上传
                            YapiResponse yapiResponse = new UploadYapi().uploadSave(yapiSaveParam, null, project.getBasePath());
                            if (yapiResponse.getErrcode() != 0) {
                                Messages.showErrorDialog("上传失败！异常:  " + yapiResponse.getErrmsg(), "上传失败！");
                            } else {
                                String url = yapiUrl + "/project/" + projectId + "/interface/api/cat_" + yapiResponse.getCatId();
                                // this.setClipboard(url);
                                // Messages.showInfoMessage("上传成功！接口文档url地址:  " + url,"上传成功！");
                            }
                        } catch (Exception e1) {
                            Messages.showErrorDialog("上传失败！异常:  " + e1, "上传失败！");
                        }
                    }
                }
            }
        } else if (ProjectTypeConstant.api.equals(projectType)) {
            //获得api 需上传的接口列表 参数对象
            for (String key : fileMap.keySet()) {
                // 从key中提取file的名字，得到类名
                String fileName = key.substring(0, key.indexOf("."));
                ArrayList<YapiApiDTO> yapiApiDTOS = new BuildJsonForYapi().actionPerformedList(fileName, fileMap.get(key), project, attachUpload, returnClass);
                if (yapiApiDTOS != null) {
                    for (YapiApiDTO yapiApiDTO : yapiApiDTOS) {
                        YapiSaveParam yapiSaveParam = new YapiSaveParam(projectToken, yapiApiDTO.getTitle(), yapiApiDTO.getPath(),
                                yapiApiDTO.getParams(), yapiApiDTO.getRequestBody(), yapiApiDTO.getResponse(), Integer.valueOf(projectId),
                                yapiUrl, true, yapiApiDTO.getMethod(), yapiApiDTO.getDesc(), yapiApiDTO.getHeader());

                        yapiSaveParam.setReq_body_form(yapiApiDTO.getReq_body_form());
                        yapiSaveParam.setReq_body_type(yapiApiDTO.getReq_body_type());
                        yapiSaveParam.setReq_params(yapiApiDTO.getReq_params());
                        if (StringUtils.isBlank(yapiApiDTO.getStatus())) {
                            yapiSaveParam.setStatus(status);
                        } else {
                            yapiSaveParam.setStatus(yapiApiDTO.getStatus());
                        }
                        if (!Strings.isNullOrEmpty(yapiApiDTO.getMenu())) {
                            yapiSaveParam.setMenu(yapiApiDTO.getMenu());
                        } else {
                            yapiSaveParam.setMenu(YapiConstant.menu);
                        }
                        if (null != tagList) {
                            yapiSaveParam.setTag(tagList);
                        }
                        if (StringUtils.isNotBlank(headerName) && StringUtils.isNotBlank(headerValue)) {
                            YapiHeaderDTO yapiHeaderDTO = new YapiHeaderDTO();
                            yapiHeaderDTO.setName(headerName);
                            yapiHeaderDTO.setValue(headerValue);
                            if (Objects.isNull(yapiSaveParam.getReq_headers())) {
                                List list = new ArrayList();
                                list.add(yapiHeaderDTO);
                                yapiSaveParam.setReq_headers(list);
                            } else {
                                yapiSaveParam.getReq_headers().add(yapiHeaderDTO);
                            }
                        }

                        try {
                            // 上传
                            YapiResponse yapiResponse = new UploadYapi().uploadSave(yapiSaveParam, attachUpload, project.getBasePath());
                            if (yapiResponse.getErrcode() != 0) {
                                Messages.showInfoMessage("上传失败，原因:  " + yapiResponse.getErrmsg(), "上传失败！");
                            } else {
                                String url = yapiUrl + "/project/" + projectId + "/interface/api/cat_" + yapiResponse.getCatId();
                                // this.setClipboard(url);
                                // Messages.showInfoMessage("上传成功！接口文档url地址:  " + url, "上传成功！");
                            }
                        } catch (Exception e1) {
                            Messages.showErrorDialog("上传失败！异常:  " + e1, "上传失败！");
                        }
                    }
                }
            }
        }
        Messages.showInfoMessage("批量上传完成！" + project.getName(), "上传成功！");
    }

}
