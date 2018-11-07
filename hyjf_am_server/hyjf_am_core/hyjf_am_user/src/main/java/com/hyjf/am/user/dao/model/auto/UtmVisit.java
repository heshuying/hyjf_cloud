package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class UtmVisit implements Serializable {
    private Integer id;

    /**
     * 推广链接id
     *
     * @mbggenerated
     */
    private Integer utmId;

    /**
     * 访问ip
     *
     * @mbggenerated
     */
    private String ip;

    /**
     * 操作系统
     *
     * @mbggenerated
     */
    private String os;

    /**
     * 浏览器
     *
     * @mbggenerated
     */
    private String browser;

    /**
     * 国家
     *
     * @mbggenerated
     */
    private String country;

    /**
     * 省份
     *
     * @mbggenerated
     */
    private String province;

    /**
     * 城市
     *
     * @mbggenerated
     */
    private String city;

    /**
     * 是否登录 0未登录 1登录
     *
     * @mbggenerated
     */
    private Integer login;

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

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUtmId() {
        return utmId;
    }

    public void setUtmId(Integer utmId) {
        this.utmId = utmId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os == null ? null : os.trim();
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser == null ? null : browser.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
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