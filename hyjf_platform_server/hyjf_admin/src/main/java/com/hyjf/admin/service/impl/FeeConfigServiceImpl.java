package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.FeeConfigService;
import com.hyjf.am.response.admin.AdminFeeConfigResponse;
import com.hyjf.am.resquest.admin.AdminFeeConfigRequest;
import com.hyjf.am.vo.trade.BankConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/19.
 */
@Service
public class FeeConfigServiceImpl implements FeeConfigService {
    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 查询手续费配置列表
     * @param request
     * @return
     */
    @Override
    public AdminFeeConfigResponse selectFeeConfigList(AdminFeeConfigRequest request){
        return amConfigClient.selectFeeConfigList(request);
    }
    /**
     * 查询银行列表
     * @param bank
     * @return
     */
    @Override
    public List<BankConfigVO> getBankConfigList(BankConfigVO bank){
        return amConfigClient.getBankConfigList(bank);
    }
    /**
     * 获取手续费列表列表
     * @param bank
     * @return
     */
    @Override
    public List<BankConfigVO> getBankConfigRecordList(BankConfigVO bank,int limitStart,int limitEnd){
        return amConfigClient.getBankConfigRecordList(bank,limitStart,limitEnd);
    }
    /**
     * 查询手续费配置详情页面
     * @param adminRequest
     * @return
     */
    @Override
    public AdminFeeConfigResponse selectFeeConfigInfo(AdminFeeConfigRequest adminRequest){
        return amConfigClient.selectFeeConfigInfo(adminRequest);
    }
    /**
     * 编辑保存手续费配置
     * @return
     */
    @Override
    public AdminFeeConfigResponse insertBankConfigRecord(AdminFeeConfigRequest req){
        return amConfigClient.insertBankConfigRecord(req);
    }
    /**
     * 修改手续费配置
     * @return
     */
    @Override
    public AdminFeeConfigResponse updateBankConfigRecord(AdminFeeConfigRequest req){
        return amConfigClient.updateBankConfigRecord(req);
    }
    /**
     * 删除手续费配置
     * @return
     */
    @Override
    public AdminFeeConfigResponse deleteFeeConfig(AdminFeeConfigRequest req){
        return amConfigClient.deleteFeeConfig(req);
    }
}
