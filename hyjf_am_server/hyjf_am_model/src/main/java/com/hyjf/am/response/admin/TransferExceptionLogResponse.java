/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.AdminTransferExceptionLogCustomizeVO;
import com.hyjf.am.vo.trade.TransferExceptionLogVO;

/**
 * @author jun
 * @version TransferExceptionLogResponse, v0.1 2018/7/10 11:09
 */
public class TransferExceptionLogResponse extends Response<TransferExceptionLogVO> {

    //标识，记录insert或者update结果返回
    private Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
