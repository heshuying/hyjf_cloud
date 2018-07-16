package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.AppBorrowImageVO;

/**
 * @author lisheng
 * @version AppBorrowImageResponse, v0.1 2018/7/13 9:26
 */

public class AppBorrowImageResponse extends Response<AppBorrowImageVO> {
    private int recordTotal;
    public int getRecordTotal() {
        return recordTotal;
    }
    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
