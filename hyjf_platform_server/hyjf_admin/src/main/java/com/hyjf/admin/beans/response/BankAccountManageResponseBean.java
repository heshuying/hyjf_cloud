/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.AdminBorrowRegistCustomizeVO;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageResponseBean, v0.1 2018/8/23 18:17
 */
public class BankAccountManageResponseBean {

    @ApiModelProperty(value = "部门下拉框")
    private List<DropDownVO> departmentList = new ArrayList<>();

    @ApiModelProperty(value = "会员等级下拉框")
    private List<DropDownVO> vipList = new ArrayList<>();

    @ApiModelProperty(value = "银行账户管理列表")
    private List<BankAccountManageCustomizeVO> recordList = new ArrayList<>();

    @ApiModelProperty(value = "总条数")
    private Integer total;

    public List<DropDownVO> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<DropDownVO> departmentList) {
        this.departmentList = departmentList;
    }

    public List<DropDownVO> getVipList() {
        return vipList;
    }

    public void setVipList(List<DropDownVO> vipList) {
        this.vipList = vipList;
    }

    public List<BankAccountManageCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BankAccountManageCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
