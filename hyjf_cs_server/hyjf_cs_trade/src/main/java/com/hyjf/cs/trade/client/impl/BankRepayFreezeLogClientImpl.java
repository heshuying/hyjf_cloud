package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BankRepayFreezeLogResponse;
import com.hyjf.am.resquest.trade.BankRepayFreezeLogRequest;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.BankRepayFreezeLogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author hesy
 * @version BankRepayFreezeLogClientImpl, v0.1 2018/7/10 16:28
 */
@Component
public class BankRepayFreezeLogClientImpl implements BankRepayFreezeLogClient {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取当前有效的冻结记录
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public BankRepayFreezeLogVO getFreezeLogValid(Integer userId, String borrowNid) {
        String url = "http://AM-TRADE/am-trade/repayfreezelog/get_logvalid/"+userId + "/" + borrowNid;
        BankRepayFreezeLogResponse response = restTemplate.getForEntity(url,BankRepayFreezeLogResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据orderId删除
     * @param orderId
     * @return
     */
    @Override
    public Integer deleteFreezeLogByOrderId(String orderId) {
        String url = "http://AM-TRADE/am-trade/repayfreezelog/deleteby_orderid/" + orderId;
        return restTemplate.getForEntity(url, Integer.class).getBody();
    }

    /**
     * 添加
     * @param requestBean
     * @return
     */
    @Override
    public Integer addFreezeLog(BankRepayFreezeLogRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/repayfreezelog/add";
        return restTemplate.postForEntity(url, requestBean, Integer.class).getBody();
    }
}
