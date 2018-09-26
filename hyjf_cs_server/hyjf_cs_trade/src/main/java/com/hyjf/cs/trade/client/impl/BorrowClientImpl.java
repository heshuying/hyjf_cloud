package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BatchCenterCustomizeResponse;
import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.response.trade.ProjectBeanResponse;
import com.hyjf.am.response.trade.WebUserRepayProjectListCustomizeResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.vo.trade.ProjectBeanVO;
import com.hyjf.am.vo.trade.borrow.BatchCenterCustomizeVO;
import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.trade.bean.BatchCenterCustomize;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.cs.trade.client.BorrowClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version BorrowClientImpl, v0.1 2018/6/19 15:33
 */
@Service
public class BorrowClientImpl implements BorrowClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BorrowAndInfoVO selectBorrowByNid(String borrowNid) {
        BorrowResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrow/getBorrow/" + borrowNid,
                BorrowResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public ProjectBeanVO getRepayProjectDetail(ProjectBeanVO form)  {
        ProjectBeanResponse response =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/borrow/getRepayProjectDetail/", form,
                ProjectBeanResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BankOpenAccountVO getBankOpenAccount(String accountId) {
        BankOpenAccountResponse response = restTemplate.getForEntity(
                "http://AM-USER/am-user/bankopen/getBankOpenAccountByAccountId/" + accountId,
                BankOpenAccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<WebUserRepayProjectListCustomizeVO> selectUserRepayProjectList(Map<String, Object> params){
        WebUserRepayProjectListCustomizeResponse result =  restTemplate.postForEntity(
            "http://AM-TRADE/am-trade/borrow/selectUserRepayProjectList/", params,
                WebUserRepayProjectListCustomizeResponse.class).getBody();
        if (result != null) {
            return result.getResultList();
        }
        return null;
    }

    @Override
    public List<WebUserRepayProjectListCustomizeVO> selectOrgRepayProjectList(Map<String, Object> params){
        WebUserRepayProjectListCustomizeResponse result =  restTemplate.postForEntity(
            "http://AM-TRADE/am-trade/borrow/selectOrgRepayProjectList/", params,
                WebUserRepayProjectListCustomizeResponse.class).getBody();
        if (result != null) {
            return result.getResultList();
        }
        return null;
    }

    @Override
    public Long countBatchCenter (BatchCenterCustomize batchCenterCustomize){
        Long result =  restTemplate.postForEntity(
        "http://AM-TRADE/am-trade/borrow/countBatchCenter/", batchCenterCustomize,
                Long.class).getBody();
        if (result != null) {
            return result;
        }
        return null;
    }

    @Override
    public List<BatchCenterCustomizeVO> selectBatchCenterList (BatchCenterCustomize batchCenterCustomize){
        BatchCenterCustomizeResponse result =  restTemplate.postForEntity(
        "http://AM-TRADE/am-trade/borrow/selectBatchCenterList/", batchCenterCustomize,
                BatchCenterCustomizeResponse.class).getBody();
        if (result != null) {
            return result.getResultList();
        }
        return null;
        }

    @Override
    public String getborrowIdByProductId (Map<String, Object> params){
        String result =  restTemplate.postForEntity(
        "http://AM-TRADE/am-trade/borrow/getborrowIdByProductId/", params,
                String.class).getBody();
        if (result != null) {
        return result;
        }
        return null;
    }
}
