/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;

import java.util.Map;

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

    /**
     * 发标更新相关金额
     *
     * @param map
     * @return
     */
    Integer updateForSendBorrow(Map<String,Object> map);
}
