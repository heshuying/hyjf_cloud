/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.HjhAllocationEngineVO;

/**
 * @author libin
 * @version HjhAllocationEngineResponse.java, v0.1 2018年7月4日 上午11:15:44
 */
public class HjhAllocationEngineResponse extends Response<HjhAllocationEngineVO>{
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
