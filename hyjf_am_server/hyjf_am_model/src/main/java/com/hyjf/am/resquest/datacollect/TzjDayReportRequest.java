package com.hyjf.am.resquest.datacollect;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author xiasq
 * @version TzjDayReportRequest, v0.1 2018/7/9 9:25
 */
public class TzjDayReportRequest implements Serializable{
	private static final long serialVersionUID = 2874759813354390582L;
	/** 投之家用户 */
	private Set<Integer> tzjUserIds;

	private Date startTime;

	private Date endTime;

	public Set<Integer> getTzjUserIds() {
		return tzjUserIds;
	}

	public void setTzjUserIds(Set<Integer> tzjUserIds) {
		this.tzjUserIds = tzjUserIds;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
