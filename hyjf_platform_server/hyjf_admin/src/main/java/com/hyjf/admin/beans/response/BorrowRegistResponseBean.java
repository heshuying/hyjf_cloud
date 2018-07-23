/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.AdminBorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowRegistResponseBean, v0.1 2018/7/5 18:24
 */
public class BorrowRegistResponseBean {
    @ApiModelProperty(value = "项目类型")
    List<BorrowProjectTypeVO> borrowProjectTypeList;

    @ApiModelProperty(value = "还款方式")
    List<BorrowStyleVO> borrowStyleList;

    @ApiModelProperty(value = "备案状态")
    private Map<String, String> borrowRegistStatusList;

    @ApiModelProperty(value = "标的备案列表")
    private List<AdminBorrowRegistCustomizeVO> recordList;

    @ApiModelProperty(value = "列表统计")
    private String sumAccount;

    @ApiModelProperty(value = "总条数")
    private Integer total;

    public List<BorrowProjectTypeVO> getBorrowProjectTypeList() {
        return borrowProjectTypeList;
    }

    public void setBorrowProjectTypeList(List<BorrowProjectTypeVO> borrowProjectTypeList) {
        this.borrowProjectTypeList = borrowProjectTypeList;
    }

    public List<BorrowStyleVO> getBorrowStyleList() {
        return borrowStyleList;
    }

    public void setBorrowStyleList(List<BorrowStyleVO> borrowStyleList) {
        this.borrowStyleList = borrowStyleList;
    }

    public Map<String, String> getBorrowRegistStatusList() {
        return borrowRegistStatusList;
    }

    public void setBorrowRegistStatusList(Map<String, String> borrowRegistStatusList) {
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
