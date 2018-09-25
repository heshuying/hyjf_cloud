/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.CouponCheckVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author yaoyong
 * @version CouponCheckResponse, v0.1 2018/7/4 11:01
 */
public class CouponCheckResponse extends Response<CouponCheckVO> {
    private int recordTotal;

    private boolean bool;

    private String status;

    private List<ParamNameVO> couponType;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = status;
    }

    public List<ParamNameVO> getCouponType() {
        return couponType;
    }

    public void setCouponType(List<ParamNameVO> couponType) {
        this.couponType = couponType;
    }
}
