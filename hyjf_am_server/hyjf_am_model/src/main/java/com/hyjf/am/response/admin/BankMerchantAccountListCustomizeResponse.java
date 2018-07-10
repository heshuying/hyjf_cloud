/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BankMerchantAccountListCustomizeVO;

/**
 * @author zhangqingqing
 * @version BankMerchantAccountListCustomizeResponse, v0.1 2018/7/10 10:00
 */
public class BankMerchantAccountListCustomizeResponse extends Response<BankMerchantAccountListCustomizeVO> {

    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

}
