package com.hyjf.cs.trade.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.trade.AccountResponse;
import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.response.trade.CreditTenderLogResponse;
import com.hyjf.am.response.trade.CreditTenderResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.trade.client.BankCreditTenderClient;

/**
 * 债转投资异常client实现类
 * @author jun
 * @since 201801619
 */
@Service
public class BankCreditTenderClientImpl implements BankCreditTenderClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询债转承接掉单的数据
     *
     * @return
     */
    @Override
    public List<CreditTenderLogVO> selectCreditTenderLogs() {
        CreditTenderLogResponse response =
                restTemplate.getForEntity("http://AM-TRADE/am-trade/bankException/selectCreditTenderLogs",
                CreditTenderLogResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据承接订单号查询债转投资表
     * @param assignNid
     * @return
     */
    @Override
    public List<CreditTenderVO> selectCreditTender(String assignNid) {
        CreditTenderResponse response =
                restTemplate.getForEntity("http://AM-TRADE/am-trade/bankException/selectCreditTender/"+assignNid,
                        CreditTenderResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }


    /**
     * 獲取銀行開戶信息
     * @param userId
     * @return
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        BankOpenAccountResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/bankopen/selectById/" + userId, BankOpenAccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新投標記錄
     * @param creditTenderLog
     * @return
     */
    @Override
    public Boolean updateCreditTenderLog(CreditTenderLogVO creditTenderLog) {
        String url = "http://AM-TRADE/am-trade/bankException/updateCreditTenderLog";
        return restTemplate.postForEntity(url, creditTenderLog, Boolean.class).getBody();
    }

    /**
     *同步回调收到后,根据logOrderId检索投资记录表
     * @param logOrderId
     * @return
     */
    @Override
    public CreditTenderLogVO selectCreditTenderLogByOrderId(String logOrderId) {
        CreditTenderLogResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/bankException/selectCreditTenderLogByOrderId/" + logOrderId, CreditTenderLogResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     *获取CreditTenderLog信息
     * @param assignOrderId
     * @param userId
     * @return
     */
    @Override
    public List<CreditTenderLogVO> selectByOrderIdAndUserId(String assignOrderId, Integer userId) {
        CreditTenderLogResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/bankException/selectByOrderIdAndUserId/" + assignOrderId+"/"+userId, CreditTenderLogResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 刪除
     * @param assignOrderId
     * @param userId
     * @return
     */
    @Override
    public boolean deleteByOrderIdAndUserId(String assignOrderId, Integer userId) {
        return restTemplate
                .getForEntity("http://AM-TRADE/am-trade/bankException/deleteByOrderIdAndUserId/" + assignOrderId+"/"+userId, Boolean.class).getBody();
    }

    /**
     * 獲取賬戶信息
     * @param sellerUserId
     * @return
     */
    @Override
    public AccountVO getAccount(int userId) {
        AccountResponse response
                = restTemplate.getForEntity("http://AM-TRADE/am-trade/account/getAccountByUserId/" + userId, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 项目详情
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowVO getBorrowByNid(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowWithBLOBsByNid/" + borrowNid;
        BorrowResponse response = restTemplate.getForEntity(url,BorrowResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }


}
