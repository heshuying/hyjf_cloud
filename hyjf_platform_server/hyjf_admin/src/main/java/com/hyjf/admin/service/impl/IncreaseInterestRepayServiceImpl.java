/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.IncreaseInterestRepayService;
import com.hyjf.am.response.admin.IncreaseInterestRepayResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayRequest;
import com.hyjf.am.vo.admin.IncreaseInterestRepayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;

/**
 * @author wenxin
 * @version IncreaseInterestRepayServiceImpl.java, v0.1 2018年8月30日
 */
@Service
public class IncreaseInterestRepayServiceImpl implements IncreaseInterestRepayService {
    @Autowired
    private AmTradeClient increaseInterestRepayClient;

    /**
     * 查询列表
     * @param request
     * @return
     */
    @Override
    public IncreaseInterestRepayResponse searchPage(IncreaseInterestRepayRequest request){
        IncreaseInterestRepayResponse response = new IncreaseInterestRepayResponse();
        //获取返回参数
        EnumMap<AmTradeClient.IncreaseProperty,Object> voList = increaseInterestRepayClient.getIncreaseInterestRepayList(request);
        voList.entrySet().iterator();
        response.setResultList((List<IncreaseInterestRepayVO>) voList.get(AmTradeClient.IncreaseProperty.VO));
        response.setSumAccount((String) voList.get(AmTradeClient.IncreaseProperty.STR));
        return response;
    }

    /**
     * 查询合计
     * @param request
     * @return
     */
    @Override
    public String getSumAccount(IncreaseInterestRepayRequest request){
        return increaseInterestRepayClient.getSumAccount(request);
    }

    /**
     * 融通宝加息还款信息导出
     *
     * @Title selectRecordList
     * @param form
     * @return
     */
    @Override
    public EnumMap<AmTradeClient.IncreaseProperty,Object> selectRecordList(IncreaseInterestRepayRequest form) {
        return increaseInterestRepayClient.getIncreaseInterestRepayList(form);
    }
}
