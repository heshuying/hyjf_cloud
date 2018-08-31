/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.app;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.AppProjectListCustomizeVO;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVO;

import java.util.Map;

/**
 * 移动端端项目列表
 * @author zhangyk
 * @date 2018/7/6 14:35
 */
public class AppProjectListResponse extends Response<AppProjectListCustomizeVO> {

    // 数据查询条数 主要用于分页情况，原子层向组合层返回
    private  Integer  count;

    // 计划专区需要统计数据？
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
