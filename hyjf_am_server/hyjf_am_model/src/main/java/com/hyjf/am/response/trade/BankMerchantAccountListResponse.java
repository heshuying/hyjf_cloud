/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;

/**
 * @author: sunpeikai
 * @version: BankMerchantAccountListResponse, v0.1 2018/7/9 18:31
 */
public class BankMerchantAccountListResponse extends Response<BankMerchantAccountListVO> {
    //新增或者update标识
    private Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
