/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.CouponCheckVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version CouponCheckResponse, v0.1 2018/7/4 11:01
 */
public class CouponCheckResponse extends Response<CouponCheckVO> {
    private int recordTotal;

    private boolean bool;

    private Map couponStatus;

    private List<ParamNameVO> couponType;

    private String filePath;

    private String fileName;

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

    public Map getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Map couponStatus) {
        this.couponStatus = couponStatus;
    }

    public List<ParamNameVO> getCouponType() {
        return couponType;
    }

    public void setCouponType(List<ParamNameVO> couponType) {
        this.couponType = couponType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
