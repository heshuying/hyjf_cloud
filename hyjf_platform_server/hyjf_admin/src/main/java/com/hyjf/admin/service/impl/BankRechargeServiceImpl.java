package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.BankRechargeService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.AdminBankRechargeConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankRechargeConfigRequest;
import com.hyjf.am.vo.config.BankRechargeLimitConfigVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/19.
 */
@Service
public class BankRechargeServiceImpl implements BankRechargeService {

    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 查询快捷充值限额列表
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse bankRechargeInit(AdminBankRechargeConfigRequest adminRequest){
        return amConfigClient.bankRechargeInit(adminRequest);
    }
    /**
     * 查询快捷充值限额详情页面
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse selectBankRechargeConfigInfo(AdminBankRechargeConfigRequest adminRequest){
        return amConfigClient.selectBankRechargeConfigInfo(adminRequest);
    }

    /**
     * 编辑保存快捷充值限额
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse saveBankRechargeConfig(AdminBankRechargeConfigRequest req){
        return amConfigClient.saveBankRechargeConfig(req);
    }

    /**
     * 修改快捷充值限额
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse updateBankRechargeConfig(AdminBankRechargeConfigRequest req){
        return amConfigClient.updateBankRechargeConfig(req);
    }

    /**
     * 删除快捷充值限额
     * @return
     */
    @Override
    public AdminBankRechargeConfigResponse deleteBankRechargeConfig(AdminBankRechargeConfigRequest req){
        return amConfigClient.deleteBankRechargeConfig(req);
    }
    /**
     * 检查银行卡是否重复
     * @return
     */
    @Override
    public IntegerResponse bankIsExists(AdminBankRechargeConfigRequest adminRequest){
        return amConfigClient.bankIsExists(adminRequest);
    }
    /**
     * 查询快捷充值限额列表
     * @param adminRequest
     * @return
     */
    @Override
    public List<BankRechargeLimitConfigVO> exportRecordList(BankRechargeLimitConfigVO adminRequest){
        return amConfigClient.exportRecordList(adminRequest);
    }

    /**
     * 查询快捷充值限额列表
     * @return
     */
    @Override
    public List<BankConfigVO> getBankRecordList(){
        return amConfigClient.getBankRecordList();
    }
}
