package com.hyjf.am.resquest.admin;

import java.io.Serializable;

import com.hyjf.am.vo.BasePage;


/**
 * @package com.hyjf.admin.maintenance.AlllBorrowCustomize;
 * @author GOGTZ-Z
 * @date 2015/07/09 17:00
 * @version V1.0  
 */
public class BorrowBeanRequest extends BasePage implements Serializable{

	/**
	 * serialVersionUID
	 */

	private static final long serialVersionUID = 387630498860089653L;

	/**
	 * 排序
	 */
	private String sort;
	/**
	 * 排序列
	 */
	private String col;

	/**
	 * 检索条件 画面迁移标识
	 */
	private String moveFlag;

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private Paginator paginator;

	/**
	 * 检索条件 借款编号
	 */
	private String borrowNidSrch;

	/**
	 * 检索条件 计划编号
	 */
	private String planNidSrch;
	
	/**
	 * 检索条件 资金来源
	 */
	private String instCodeSrch;
	
	/**
	 * 检索条件 借款标题
	 */
	private String borrowNameSrch;
	/**
	 * 检索条件 用户名
	 */
	private String usernameSrch;

	/**
	 * 检索条件 状态
	 */
	private String statusSrch;

	/**
	 * 检索条件 还款方式
	 */
	private String borrowStyleSrch;

	/**
	 * 检索条件 项目类型
	 */
	private String projectTypeSrch;

	/**
	 * 检索条件 放款开始
	 */
	private String recoverTimeStartSrch;

	/**
	 * 检索条件 放款结束
	 */
	private String recoverTimeEndSrch;

	/**
	 * 检索条件 时间开始
	 */
	private String timeStartSrch;

	/**
	 * 检索条件 时间结束
	 */
	private String timeEndSrch;
	/**
	 * 标签名称 时间结束
	 */
	private String labelNameSrch;
	
	/**
	 * 借款编码
	 */
	private String borrowNid;
	
	/**
	 * 计划编号
	 */
	private String planNid;
	
	/**
	 * 资金来源
	 */
	private String instName;

	/**
	 * 检索条件 limitStart
	 */
	private int limitStart = -1;
	/**
	 * 检索条件 limitEnd
	 */
	private int limitEnd = -1;
	/**
	 * 借发标方式
	 */
	private String verifyStatus;
	/**
	 * 设定发标时间
	 */
	private String ontime;
	/**
	 * 项目期限
	 */
	private String borrowPeriod;

	/**
	 * 初审时间段 开始
	 */
	private String verifyTimeStartSrch;
	/**
	 * 初审时间段 结束
	 */
	private String verifyTimeEndSrch;

	/**
	 * recoverTimeStartSrch
	 * 
	 * @return the recoverTimeStartSrch
	 */

	public String getRecoverTimeStartSrch() {
		return recoverTimeStartSrch;
	}

	/**
	 * @param recoverTimeStartSrch
	 *            the recoverTimeStartSrch to set
	 */

	public void setRecoverTimeStartSrch(String recoverTimeStartSrch) {
		this.recoverTimeStartSrch = recoverTimeStartSrch;
	}

	/**
	 * recoverTimeEndSrch
	 * 
	 * @return the recoverTimeEndSrch
	 */

	public String getRecoverTimeEndSrch() {
		return recoverTimeEndSrch;
	}

	/**
	 * @param recoverTimeEndSrch
	 *            the recoverTimeEndSrch to set
	 */

	public void setRecoverTimeEndSrch(String recoverTimeEndSrch) {
		this.recoverTimeEndSrch = recoverTimeEndSrch;
	}

	/**
	 * verifyStatus
	 * 
	 * @return the verifyStatus
	 */

	public String getVerifyStatus() {
		return verifyStatus;
	}

	/**
	 * @param verifyStatus
	 *            the verifyStatus to set
	 */

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	/**
	 * ontime
	 * 
	 * @return the ontime
	 */

	public String getOntime() {
		return ontime;
	}

	/**
	 * @param ontime
	 *            the ontime to set
	 */

	public void setOntime(String ontime) {
		this.ontime = ontime;
	}

	/**
	 * limitStart
	 * 
	 * @return the limitStart
	 */

	public int getLimitStart() {
		return limitStart;
	}

	/**
	 * @param limitStart
	 *            the limitStart to set
	 */

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * limitEnd
	 * 
	 * @return the limitEnd
	 */

	public int getLimitEnd() {
		return limitEnd;
	}

	/**
	 * @param limitEnd
	 *            the limitEnd to set
	 */

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	/**
	 * moveFlag
	 * 
	 * @return the moveFlag
	 */

	public String getMoveFlag() {
		return moveFlag;
	}

	/**
	 * @param moveFlag
	 *            the moveFlag to set
	 */

	public void setMoveFlag(String moveFlag) {
		this.moveFlag = moveFlag;
	}

	/**
	 * borrowNidSrch
	 * 
	 * @return the borrowNidSrch
	 */

	public String getBorrowNidSrch() {
		return borrowNidSrch;
	}

	/**
	 * @param borrowNidSrch
	 *            the borrowNidSrch to set
	 */

