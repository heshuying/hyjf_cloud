/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.AdminBorrowFirstCustomizeVO;
import com.hyjf.admin.beans.vo.DropDownVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowFirstResponseBean, v0.1 2018/7/5 15:36
 */
public class BorrowFirstResponseBean {
    @ApiModelProperty(value = "资产来源下拉框")
    private List<DropDownVO> hjhInstConfigList;

    @ApiModelProperty(value = "初审状态下拉框")
    private List<DropDownVO> borrowStatusList;

    @ApiModelProperty(value = "初审列表")
    private List<AdminBorrowFirstCustomizeVO> recordList;

    @ApiModelProperty(value = "列表统计")
    private String sumAccount;

    @ApiModelProperty(value = "总条数")
    private Integer total;

    public List<DropDownVO> getHjhInstConfigList() {
        return hjhInstConfigList;
    }

    public void setHjhInstConfigList(List<DropDownVO> hjhInstConfigList) {
        this.hjhInstConfigList = hjhInstConfigList;
    }

    public List<DropDownVO> getBorrowStatusList() {
        return borrowStatusList;
    }

    public void setBorrowStatusList(List<DropDownVO> borrowStatusList) {
        this.borrowStatusList = borrowStatusList;
    }

    public List<AdminBorrowFirstCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<AdminBorrowFirstCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public String getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(String sumAccount) {
        this.sumAccount = sumAccount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
