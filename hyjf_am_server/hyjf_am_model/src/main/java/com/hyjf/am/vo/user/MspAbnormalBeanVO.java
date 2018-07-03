package com.hyjf.am.vo.user;

import java.io.Serializable;


/**
 * 文章管理实体类
 * 
 * @author 
 *
 */
public class MspAbnormalBeanVO extends MspAbnormalcreditVO implements Serializable {
	
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		private String abcdId;

	    private String checkoverduedate;

	    private String overduedays;

	    private String overduereason;

	    private String overduestate;

	    private String opertime;

	    private String remark;


	    public String getAbcdId() {
	        return abcdId;
	    }

	    public void setAbcdId(String abcdId) {
	        this.abcdId = abcdId == null ? null : abcdId.trim();
	    }

	    public String getCheckoverduedate() {
	        return checkoverduedate;
	    }

	    public void setCheckoverduedate(String checkoverduedate) {
	        this.checkoverduedate = checkoverduedate == null ? null : checkoverduedate.trim();
	    }

	    public String getOverduedays() {
	        return overduedays;
	    }

	    public void setOverduedays(String overduedays) {
	        this.overduedays = overduedays == null ? null : overduedays.trim();
	    }

	    public String getOverduereason() {
	        return overduereason;
	    }

	    public void setOverduereason(String overduereason) {
	        this.overduereason = overduereason == null ? null : overduereason.trim();
	    }

	    public String getOverduestate() {
	        return overduestate;
	    }

	    public void setOverduestate(String overduestate) {
	        this.overduestate = overduestate == null ? null : overduestate.trim();
	    }

	    public String getOpertime() {
	        return opertime;
	    }

	    public void setOpertime(String opertime) {
	        this.opertime = opertime == null ? null : opertime.trim();
	    }

	    public String getRemark() {
	        return remark;
	    }

	    public void setRemark(String remark) {
	        this.remark = remark == null ? null : remark.trim();
	    }


}
