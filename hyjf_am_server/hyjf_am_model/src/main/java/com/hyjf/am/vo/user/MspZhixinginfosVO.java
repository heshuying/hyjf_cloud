package com.hyjf.am.vo.user;

import java.io.Serializable;

public class MspZhixinginfosVO implements Serializable {
    private Integer id;

    private String applyId;

    private String zguxubgId;

    private String name;

    private String papernum;

    private String zhixingcourt;

    private String anlinum;

    private String anjianstate;

    private String zhixingtaget;

    private String liantime;

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

    public String getZguxubgId() {
        return zguxubgId;
    }

    public void setZguxubgId(String zguxubgId) {
        this.zguxubgId = zguxubgId == null ? null : zguxubgId.trim();
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

    public String getZhixingcourt() {
        return zhixingcourt;
    }

    public void setZhixingcourt(String zhixingcourt) {
        this.zhixingcourt = zhixingcourt == null ? null : zhixingcourt.trim();
    }

    public String getAnlinum() {
        return anlinum;
    }

    public void setAnlinum(String anlinum) {
        this.anlinum = anlinum == null ? null : anlinum.trim();
    }

    public String getAnjianstate() {
        return anjianstate;
    }

    public void setAnjianstate(String anjianstate) {
        this.anjianstate = anjianstate == null ? null : anjianstate.trim();
    }

    public String getZhixingtaget() {
        return zhixingtaget;
    }

    public void setZhixingtaget(String zhixingtaget) {
        this.zhixingtaget = zhixingtaget == null ? null : zhixingtaget.trim();
    }

    public String getLiantime() {
        return liantime;
    }

    public void setLiantime(String liantime) {
        this.liantime = liantime == null ? null : liantime.trim();
    }
}