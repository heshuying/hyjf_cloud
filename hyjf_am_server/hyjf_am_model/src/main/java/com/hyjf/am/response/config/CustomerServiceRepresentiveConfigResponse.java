/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;

import java.util.List;

/**
 * 坐席配置Response
 *
 * @author liuyang
 * @version CustomerServiceRepresentiveConfigResponse, v0.1 2019/5/29 14:07
 */
public class CustomerServiceRepresentiveConfigResponse extends Response<CustomerServiceRepresentiveConfigVO> {

    private List<CustomerServiceRepresentiveConfigVO> resultList;

    // 坐席总数
    private int total = 0;

    // 客组列表
    private List<CustomerServiceGroupConfigVO> groupList;

    @Override
    public List<CustomerServiceRepresentiveConfigVO> getResultList() {
        return resultList;
    }

    @Override
    public void setResultList(List<CustomerServiceRepresentiveConfigVO> resultList) {
        this.resultList = resultList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CustomerServiceGroupConfigVO> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<CustomerServiceGroupConfigVO> groupList) {
        this.groupList = groupList;
    }
}
