/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import java.util.HashMap;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version HjhBailConfigCustomizeMapper, v0.1 2018/9/29 18:38
 */
public interface HjhBailConfigCustomizeMapper {
    /**
     * 还款回滚保证金配置相关金额
     *
     * @param map
     * @return
     */
    Integer updateRepayInstitutionAmount(HashMap map);

    /**
     * 少放款保证金配置相关金额变更
     *
     * @param map
     * @return
     */
    Integer updateLoanInstitutionAmount(HashMap map);

    /**
     * 发标更新相关金额
     * @return
     */
    Integer updateForSendBorrow(Map<String,Object> map);
}
