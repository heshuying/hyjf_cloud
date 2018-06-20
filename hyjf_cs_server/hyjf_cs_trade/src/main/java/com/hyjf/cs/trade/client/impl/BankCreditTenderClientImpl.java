package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.CreditTenderLogResponse;
import com.hyjf.am.response.trade.CreditTenderResponse;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.cs.trade.client.BankCreditTenderClient;

import java.util.List;

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
        CreditTenderLogResponse response = restTemplate.getForEntity("http://AM-TRADE/bankException/selectCreditTenderLogs",
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
                restTemplate.getForEntity("http://AM-TRADE/bankException/selectCreditTender/"+assignNid,
                        CreditTenderResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }


}
