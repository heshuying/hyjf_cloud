package com.hyjf.am.vo.trade.assetmanage;

import com.hyjf.common.util.CustomConstants;

import java.util.List;

/**
 * @author fuqiang
 */
public class MyProjectResponse  {
    // 债权总数
    private int projectTotal;
    // 债权总数列表
    private List<MyProjectVo> projectList;

    // 待收总额
    private String money = "";

    private String status;

    private String statusDesc;

    private String request;

    public MyProjectResponse() {
        this.status=CustomConstants.APP_STATUS_SUCCESS;
        this.statusDesc=CustomConstants.APP_STATUS_DESC_SUCCESS;
    }

    public int getProjectTotal() {
        return projectTotal;
    }

    public void setProjectTotal(int projectTotal) {
        this.projectTotal = projectTotal;
    }

    public List<MyProjectVo> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<MyProjectVo> projectList) {
        this.projectList = projectList;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
