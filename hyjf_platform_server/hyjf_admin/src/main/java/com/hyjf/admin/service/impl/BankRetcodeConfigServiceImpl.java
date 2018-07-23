package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.BankRetcodeConfigClient;
import com.hyjf.admin.service.BankRetcodeConfigService;
import com.hyjf.am.response.trade.BankReturnCodeConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankRetcodeConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by xiehuili on 2018/7/17.
 */
@Service
public class BankRetcodeConfigServiceImpl implements BankRetcodeConfigService {

    @Autowired
    private BankRetcodeConfigClient bankRetcodeConfigClient;

    /**
     * 查询返回码配置列表
     * @param adminRequest
     * @return
     */
    @Override
    public BankReturnCodeConfigResponse selectBankRetcodeListByPage(AdminBankRetcodeConfigRequest adminRequest){
        return bankRetcodeConfigClient.selectBankRetcodeListByPage(adminRequest);
    }
    /**
     * 查询返回码配置详情页面
     * @param adminRequest
     * @return
     */
    @Override
    public BankReturnCodeConfigResponse selectBankRetcodeConfigInfo(AdminBankRetcodeConfigRequest adminRequest){
        return bankRetcodeConfigClient.selectBankRetcodeConfigInfo(adminRequest);
    }

    /**
     * 编辑保存返回码配置
     * @return
     */
    @Override
    public BankReturnCodeConfigResponse insertBankReturnCodeConfig(AdminBankRetcodeConfigRequest req){
        return bankRetcodeConfigClient.insertBankReturnCodeConfig(req);
    }

    /**
     * 修改返回码配置
     * @return
     */
    @Override
    public BankReturnCodeConfigResponse updateBankReturnCodeConfig(AdminBankRetcodeConfigRequest req){
        return bankRetcodeConfigClient.updateBankReturnCodeConfig(req);
    }

    /**
     * 根据主键判断维护中数据是否存在
     *
     * @return
     */
    @Override
    public boolean isExistsReturnCode(AdminBankRetcodeConfigRequest record){
        return bankRetcodeConfigClient.isExistsReturnCode(record);
    }
    /**
     * 根据条件判断该条数据在数据库中是否存在
     * @param adminRequest
     * @return
     */
    @Override
    public boolean isExistsRecord(AdminBankRetcodeConfigRequest adminRequest){
        return bankRetcodeConfigClient.isExistsRecord(adminRequest);
    }







}
