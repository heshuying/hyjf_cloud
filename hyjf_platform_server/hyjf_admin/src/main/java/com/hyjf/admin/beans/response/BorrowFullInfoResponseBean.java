/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;


import com.hyjf.admin.beans.vo.AdminBorrowFullCustomizeVO;
import com.hyjf.am.vo.admin.BorrowFullCustomizeVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * 复审详细信息返回类
 * @author wangjun
 * @version BorrowFullInfoResponseBean, v0.1 2018/7/3 18:50
 */
public class BorrowFullInfoResponseBean {
    @ApiModelProperty(value = "复审详细信息")
    private BorrowFullCustomizeVO borrowFullInfo;

    @ApiModelProperty(value = "复审详细列表")
    private List<AdminBorrowFullCustomizeVO> recordList;

    @ApiModelProperty(value = "复审详细列表统计(investmentAmount:投资金额合计 loanAmount:应放款金额合计 serviceCharge:应收服务费金额合计)")
    private Map<String,String> sumAmount;

    @ApiModelProperty(value = "复审详细列表总条数")
    private Integer total;

    public BorrowFullCustomizeVO getBorrowFullInfo() {
        return borrowFullInfo;
    }

    public void setBorrowFullInfo(BorrowFullCustomizeVO borrowFullInfo) {
        this.borrowFullInfo = borrowFullInfo;
    }

    public List<AdminBorrowFullCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<AdminBorrowFullCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public Map<String, String> getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(Map<String, String> sumAmount) {
        this.sumAmount = sumAmount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
