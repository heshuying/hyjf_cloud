/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.config.WithdrawRuleConfigVO;

/**
 * 提现规则配置response
 */
public class WithdrawRuleConfigResponse extends Response<WithdrawRuleConfigVO> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
