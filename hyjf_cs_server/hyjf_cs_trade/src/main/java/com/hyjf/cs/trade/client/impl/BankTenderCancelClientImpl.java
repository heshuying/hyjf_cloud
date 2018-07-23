package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BorrowTenderTmpResponse;
import com.hyjf.am.resquest.trade.TenderCancelRequest;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.BankTenderCancelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 投资撤销异常
 *
 * @author jijun
 * @date 20180625
 */
@Service
public class BankTenderCancelClientImpl implements BankTenderCancelClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<BorrowTenderTmpVO> getBorrowTenderTmpsForTenderCancel() {
        String url = "http://AM-TRADE/am-trade/bankException/getBorrowTenderTmpsForTenderCancel";
        BorrowTenderTmpResponse response = restTemplate.getForEntity(url, BorrowTenderTmpResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public boolean updateBidCancelRecord(TenderCancelRequest request) {
        String url = "http://AM-TRADE/am-trade/bankException/updateBidCancelRecord";
        return restTemplate.postForEntity(url,request,Boolean.class).getBody();

    }

    /**
     * 处理撤销出现异常的数据
     * @param info
     */
    @Override
    public boolean updateTenderCancelExceptionData(BorrowTenderTmpVO info) {
        String url = "http://AM-TRADE/am-trade/bankException/updateTenderCancelExceptionData";
        return restTemplate.postForEntity(url,info,Boolean.class).getBody();
    }
}
