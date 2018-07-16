package com.hyjf.am.vo.datacollect;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

/**
 * 运营统计数据vo
 * @author zhangyk
 * @date 2018/7/14 16:16
 */
public class TotalInvestAndInterestVO extends BaseVO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String id;

    // 累计投资总额
    private BigDecimal totalInvestAmount;
    // 累计投资收益
    private BigDecimal totalInterestAmount;
    // 累计投资总数
    private int totalInvestNum;
    // 计划累计投资总额
    private BigDecimal hjhTotalInvestAmount;
    // 计划累计投资收益
    private BigDecimal hjhTotalInterestAmount;
    // 计划累计投资总数
    private int hjhTotalInvestNum;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getTotalInvestAmount() {
        return totalInvestAmount;
    }

    public void setTotalInvestAmount(BigDecimal totalInvestAmount) {
        this.totalInvestAmount = totalInvestAmount;
    }

    public BigDecimal getTotalInterestAmount() {
        return totalInterestAmount;
    }

    public void setTotalInterestAmount(BigDecimal totalInterestAmount) {
        this.totalInterestAmount = totalInterestAmount;
    }

    public int getTotalInvestNum() {
        return totalInvestNum;
    }

    public void setTotalInvestNum(int totalInvestNum) {
        this.totalInvestNum = totalInvestNum;
    }

    public BigDecimal getHjhTotalInvestAmount() {
        return hjhTotalInvestAmount;
    }

    public void setHjhTotalInvestAmount(BigDecimal hjhTotalInvestAmount) {
        this.hjhTotalInvestAmount = hjhTotalInvestAmount;
    }

    public BigDecimal getHjhTotalInterestAmount() {
        return hjhTotalInterestAmount;
    }

    public void setHjhTotalInterestAmount(BigDecimal hjhTotalInterestAmount) {
        this.hjhTotalInterestAmount = hjhTotalInterestAmount;
    }

    public int getHjhTotalInvestNum() {
        return hjhTotalInvestNum;
    }

    public void setHjhTotalInvestNum(int hjhTotalInvestNum) {
        this.hjhTotalInvestNum = hjhTotalInvestNum;
    }
}
