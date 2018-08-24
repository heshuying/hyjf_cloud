package com.hyjf.am.config.service;

import java.util.List;

import com.hyjf.am.config.dao.model.auto.BankConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfig;
import com.hyjf.am.config.dao.model.auto.BankReturnCodeConfigExample;
import com.hyjf.am.config.dao.model.auto.JxBankConfig;
import com.hyjf.am.config.dao.model.auto.ParamName;
import com.hyjf.am.resquest.admin.AdminBankConfigRequest;
import com.hyjf.am.vo.trade.BankConfigVO;

public interface BankConfigService {

    /**
     * 获取银行卡配置信息
     * @param bankId
     * @return
     */
    BankConfig getBankConfigByBankId(Integer bankId);

    BankConfig selectBankConfigByCode(String code);

    BankReturnCodeConfig selectByExample(BankReturnCodeConfigExample example);

	String queryBankIdByCardNo(String cardNo);

    /**
     * 获取银行列表
     */
    List<BankConfig> selectBankConfigList();
    /**
     * 获取status=1的银行列表
     */
    List<BankConfig> getBankConfigListByStatus(BankConfigVO bankConfigVO);
    /**
     * 获取银行列表(快捷支付卡)
     */
    List<BankConfig> getBankRecordListByQuickPayment(BankConfigVO bankConfigVO);

    List<ParamName> getParamNameList(String nameClass);
    /**
     * 分页查询银行配置
     */
    List<BankConfig> selectBankConfigListByPage(BankConfigVO banksConfigVO, int page, int size);
    /**
     * 根据bankName查询银行配置
     */
    List<BankConfig> selectBankConfigByBankName(BankConfigVO banksConfigVO,int limitStart, int limitEnd);

    /**
     * 添加银行配置
     */
    int insertBankConfig(AdminBankConfigRequest adminBankConfigRequest);
    /**
     * 修改银行配置
     */
    int updadteBankConfig(AdminBankConfigRequest adminBankConfigRequest);

    /**
     * 删除银行配置
     */
    void deleteBankConfigById(Integer id);

    /**
     * 根据银行code获取银行配置
     * @auth sunpeikai
     * @param code 银行code,例如：招商银行,code是CMB
     * @return
     */
    List<BankConfig> getBankConfigByCode(String code);
    /**
     * 根据bankId查找江西银行的银行卡配置表
     * @param bankId
     * @return
     */
    JxBankConfig getJxBankConfigByBankId(int bankId);

    List<JxBankConfig> getRechargeQuotaLimit(Integer quickPayment);

    List<ParamName> getParamName(String other1);

    /**
     * 获取银行列表（快捷卡）
     * @param quickPayment
     * @return
     */
    List<JxBankConfig> getBankRecordList(Integer quickPayment);
}
