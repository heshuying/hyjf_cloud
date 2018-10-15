/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.IncreaseInterestRepayInfoListService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.response.admin.IncreaseInterestRepayInfoListResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayInfoListRequest;
import com.hyjf.am.vo.admin.AdminIncreaseInterestRepayCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;

/**
 * @author wenxin
 * @version IncreaseInterestRepayInfoListServiceImpl.java, v0.1 2018年8月30日
 */
@Service
public class IncreaseInterestRepayInfoListServiceImpl implements IncreaseInterestRepayInfoListService {
    @Autowired
    private AmTradeClient increaseInterestRepayInfoListClient;

    /**
     * 查询列表
     * @param request
     * @return
     */
    @Override
    public IncreaseInterestRepayInfoListResponse searchPage(IncreaseInterestRepayInfoListRequest request){
        IncreaseInterestRepayInfoListResponse response = new IncreaseInterestRepayInfoListResponse();
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        Integer count = increaseInterestRepayInfoListClient.getIncreaseInterestRepayInfoListCount(request);
        if (count != null && count > 0) {
            //获取返回参数
            EnumMap<AmTradeClient.IncreaseProperty,Object> voList = increaseInterestRepayInfoListClient.getIncreaseInterestRepayInfoListList(request);
            voList.entrySet().iterator();
            response.setResultList((List<AdminIncreaseInterestRepayCustomizeVO>) voList.get(AmTradeClient.IncreaseProperty.VO));
            response.setSumRepayInterest((String) voList.get(AmTradeClient.IncreaseProperty.STR));
            response.setSumRepayCapital((String) voList.get(AmTradeClient.IncreaseProperty.STR1));
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
    public AdminIncreaseInterestRepayCustomizeVO getSumBorrowLoanmentInfo(IncreaseInterestRepayInfoListRequest request){
        return increaseInterestRepayInfoListClient.getSumBorrowLoanmentInfo(request);
    }

    /**
     * 融通宝加息还款信息导出
     *
     * @Title selectRecordList
     * @param form
     * @return
     */
    @Override
    public EnumMap<AmTradeClient.IncreaseProperty,Object> selectRecordList(IncreaseInterestRepayInfoListRequest form) {
        return increaseInterestRepayInfoListClient.getIncreaseInterestRepayInfoListList(form);
    }
}
