/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BorrowFullClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.admin.BorrowFullCustomizeResponse;
import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.resquest.admin.BorrowFullRequest;
import com.hyjf.am.vo.admin.BorrowFullCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowFullClientImpl, v0.1 2018/7/6 10:32
 */
@Service
public class BorrowFullClientImpl implements BorrowFullClient {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 借款复审总条数
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public Integer countBorrowFull(BorrowFullRequest borrowFullRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_full/count_borrow_full";
        return restTemplate.postForEntity(url, borrowFullRequest, Integer.class).getBody();
    }

    /**
     * 借款复审列表
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public List<BorrowFullCustomizeVO> selectBorrowFullList(BorrowFullRequest borrowFullRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_full/select_borrow_full_list";
        BorrowFullCustomizeResponse response =
                restTemplate.postForEntity(url, borrowFullRequest, BorrowFullCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 借款复审合计
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public BorrowFullCustomizeVO sumAccount(BorrowFullRequest borrowFullRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_full/sum_account";
        BorrowFullCustomizeResponse response =
                restTemplate.postForEntity(url, borrowFullRequest, BorrowFullCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 复审详细信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowFullCustomizeVO getFullInfo(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow_full/get_full_info/" + borrowNid;
        BorrowFullCustomizeResponse response =
                restTemplate.getForEntity(url, BorrowFullCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 复审详细列表条数
     *
     * @param borrowNid
     * @return
     */
    @Override
    public Integer countFullList(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow_full/count_full_list/" + borrowNid;
        return restTemplate.getForEntity(url, Integer.class).getBody();
    }

    /**
     * 借款复审列表
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public List<BorrowFullCustomizeVO> getFullList(BorrowFullRequest borrowFullRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_full/get_full_list";
        BorrowFullCustomizeResponse response =
                restTemplate.postForEntity(url, borrowFullRequest, BorrowFullCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 借款复审合计
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowFullCustomizeVO sumAmount(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow_full/sum_amount/" + borrowNid;
        BorrowFullCustomizeResponse response =
                restTemplate.getForEntity(url, BorrowFullCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据UserID查询开户信息
     *
     * @param userId
     * @return
     */
    @Override
    public BankOpenAccountVO getBankOpenAccountByUserId(Integer userId) {
        String url = "http://AM-USER/am-user/bankopen/selectById/" + userId;
        BankOpenAccountResponse response = restTemplate.getForEntity(url, BankOpenAccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据UserID查询账户信息
     *
     * @param userId
     * @return
     */
    @Override
    public AccountVO getAccountByUserId(int userId) {
        String url = "http://AM-TRADE/am-trade/account/getAccountByUserId/" + userId;
        AccountResponse response = restTemplate.getForEntity(url, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 标的复审数据更新
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public AdminResult updateBorrowFull(BorrowFullRequest borrowFullRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_full/update_borrow_full";
        AdminResponse response = restTemplate.postForEntity(url, borrowFullRequest, AdminResponse.class).getBody();
        if (response != null) {
            AdminResult adminResult = new AdminResult();
            if (!AdminResponse.isSuccess(response)) {
                adminResult.setStatus(BaseResult.FAIL);
                adminResult.setStatusDesc(response.getMessage());
            }
            return adminResult;
        }
        return null;
    }

    /**
     * 流标
     *
     * @param borrowFullRequest
     * @return
     */
    @Override
    public AdminResult updateBorrowOver(BorrowFullRequest borrowFullRequest) {
        String url = "http://AM-TRADE/am-trade/borrow_full/update_borrow_over";
        AdminResponse response = restTemplate.postForEntity(url, borrowFullRequest, AdminResponse.class).getBody();
        if (response != null) {
            AdminResult adminResult = new AdminResult();
            if (!AdminResponse.isSuccess(response)) {
                adminResult.setStatus(BaseResult.FAIL);
                adminResult.setStatusDesc(response.getMessage());
            }
            return adminResult;
        }
        return null;
    }
}
