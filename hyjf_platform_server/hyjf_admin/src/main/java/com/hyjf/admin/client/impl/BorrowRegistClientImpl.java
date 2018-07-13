/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowRegistClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowRegistCustomizeResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowRegistClientImpl, v0.1 2018/6/29 15:35
 */
@Service
public class BorrowRegistClientImpl implements BorrowRegistClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<BorrowProjectTypeVO> selectBorrowProjectList() {
        String url = "http://AM-TRADE/am-trade/borrow_regist/select_borrow_project";
        BorrowProjectTypeResponse response = restTemplate.getForEntity(url, BorrowProjectTypeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowStyleVO> selectBorrowStyleList() {
        String url = "http://AM-TRADE/am-trade/borrow_regist/select_borrow_style";
        BorrowStyleResponse response = restTemplate.getForEntity(url, BorrowStyleResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_regist/get_regist_count";
        BorrowRegistCustomizeResponse response =
                restTemplate.postForEntity(url, borrowRegistListRequest, BorrowRegistCustomizeResponse.class).getBody();
        if(response != null){
            return response.getTotal();
        }
        return 0;
    }

    @Override
    public List<BorrowRegistCustomizeVO> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_regist/select_borrow_regist_list";
        BorrowRegistCustomizeResponse response = restTemplate.postForEntity(url, borrowRegistListRequest, BorrowRegistCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public String sumBorrowRegistAccount(BorrowRegistListRequest borrowRegistListRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_regist/sum_borrow_regist_account";
        return restTemplate.postForEntity(url, borrowRegistListRequest, String.class).getBody();
    }

    @Override
    public BorrowVO selectBorrowByNid(String borrowNid) {
        BorrowResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrow/getBorrow/" + borrowNid,
                BorrowResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BorrowInfoVO getBorrowInfoByNid(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowInfoByNid/" + borrowNid;
        BorrowInfoResponse response = restTemplate.getForEntity(url, BorrowInfoResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public UserVO findUserById(int userId) {
        UserResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/user/findById/" + userId, UserResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BankOpenAccountVO selectBankOpenAccountById(int userId) {
        BankOpenAccountResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/bankopen/selectById/" + userId, BankOpenAccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public STZHWhiteListVO selectStzfWhiteList(String instCode, String entrustedAccountId) {
        String url = "http://AM-TRADE/am-trade/borrow_regist/select_stzf_white_list/" + instCode + "/" + entrustedAccountId;
        STZHWhiteListResponse response = restTemplate.getForEntity(url, STZHWhiteListResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int updateBorrowRegist(BorrowRegistRequest borrowRegistRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_regist/update_borrow_regist";
        BorrowRegistCustomizeResponse response =
                restTemplate.postForEntity(url, borrowRegistRequest, BorrowRegistCustomizeResponse.class).getBody();
        if(response != null){
            return response.getTotal();
        }
        return 0;
    }

    @Override
    public int updateEntrustedBorrowRegist(BorrowRegistRequest borrowRegistRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_regist/update_entrusted_borrow_regist";
        BorrowRegistCustomizeResponse response =
                restTemplate.postForEntity(url, borrowRegistRequest, BorrowRegistCustomizeResponse.class).getBody();
        if(response != null){
            return response.getTotal();
        }
        return 0;
    }
}
