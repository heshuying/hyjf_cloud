package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.AccountBalanceMonitoringRequestBean;
import com.hyjf.admin.client.AccountBalanceMonitoringClient;
import com.hyjf.admin.service.AccountBalanceMonitoringService;
import com.hyjf.am.response.admin.AdminAccountBalanceMonitoringResponse;
import com.hyjf.am.resquest.admin.AdminAccountBalanceMonitoringRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
@Service
public class AccountBalanceMonitoringServiceImpl implements AccountBalanceMonitoringService {

    @Autowired
   private AccountBalanceMonitoringClient accountBalanceMonitoringClient;
    /**
     * 查询配置中心平台账户配置 余额监控
     * @param adminRequest
     * @return
     */
    @Override
   public AdminAccountBalanceMonitoringResponse selectaccountBalanceMonitoringByPage(AdminAccountBalanceMonitoringRequest adminRequest){
        return accountBalanceMonitoringClient.selectAccountBalanceMonitoringByPage(adminRequest);
    }

    /**
     * 编辑画面检索列表
     * @param adminRequest
     * @return
     */
    @Override
    public AdminAccountBalanceMonitoringResponse selectaccountBalanceMonitoringById(AdminAccountBalanceMonitoringRequest adminRequest){
        return accountBalanceMonitoringClient.selectaccountBalanceMonitoringById(adminRequest);
    }

    /**
     * 查询配置中心平台账户配置 余额监控
     * @param adminRequest
     * @return
     */
    @Override
    public List<AccountBalanceMonitoringRequestBean> searchMerchantAccountList(AdminAccountBalanceMonitoringRequest adminRequest, int limitStart, int limitEnd){
        return accountBalanceMonitoringClient.searchMerchantAccountList(adminRequest);
    }
    /**
     * 数据更新 平台账户设置
     * @param adminRequest
     * @return
     */
    @Override
     public AdminAccountBalanceMonitoringResponse updateMerchantAccountList(List<AccountBalanceMonitoringRequestBean> adminRequest){
        return accountBalanceMonitoringClient.updateMerchantAccountList(adminRequest);
    }


}
