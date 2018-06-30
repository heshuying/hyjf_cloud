package com.hyjf.am.vo.user;

import java.io.Serializable;

public class MspShixininfosVO implements Serializable {
    private Integer id;

    private String applyId;

    private String shixinId;

    private String name;

    private String papernum;

    private String anlinum;

    private String beizhixingrenlvxingstatus;

    private String jutistatus;

    private String liantime;

    private String province;

    private String publictime;

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