/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigResponseBean, v0.1 2018/9/26 15:38
 */
public class BailConfigResponseBean {

    @ApiModelProperty(value = "保证金配置")
    List<BailConfigCustomizeVO> recordList;

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
}
