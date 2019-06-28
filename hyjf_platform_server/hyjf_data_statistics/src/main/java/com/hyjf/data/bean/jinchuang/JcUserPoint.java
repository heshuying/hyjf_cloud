/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.bean.jinchuang;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

/**
 * @author yaoyong
 * @version JcUserPoint, v0.1 2019/6/19 11:38
 * 金创用户行为表
 */
@Document(collection = "ht_jc_user_point")
public class JcUserPoint implements Serializable {

    private String id;

    private String jo;

    private String time;

    private String createTime;

    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getJo() {
        return jo;
    }

    public void setJo(String jo) {
        this.jo = jo;
    }
}
