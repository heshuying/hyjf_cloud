/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.service.BaseServiceImpl;
import com.hyjf.admin.service.BailConfigService;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigServiceImpl, v0.1 2018/9/26 16:36
 */
@Service
public class BailConfigServiceImpl extends BaseServiceImpl implements BailConfigService {

    @Autowired
    AmAdminClient amAdminClient;

    /**
     * 获取保证金配置总数
     *
     * @param request
     * @return
     */
    @Override
    public Integer selectBailConfigCount(BailConfigRequest request) {
        return amAdminClient.selectBailConfigCount(request);
    }

    /**
     * 获取保证金配置列表
     *
     * @param request
     * @return
     */
    @Override
    public List<BailConfigCustomizeVO> selectRecordList(BailConfigRequest request) {
        return amAdminClient.selectBailConfigRecordList(request);
    }
}
