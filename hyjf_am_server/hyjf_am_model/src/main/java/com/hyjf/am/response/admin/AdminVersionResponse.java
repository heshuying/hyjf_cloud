package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.VersionVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author by xiehuili on 2018/7/16.
 */
public class AdminVersionResponse extends Response<VersionVO> {
    private int recordTotal;

    public int getRecordTotal() {
        return recordTotal;
    }

    public void setRecordTotal(int recordTotal) {
        this.recordTotal = recordTotal;
    }
    /*数据字典 版本号 */
    private List<ParamNameVO> versionNames;
    /*数据字典 是否需要更新 */
    private List<ParamNameVO> isUpdates;

    public List<ParamNameVO> getVersionNames() {
        return versionNames;
    }

    public void setVersionNames(List<ParamNameVO> versionNames) {
        this.versionNames = versionNames;
    }

    public List<ParamNameVO> getIsUpdates() {
        return isUpdates;
    }

    public void setIsUpdates(List<ParamNameVO> isUpdates) {
        this.isUpdates = isUpdates;
    }
}
