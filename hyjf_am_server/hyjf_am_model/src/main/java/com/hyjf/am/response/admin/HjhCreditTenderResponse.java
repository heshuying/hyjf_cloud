/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;

/**
 * @author Albert
 * @version HjhCreditTenderResponse.java, v0.1 2018年7月11日 下午2:47:25
 */
public class HjhCreditTenderResponse extends Response<HjhCreditTenderCustomizeVO>{
	
    // 数据查询条数 主要用于分页情况，原子层向组合层返回AccedeListCustomizeVO
    private  Integer  count;
    // 特用
    private  String  planName;
    
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

}
