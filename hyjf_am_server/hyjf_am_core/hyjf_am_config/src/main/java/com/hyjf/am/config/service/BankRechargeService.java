package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.BankRechargeConfig;
import com.hyjf.am.resquest.admin.AdminBankRechargeConfigRequest;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/19.
 */
public interface BankRechargeService {

    /**
     * 查询查询快捷充值列表
     * @param adminRequest
     * @param limitStart
     * @param limitEnd
     * @return
     */
    List<BankRechargeConfig> selectBankRechargeByPage(AdminBankRechargeConfigRequest adminRequest, int limitStart, int limitEnd);

    /**
     * 查询快捷充值情页面
     * @param id
     * @return
     */
    BankRechargeConfig selectBankRechargeById(Integer id);

    /**
     * 添加快捷充值
     * @param adminRequest
     * @return
     */
    Integer insertBankRecharge(AdminBankRechargeConfigRequest adminRequest);
    /**
     * 添加快捷充值
     * @param adminRequest
     * @return
     */
    Integer updateBankRecharge(AdminBankRechargeConfigRequest adminRequest);

    /**
     * 添加快捷充值
     * @param id
     * @return
     */
    void deleteBankRecharge(Integer id);
    /**
     * 查询查询快捷充值列表
     * @param adminRequest
     * @return
     */
    List<BankRechargeConfig> selectExportRecordList(AdminBankRechargeConfigRequest adminRequest);

    /**
     * 检查银行卡是否重复
     * @return
     */
    public int bankIsExists(AdminBankRechargeConfigRequest adminRequest);
    /**
     * 根据bankId查询BankRechargeConfig
     * @auth sunpeikai
     * @param bankId
     * @return
     */
    List<BankRechargeConfig> getBankRechargeConfigByBankId(Integer bankId);
}
