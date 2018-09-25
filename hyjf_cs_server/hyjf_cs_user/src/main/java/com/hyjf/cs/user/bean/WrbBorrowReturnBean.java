/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.bean;

import com.hyjf.am.response.WrbResponse;
import com.hyjf.am.vo.trade.wrb.WrbBorrowListCustomizeVO;

import java.util.List;

/**
 * @author fq
 * @version WrbBorrowReturnBean, v0.1 2018/9/25 10:40
 */
public class WrbBorrowReturnBean extends WrbResponse {
    private List<WrbBorrowListCustomizeVO> invest_list;

    public List<WrbBorrowListCustomizeVO> getInvest_list() {
        return invest_list;
    }

    public void setInvest_list(List<WrbBorrowListCustomizeVO> invest_list) {
        this.invest_list = invest_list;
    }
}
