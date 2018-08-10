package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.trade.ProjectInvestListVO;

import java.io.Serializable;
import java.util.List;

/**
 * 投资记录返回值类型
 * @author zhangyk
 * @date 2018/8/9 17:08
 */
public class WebBorrowInvestResult implements Serializable {


    private List<ProjectInvestListVO> projectInvestList;

    private String investTotal;

    private String investTimes;


    public List<ProjectInvestListVO> getProjectInvestList() {
        return projectInvestList;
    }

    public void setProjectInvestList(List<ProjectInvestListVO> projectInvestList) {
        this.projectInvestList = projectInvestList;
    }

    public String getInvestTotal() {
        return investTotal;
    }

    public void setInvestTotal(String investTotal) {
        this.investTotal = investTotal;
    }

    public String getInvestTimes() {
        return investTimes;
    }

    public void setInvestTimes(String investTimes) {
        this.investTimes = investTimes;
    }
}
