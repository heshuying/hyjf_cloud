/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowInvestClient;
import com.hyjf.am.response.admin.BorrowInvestCustomizeResponse;
import com.hyjf.am.response.admin.BorrowListCustomizeResponse;
import com.hyjf.am.response.admin.WebProjectRepayListCustomizeResponse;
import com.hyjf.am.response.admin.WebUserInvestListCustomizeResponse;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.response.trade.BorrowRecoverResponse;
import com.hyjf.am.response.trade.TenderAgreementResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;
import com.hyjf.am.vo.admin.BorrowListCustomizeVO;
import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowInvestClientImpl, v0.1 2018/7/10 9:19
 */
@Service
public class BorrowInvestClientImpl implements BorrowInvestClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer countBorrowInvest(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/count_borrow_invest";
        BorrowInvestCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, BorrowInvestCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    @Override
    public List<BorrowInvestCustomizeVO> selectBorrowInvestList(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_borrow_invest_list";
        BorrowInvestCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, BorrowInvestCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public String selectBorrowInvestAccount(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_borrow_invest_account";
        BorrowInvestCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, BorrowInvestCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getSumAccount();
        }
        return null;
    }

    @Override
    public List<BorrowInvestCustomizeVO> getExportBorrowInvestList(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/export_borrow_invest_list";
        BorrowInvestCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, BorrowInvestCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取银行返回码
     *
     * @param retCode
     * @return
     */
    @Override
    public BankReturnCodeConfigVO getBankReturnCodeConfig(String retCode) {
        String url = "http://AM-CONFIG/am-config/config/getBankReturnCodeConfig/" + retCode;
        BankReturnCodeConfigResponse response = restTemplate.getForEntity(url, BankReturnCodeConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取用户投资协议
     *
     * @param nid
     * @return
     */
    @Override
    public TenderAgreementVO selectTenderAgreement(String nid) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/tender_agreement/" + nid;
        TenderAgreementResponse response = restTemplate.getForEntity(url, TenderAgreementResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 标的放款记录
     *
     * @param userId
     * @param borrowNid
     * @param nid
     * @return
     */
    @Override
    public BorrowRecoverVO selectBorrowRecover(Integer userId, String borrowNid, String nid) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_borrow_recover/" + userId + "/" + borrowNid + "/" + nid;
        BorrowRecoverResponse response = restTemplate.getForEntity(url, BorrowRecoverResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据ID获取userInfo
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfoVO findUserInfoById(Integer userId) {
        String url = "http://AM-USER/am-user/userInfo/findById/" + userId;
        UserInfoResponse response = restTemplate.getForEntity(url, UserInfoResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取借款列表
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowListCustomizeVO> selectBorrowList(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_borrow_list/" + borrowNid;
        BorrowListCustomizeResponse response = restTemplate.getForEntity(url, BorrowListCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 标的投资信息
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public List<WebUserInvestListCustomizeVO> selectUserInvestList(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_user_invest_list";
        WebUserInvestListCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, WebUserInvestListCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 标的放款记录分期count
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public Integer countProjectRepayPlanRecordTotal(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/count_project_repay";
        WebProjectRepayListCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, WebProjectRepayListCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    /**
     * 标的放款记录分期
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public List<WebProjectRepayListCustomizeVO> selectProjectRepayPlanList(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/select_project_repay";
        WebProjectRepayListCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, WebProjectRepayListCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 更新标的放款记录
     *
     * @param borrowInvestRequest
     * @return
     */
    @Override
    public Integer updateBorrowRecover(BorrowInvestRequest borrowInvestRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_invest/update_borrow_recover";
        WebProjectRepayListCustomizeResponse response =
                restTemplate.postForEntity(url, borrowInvestRequest, WebProjectRepayListCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return null;
    }
}
