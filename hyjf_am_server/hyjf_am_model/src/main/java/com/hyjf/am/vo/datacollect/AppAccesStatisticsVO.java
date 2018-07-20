package com.hyjf.am.vo.datacollect;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiasq
 * @version AppAccesStatisticsVO, v0.1 2018/7/19 14:46
 */
public class AppAccesStatisticsVO implements Serializable {

	private static final long serialVersionUID = -162184058705242885L;
	/** 渠道编号 */
	private int sourceId;
	/** 访问时间 */
	private Date accessTime;

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

    @Override
    public String toString() {
        return "AppAccesStatisticsVO{" +
                "sourceId=" + sourceId +
                ", accessTime=" + accessTime +
                '}';
    }
}
