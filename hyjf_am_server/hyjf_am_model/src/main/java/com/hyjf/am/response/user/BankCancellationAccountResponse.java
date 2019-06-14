/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.user;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.BankCancellationAccountVO;

/**
 * 用户销户记录Response
 *
 * @author liuyang
 * @version BankCancellationAccountResponse, v0.1 2019/3/31 13:23
 */
public class BankCancellationAccountResponse extends Response<BankCancellationAccountVO> {

    // 数据查询条数 主要用于分页情况，原子层向组合层返回
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
