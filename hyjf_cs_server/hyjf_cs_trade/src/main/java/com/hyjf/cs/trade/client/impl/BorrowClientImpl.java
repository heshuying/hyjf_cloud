package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.trade.bean.BatchCenterCustomize;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
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
    public ProjectBean searchRepayProjectDetail(ProjectBean form)  {
        ProjectBean result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/borrow/searchRepayProjectDetail/", form,
                ProjectBean.class).getBody();
        if (result != null) {
            return result.getResult();
        }
        return null;
    }

    @Override
    public BankOpenAccountVO getBankOpenAccount(String accountId) {
        BankOpenAccountVO response = restTemplate.getForEntity(
                "http://AM-USER/am-user/bankopen/getBankOpenAccountByAccountId/" + accountId,
                BankOpenAccountVO.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<WebUserRepayProjectListCustomizeVO> selectUserRepayProjectList(Map<String, Object> params){
        WebUserRepayProjectListCustomizeVO result =  restTemplate.postForEntity(
            "http://AM-TRADE/am-trade/borrow/selectUserRepayProjectList/", params,
                WebUserRepayProjectListCustomizeVO.class).getBody();
        if (result != null) {
            return result.getResult();
        }
        return null;
    }

    @Override
    public List<WebUserRepayProjectListCustomizeVO> selectOrgRepayProjectList(Map<String, Object> params){
        WebUserRepayProjectListCustomizeVO result =  restTemplate.postForEntity(
            "http://AM-TRADE/am-trade/borrow/selectOrgRepayProjectList/", params,
                WebUserRepayProjectListCustomizeVO.class).getBody();
        if (result != null) {
            return result.getResult();
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
    public List<BatchCenterCustomize> selectBatchCenterList (BatchCenterCustomize batchCenterCustomize){
        BatchCenterCustomize result =  restTemplate.postForEntity(
        "http://AM-TRADE/am-trade/borrow/selectBatchCenterList/", batchCenterCustomize,
                BatchCenterCustomize.class).getBody();
        if (result != null) {
        return result.getResult();
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
