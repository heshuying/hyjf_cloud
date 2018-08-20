/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.vo.admin.BankAccountManageCustomizeVO;
import com.hyjf.common.paginator.Paginator;

import java.io.Serializable;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version BankAccountManageBean, v0.1 2018/7/20 16:50
 */
public class BankAccountManageBean implements Serializable {

    List<DropDownVO> departmentList;

    List<DropDownVO> vipList;

    List<BankAccountManageCustomizeVO> recordList;

    /**
     * 用户名
     */
    private String userNameSrch;
    /**
     * 部门
     */
    private String combotreeSrch;
    /**
     * 部门
     */
    private String[] combotreeListSrch;

    /**
     * 电子账号(检索用)
     */
    private String accountSrch;

    /**
     * 会员等级(检索用)
     */
    private String vipSrch;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;

    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public List<BankAccountManageCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BankAccountManageCustomizeVO> recordList) {
        this.recordList = recordList;
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

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getCombotreeSrch() {
        return combotreeSrch;
    }

    public void setCombotreeSrch(String combotreeSrch) {
        this.combotreeSrch = combotreeSrch;
    }

    public String[] getCombotreeListSrch() {
        return combotreeListSrch;
    }

    public void setCombotreeListSrch(String[] combotreeListSrch) {
        this.combotreeListSrch = combotreeListSrch;
    }

    public String getAccountSrch() {
        return accountSrch;
    }

    public void setAccountSrch(String accountSrch) {
        this.accountSrch = accountSrch;
    }

    public String getVipSrch() {
        return vipSrch;
    }

    public void setVipSrch(String vipSrch) {
        this.vipSrch = vipSrch;
    }

    public List<DropDownVO> getDepartmentList() { return departmentList; }

    public void setDepartmentList(List<DropDownVO> departmentList) {
        this.departmentList = departmentList;
    }

    public List<DropDownVO> getVipList() {
        return vipList;
    }

    public void setVipList(List<DropDownVO> vipList) {
        this.vipList = vipList;
    }
}
