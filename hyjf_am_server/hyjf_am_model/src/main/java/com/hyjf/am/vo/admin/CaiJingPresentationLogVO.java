/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version CaiJingPresentationLogVO, v0.1 2019/6/10 9:36
 */
public class CaiJingPresentationLogVO implements Serializable {

    private String id;

    private String logType;

    private Integer count;

    private Integer status;

    private String description;

    private Integer presentationTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPresentationTime() {
        return presentationTime;
    }

    public void setPresentationTime(Integer presentationTime) {
        this.presentationTime = presentationTime;
    }
}
