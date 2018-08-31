/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade.account;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.account.MerchantTransferVO;

/**
 * @author zhangqingqing
 * @version MerchantTransferResponse, v0.1 2018/8/30 17:49
 */
public class MerchantTransferResponse extends Response<MerchantTransferVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
