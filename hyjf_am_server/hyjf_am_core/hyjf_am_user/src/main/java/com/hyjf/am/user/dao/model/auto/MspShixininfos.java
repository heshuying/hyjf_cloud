package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class MspShixininfos implements Serializable {
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
    private String shixinId;

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
     * 案号
     *
     * @mbggenerated
     */
    private String anlinum;

    /**
     * 被执行人的履行情况
     *
     * @mbggenerated
     */
    private String beizhixingrenlvxingstatus;

    /**
     * 失信被执行人行为具体情形
     *
     * @mbggenerated
     */
    private String jutistatus;

    /**
     * 立案时间
     *
     * @mbggenerated
     */
    private String liantime;

    /**
     * 省份
     *
     * @mbggenerated
     */
    private String province;

    /**
     * 发布时间
     *
     * @mbggenerated
     */
    private String publictime;

    /**
     * 执行法院
     *
     * @mbggenerated
     */
    private String zhixingcourt;

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

    public String getShixinId() {
        return shixinId;
    }

    public void setShixinId(String shixinId) {
        this.shixinId = shixinId == null ? null : shixinId.trim();
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

    public String getAnlinum() {
        return anlinum;
    }

    public void setAnlinum(String anlinum) {
        this.anlinum = anlinum == null ? null : anlinum.trim();
    }

    public String getBeizhixingrenlvxingstatus() {
        return beizhixingrenlvxingstatus;
    }

    public void setBeizhixingrenlvxingstatus(String beizhixingrenlvxingstatus) {
        this.beizhixingrenlvxingstatus = beizhixingrenlvxingstatus == null ? null : beizhixingrenlvxingstatus.trim();
    }

    public String getJutistatus() {
        return jutistatus;
    }

    public void setJutistatus(String jutistatus) {
        this.jutistatus = jutistatus == null ? null : jutistatus.trim();
    }

    public String getLiantime() {
        return liantime;
    }

    public void setLiantime(String liantime) {
        this.liantime = liantime == null ? null : liantime.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getPublictime() {
        return publictime;
    }

    public void setPublictime(String publictime) {
        this.publictime = publictime == null ? null : publictime.trim();
    }

    public String getZhixingcourt() {
        return zhixingcourt;
    }

    public void setZhixingcourt(String zhixingcourt) {
        this.zhixingcourt = zhixingcourt == null ? null : zhixingcourt.trim();
    }
}