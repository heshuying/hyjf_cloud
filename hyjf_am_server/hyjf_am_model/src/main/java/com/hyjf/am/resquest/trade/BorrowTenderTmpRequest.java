/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.EmployeeCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

/**
 * @author jun
 * @version BorrowTenderTmpRequest, v0.1 2018/6/23 17:59
 */
public class BorrowTenderTmpRequest extends Request {

    private BankCallBeanVO bankCallBean;

    private String orderId;

    private Integer userId;

    private BorrowTenderTmpVO borrowTenderTmpVO;

    private UserVO user;

    private UserInfoVO userInfo;

    private boolean is51User;
    
    private BankOpenAccountVO accountChinapnrTender;
    
    private BankOpenAccountVO accountChinapnrBorrower;

    private BorrowVO borrow;

    private BorrowInfoVO borrowInfo;

    private UserVO logUser;

    private UserInfoVO logUserInfo;
    
    private EmployeeCustomizeVO employeeCustomize;
    
    private UserVO userss;
    
    private UserInfoVO refUsers;
    
    private EmployeeCustomizeVO employeeCustomize_ref;
    
	private int nowTime;

    public int getNowTime() {
        return nowTime;
    }

    public void setNowTime(int nowTime) {
        this.nowTime = nowTime;
    }

    public UserVO getLogUser() {
        return logUser;
    }

    public void setLogUser(UserVO logUser) {
        this.logUser = logUser;
    }

    public BorrowInfoVO getBorrowInfo() {
        return borrowInfo;
    }

    public void setBorrowInfo(BorrowInfoVO borrowInfo) {
        this.borrowInfo = borrowInfo;
    }

    public BorrowVO getBorrow() {
        return borrow;
    }

    public void setBorrow(BorrowVO borrow) {
        this.borrow = borrow;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BorrowTenderTmpVO getBorrowTenderTmpVO() {
        return borrowTenderTmpVO;
    }

    public void setBorrowTenderTmpVO(BorrowTenderTmpVO borrowTenderTmpVO) {
        this.borrowTenderTmpVO = borrowTenderTmpVO;
    }

    public BankCallBeanVO getBankCallBean() {
        return bankCallBean;
    }

    public void setBankCallBean(BankCallBeanVO bankCallBean) {
        this.bankCallBean = bankCallBean;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUserInfo(UserInfoVO userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfoVO getUserInfo() {
        return userInfo;
    }

	public boolean getIs51User() {
		return is51User;
	}

	public void setIs51User(boolean is51User) {
		this.is51User = is51User;
	}

	public BankOpenAccountVO getAccountChinapnrTender() {
		return accountChinapnrTender;
	}

	public void setAccountChinapnrTender(BankOpenAccountVO accountChinapnrTender) {
		this.accountChinapnrTender = accountChinapnrTender;
	}

	public BankOpenAccountVO getAccountChinapnrBorrower() {
		return accountChinapnrBorrower;
	}

	public void setAccountChinapnrBorrower(BankOpenAccountVO accountChinapnrBorrower) {
		this.accountChinapnrBorrower = accountChinapnrBorrower;
	}

	public UserInfoVO getLogUserInfo() {
		return logUserInfo;
	}

	public void setLogUserInfo(UserInfoVO logUserInfo) {
		this.logUserInfo = logUserInfo;
	}

	public EmployeeCustomizeVO getEmployeeCustomize() {
		return employeeCustomize;
	}

	public void setEmployeeCustomize(EmployeeCustomizeVO employeeCustomize) {
		this.employeeCustomize = employeeCustomize;
	}

	public UserVO getUserss() {
		return userss;
	}

	public void setUserss(UserVO userss) {
		this.userss = userss;
	}

	public UserInfoVO getRefUsers() {
		return refUsers;
	}

	public void setRefUsers(UserInfoVO refUsers) {
		this.refUsers = refUsers;
	}

	public EmployeeCustomizeVO getEmployeeCustomize_ref() {
		return employeeCustomize_ref;
	}

	public void setEmployeeCustomize_ref(EmployeeCustomizeVO employeeCustomize_ref) {
		this.employeeCustomize_ref = employeeCustomize_ref;
	}
}
