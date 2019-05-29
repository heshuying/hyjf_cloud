/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
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

    @Override
    public List<CustomerServiceRepresentiveConfigVO> getResultList() {
        return resultList;
    }

    @Override
    public void setResultList(List<CustomerServiceRepresentiveConfigVO> resultList) {
        this.resultList = resultList;
    }
}
