/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BanksConfigResponse;
import com.hyjf.am.response.user.BankCardLogResponse;
import com.hyjf.am.response.user.BankCardManagerResponse;
import com.hyjf.am.resquest.user.BankCardLogRequest;
import com.hyjf.am.resquest.user.BankCardManagerRequest;
import com.hyjf.am.vo.trade.BanksConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.hyjf.admin.client.BankCardManagerClient;
import java.util.List;

/**
 * @author nixiaoling
 * @version UserCenterClientImpl, v0.1 2018/6/20 15:38
 */
@Service
public class BankCardManagerClientImpl implements BankCardManagerClient{
    @Autowired
    private RestTemplate restTemplate;
    /**
     * 获取银行列表
     * @author nixiaoling
     * @return
     */
    @Override
    public List<BanksConfigVO> selectBankConfigList() {
        BanksConfigResponse response = restTemplate
                .getForEntity("http://AM-USER//am-config/config/selectBankConfigList", BanksConfigResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     *  根据筛选条件查找汇付银行卡信息列表
     * @param request 筛选条件
     * @return
     */
    @Override
    public BankCardManagerResponse selectBankCardList(BankCardManagerRequest request){
        BankCardManagerResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/bankCardManager/bankcardlistHF",request, BankCardManagerResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }


    /**
     * 根据筛选条件查找江西银行卡信息列表
     * @author nixiaoling
     * @return
     */
    @Override
    public BankCardManagerResponse selectNewBankCardList (BankCardManagerRequest request){
        BankCardManagerResponse response = restTemplate
                .postForEntity("http://AM-USER/am-user/bankCardManager/bankcardlistJX",request, BankCardManagerResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 查找用户银行卡操作记录表
     * @param request
     * @author nixiaoling
     * @return
     */
    @Override
    public BankCardLogResponse selectBankCardLogByExample(BankCardLogRequest request){
        BankCardLogResponse response =  restTemplate
                .postForEntity("http://AM-USER/am-user/bankCardManager/selectBankCardLogByExample",request, BankCardLogResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

}
