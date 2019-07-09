package com.hyjf.am.vo.screen;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ScreenTransferVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = 3803722754627032581L;

    //用户ID
    private Long userId;
    //一级部门
    private String grdfatherName;
    //三级部门
    private String deptName;
    //渠道
    private String sourceId;
    //拥有人（客服/坐席）
    private String currentOwner;
    //坐席分组
    private Integer groups;
    //用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
    private Integer attribute;

    public ScreenTransferVO() {
    }

    public ScreenTransferVO(Long userId, String grdfatherName, String deptName, String sourceId, String currentOwner, Integer groups, Integer attribute) {
        this.userId = userId;
        this.grdfatherName = grdfatherName;
        this.deptName = deptName;
        this.sourceId = sourceId;
        this.currentOwner = currentOwner;
        this.groups = groups;
        this.attribute = attribute;
    }

    public ScreenTransferVO(Long userId, String currentOwner, Integer groups) {
        this.userId = userId;
        this.currentOwner = currentOwner;
        this.groups = groups;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGrdfatherName() {
        return grdfatherName;
    }

    public void setGrdfatherName(String grdfatherName) {
        this.grdfatherName = grdfatherName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }

    public Integer getGroups() {
        return groups;
    }

    public void setGroups(Integer groups) {
        this.groups = groups;
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }
}