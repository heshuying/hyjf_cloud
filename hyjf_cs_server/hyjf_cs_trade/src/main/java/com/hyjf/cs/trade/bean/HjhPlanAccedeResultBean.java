package com.hyjf.cs.trade.bean;

import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jijun
 * @version HjhPlanAccedeResultBean
 * @date 20180727
 * 计划加入记录
 */
public class HjhPlanAccedeResultBean extends BaseResultBeanFrontEnd {

    // 加入人次
    private Integer userCount;
    // 加入金额
    private String account;
    private Boolean isEnd;

    private List<AccedeList> accedeList;

    public HjhPlanAccedeResultBean() {
        super();
        this.accedeList = new ArrayList<AccedeList>();
    }

    public static final class AccedeList {
        // 用户名
        private String userName;
        // 加入金额
        private String accedeAccount;
        // 加入时间
        private String accedeTime;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAccedeAccount() {
            return accedeAccount;
        }

        public void setAccedeAccount(String accedeAccount) {
            this.accedeAccount = accedeAccount;
        }

        public String getAccedeTime() {
            return accedeTime;
        }

        public void setAccedeTime(String accedeTime) {
            this.accedeTime = accedeTime;
        }
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Boolean isEnd() {
        return isEnd;
    }

    public void setEnd(Boolean end) {
        isEnd = end;
    }

    public List<AccedeList> getAccedeList() {
        return accedeList;
    }

    public void setAccedeList(List<AccedeList> accedeList) {
        this.accedeList = accedeList;
    }
}
