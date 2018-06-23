/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVo;

import java.util.Map;

/**
 * Web端项目列表
 * @author liuyang
 * @version ProjectListResponse, v0.1 2018/6/13 11:26
 */
public class ProjectListResponse extends Response<WebProjectListCustomizeVo> {

    // 数据查询条数 主要用于分页情况，原子层向组合层返回
    private  Integer  count;

    // 计划专区需要统计数据
    private Map<String,Object> totalData;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Map<String, Object> getTotalData() {
        return totalData;
    }

    public void setTotalData(Map<String, Object> totalData) {
        this.totalData = totalData;
    }
}
