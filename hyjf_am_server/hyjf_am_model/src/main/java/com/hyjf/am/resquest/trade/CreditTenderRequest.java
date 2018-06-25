package com.hyjf.am.resquest.trade;

import com.hyjf.am.response.user.EmployeeCustomizeResponse;
import com.hyjf.am.resquest.Request;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.SpreadsUserVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * @author jun
 * @since 20180621
 */
public class CreditTenderRequest extends Request {

    private String assignNid;
    private Integer userId;
    private String authCode;

    private BankOpenAccountVO sellerBankAccount;
    private BankOpenAccountVO assignBankAccount;

    private UserVO webUser;
    private UserInfoVO userInfo;

    private UserInfoCustomizeVO userInfoCustomizeRefCj;

    private UserInfoCustomizeVO userInfoCustomizeRefCr;

    private SpreadsUserVO spreadsUsers;

    private UserInfoCustomizeVO userInfoCustomize;

    private SpreadsUserVO spreadsUsersSeller;

    private UserInfoCustomizeVO userInfoCustomizeSeller;

    private EmployeeCustomizeResponse employeeCustomizeResponse;

    private int nowTime;

    private List<CreditTenderLogVO> creditTenderLogs;

    private List<BorrowCreditVO> borrowCreditList;

    public List<BorrowCreditVO> getBorrowCreditList() {
        return borrowCreditList;
    }

    public void setBorrowCreditList(List<BorrowCreditVO> borrowCreditList) {
        this.borrowCreditList = borrowCreditList;
    }

    public List<CreditTenderLogVO> getCreditTenderLogs() {
        return creditTenderLogs;
    }

    public void setCreditTenderLogs(List<CreditTenderLogVO> creditTenderLogs) {
        this.creditTenderLogs = creditTenderLogs;
    }

    public int getNowTime() {
        return nowTime;
    }

    public void setNowTime(int nowTime) {
        this.nowTime = nowTime;
    }

    public UserInfoCustomizeVO getUserInfoCustomizeSeller() {
        return userInfoCustomizeSeller;
    }

    public void setUserInfoCustomizeSeller(UserInfoCustomizeVO userInfoCustomizeSeller) {
        this.userInfoCustomizeSeller = userInfoCustomizeSeller;
    }

    public SpreadsUserVO getSpreadsUsersSeller() {
        return spreadsUsersSeller;
    }

    public void setSpreadsUsersSeller(SpreadsUserVO spreadsUsersSeller) {
        this.spreadsUsersSeller = spreadsUsersSeller;
    }

    public UserInfoCustomizeVO getUserInfoCustomize() {
        return userInfoCustomize;
    }

    public void setUserInfoCustomize(UserInfoCustomizeVO userInfoCustomize) {
        this.userInfoCustomize = userInfoCustomize;
    }

    public SpreadsUserVO getSpreadsUsers() {
        return spreadsUsers;
    }

    public void setSpreadsUsers(SpreadsUserVO spreadsUsers) {
        this.spreadsUsers = spreadsUsers;
    }

    public UserInfoCustomizeVO getUserInfoCustomizeRefCj() {
        return userInfoCustomizeRefCj;
    }

    public void setUserInfoCustomizeRefCj(UserInfoCustomizeVO userInfoCustomizeRefCj) {
        this.userInfoCustomizeRefCj = userInfoCustomizeRefCj;
    }

    public UserInfoCustomizeVO getUserInfoCustomizeRefCr() {
        return userInfoCustomizeRefCr;
    }

    public void setUserInfoCustomizeRefCr(UserInfoCustomizeVO userInfoCustomizeRefCr) {
        this.userInfoCustomizeRefCr = userInfoCustomizeRefCr;
    }

    public BankOpenAccountVO getSellerBankAccount() {
        return sellerBankAccount;
    }

    public void setSellerBankAccount(BankOpenAccountVO sellerBankAccount) {
        this.sellerBankAccount = sellerBankAccount;
    }

    public BankOpenAccountVO getAssignBankAccount() {
        return assignBankAccount;
    }

    public void setAssignBankAccount(BankOpenAccountVO assignBankAccount) {
        this.assignBankAccount = assignBankAccount;
    }

    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

	public UserVO getWebUser() {
		return webUser;
	}

	public void setWebUser(UserVO webUser) {
		this.webUser = webUser;
	}

	public UserInfoVO getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoVO userInfo) {
		this.userInfo = userInfo;
	}

	public EmployeeCustomizeResponse getEmployeeCustomizeResponse() {
		return employeeCustomizeResponse;
	}

	public void setEmployeeCustomizeResponse(EmployeeCustomizeResponse employeeCustomizeResponse) {
		this.employeeCustomizeResponse = employeeCustomizeResponse;
	}
}
