package com.hyjf.cs.user.bean;

public class AuthBean extends BaseBean {

    //自动投标授权
    public static final String AUTH_TYPE_AUTO_BID = "autoBid";
    //自动债转授权
    public static final String AUTH_TYPE_AUTO_CREDIT = "autoCredit";
    //服务费授权
    public static final String AUTH_TYPE_PAYMENT_AUTH = "paymentAuth";
    //还款授权
    public static final String AUTH_TYPE_REPAY_AUTH = "repayAuth";
    //合并授权（自动投标、自动债转、服务费授权）三合一
    public static final String AUTH_TYPE_MERGE_AUTH = "mergeAuth";
    //（服务费授权、还款授权）二合一
    public static final String AUTH_TYPE_PAY_REPAY_AUTH = "payRepayAuth";
	
	public static final String AUTH_START_OPEN = "1";
	public static final String AUTH_START_CLOSE = "0";
	
    // 请求银行类型
    private String TxCode;
    // 用户id
    private  Integer userId;
    
    private  String ip;
    
    private  String channel;
    // 同步失败地址
    private  String retUrl;
    // 同步成功地址
    private String successUrl;
    // 异步地址
    private  String notifyUrl;
    // 忘记密码
    private  String forgotPwdUrl;
    // 哪个平台发起的
    private  String platform;
    
    private  String orderId;
    
    private  String accountId;
    //授权类型
    private  String authType;
    //用户名
    private  String name;
    //身份证号码
    private  String idNo;
    //身份属性角色
    private  String identity;
    //用户类型 0普通用户 1企业用户
    private Integer userType;

    //wjt
    private  String wjtClient;
    
    //自动出借授权标识
    private  boolean autoBidStatus=false;
    //自动债转授权标识
    private  boolean autoCreditStatus=false;
    //缴费授权标识
    private  boolean paymentAuthStatus=false;
    //还款授权标识
    private  boolean repayAuthAuthStatus=false;
    
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getTxCode() {
        return TxCode;
    }
    public void setTxCode(String txCode) {
        TxCode = txCode;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getRetUrl() {
        return retUrl;
    }
    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }
    public String getNotifyUrl() {
        return notifyUrl;
    }
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getForgotPwdUrl() {
        return forgotPwdUrl;
    }
    public void setForgotPwdUrl(String forgotPwdUrl) {
        this.forgotPwdUrl = forgotPwdUrl;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public boolean getAutoBidStatus() {
		return autoBidStatus;
	}
	public void setAutoBidStatus(boolean autoBidStatus) {
		this.autoBidStatus = autoBidStatus;
	}
	public boolean getAutoCreditStatus() {
		return autoCreditStatus;
	}
	public void setAutoCreditStatus(boolean autoCreditStatus) {
		this.autoCreditStatus = autoCreditStatus;
	}
	public boolean getPaymentAuthStatus() {
		return paymentAuthStatus;
	}
	public void setPaymentAuthStatus(boolean paymentAuthStatus) {
		this.paymentAuthStatus = paymentAuthStatus;
	}
    public String getSuccessUrl() {
        return successUrl;
    }
    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }
    public boolean getRepayAuthAuthStatus() {
        return repayAuthAuthStatus;
    }
    public void setRepayAuthAuthStatus(boolean repayAuthAuthStatus) {
        this.repayAuthAuthStatus = repayAuthAuthStatus;
    }

    public String getWjtClient() {
        return wjtClient;
    }

    public void setWjtClient(String wjtClient) {
        this.wjtClient = wjtClient;
    }
}
