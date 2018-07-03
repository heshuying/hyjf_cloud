package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.cs.trade.client.AccountClient;
import com.hyjf.cs.trade.client.CreditClient;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class CreditClientImpl implements CreditClient {


    @Autowired
    private RestTemplate restTemplate;

    /**
     * 判断用户所处的渠道如果不允许债转，可债转金额为0  start
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isAllowChannelAttorn(Integer userId) {
        // 根据userId获取用户注册推广渠道
        UtmPlatResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/user/selectUtmPlatByUserId/" + userId,UtmPlatResponse.class)
                .getBody();
        if (Response.isSuccess(response) && response.getResult().getAttornFlag()==0) {
            return false;
        }
        return true;
    }

    /**
     * 获取可债转金额   转让中本金   累计已转让本金
     *
     * @param userId
     * @return
     */
    @Override
    public CreditPageVO selectCreditPageMoneyTotal(Integer userId) {
        // 根据userId获取用户注册推广渠道
        CreaditPageResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/creditTender/select_credit_page_money_total/" + userId,CreaditPageResponse.class)
                .getBody();
        if (Response.isSuccess(response) ) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 查询可债转列表数量
     *
     * @param request
     * @return
     */
    @Override
    public MyCreditListQueryResponse countMyCreditList(MyCreditListQueryRequest request) {
        MyCreditListQueryResponse response =  restTemplate.postForEntity("http://AM-TRADE/am-trade/creditTender/countMyCreditList",request,MyCreditListQueryResponse.class).getBody();
        return response;
    }

    /**
     * 查询可债转列表
     *
     * @param request
     * @return
     */
    @Override
    public MyCreditListQueryResponse searchMyCreditList(MyCreditListQueryRequest request) {
        MyCreditListQueryResponse response =  restTemplate.postForEntity("http://AM-TRADE/am-trade/creditTender/seachMyCreditList",request,MyCreditListQueryResponse.class).getBody();
        return response;
    }

    /**
     * 查询债转详情
     *
     * @param userId
     * @param borrowNid
     * @param tenderNid
     * @return
     */
    @Override
    public TenderCreditCustomizeVO selectTenderToCreditDetail(Integer userId, String borrowNid, String tenderNid) {
        String url = "http://AM-TRADE/am-trade/creditTender/selectTenderToCreditDetail/" + userId + "/" + borrowNid + "/" + tenderNid;
        MyCreditListQueryResponse response = restTemplate.getForEntity(url, MyCreditListQueryResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 债转详细预计服务费计算
     *
     * @param borrowNid
     * @param tenderNid
     * @return
     */
    @Override
    public ExpectCreditFeeVO selectExpectCreditFee(String borrowNid, String tenderNid) {
        String url = "http://AM-TRADE/am-trade/creditTender/selectExpectCreditFee/" + borrowNid + "/" + tenderNid ;
        ExpectCreditFeeResponse response = restTemplate.getForEntity(url, ExpectCreditFeeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 验证投资人当天是否可以债转
     *
     * @param userId
     * @return
     */
    @Override
    public Integer tenderAbleToCredit(Integer userId) {
        String url = "http://AM-TRADE/am-trade/creditTender/get_tender_credit_count/" + userId ;
        Integer response = restTemplate.getForEntity(url, Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据投资订单号检索已债转还款信息
     *
     * @param tenderId
     * @return
     */
    @Override
    public List<CreditRepayVO> selectCreditRepayList(Integer tenderId) {
        CreditRepayResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/creditTender/select_credit_repay_list/" + tenderId , CreditRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 插入债转  我要债转
     *
     * @param borrowCredit
     */
    @Override
    public Integer insertCredit(BorrowCreditVO borrowCredit) {
        String url = "http://AM-TRADE/am-trade/creditTender/save_credit_tender";
        return restTemplate.postForEntity(url, borrowCredit, Integer.class).getBody();
    }

}