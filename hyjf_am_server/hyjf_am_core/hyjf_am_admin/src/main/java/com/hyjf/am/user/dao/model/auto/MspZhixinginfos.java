package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class MspZhixinginfos implements Serializable {
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
    private String zguxubgId;

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
     * 执行法院
     *
     * @mbggenerated
     */
    private String zhixingcourt;

    /**
     * 案例号
     *
     * @mbggenerated
     */
    private String anlinum;

    /**
     * 案件状态
     *
     * @mbggenerated
     */
    private String anjianstate;

    /**
     * 执行标的
     *
     * @mbggenerated
     */
    private String zhixingtaget;

    /**
     * 立案时间
     *
     * @mbggenerated
     */
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