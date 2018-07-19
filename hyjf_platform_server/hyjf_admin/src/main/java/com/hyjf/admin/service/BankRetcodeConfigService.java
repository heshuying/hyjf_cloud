package com.hyjf.admin.service;

import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankRetcodeConfigRequest;

/**
 * @author by xiehuili on 2018/7/17.
 */
public interface BankRetcodeConfigService {

    /**
     * 查询返回码配置列表
     * @param adminRequest
     * @return
     */
    public BankReturnCodeConfigResponse selectBankRetcodeListByPage(AdminBankRetcodeConfigRequest adminRequest);
    /**
     * 查询返回码配置详情页面
     * @param adminRequest
     * @return
     */
    public BankReturnCodeConfigResponse selectBankRetcodeConfigInfo(AdminBankRetcodeConfigRequest adminRequest);

    /**
     * 编辑保存返回码配置
     * @return
     */
    public BankReturnCodeConfigResponse insertBankReturnCodeConfig(AdminBankRetcodeConfigRequest req);

    /**
     * 修改返回码配置
     * @return
     */
    public BankReturnCodeConfigResponse updateBankReturnCodeConfig(AdminBankRetcodeConfigRequest req);

    /**
     * 根据主键判断维护中数据是否存在
     *
     * @return
     */
    boolean isExistsReturnCode(AdminBankRetcodeConfigRequest record);
    /**
     * 根据条件判断该条数据在数据库中是否存在
     * @param adminRequest
     * @return
     */
    public boolean isExistsRecord(AdminBankRetcodeConfigRequest adminRequest);



}
