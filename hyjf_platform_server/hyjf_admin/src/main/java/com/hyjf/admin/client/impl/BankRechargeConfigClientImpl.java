package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.BankRechargeConfigClient;
import com.hyjf.am.response.admin.AdminBankRechargeConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankRechargeConfigRequest;
import com.hyjf.am.vo.config.BankRechargeLimitConfigVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/19.
 */
@Service
public class BankRechargeConfigClientImpl implements BankRechargeConfigClient {

    @Autowired
    private RestTemplate restTemplate;
    /**
     * 查询快捷充值限额列表
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse bankRechargeInit(AdminBankRechargeConfigRequest adminRequest){
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/bankrecharge/list",adminRequest, AdminBankRechargeConfigResponse.class)
                .getBody();
    }
    /**
     * 查询快捷充值限额详情页面
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse selectBankRechargeConfigInfo(AdminBankRechargeConfigRequest adminRequest){
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/bankrecharge/info",adminRequest, AdminBankRechargeConfigResponse.class)
                .getBody();
    }

    /**
     * 编辑保存快捷充值限额
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse saveBankRechargeConfig(AdminBankRechargeConfigRequest req){
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/bankrecharge/insert",req, AdminBankRechargeConfigResponse.class)
                .getBody();
    }

    /**
     * 修改快捷充值限额
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse updateBankRechargeConfig(AdminBankRechargeConfigRequest req){
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/bankrecharge/update",req, AdminBankRechargeConfigResponse.class)
                .getBody();
    }

    /**
     * 删除快捷充值限额
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse deleteBankRechargeConfig(AdminBankRechargeConfigRequest req){
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/bankrecharge/delete",req, AdminBankRechargeConfigResponse.class)
                .getBody();
    }
    /**
     * 查询快捷充值限额列表
     * @param adminRequest
     * @return
     */
    @Override
    public List<BankRechargeLimitConfigVO> exportRecordList(BankRechargeLimitConfigVO adminRequest){
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/bankrecharge/exportRecordList",adminRequest, List.class)
                .getBody();
    }

    /**
     * 获取银行列表(快捷支付卡)
     * @return
     */
    @Override
    public List<BankConfigVO> getBankRecordList(){
        BankConfigVO bank=new BankConfigVO();
        return restTemplate.postForEntity("http://AM-CONFIG/am-config/config/getBankRecordListByQuickPayment",bank, List.class)
                .getBody();
    }
}
