package com.hyjf.cs.trade.client.impl;

import java.util.List;
import java.util.Map;

import com.hyjf.am.response.trade.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.TenderToCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.BankCreditTenderClient;

import javax.validation.Valid;

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

    @Override
    public void handle() {

    }

    @Override
    public CreditTenderVO selectByAssignNidAndUserId(String assignNid, Integer userId) {
        String url = "http://AM-TRADE/am-trade/creditTender/selectByAssignNidAndUserId/" + assignNid+"/"+userId;
        CreditTenderResponse response = restTemplate.getForEntity(url,CreditTenderResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }


    @Override
    public boolean updateTenderCreditInfo(CreditTenderRequest request) {
        String url = "http://AM-TRADE/am-trade/creditTender/updateTenderCreditInfo";
        return restTemplate.postForEntity(url,request,Boolean.class).getBody();
    }

    /**
     * 获取CreditTenderLogs
     * add by jijun 20180622
     */
	@Override
	public List<CreditTenderLogVO> getCreditTenderLogs(String logOrderId, Integer userId) {
		String url = "http://AM-TRADE/am-trade/bankException/selectByOrderIdAndUserId/" + logOrderId+"/"+userId;
		CreditTenderLogResponse response = restTemplate.getForEntity(url,CreditTenderLogResponse.class).getBody();
		if (response != null) {
            return response.getResultList();
        }	
		return null;
	}

	/**
	 * 获取BorrowCreditList
	 * add by jijun 20180622
	 */
	@Override
	public List<BorrowCreditVO> getBorrowCreditList(String creditNid, int sellerUserId, String tenderOrderId) {
		BorrowCreditRequest request = new BorrowCreditRequest();
		request.setCreditNid(creditNid);
		request.setSellerUserId(sellerUserId);
		request.setTenderOrderId(tenderOrderId);
		String url = "http://AM-TRADE/am-trade/bankException/getBorrowCreditList";
		BorrowCreditResponse response = restTemplate.postForEntity(url, request, BorrowCreditResponse.class).getBody();
		if(response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<CreditTenderVO> getCreditTenderList(CreditTenderRequest request) {
		String url = "http://AM-TRADE/am-trade/creditTender/getCreditTenderList";
		CreditTenderResponse response = restTemplate.postForEntity(url, request, CreditTenderResponse.class).getBody();
		if(Validator.isNotNull(response)) {
			return response.getResultList();
		}
		return null;
	}

    @Override
    public List<TenderToCreditDetailCustomizeVO> selectWebCreditTenderDetailForContract(Map<String, Object> params) {
	    String url  = "http://AM-TRADE/am-trade/creditTender/selectWebCreditTenderDetailForContract";
        TenderToCreditDetailCustomizeResponse response = restTemplate.postForEntity(url, params, TenderToCreditDetailCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<TenderToCreditDetailCustomizeVO> selectHJHWebCreditTenderDetail(Map<String, Object> params) {
        String url  = "http://AM-TRADE/am-trade/creditTender/selectHJHWebCreditTenderDetail";
        TenderToCreditDetailCustomizeResponse response = restTemplate.postForEntity(url,params,TenderToCreditDetailCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            response.getResultList();
        }
        return null;
    }


}
