/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;

import java.util.List;

/**
 * 客组配置Response
 *
 * @author liuyang
 * @version CustomerServiceGroupConfigResponse, v0.1 2019/5/29 10:41
 */
public class CustomerServiceGroupConfigResponse extends Response<CustomerServiceGroupConfigVO> {

    private List<CustomerServiceGroupConfigVO> resultList;

    // 客组总数
    private int total = 0;

    @Override
    public List<CustomerServiceGroupConfigVO> getResultList() {
        return resultList;
    }

    @Override
    public void setResultList(List<CustomerServiceGroupConfigVO> resultList) {
        this.resultList = resultList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