	public void setBorrowNidSrch(String borrowNidSrch) {
		this.borrowNidSrch = borrowNidSrch;
	}

	/**
	 * usernameSrch
	 * 
	 * @return the usernameSrch
	 */

	public String getUsernameSrch() {
		return usernameSrch;
	}

	/**
	 * @param usernameSrch
	 *            the usernameSrch to set
	 */

	public void setUsernameSrch(String usernameSrch) {
		this.usernameSrch = usernameSrch;
	}

	/**
	 * statusSrch
	 * 
	 * @return the statusSrch
	 */

	public String getStatusSrch() {
		return statusSrch;
	}

	/**
	 * @param statusSrch
	 *            the statusSrch to set
	 */

	public void setStatusSrch(String statusSrch) {
		this.statusSrch = statusSrch;
	}

	/**
	 * borrowStyleSrch
	 * 
	 * @return the borrowStyleSrch
	 */

	public String getBorrowStyleSrch() {
		return borrowStyleSrch;
	}

	/**
	 * @param borrowStyleSrch
	 *            the borrowStyleSrch to set
	 */

	public void setBorrowStyleSrch(String borrowStyleSrch) {
		this.borrowStyleSrch = borrowStyleSrch;
	}

	/**
	 * projectTypeSrch
	 * 
	 * @return the projectTypeSrch
	 */

	public String getProjectTypeSrch() {
		return projectTypeSrch;
	}

	/**
	 * @param projectTypeSrch
	 *            the projectTypeSrch to set
	 */

	public void setProjectTypeSrch(String projectTypeSrch) {
		this.projectTypeSrch = projectTypeSrch;
	}

	/**
	 * borrowNameSrch
	 * 
	 * @return the borrowNameSrch
	 */

	public String getBorrowNameSrch() {
		return borrowNameSrch;
	}

	/**
	 * @param borrowNameSrch
	 *            the borrowNameSrch to set
	 */

	public void setBorrowNameSrch(String borrowNameSrch) {
		this.borrowNameSrch = borrowNameSrch;
	}

	/**
	 * timeStartSrch
	 * 
	 * @return the timeStartSrch
	 */

	public String getTimeStartSrch() {
		return timeStartSrch;
	}

	/**
	 * @param timeStartSrch
	 *            the timeStartSrch to set
	 */

	public void setTimeStartSrch(String timeStartSrch) {
		this.timeStartSrch = timeStartSrch;
	}

	/**
	 * timeEndSrch
	 * 
	 * @return the timeEndSrch
	 */

	public String getTimeEndSrch() {
		return timeEndSrch;
	}

	/**
	 * @param timeEndSrch
	 *            the timeEndSrch to set
	 */

	public void setTimeEndSrch(String timeEndSrch) {
		this.timeEndSrch = timeEndSrch;
	}

	/**
	 * borrowNid
	 * 
	 * @return the borrowNid
	 */

	public String getBorrowNid() {
		return borrowNid;
	}

	/**
	 * @param borrowNid
	 *            the borrowNid to set
	 */

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	/**
	 * serialversionuid
	 * 
	 * @return the serialversionuid
	 */

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * sort
	 * 
	 * @return the sort
	 */

	public String getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */

	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * col
	 * 
	 * @return the col
	 */

	public String getCol() {
		return col;
	}

	/**
	 * @param col
	 *            the col to set
	 */

	public void setCol(String col) {
		this.col = col;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	/**
	 * planNidSrch
	 * @return the planNidSrch
	 */
	
	public String getPlanNidSrch() {
		return planNidSrch;
	}

	/**
	 * @param planNidSrch the planNidSrch to set
	 */
	
	public void setPlanNidSrch(String planNidSrch) {
		this.planNidSrch = planNidSrch;
	}

	/**
	 * instCodeSrch
	 * @return the instCodeSrch
	 */
	
	public String getInstCodeSrch() {
		return instCodeSrch;
	}

	/**
	 * @param instCodeSrch the instCodeSrch to set
	 */
	
	public void setInstCodeSrch(String instCodeSrch) {
		this.instCodeSrch = instCodeSrch;
	}

	/**
	 * planNid
	 * @return the planNid
	 */
	
	public String getPlanNid() {
		return planNid;
	}

	/**
	 * @param planNid the planNid to set
	 */
	
	public void setPlanNid(String planNid) {
		this.planNid = planNid;
	}

	/**
	 * instName
	 * @return the instName
	 */
	
	public String getInstName() {
		return instName;
	}

	/**
	 * @param instName the instName to set
	 */
	
	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getLabelNameSrch() {
		return labelNameSrch;
	}

	public void setLabelNameSrch(String labelNameSrch) {
		this.labelNameSrch = labelNameSrch;
	}

	public String getVerifyTimeStartSrch() {
		return verifyTimeStartSrch;
	}

	public void setVerifyTimeStartSrch(String verifyTimeStartSrch) {
		this.verifyTimeStartSrch = verifyTimeStartSrch;
	}

	public String getVerifyTimeEndSrch() {
		return verifyTimeEndSrch;
	}

	public void setVerifyTimeEndSrch(String verifyTimeEndSrch) {
		this.verifyTimeEndSrch = verifyTimeEndSrch;
	}
}
