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
    private AmTradeClient client;

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

        Integer count = getHjhAccountBalanceMonthCount(request);
        response.setCount(count);
        if (count > 0) {
            //分页参数
            Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
            page.setTotal(count);
            request.setLimitStart(page.getOffset());
            request.setLimitEnd(page.getLimit());

            List<HjhAccountBalanceVO> voList = csMessageClient.getHjhAccountBalanceMonthCount(request).getRecordList();
            response.setResultList(voList);
            response.setSum(csMessageClient.getHjhAccountBalanceMonthCount(request).getSum());
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

        Integer count = csMessageClient.getHjhAccountBalanceDayCount(request).getCount();
        response.setCount(count);
        if (count > 0) {
            //分页参数
            Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
            page.setTotal(count);
            request.setLimitStart(page.getOffset());
            request.setLimitEnd(page.getLimit());

            List<HjhAccountBalanceVO> voList = csMessageClient.getHjhAccountBalanceDayCount(request).getRecordList();
            response.setResultList(voList);
            response.setSum(csMessageClient.getHjhAccountBalanceDayCount(request).getSum());
        }
        return response;
    }

    /**
     * 按月查询数量
     *
     * @return public Integer getHjhAccountBalancecount(HjhAccountBalanceCustomize
     *         entity) {
     */
    public Integer getHjhAccountBalanceMonthCount(HjhAccountBalanceRequest request) {

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
            try {
                int count = csMessageClient.getHjhAccountBalanceMonthCount(request).getCount();
                return count;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // 排序
        Integer count = csMessageClient
                .getHjhAccountBalanceMonthCount(request).getCount();
        return count;
    }

    @Override
    public List<HjhAccountBalanceVO> getHjhAccountBalanceList(HjhAccountBalanceRequest request) {
        if (request.getAddTimeStart() != null
                && request.getAddTimeEnd() != null) {
            Date date;
            try {
                return client.getHjhAccountBalanceListByDay(request);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return client.getHjhAccountBalanceListByDay(request);
    }

    /**
     *
     * 按月查询List集合数据
     *
     *
     */
    @Override
    public List<HjhAccountBalanceVO> getHjhAccountBalanceMonthList(
            HjhAccountBalanceRequest hjhAccountBalanceCustomize) {
        if (StringUtils.isNotEmpty(hjhAccountBalanceCustomize.getAddTimeStart()) && StringUtils.isNotEmpty(hjhAccountBalanceCustomize.getAddTimeEnd())) {
            try {
                List<HjhAccountBalanceVO> hjhAccountBalanceList = client
                        .getHjhAccountBalanceMonthList(hjhAccountBalanceCustomize);
                return hjhAccountBalanceList;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        List<HjhAccountBalanceVO> list = client
                .getHjhAccountBalanceMonthList(hjhAccountBalanceCustomize);
        return list;
    }
}
