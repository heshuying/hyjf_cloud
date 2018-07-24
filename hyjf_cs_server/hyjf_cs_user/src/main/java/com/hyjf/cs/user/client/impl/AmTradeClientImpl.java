/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.response.user.RecentPaymentListCustomizeResponse;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.RecentPaymentListCustomizeVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmTradeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AmTradeClientImpl, v0.1 2018/6/20 12:45
 */
@Service
public class AmTradeClientImpl implements AmTradeClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${am.trade.service.name}")
    private String tradeService;

    @Override
    public HjhInstConfigVO selectInstConfigByInstCode(String instCode) {
        HjhInstConfigResponse response = restTemplate
                .getForEntity(tradeService+"/trade/selectInstConfigByInstCode/"+instCode, HjhInstConfigResponse.class)
                .getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取用户account信息
     * @param userId
     * @return
     */
    @Override
    public AccountVO getAccount(Integer userId) {
        AccountResponse response = restTemplate
                .getForEntity(tradeService+"/trade/getAccount/" + userId, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<RecentPaymentListCustomizeVO> selectRecentPaymentList(Integer userId) {
        RecentPaymentListCustomizeResponse response = restTemplate
                .getForEntity(tradeService+"/borrow/selectRecentPaymentList/" + userId, RecentPaymentListCustomizeResponse.class)
                .getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BatchUserPortraitQueryVO> searchInfoForUserPortrait(String userIds) {
        String url = tradeService+"/batch/search_user_portrait_list/" + userIds;
        BatchUserPortraitQueryResponse response = restTemplate.getForEntity(url, BatchUserPortraitQueryResponse.class).getBody();
        if(response != null){
            return response.getResultList();
        }
        return null;
    }


    @Override
    public Integer countCouponValid(Integer userId) {
        String url =tradeService+"/couponUser/countCouponValid/" + userId;
        return restTemplate.getForEntity(url, Integer.class).getBody();
    }

    @Override
    public List<CouponUserListCustomizeVO> selectCouponUserList(Map<String, Object> mapParameter) {
        String url = tradeService+"/couponUser/selectCouponUserList";
        CouponUserListCustomizeResponse response = restTemplate.postForEntity(url,mapParameter,CouponUserListCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取账户信息通过userId范围
     * @param ids
     * @return
     */
    @Override
    public List<AccountVO> getAccountByUserIds(List<Integer> ids) {
        String url="http://AM-TRADE/am-trade/account/getAccountByUserIds";
        AccountResponse response=restTemplate.postForEntity(url,ids,AccountResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public ProductSearchForPageVO selectUserPrincipal(ProductSearchForPageVO productSearchForPage) {
        String url = tradeService+"/htlCommonController/selectUserPrincipal";
        ProductSearchForPageResponse response = restTemplate.postForEntity(url,productSearchForPage,ProductSearchForPageResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public WebPandectRecoverMoneyCustomizeVO queryRecoverMoney(Integer userId) {
        String url = tradeService+"/webPandect/queryRecoverMoney/"+userId;
        WebPandectRecoverMoneyCustomizeResponse response = restTemplate.getForEntity(url,WebPandectRecoverMoneyCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public WebPandectRecoverMoneyCustomizeVO queryRecoverMoneyForRtb(Integer userId) {
        String url = tradeService+"/webPandect/queryRecoverMoneyForRtb/"+userId;
        WebPandectRecoverMoneyCustomizeResponse response = restTemplate.getForEntity(url,WebPandectRecoverMoneyCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public WebPandectWaitMoneyCustomizeVO queryWaitMoney(Integer userId) {
        String url = tradeService+"/webPandect/queryWaitMoney/"+userId;
        WebPandectWaitMoneyCustomizeResponse response = restTemplate.getForEntity(url,WebPandectWaitMoneyCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public WebPandectWaitMoneyCustomizeVO queryWaitMoneyForRtb(Integer userId) {
        String url = tradeService+"/webPandect/queryWaitMoneyForRtb/"+userId;
        WebPandectWaitMoneyCustomizeResponse response = restTemplate.getForEntity(url,WebPandectWaitMoneyCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public BigDecimal queryHtlSumRestAmount(Integer userId) {
        String url = tradeService+"/webPandect/queryHtlSumRestAmount/"+userId;
        BigDecimal response = restTemplate.getForEntity(url,BigDecimal.class).getBody();
        return response;
    }

    @Override
    public WebPandectCreditTenderCustomizeVO queryCreditInfo(Integer userId) {
        String url = tradeService+"/webPandect/queryCreditInfo/"+userId;
        WebPandectCreditTenderCustomizeResponse response = restTemplate.getForEntity(url,WebPandectCreditTenderCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public WebPandectBorrowRecoverCustomizeVO queryRecoverInfo(Integer userId, int recoverStatus) {
        String url = tradeService+"/webPandect/queryRecoverInfo/"+userId+"/"+recoverStatus;
        WebPandectBorrowRecoverCustomizeResponse response = restTemplate.getForEntity(url,WebPandectBorrowRecoverCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public BigDecimal queryHtlSumInterest(Integer userId) {
        String url = tradeService+"/webPandect/queryHtlSumInterest/"+userId;
        BigDecimal response = restTemplate.getForEntity(url,BigDecimal.class).getBody();
        return response;
    }

    @Override
    public String selectCouponInterestTotal(Integer userId) {
        String url = tradeService+"/couponConfig/selectCouponInterestTotal/"+userId;
        String response = restTemplate.getForEntity(url,String.class).getBody();
        return response;
    }

    @Override
    public String selectCouponReceivedInterestTotal(Integer userId) {
        String url = tradeService+"/couponConfig/selectCouponReceivedInterestTotal/"+userId;
        String response = restTemplate.getForEntity(url,String.class).getBody();
        return response;
    }


    @Override
    public int selectUserTenderCount(Integer userId) {
        String url = tradeService+"/webPandect/selectUserTenderCount/"+userId;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        return response;
    }

    @Override
    public List<CouponUserCustomizeVO> selectLatestCouponValidUNReadList(Integer userId) {
        CouponUserCustomizeResponse response = restTemplate
                .getForEntity(tradeService+"/bankException/selectLatestCouponValidUNReadList/" + userId, CouponUserCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
