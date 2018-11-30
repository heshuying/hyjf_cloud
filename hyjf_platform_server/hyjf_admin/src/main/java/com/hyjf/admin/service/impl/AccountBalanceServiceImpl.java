package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.CsMessageClient;
import com.hyjf.admin.service.AccountBalanceService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.response.admin.HjhInfoAccountBalanceResponse;
import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/7  10:29
 */
@Service
public class AccountBalanceServiceImpl implements AccountBalanceService {

    @Autowired
    private CsMessageClient csMessageClient;

    /**
     * 查询月度
     * @param request
     * @return
     */
    @Override
    public HjhInfoAccountBalanceResponse getSearchListByMonth(HjhAccountBalanceRequest request){
        HjhInfoAccountBalanceResponse response = new HjhInfoAccountBalanceResponse();

        HjhInfoAccountBalanceResponse countSum = getHjhAccountBalanceMonthCount(request);
        int count = countSum.getCount();
        response.setCount(count);
        if (count > 0) {
            //分页参数
            Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
            page.setTotal(count);
            request.setLimitStart(page.getOffset());
            request.setLimitEnd(page.getLimit());

            List<HjhAccountBalanceVO> voList = csMessageClient.getHjhAccountBalanceMonth(request).getRecordList();
            response.setResultList(voList);
            response.setSum(countSum.getSum());
        }
        return response;
    }

    /**
     * 查询每日
     * @param request
     * @return
     */
    @Override
    public HjhInfoAccountBalanceResponse getSearchListByDay(HjhAccountBalanceRequest request) {
        HjhInfoAccountBalanceResponse response = new HjhInfoAccountBalanceResponse();

        HjhInfoAccountBalanceResponse countSum = csMessageClient.getHjhAccountBalanceDayCount(request);
        Integer count = countSum.getCount();
        response.setCount(count);
        if (count > 0) {
            //分页参数
            Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
            page.setTotal(count);
            request.setLimitStart(page.getOffset());
            request.setLimitEnd(page.getLimit());

            List<HjhAccountBalanceVO> voList = csMessageClient.getHjhAccountBalanceDay(request).getRecordList();
            response.setResultList(voList);
            response.setSum(countSum.getSum());
        }
        return response;
    }

    /**
     * 按月查询数量
     *
     * @return public Integer getHjhAccountBalancecount(HjhAccountBalanceCustomize
     *         entity) {
     */
    public HjhInfoAccountBalanceResponse getHjhAccountBalanceMonthCount(HjhAccountBalanceRequest request) {

        if (StringUtils.isNotEmpty(request.getAddTimeStart())
                && StringUtils.isNotEmpty(request.getAddTimeEnd())) {

            String addTimeStart = request.getAddTimeStart();
            String kai = "-01";
            String kaishi =addTimeStart+kai;
            request.setAddTimeStart(kaishi);
            String addTimeEnd = request.getAddTimeEnd();
            String mo = "-31";
            String mowei= addTimeEnd+mo;
            request.setAddTimeEnd(mowei);

        }
        HjhInfoAccountBalanceResponse count = csMessageClient.getHjhAccountBalanceMonthCount(request);
        return count;
    }

}
