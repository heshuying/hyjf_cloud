/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

/**
 * @author libin
 * @version HjhPlanResponse.java, v0.1 2018年7月6日 上午9:44:52
 */
public class HjhPlanResponse extends Response<HjhPlanVO>{
	
    // 数据查询条数 主要用于分页情况，原子层向组合层返回
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
