package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class MspBlackdata implements Serializable {
    private Integer id;

    /**
     * 申请编号
     *
     * @mbggenerated
     */
    private String applyId;

    /**
     * 报送/公开日期
     *
     * @mbggenerated
     */
    private String createdate;

    /**
     * 最近逾期开始日期
     *
     * @mbggenerated
     */
    private String lastoverduedate;

    /**
     * 借款地点
     *
     * @mbggenerated
     */
    private String creditaddress;

    /**
     * 欠款总额
     *
     * @mbggenerated
     */
    private String arrears;

    /**
     * 逾期天数
     *
     * @mbggenerated
     */
    private String overduedays;

    /**
     * 电话
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * 邮箱
     *
     * @mbggenerated
     */
    private String email;

    /**
     * 户籍地址
     *
     * @mbggenerated
     */
    private String residenceaddress;

    /**
     * 现居地址
     *
     * @mbggenerated
     */
    private String currentaddress;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate == null ? null : createdate.trim();
    }

    public String getLastoverduedate() {
        return lastoverduedate;
    }

    public void setLastoverduedate(String lastoverduedate) {
        this.lastoverduedate = lastoverduedate == null ? null : lastoverduedate.trim();
    }

    public String getCreditaddress() {
        return creditaddress;
    }

    public void setCreditaddress(String creditaddress) {
        this.creditaddress = creditaddress == null ? null : creditaddress.trim();
    }

    public String getArrears() {
        return arrears;
    }

    public void setArrears(String arrears) {
        this.arrears = arrears == null ? null : arrears.trim();
    }

    public String getOverduedays() {
        return overduedays;
    }

    public void setOverduedays(String overduedays) {
        this.overduedays = overduedays == null ? null : overduedays.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getResidenceaddress() {
        return residenceaddress;
    }

    public void setResidenceaddress(String residenceaddress) {
        this.residenceaddress = residenceaddress == null ? null : residenceaddress.trim();
    }

    public String getCurrentaddress() {
        return currentaddress;
    }

    public void setCurrentaddress(String currentaddress) {
        this.currentaddress = currentaddress == null ? null : currentaddress.trim();
    }
}