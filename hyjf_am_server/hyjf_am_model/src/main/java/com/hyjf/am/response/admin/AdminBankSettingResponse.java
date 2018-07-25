/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dangzw
 * @version AdminBankSettingResponse, v0.1 2018/7/24 22:44
 */
public class AdminBankSettingResponse extends Response<JxBankConfigVO> {

    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
