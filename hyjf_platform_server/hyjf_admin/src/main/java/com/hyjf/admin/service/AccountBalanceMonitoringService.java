package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.AccountBalanceMonitoringRequestBean;
import com.hyjf.am.response.admin.AdminAccountBalanceMonitoringResponse;
import com.hyjf.am.resquest.admin.AdminAccountBalanceMonitoringRequest;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/13.
 */
public interface AccountBalanceMonitoringService {

    /**
     * 查询配置中心平台账户配置 余额监控
     * @param adminRequest
     * @return
     */
    AdminAccountBalanceMonitoringResponse selectaccountBalanceMonitoringByPage(AdminAccountBalanceMonitoringRequest adminRequest);

    /**
     * 编辑画面检索列表
     * @param adminRequest
     * @return
     */
    AdminAccountBalanceMonitoringResponse selectaccountBalanceMonitoringById(AdminAccountBalanceMonitoringRequest adminRequest);

    /**
     * 查询配置中心平台账户配置 余额监控
     * @param adminRequest
     * @return
     */
    List<AccountBalanceMonitoringRequestBean> searchMerchantAccountList(AdminAccountBalanceMonitoringRequest adminRequest, int limitStart, int limitEnd);
    /**
     * 数据更新 平台账户设置
     * @param adminRequest
     * @return
     */
    AdminAccountBalanceMonitoringResponse updateMerchantAccountList(List<AccountBalanceMonitoringRequestBean> adminRequest);
}
