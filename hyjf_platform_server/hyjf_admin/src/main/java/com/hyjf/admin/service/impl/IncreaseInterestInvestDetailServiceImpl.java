/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.IncreaseInterestInvestDetailService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.response.admin.IncreaseInterestInvestDetailResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestInvestDetailRequest;
import com.hyjf.am.vo.admin.IncreaseInterestInvestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;


/**
 * @author wenxin
 * @version IncreaseInterestInvestDetailServiceImpl.java, v0.1 2018年8月30日
 */
@Service
public class IncreaseInterestInvestDetailServiceImpl implements IncreaseInterestInvestDetailService {
    @Autowired
    private AmTradeClient increaseInterestInvestDetaiClient;

    /**
     * 查询列表
     * @param request
     * @return
     */
    @Override
    public IncreaseInterestInvestDetailResponse searchPage(IncreaseInterestInvestDetailRequest request){
        IncreaseInterestInvestDetailResponse response = new IncreaseInterestInvestDetailResponse();
            Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
            request.setLimitStart(page.getOffset());
            request.setLimitEnd(page.getLimit());
            Integer count = increaseInterestInvestDetaiClient.getIncreaseInterestInvestDetaiCount(request);
            if (count != null && count > 0) {
                //获取返回参数
                EnumMap<AmTradeClient.IncreaseProperty,Object> voList = increaseInterestInvestDetaiClient.getIncreaseInterestInvestDetaiList(request);
                voList.entrySet().iterator();
                response.setResultList((List<IncreaseInterestInvestVO>) voList.get(AmTradeClient.IncreaseProperty.VO));
                response.setSumAccount((String) voList.get(AmTradeClient.IncreaseProperty.STR));
                response.setTotal(count);
            }
        return response;
    }

    /**
     * 查询合计
     * @param request
     * @return
     */
    @Override
    public String getSumAccount(IncreaseInterestInvestDetailRequest request){
        return increaseInterestInvestDetaiClient.getSumAccount(request);
    }

    /**
     * 融通宝加息交易明细导出
     *
     * @Title selectRecordList
     * @param form
     * @return
     */
    @Override
    public EnumMap<AmTradeClient.IncreaseProperty,Object> selectRecordList(IncreaseInterestInvestDetailRequest form) {
        return increaseInterestInvestDetaiClient.getIncreaseInterestInvestDetaiList(form);
    }
}
