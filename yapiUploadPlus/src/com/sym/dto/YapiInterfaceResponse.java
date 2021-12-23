package com.sym.dto;

import java.io.Serializable;

/**
 * 接口对象
 *
 * @author 543426555@qq.com
 * @date 2019/7/28 10:17 AM
 */
public class YapiInterfaceResponse implements Serializable{
    /**
     * 描述
     */
    private String desc;
    /**
     * 分类id
     */
    private Integer catid;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }
}
