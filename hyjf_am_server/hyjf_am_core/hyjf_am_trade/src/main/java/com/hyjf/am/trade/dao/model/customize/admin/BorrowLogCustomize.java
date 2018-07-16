package com.hyjf.am.trade.dao.model.customize.admin;

/**
 * @author pangchengchao
 * @version BorrowLogCustomize, v0.1 2018/7/11 10:39
 */
public class BorrowLogCustomize {
    /**
     * 借款编码
     */
    private String borrowNid;

    /**
     * 状态
     */
    private String borrowStatus;

    /**
     * 修改类型-创建标的-新增-修改-删除
     */
    private String type;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建用户
     */
    private String createUserName;

    private int delFlg;
    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public int getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(int delFlg) {
        this.delFlg = delFlg;
    }
}
