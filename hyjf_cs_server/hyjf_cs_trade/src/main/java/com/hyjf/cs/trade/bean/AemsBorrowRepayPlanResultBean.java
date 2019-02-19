/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.trade.AemsBorrowRepayPlanCustomizeVO;

import java.util.List;

/**
 * AEMS系统:查询还款计划返回Bean
 *
 * @author liuyang
 * @version AemsBorrowRepayPlanResultBean, v0.1 2018/12/12 10:36
 */
public class AemsBorrowRepayPlanResultBean extends BaseResultBean {
    private List<AemsBorrowRepayPlanCustomizeVO> detailList;

    private Integer totalCounts;

    public List<AemsBorrowRepayPlanCustomizeVO> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<AemsBorrowRepayPlanCustomizeVO> detailList) {
        this.detailList = detailList;
    }

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }
}
