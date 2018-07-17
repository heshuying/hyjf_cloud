/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.CreditListVO;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;

/**
 * @desc 债转标的vo
 * @author zhangyk
 * @date 2018/6/19 15:29
 */
public class CreditListResponse extends Response<CreditListVO> {

    // 数据查询条数 主要用于分页情况，原子层向组合层返回
    private  Integer  count;



    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
