/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.AdminBorrowFirstCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowFirstResponseBean, v0.1 2018/7/5 15:36
 */
public class BorrowFirstResponseBean {
    @ApiModelProperty(value = "资产来源下拉框")
    private List<HjhInstConfigVO> hjhInstConfigList;

    @ApiModelProperty(value = "初审状态下拉框")
    private Map<String, String> borrowStatusList;

    @ApiModelProperty(value = "初审列表")
    private List<AdminBorrowFirstCustomizeVO> recordList;

    @ApiModelProperty(value = "列表统计")
    private String sumAccount;

    @ApiModelProperty(value = "总条数")
    private Integer total;

    public List<HjhInstConfigVO> getHjhInstConfigList() {
        return hjhInstConfigList;
    }

    public void setHjhInstConfigList(List<HjhInstConfigVO> hjhInstConfigList) {
        this.hjhInstConfigList = hjhInstConfigList;
    }

    public Map<String, String> getBorrowStatusList() {
        return borrowStatusList;
    }

    public void setBorrowStatusList(Map<String, String> borrowStatusList) {
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
