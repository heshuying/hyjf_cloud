/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.HolidaysConfigNewVO;

import java.util.Date;

/**
 * @author yaoy
 * @version HolidaysConfigResponse, v0.1 2018/6/21 17:38
 */
public class HolidaysConfigResponse extends Response<HolidaysConfigNewVO> {

    private int recordTotal;

    private boolean workDateFlag;

    private Date somedate;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

    public boolean isWorkDateFlag() {
        return workDateFlag;
    }

    public void setWorkDateFlag(boolean workDateFlag) {
        this.workDateFlag = workDateFlag;
    }

    public Date getSomedate() {
        return somedate;
    }

    public void setSomedate(Date somedate) {
        this.somedate = somedate;
    }
}
