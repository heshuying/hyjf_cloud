package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.InstConfigClient;
import com.hyjf.admin.service.InstConfigService;
import com.hyjf.am.response.admin.AdminInstConfigDetailResponse;
import com.hyjf.am.response.admin.AdminInstConfigListResponse;
import com.hyjf.am.resquest.admin.AdminInstConfigListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by xiehuili on 2018/7/5.
 * @version InstConfigServiceImpl, v0.1 2018/7/5.
 */
@Service
public class InstConfigServiceImpl implements InstConfigService {

    /**
     * 查询列表
     * @param aicl
     * @return
     */
    @Autowired
    private AmTradeClient amTradeClient;
    @Override
    public AdminInstConfigDetailResponse instConfigInit(AdminInstConfigListRequest adminRequest){
        return amTradeClient.instConfigInit(adminRequest);
    }
    /**
     * 查询详情
     * @param adminRequest
     * @return
     */
    @Override
    public AdminInstConfigDetailResponse searchInstConfigInfo(AdminInstConfigListRequest adminRequest){
        return amTradeClient.searchInstConfigInfo(adminRequest);
    }

    /**
     * 编辑保存保证金配置
     * @return
     */
    @Override
    public AdminInstConfigListResponse saveInstConfig(AdminInstConfigListRequest req){
        return amTradeClient.saveInstConfig(req);
    }
    /**
     * 修改保证金配置
     * @return
     */
    @Override
    public AdminInstConfigListResponse updateInstConfig(AdminInstConfigListRequest req){
        return amTradeClient.updateInstConfig(req);
    }

    /**
     * 删除保证金配置
     * @return
     */
    @Override
    public AdminInstConfigListResponse deleteInstConfig(AdminInstConfigListRequest req){
        return amTradeClient.deleteInstConfig(req);
    }

}
