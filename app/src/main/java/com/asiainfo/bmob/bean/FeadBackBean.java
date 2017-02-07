package com.asiainfo.bmob.bean;

import cn.bmob.v3.BmobObject;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月07日20点09分 描述:
 */
public class FeadBackBean extends BmobObject {

    private String name;
    private String feadback;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeadback() {
        return feadback;
    }

    public void setFeadback(String feadback) {
        this.feadback = feadback;
    }
}
