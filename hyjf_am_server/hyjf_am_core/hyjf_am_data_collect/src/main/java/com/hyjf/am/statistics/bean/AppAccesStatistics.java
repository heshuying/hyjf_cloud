package com.hyjf.am.statistics.bean;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiasq
 * @version AppAccesStatistics, v0.1 2018/7/19 14:15
 * app登录渠道统计
 */
@Document(collection = "t_app_access_statistics")
public class AppAccesStatistics implements Serializable {
    private static final long serialVersionUID = 2906684153855336067L;
    private String id;
    /** 渠道编号 */
    private int sourceId;
    /** 访问时间 */
    private Date accessTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }
}
