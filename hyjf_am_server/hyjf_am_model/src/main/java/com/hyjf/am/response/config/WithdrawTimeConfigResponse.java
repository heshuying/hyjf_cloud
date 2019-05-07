/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.WithdrawTimeConfigVO;

/**
 * 提现时间配置Response
 *
 * @author liuyang
 * @version WithdrawTimeConfigResponse, v0.1 2019/4/22 9:48
 */
public class WithdrawTimeConfigResponse extends Response<WithdrawTimeConfigVO> {
    // 是否是工作日
    private  boolean isWorkDateFlag;

    private int count;

    public boolean isWorkDateFlag() {
        return isWorkDateFlag;
    }

    public void setWorkDateFlag(boolean workDateFlag) {
        isWorkDateFlag = workDateFlag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
