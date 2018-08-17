/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.AdminBorrowFullCustomizeVO;
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
    private List<AdminBorrowFullCustomizeVO> recordList;

    @ApiModelProperty(value = "列表统计(sumAccount:借款金额合计 sumBorrowAccountYes:借到金额合计 sumServiceScale:放款服务费合计)")
    private Map<String, String> sumAccount;

    @ApiModelProperty(value = "总条数")
    private Integer total;

    public List<AdminBorrowFullCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<AdminBorrowFullCustomizeVO> recordList) {
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
