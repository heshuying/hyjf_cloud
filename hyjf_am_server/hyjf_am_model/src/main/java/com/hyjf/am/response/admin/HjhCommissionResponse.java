/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.hjh.HjhCommissionCustomizeVO;
/**
 * @author libin
 * @version HjhCommissionResponse.java, v0.1 2018年8月7日 下午4:10:42
 */
public class HjhCommissionResponse extends Response<HjhCommissionCustomizeVO>{
	
    // 数据查询条数 主要用于分页情况，原子层向组合层返回
    private  Integer  count;
    // 特用
    private  String  planName;
	/**
	 * 加入金额累计 
	 */
	private String tenderTotal;
	/**
	 * 提成金额累计
	 */
	private String commissionTotal;
    
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getTenderTotal() {
		return tenderTotal;
	}
	public void setTenderTotal(String tenderTotal) {
		this.tenderTotal = tenderTotal;
	}
	public String getCommissionTotal() {
		return commissionTotal;
	}
	public void setCommissionTotal(String commissionTotal) {
		this.commissionTotal = commissionTotal;
	}
}
