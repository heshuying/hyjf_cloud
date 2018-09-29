/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.vo.admin.AssetExceptionCustomizeVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version AssetExceptionResponseBean, v0.1 2018/9/28 17:58
 */
public class AssetExceptionResponseBean {

    @ApiModelProperty(value = "资产来源下拉框")
    List<DropDownVO> instNameList;

    @ApiModelProperty(value = "总条数")
    private Integer total;

    @ApiModelProperty(value = "保证金配置")
    List<AssetExceptionCustomizeVO> recordList;

    public List<DropDownVO> getInstNameList() {
        return instNameList;
    }

    public void setInstNameList(List<DropDownVO> instNameList) {
        this.instNameList = instNameList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<AssetExceptionCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<AssetExceptionCustomizeVO> recordList) {
        this.recordList = recordList;
    }
}
