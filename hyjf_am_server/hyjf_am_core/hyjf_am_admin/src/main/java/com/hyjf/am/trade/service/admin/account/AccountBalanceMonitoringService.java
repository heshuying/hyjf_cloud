package com.hyjf.am.trade.service.admin.account;

import java.util.List;

import com.hyjf.am.resquest.admin.AdminAccountBalanceMonitoringRequest;
import com.hyjf.am.trade.dao.model.auto.MerchantAccount;

/**
 * @author by xiehuili on 2018/7/13.
 */
public interface AccountBalanceMonitoringService {

    /**
     * 分页查询平台账户配置 余额监控列表 条数
     * @param adminRequest
     * @return
     */
    int getAccountBalanceMonitoringCount(AdminAccountBalanceMonitoringRequest adminRequest);


    /**
     * 分页查询平台账户配置 余额监控列表
     * @param adminRequest
     * @return
     */
    List<MerchantAccount> getAccountBalanceMonitoringByPage(AdminAccountBalanceMonitoringRequest adminRequest,int limitStart,int limitEnd);
    /**
     * 平台账户配置 余额监控详情页面
     * @param adminRequest
     * @return
     */
    List<MerchantAccount> selectAccountBalanceMonitoringById(AdminAccountBalanceMonitoringRequest adminRequest);

    /**
     * 分页查询平台账户配置 余额监控列表 条数
     * @param adminRequest
     * @return
     */
    int updateMerchantAccountList(List<AdminAccountBalanceMonitoringRequest> adminRequest);
}
