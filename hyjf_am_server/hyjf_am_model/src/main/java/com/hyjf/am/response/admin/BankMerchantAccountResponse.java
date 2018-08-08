/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;

/**
 * @author zhangqingqing
 * @version BankMerchantAccountResponse, v0.1 2018/7/9 16:53
 */
public class BankMerchantAccountResponse extends Response<BankMerchantAccountVO> {

    private int recordTotal;

    private Boolean successFlag;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

    public Boolean getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(Boolean successFlag) {
        this.successFlag = successFlag;
    }
}
