package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class MspAnliinfos implements Serializable {
    private Integer id;

    /**
     * 申请编号
     *
     * @mbggenerated
     */
    private String applyId;

    /**
     * ID号
     *
     * @mbggenerated
     */
    private String anliId;

    /**
     * 姓名
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 身份证
     *
     * @mbggenerated
     */
    private String papernum;

    /**
     * 当事人类型
     *
     * @mbggenerated
     */
    private String dangshirentype;

    /**
     * 性别
     *
     * @mbggenerated
     */
    private String sex;

    /**
     * 生日
     *
     * @mbggenerated
     */
    private String birthday;

    /**
     * 案例标题
     *
     * @mbggenerated
     */
    private String anjiantitle;

    /**
     * 审结日期
     *
     * @mbggenerated
     */
    private String enddate;

    /**
     * 案件类型
     *
     * @mbggenerated
     */
    private String anjiantype;

    /**
     * 案件字号
     *
     * @mbggenerated
     */
    private String anjiannum;

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

    public String getAnliId() {
        return anliId;
    }

    public void setAnliId(String anliId) {
        this.anliId = anliId == null ? null : anliId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPapernum() {
        return papernum;
    }

    public void setPapernum(String papernum) {
        this.papernum = papernum == null ? null : papernum.trim();
    }

    public String getDangshirentype() {
        return dangshirentype;
    }

    public void setDangshirentype(String dangshirentype) {
        this.dangshirentype = dangshirentype == null ? null : dangshirentype.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getAnjiantitle() {
        return anjiantitle;
    }

    public void setAnjiantitle(String anjiantitle) {
        this.anjiantitle = anjiantitle == null ? null : anjiantitle.trim();
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    public String getAnjiantype() {
        return anjiantype;
    }

    public void setAnjiantype(String anjiantype) {
        this.anjiantype = anjiantype == null ? null : anjiantype.trim();
    }

    public String getAnjiannum() {
        return anjiannum;
    }

    public void setAnjiannum(String anjiannum) {
        this.anjiannum = anjiannum == null ? null : anjiannum.trim();
    }
}