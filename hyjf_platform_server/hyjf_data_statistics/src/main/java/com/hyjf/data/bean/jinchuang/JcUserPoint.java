/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.bean.jinchuang;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version JcUserPoint, v0.1 2019/6/19 11:38
 */
@Document(collection = "ht_jc_user_point")
public class JcUserPoint implements Serializable {

    private String id;

    private JSONObject jo;

    private String time;

    private Integer createTime;

    private Integer updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JSONObject getJo() {
        return jo;
    }

    public void setJo(JSONObject jo) {
        this.jo = jo;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }
}
