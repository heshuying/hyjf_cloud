package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.RepayListResponse;
import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.trade.RepayRequestUpdateRequest;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.cs.trade.client.RepayManageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 还款管理页面相关列表查询
 * @author hesy
 * @version RepayManageClientImpl, v0.1 2018/6/19 15:33
 */
@Service
public class RepayManageClientImpl implements RepayManageClient {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 用户待还款/已还款列表
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> repayList(RepayListRequest requestBean) {
        RepayListResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/repay/repaylist",
                RepayListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 垫付机构待还款列表
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> orgRepayList(RepayListRequest requestBean) {
        RepayListResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/repay/orgrepaylist",
                RepayListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 垫付机构已还款列表
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> orgRepayedList(RepayListRequest requestBean) {
        RepayListResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/repay/orgrepayedlist",
                RepayListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 用户待还款/已还款总记录数
     * @param requestBean
     * @return
     */
    @Override
    public int repayCount(RepayListRequest requestBean) {
        int count = restTemplate
                .postForEntity( "http://AM-TRADE/am-trade/repay/repaycount", requestBean, Integer.class).getBody();
        return count;
    }

    /**
     * 垫付机构待还款总记录数
     * @param requestBean
     * @return
     */
    @Override
    public int orgRepayCount(RepayListRequest requestBean) {
        int count = restTemplate
                .postForEntity( "http://AM-TRADE/am-trade/repay/orgrepaycount", requestBean, Integer.class).getBody();
        return count;
    }

    /**
     * 垫付机构已还款总记录数
     * @param requestBean
     * @return
     */
    @Override
    public int orgRepayedCount(RepayListRequest requestBean) {
        int count = restTemplate
                .postForEntity( "http://AM-TRADE/am-trade/repay/orgrepayedcount", requestBean, Integer.class).getBody();
        return count;
    }

    /**
     * 还款申请更新
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public Boolean repayRequestUpdate(RepayRequestUpdateRequest requestBean){
        Boolean result = restTemplate
                .postForEntity( "http://AM-TRADE/am-trade/repay/update", requestBean, Boolean.class).getBody();
        return result;
    }
}
