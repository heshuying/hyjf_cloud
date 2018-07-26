package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BankInterfaceClient;
import com.hyjf.am.response.trade.BankInterfaceResponse;
import com.hyjf.am.resquest.admin.BankInterfaceRequest;
import com.hyjf.am.vo.trade.account.BankInterfaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author by xiehuili on 2018/7/20.
 */
@Service
public class BankInterfaceClientImpl implements BankInterfaceClient {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询配置中心接口切换列表
     * @param adminRequest
     * @return
     */
    @Override
    public BankInterfaceResponse bankInterfaceInit(BankInterfaceRequest adminRequest){
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/bankInterface/list",adminRequest, BankInterfaceResponse.class)
                .getBody();
    }
    /**
     * 查询配置中心接口切换详情页面
     * @param adminRequest
     * @return
     */
    @Override
    public BankInterfaceResponse bankInterfaceInfo(BankInterfaceRequest adminRequest){
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/bankInterface/info",adminRequest, BankInterfaceResponse.class)
                .getBody();
    }
    /**
     * 修改 接口切换
     * @return
     */
    @Override
    public BankInterfaceResponse updateBankIntefaceAction(BankInterfaceVO req){
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/bankInterface/update",req, BankInterfaceResponse.class)
                .getBody();
    }

    /**
     * 删除快捷充值限额
     * @return
     */
    @Override
    public BankInterfaceResponse deleteBankInterfaceConfig(BankInterfaceVO req){
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/bankInterface/delete",req, BankInterfaceResponse.class)
                .getBody();
    }
}
