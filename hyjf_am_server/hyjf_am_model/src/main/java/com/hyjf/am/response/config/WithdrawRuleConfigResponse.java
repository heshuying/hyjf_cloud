/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.WithdrawRuleConfigVO;

/**
 * 提现规则配置Response
 *
 * @author liuyang
 * @version WithdrawRuleConfigResponse, v0.1 2019/4/19 16:16
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
