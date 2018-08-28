package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankConfigRequest;
import com.hyjf.am.vo.trade.BankConfigVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/11.
 */
public interface BankConfigService {
    /**
     * 查询银行配置列表
     * @param adminRequest
     * @return
     */
    public AdminBankConfigResponse bankConfigInit(AdminBankConfigRequest adminRequest);
    /**
     * 根据id查询银行配置
     * @param bankId
     * @return
     */
    public AdminBankConfigResponse selectBankConfigById(Integer bankId);
    /**
     * 根据银行名称查询银行配置
     * @return
     */
    public List<BankConfigVO> getBankConfigRecordList(BankConfigVO bank);

    /**
     * 添加银行配置
     * @param adminRequest
     * @returnz
     */
    public AdminBankConfigResponse insertBankConfigRecord(AdminBankConfigRequest adminRequest);

    /**
     * 修改银行配置
     * @param adminRequest
     * @return
     */
    public AdminBankConfigResponse updateBankConfigRecord(AdminBankConfigRequest adminRequest);
    /**
     * 删除银行配置
     * @param id
     * @return
     */
    public AdminBankConfigResponse deleteBankConfigById(Integer id);


    /**
     * 保存之前的去重校验
     * @param adminBankConfigRequest
     * @return
     */
    public AdminBankConfigResponse validateBeforeAction(AdminBankConfigRequest adminBankConfigRequest);
}
