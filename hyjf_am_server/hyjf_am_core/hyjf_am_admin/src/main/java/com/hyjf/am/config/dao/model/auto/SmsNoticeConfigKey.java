package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;

public class SmsNoticeConfigKey implements Serializable {
    private Integer id;

    /**
     * 标识名称
     *
     * @mbggenerated
     */
    private String name;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}