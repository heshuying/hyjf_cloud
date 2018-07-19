package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.HjhInstConfigWrapVo;

/**
 * @author by xiehuili on 2018/7/6.
 */
public class AdminInstConfigDetailResponse extends Response<HjhInstConfigWrapVo>  {
    private int recordTotal;

    public int getRecordTotal() {
            return recordTotal;
            }

    public void setRecordTotal(int recordTotal) {
            this.recordTotal = recordTotal;
            }

}
