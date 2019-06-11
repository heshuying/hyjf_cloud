/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.mapper.customize;

import com.hyjf.pay.lib.duiba.sdk.CreditConsumeParams;

/**
 * @author wangjun
 */
public interface DuiBaCustomizeMapper {
    /**
     * 扣除用户积分
     * @return
     */
    Integer updateUserPoints(CreditConsumeParams consumeParams);

}
