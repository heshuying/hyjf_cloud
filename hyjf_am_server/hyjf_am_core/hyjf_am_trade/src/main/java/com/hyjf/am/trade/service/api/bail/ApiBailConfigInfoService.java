/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.bail;

import com.hyjf.am.trade.service.BaseService;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;

/**
 * @author PC-LIUSHOUYI
 * @version ApiBailConfigInfoService, v0.1 2018/9/29 10:56
 */
public interface ApiBailConfigInfoService extends BaseService {
    /**
     * 根据资产来源查询保证金配置
     *
     * @param instCode
     * @return
     */
    BailConfigInfoCustomizeVO selectBailConfigByInstCode(String instCode);
}
