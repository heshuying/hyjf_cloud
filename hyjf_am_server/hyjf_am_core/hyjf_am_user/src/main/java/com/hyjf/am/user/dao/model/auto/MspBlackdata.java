package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class MspBlackdata implements Serializable {
    private Integer id;

    private String applyId;

    private String createdate;

    private String lastoverduedate;

    private String creditaddress;

    private String arrears;

    private String overduedays;

    private String phone;

    private String email;

    private String residenceaddress;

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