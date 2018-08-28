package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.BankConfigService;
import com.hyjf.am.response.admin.AdminBankConfigResponse;
import com.hyjf.am.resquest.admin.AdminBankConfigRequest;
import com.hyjf.am.vo.trade.BankConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author by xiehuili on 2018/7/11.
 */
@Service
public class BankConfigServiceImpl implements BankConfigService {

    @Value("${file.domain.url}")
    private String url;
    @Value("${file.physical.path}")
    private String physical;
    @Value("${file.upload.real.path}")
    private String real;

    @Autowired
    private AmConfigClient amConfigClient;

    /**
     * 查询银行配置列表
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse bankConfigInit(AdminBankConfigRequest adminRequest){
        return amConfigClient.bankConfigInit(adminRequest);
    }

    /**
     * 根据id查询银行配置
     * @param bankId
     * @return
     */
    @Override
    public AdminBankConfigResponse selectBankConfigById(Integer bankId){
        return amConfigClient.selectBankConfigById(bankId);
    }

    /**
     * 根据银行名称查询银行配置
     * @return
     */
    @Override
    public List<BankConfigVO> getBankConfigRecordList(BankConfigVO bank){
        return amConfigClient.getBankConfigRecordList(bank.getName());
    }

    /**
     * 添加银行配置
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse insertBankConfigRecord(AdminBankConfigRequest adminRequest){
        return amConfigClient.insertBankConfigRecord(adminRequest);
    }


    /**
     * 修改银行配置
     * @param adminRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse updateBankConfigRecord(AdminBankConfigRequest adminRequest){
        return amConfigClient.updateBankConfigRecord(adminRequest);
    }
    /**
     * 删除银行配置
     * @param id
     * @return
     */
    @Override
    public AdminBankConfigResponse deleteBankConfigById(Integer id){
        return amConfigClient.deleteBankConfigById(id);
    }

    /**
     * 保存之前的去重校验
     * @param adminBankConfigRequest
     * @return
     */
    @Override
    public AdminBankConfigResponse validateBeforeAction(AdminBankConfigRequest adminBankConfigRequest){
        return amConfigClient.validateBeforeAction(adminBankConfigRequest);
    }


}
