package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.*;
import com.hyjf.am.resquest.admin.AdminBankConfigRequest;
import com.hyjf.am.vo.trade.BankConfigVO;

import java.util.List;

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

}
