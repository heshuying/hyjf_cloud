/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.CustomerServiceChannelVO;

import java.util.List;

/**
 * 推送禁用渠道Response
 *
 * @author liuyang
 * @version CustomerServiceChannelResponse, v0.1 2019/5/29 15:45
 */
public class CustomerServiceChannelResponse extends Response<CustomerServiceChannelVO> {

    private List<CustomerServiceChannelVO> resultList;

    @Override
    public List<CustomerServiceChannelVO> getResultList() {
        return resultList;
    }

    @Override
    public void setResultList(List<CustomerServiceChannelVO> resultList) {
        this.resultList = resultList;
    }
}
