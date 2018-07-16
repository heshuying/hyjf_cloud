package com.hyjf.am.response.config;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.config.AppBorrowImageVO;
import com.hyjf.am.vo.config.VersionVO;

import java.io.Serializable;
import java.util.Map;

/**
 * @author lisheng
 * @version VersionConfigBeanRequest, v0.1 2018/7/13 18:01
 */

public class VersionConfigBeanResponse extends Response<VersionVO> {
    private int recordTotal;
    public int getRecordTotal() {
        return recordTotal;
    }
    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }

    private Map<String, String>  versionName;
    private Map<String, String> isUpdate;

    public Map<String, String> getVersionName() {
        return versionName;
    }

    public void setVersionName(Map<String, String> versionName) {
        this.versionName = versionName;
    }

    public Map<String, String> getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Map<String, String> isUpdate) {
        this.isUpdate = isUpdate;
    }
}
