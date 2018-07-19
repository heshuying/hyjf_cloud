package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.user.HjhInstConfigVO;

/**
 * @author by xiehuili on 2018/7/5.
 */
public class AdminInstConfigListResponse extends Response<HjhInstConfigVO> {

    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
}
