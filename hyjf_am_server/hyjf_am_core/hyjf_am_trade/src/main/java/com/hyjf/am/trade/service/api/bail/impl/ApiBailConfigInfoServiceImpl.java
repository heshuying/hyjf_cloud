/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.bail.impl;

import com.hyjf.am.trade.service.api.bail.ApiBailConfigInfoService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;
import org.springframework.stereotype.Service;

/**
 * @author PC-LIUSHOUYI
 * @version ApiBailConfigInfoServiceImpl, v0.1 2018/9/29 10:56
 */
@Service
public class ApiBailConfigInfoServiceImpl extends BaseServiceImpl implements ApiBailConfigInfoService {
    /**
     * 根据资产来源查询保证金配置
     *
     * @param instCode
     * @return
     */
    @Override
    public BailConfigInfoCustomizeVO selectBailConfigByInstCode(String instCode) {
        return apiBailConfigInfoCustomizeMapper.selectBailConfigByInstCode(instCode);
    }
}
