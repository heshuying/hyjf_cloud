/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.IncreaseInterestRepayDetailService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.response.admin.IncreaseInterestRepayDetailResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayDetailRequest;
import com.hyjf.am.vo.admin.AdminIncreaseInterestRepayCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;

/**
 * @author wenxin
 * @version IncreaseInterestRepayDetailServiceImpl.java, v0.1 2018年8月30日
 */
@Service
public class IncreaseInterestRepayDetailServiceImpl implements IncreaseInterestRepayDetailService {
    @Autowired
    private AmTradeClient increaseInterestRepayDetailClient;

    /**
     * 查询列表
     * @param request
     * @return
     */
    @Override
    public IncreaseInterestRepayDetailResponse searchPage(IncreaseInterestRepayDetailRequest request){
        IncreaseInterestRepayDetailResponse response = new IncreaseInterestRepayDetailResponse();
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        Integer count = increaseInterestRepayDetailClient.getIncreaseInterestRepayDetailCount(request);
        if (count != null && count > 0) {
            //获取返回参数
            EnumMap<AmTradeClient.IncreaseProperty,Object> voList = increaseInterestRepayDetailClient.getIncreaseInterestRepayDetailList(request);
            voList.entrySet().iterator();
            response.setResultList((List<AdminIncreaseInterestRepayCustomizeVO>) voList.get(AmTradeClient.IncreaseProperty.VO));
            response.setSumRepayCapital((String) voList.get(AmTradeClient.IncreaseProperty.STR));
            response.setSumRepayInterest((String) voList.get(AmTradeClient.IncreaseProperty.STR1));
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
    public AdminIncreaseInterestRepayCustomizeVO getSumBorrowRepaymentInfo(IncreaseInterestRepayDetailRequest request){
        return increaseInterestRepayDetailClient.getSumBorrowRepaymentInfo(request);
    }

    /**
     * 融通宝加息还款信息导出
     *
     * @Title selectRecordList
     * @param form
     * @return
     */
    @Override
    public EnumMap<AmTradeClient.IncreaseProperty,Object> selectRecordList(IncreaseInterestRepayDetailRequest form) {
        return increaseInterestRepayDetailClient.getIncreaseInterestRepayDetailList(form);
    }
}
