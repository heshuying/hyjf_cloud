package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.trade.RepaymentPlanVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lisheng
 * @version ScreenDataBean, v0.1 2019/3/18 14:08
 */

public class ScreenDataBean {

    private Integer id;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;
    /**
     * 用户id集合
     *
     * @mbggenerated
     */
    private List<Integer> userIds;


    List<RepaymentPlanVO> repaymentPlanVOS;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 当前拥有人(坐席姓名)
     *
     * @mbggenerated
     */
    private String currentOwner;

    /**
     * 坐席分组 0:其他,1:新客组,2:老客组,3:惠众
     *
     * @mbggenerated
     */
    private Integer customerGroup;

    /**
     * 金额
     *
     * @mbggenerated
     */
    private BigDecimal money;

    /**
     * 年化金额
     *
     * @mbggenerated
     */
    private BigDecimal yearMoney;

    /**
     * 站岗资金
     *
     * @mbggenerated
     */
    private BigDecimal balance;

    /**
     * 用户行为 1:投资,2:充值,3:回款,4提现
     *
     * @mbggenerated
     */
    private Integer operating;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     *订单id
     */
    String orderId;
    /**
     *投资类型
     */
    Integer productType;

    public ScreenDataBean(Integer userId, String userName, BigDecimal money, Integer operating) {
        this.userId = userId;
        this.userName = userName;
        this.money = money;
        this.operating = operating;
    }

    public ScreenDataBean() {
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner == null ? null : currentOwner.trim();
    }

    public Integer getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(Integer customerGroup) {
        this.customerGroup = customerGroup;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getYearMoney() {
        return yearMoney;
    }

    public void setYearMoney(BigDecimal yearMoney) {
        this.yearMoney = yearMoney;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getOperating() {
        return operating;
    }

    public void setOperating(Integer operating) {
        this.operating = operating;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public List<RepaymentPlanVO> getRepaymentPlanVOS() {
        return repaymentPlanVOS;
    }

    public void setRepaymentPlanVOS(List<RepaymentPlanVO> repaymentPlanVOS) {
        this.repaymentPlanVOS = repaymentPlanVOS;
    }
}
