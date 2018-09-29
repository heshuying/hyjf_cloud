/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;

/**
 * @author PC-LIUSHOUYI
 * @version ApiBailConfigInfoCustomizeMapper, v0.1 2018/9/29 11:04
 */
public interface ApiBailConfigInfoCustomizeMapper {
    /**
     * 根据资产来源查询保证金配置
     *
     * @param instCode
     * @return
     */
    BailConfigInfoCustomizeVO selectBailConfigByInstCode(String instCode);
}
