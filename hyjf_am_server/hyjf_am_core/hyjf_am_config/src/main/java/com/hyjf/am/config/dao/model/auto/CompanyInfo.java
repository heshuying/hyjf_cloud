package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class CompanyInfo implements Serializable {
    private Integer id;

    private String name;

    private String provence;

    private String city;

    private String registrationTime;

    private String brief;

    private String tel;

    private String banner;

    private String regImg1;

    private String regImg2;

    private String regImg3;

    private String regImg4;

    private String regImg5;

    private String regImg6;

    private Integer qq;

    private String weibo;

    private String email;

    private String weixin;

    private Integer status;

    private String regMoney;

    private Integer sort;

    private String listImg;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProvence() {
        return provence;
    }

    public void setProvence(String provence) {
        this.provence = provence == null ? null : provence.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime == null ? null : registrationTime.trim();
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner == null ? null : banner.trim();
    }

    public String getRegImg1() {
        return regImg1;
    }

    public void setRegImg1(String regImg1) {
        this.regImg1 = regImg1 == null ? null : regImg1.trim();
    }

    public String getRegImg2() {
        return regImg2;
    }

    public void setRegImg2(String regImg2) {
        this.regImg2 = regImg2 == null ? null : regImg2.trim();
    }

    public String getRegImg3() {
        return regImg3;
    }

    public void setRegImg3(String regImg3) {
        this.regImg3 = regImg3 == null ? null : regImg3.trim();
    }

    public String getRegImg4() {
        return regImg4;
    }

    public void setRegImg4(String regImg4) {
        this.regImg4 = regImg4 == null ? null : regImg4.trim();
    }

    public String getRegImg5() {
        return regImg5;
    }

    public void setRegImg5(String regImg5) {
        this.regImg5 = regImg5 == null ? null : regImg5.trim();
    }

    public String getRegImg6() {
        return regImg6;
    }

    public void setRegImg6(String regImg6) {
        this.regImg6 = regImg6 == null ? null : regImg6.trim();
    }

    public Integer getQq() {
        return qq;
    }

    public void setQq(Integer qq) {
        this.qq = qq;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo == null ? null : weibo.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRegMoney() {
        return regMoney;
    }

    public void setRegMoney(String regMoney) {
        this.regMoney = regMoney == null ? null : regMoney.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getListImg() {
        return listImg;
    }

    public void setListImg(String listImg) {
        this.listImg = listImg == null ? null : listImg.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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
}