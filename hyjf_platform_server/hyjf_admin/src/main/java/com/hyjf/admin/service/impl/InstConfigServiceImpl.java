package com.hyjf.admin.service.impl;

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
    private InstConfigClient instConfigClient;
    @Override
    public AdminInstConfigListResponse instConfigInit(AdminInstConfigListRequest adminRequest){
        return instConfigClient.instConfigInit(adminRequest);
    }
    /**
     * 查询详情
     * @param adminRequest
     * @return
     */
    @Override
    public AdminInstConfigDetailResponse searchInstConfigInfo(AdminInstConfigListRequest adminRequest){
        return instConfigClient.searchInstConfigInfo(adminRequest);
    }

    /**
     * 编辑保存保证金配置
     * @return
     */
    @Override
    public AdminInstConfigListResponse saveInstConfig(AdminInstConfigListRequest req){
        return instConfigClient.saveInstConfig(req);
    }
    /**
     * 修改保证金配置
     * @return
     */
    @Override
    public AdminInstConfigListResponse updateInstConfig(AdminInstConfigListRequest req){
        return instConfigClient.updateInstConfig(req);
    }

    /**
     * 删除保证金配置
     * @return
     */
    @Override
    public AdminInstConfigListResponse deleteInstConfig(AdminInstConfigListRequest req){
        return instConfigClient.deleteInstConfig(req);
    }

}
