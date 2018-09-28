/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.vo.admin.BailConfigLogCustomizeVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigLogResponseBean, v0.1 2018/9/28 9:48
 */
public class BailConfigLogResponseBean {

    @ApiModelProperty(value = "机构名称下拉框")
    List<DropDownVO> instNameList;

    @ApiModelProperty(value = "机构名称下拉框")
    List<DropDownVO> modifyColumnList;

    @ApiModelProperty(value = "总条数")
    private Integer total;

    @ApiModelProperty(value = "保证金配置")
    List<BailConfigLogCustomizeVO> recordList;

    public List<DropDownVO> getInstNameList() {
        return instNameList;
    }

    public void setInstNameList(List<DropDownVO> instNameList) {
        this.instNameList = instNameList;
    }

    public List<DropDownVO> getModifyColumnList() {
        return modifyColumnList;
    }

    public void setModifyColumnList(List<DropDownVO> modifyColumnList) {
        this.modifyColumnList = modifyColumnList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<BailConfigLogCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BailConfigLogCustomizeVO> recordList) {
        this.recordList = recordList;
    }
}
