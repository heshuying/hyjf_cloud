package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.PartnerConfigService;
import com.hyjf.am.response.admin.AdminPartnerConfigDetailResponse;
import com.hyjf.am.resquest.admin.AdminPartnerConfigListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by xiehuili on 2018/7/5.
 * @version InstConfigServiceImpl, v0.1 2018/7/5.
 */
@Service
public class PartnerConfigServiceImpl implements PartnerConfigService {

    /**
     * 查询列表
     * @param aicl
     * @return
     */
    @Autowired
    private AmTradeClient amTradeClient;
    @Override
    public AdminPartnerConfigDetailResponse partnerConfigInit(AdminPartnerConfigListRequest adminRequest){
        return amTradeClient.partnerConfigInit(adminRequest);
    }
    /**
     * 查询详情
     * @param adminRequest
     * @return
     */
    @Override
    public AdminPartnerConfigDetailResponse searchPartnerConfigInfo(AdminPartnerConfigListRequest adminRequest){
        return amTradeClient.searchPartnerConfigInfo(adminRequest);
    }

    /**
     * 编辑保存合作机构配置
     * @return
     */
    @Override
    public AdminPartnerConfigDetailResponse savePartnerConfig(AdminPartnerConfigListRequest req){
        return amTradeClient.savePartnerConfig(req);
    }
    /**
     * 修改合作机构配置
     * @return
     */
    @Override
    public AdminPartnerConfigDetailResponse updatePartnerConfig(AdminPartnerConfigListRequest req){
        return amTradeClient.updatePartnerConfig(req);
    }

    /**
     * 删除合作机构配置
     * @return
     */
    @Override
    public AdminPartnerConfigDetailResponse deletePartnerConfig(AdminPartnerConfigListRequest req){
        return amTradeClient.deletePartnerConfig(req);
    }

}
