/**
 * Description:商户子账户记录列表前端查询所用vo
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.admin.BankMerchantAccountListCustomizeVO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author
 */

public class BankRedPacketAccountListRequest implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 7768418442884796575L;
	//流水号
	private String seqNo;
	//订单号
	private String orderId;
	//电子帐号
	private String accountId;
	//收支类型
	private Integer type;
	//交易类型
	private Integer transType;
	//交易状态
	private Integer status;
	
	private String bankAccountCode;

	private List<Integer> typeList;

	private List<Integer> transTypeList;

	private List<Integer> statusList;

	private List<BankMerchantAccountListCustomizeVO> recordList;

	
	/**
     * 检索条件 limitStart
     */
    private int limitStart = -1;

    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;
	

    /**
     * 检索条件 时间开始
     */
    private Date timeStartSrch;

    /**
     * 检索条件 时间结束
     */
    private Date timeEndSrch;
	
	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 列表画面自定义标签上的用翻页对象：paginator
	 */
	private Paginator paginator;

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

    public List<Integer> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Integer> typeList) {
        this.typeList = typeList;
    }

    public List<Integer> getTransTypeList() {
        return transTypeList;
    }

    public void setTransTypeList(List<Integer> transTypeList) {
        this.transTypeList = transTypeList;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
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

    public List<BankMerchantAccountListCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BankMerchantAccountListCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(Date timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public Date getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(Date timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getBankAccountCode() {
        return bankAccountCode;
    }

    public void setBankAccountCode(String bankAccountCode) {
        this.bankAccountCode = bankAccountCode;
    }



}