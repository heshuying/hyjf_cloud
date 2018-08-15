/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.AdminBorrowRegistCustomizeVO;
import com.hyjf.admin.beans.vo.DropDownVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowRegistResponseBean, v0.1 2018/7/5 18:24
 */
public class BorrowRegistResponseBean {
    @ApiModelProperty(value = "项目类型")
    private List<DropDownVO> borrowProjectTypeList;

    @ApiModelProperty(value = "还款方式")
    private List<DropDownVO> borrowStyleList;

    @ApiModelProperty(value = "备案状态")
    private List<DropDownVO> borrowRegistStatusList;

    @ApiModelProperty(value = "标的备案列表")
    private List<AdminBorrowRegistCustomizeVO> recordList;

    @ApiModelProperty(value = "列表统计")
    private String sumAccount;

    @ApiModelProperty(value = "总条数")
    private Integer total;

    public List<DropDownVO> getBorrowProjectTypeList() {
        return borrowProjectTypeList;
    }

    public void setBorrowProjectTypeList(List<DropDownVO> borrowProjectTypeList) {
        this.borrowProjectTypeList = borrowProjectTypeList;
    }

    public List<DropDownVO> getBorrowStyleList() {
        return borrowStyleList;
    }

    public void setBorrowStyleList(List<DropDownVO> borrowStyleList) {
        this.borrowStyleList = borrowStyleList;
    }

    public List<DropDownVO> getBorrowRegistStatusList() {
        return borrowRegistStatusList;
    }

    public void setBorrowRegistStatusList(List<DropDownVO> borrowRegistStatusList) {
        this.borrowRegistStatusList = borrowRegistStatusList;
    }

    public List<AdminBorrowRegistCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<AdminBorrowRegistCustomizeVO> recordList) {
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
