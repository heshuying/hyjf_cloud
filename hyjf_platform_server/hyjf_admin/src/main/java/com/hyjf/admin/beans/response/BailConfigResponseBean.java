/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigResponseBean, v0.1 2018/9/26 15:38
 */
public class BailConfigResponseBean {

    @ApiModelProperty(value = "机构名称下拉框")
    private List<DropDownVO> instNameList = new ArrayList<>();

    @ApiModelProperty(value = "保证金配置")
    private List<BailConfigCustomizeVO> recordList = new ArrayList<>();

    @ApiModelProperty(value = "总条数")
    private Integer total;

    public List<BailConfigCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BailConfigCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<DropDownVO> getInstNameList() {
        return instNameList;
    }

    public void setInstNameList(List<DropDownVO> instNameList) {
        this.instNameList = instNameList;
    }
}
