/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version BorrowProjectTypeVO, v0.1 2018/6/12 17:49
 */
public class BorrowProjectTypeVO extends BaseVO implements Serializable {
    private Integer id;

    private String borrowProjectType;

    private String borrowCd;

    private String borrowName;

    private String borrowClass;

    private Integer investUserType;

    private Integer status;

    private String investStart;

    private String investEnd;

    private String remark;

    private String createGroupId;

    private Integer createUserId;

    private Date createTime;

    private String updateGroupId;

    private Integer updateUserId;

    private Date updateTime;
    private Long increaseMoney;

    private Integer interestCoupon;

    private Integer tasteMoney;
    //modifyFlag
    private String modifyFlag;

    private String repayName;
    //modifyFlag
    private List<BorrowProjectRepayVO> repayNames;
    // 回显checkbox标签
    private  List<BorrowStyleVO> repayStyles;
    // 用户角色
    private  List<ParamNameVO> investUsers;
    // 获取数据字典表的下拉列表
    private List<ParamNameVO> projectTypeList;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowProjectType() {
        return borrowProjectType;
    }

    public void setBorrowProjectType(String borrowProjectType) {
        this.borrowProjectType = borrowProjectType == null ? null : borrowProjectType.trim();
    }

    public String getBorrowCd() {
        return borrowCd;
    }

    public void setBorrowCd(String borrowCd) {
        this.borrowCd = borrowCd == null ? null : borrowCd.trim();
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName == null ? null : borrowName.trim();
    }

    public String getBorrowClass() {
        return borrowClass;
    }

    public void setBorrowClass(String borrowClass) {
        this.borrowClass = borrowClass == null ? null : borrowClass.trim();
    }

    public Integer getInvestUserType() {
        return investUserType;
    }

    public void setInvestUserType(Integer investUserType) {
        this.investUserType = investUserType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInvestStart() {
        return investStart;
    }

    public void setInvestStart(String investStart) {
        this.investStart = investStart == null ? null : investStart.trim();
    }

    public String getInvestEnd() {
        return investEnd;
    }

    public void setInvestEnd(String investEnd) {
        this.investEnd = investEnd == null ? null : investEnd.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateGroupId() {
        return createGroupId;
    }

    public void setCreateGroupId(String createGroupId) {
        this.createGroupId = createGroupId == null ? null : createGroupId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateGroupId() {
        return updateGroupId;
    }

    public void setUpdateGroupId(String updateGroupId) {
        this.updateGroupId = updateGroupId == null ? null : updateGroupId.trim();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getIncreaseMoney() {
        return increaseMoney;
    }

    public void setIncreaseMoney(Long increaseMoney) {
        this.increaseMoney = increaseMoney;
    }

    public Integer getInterestCoupon() {
        return interestCoupon;
    }

    public void setInterestCoupon(Integer interestCoupon) {
        this.interestCoupon = interestCoupon;
    }

    public Integer getTasteMoney() {
        return tasteMoney;
    }

    public void setTasteMoney(Integer tasteMoney) {
        this.tasteMoney = tasteMoney;
    }

    public String getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(String modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    public List<BorrowProjectRepayVO> getRepayNames() {
        return repayNames;
    }

    public void setRepayNames(List<BorrowProjectRepayVO> repayNames) {
        this.repayNames = repayNames;
    }

    public List<BorrowStyleVO> getRepayStyles() {
        return repayStyles;
    }

    public void setRepayStyles(List<BorrowStyleVO> repayStyles) {
        this.repayStyles = repayStyles;
    }

    public List<ParamNameVO> getInvestUsers() {
        return investUsers;
    }

    public void setInvestUsers(List<ParamNameVO> investUsers) {
        this.investUsers = investUsers;
    }

    public List<ParamNameVO> getProjectTypeList() {
        return projectTypeList;
    }

    public void setProjectTypeList(List<ParamNameVO> projectTypeList) {
        this.projectTypeList = projectTypeList;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getRepayName() {
        return repayName;
    }

    public void setRepayName(String repayName) {
        this.repayName = repayName;
    }
}
