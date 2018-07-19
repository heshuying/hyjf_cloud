/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.admin.BorrowFullCustomizeVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 借款复审列表返回类
 *
 * @author wangjun
 * @version BorrowFullResponseBean, v0.1 2018/7/6 9:52
 */
public class BorrowFullResponseBean {
    @ApiModelProperty(value = "复审列表")
    private List<BorrowFullCustomizeVO> recordList;

    @ApiModelProperty(value = "列表统计")
    private Map<String, String> sumAccount;

    @ApiModelProperty(value = "总条数")
    private Integer total;

    public List<BorrowFullCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowFullCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public Map<String, String> getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(Map<String, String> sumAccount) {
        this.sumAccount = sumAccount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
