package com.hyjf.am.vo.datacollect;

import java.math.BigDecimal;

/**
 * @author lisheng
 * @version UserOperateListVO, v0.1 2019/3/15 15:09
 */

public class UserOperateListVO {
    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 金额
     *
     * @mbggenerated
     */
    private BigDecimal money;


    /**
     * 用户行为 1:投资,2:充值,3:回款,4提现
     *
     * @mbggenerated
     */
    private Integer operating;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getOperating() {
        return operating;
    }

    public void setOperating(Integer operating) {
        this.operating = operating;
    }
}
