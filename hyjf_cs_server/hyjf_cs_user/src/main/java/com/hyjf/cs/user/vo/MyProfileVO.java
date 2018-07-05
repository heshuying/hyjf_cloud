package com.hyjf.cs.user.vo;

import java.math.BigDecimal;

/**
 * 账户总览接口返回数据结构
 * Created by cuigq on 2018/2/1.
 */
public class MyProfileVO {

    private UserAccountInfo userAccountInfo;//用户账户信息

    private BigDecimal bankBalance;  //可用余额

    private BigDecimal accountTotle; // 账户总资产

    private BigDecimal waitInterest; // 待收收益

    private BigDecimal interestTotle; // 累计收益

    private BigDecimal chinapnrBalance; //汇付账户余额（非汇付用户时无效）

    private Integer couponValidCount; //我的优惠卷

    private BigDecimal bankAwait; //我的散标

    private BigDecimal planAccountWait; //我的计划

    public UserAccountInfo getUserAccountInfo() {
        return userAccountInfo;
    }

    public void setUserAccountInfo(UserAccountInfo userAccountInfo) {
        this.userAccountInfo = userAccountInfo;
    }

    public BigDecimal getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(BigDecimal bankBalance) {
        this.bankBalance = bankBalance;
    }

    public BigDecimal getAccountTotle() {
        return accountTotle;
    }

    public void setAccountTotle(BigDecimal accountTotle) {
        this.accountTotle = accountTotle;
    }

    public BigDecimal getWaitInterest() {
        return waitInterest;
    }

    public void setWaitInterest(BigDecimal waitInterest) {
        this.waitInterest = waitInterest;
    }

    public BigDecimal getInterestTotle() {
        return interestTotle;
    }

    public void setInterestTotle(BigDecimal interestTotle) {
        this.interestTotle = interestTotle;
    }

    public BigDecimal getChinapnrBalance() {
        return chinapnrBalance;
    }

    public void setChinapnrBalance(BigDecimal chinapnrBalance) {
        this.chinapnrBalance = chinapnrBalance;
    }

    public Integer getCouponValidCount() {
        return couponValidCount;
    }

    public void setCouponValidCount(Integer couponValidCount) {
        this.couponValidCount = couponValidCount;
    }

    public BigDecimal getBankAwait() {
        return bankAwait;
    }

    public void setBankAwait(BigDecimal bankAwait) {
        this.bankAwait = bankAwait;
    }

    public BigDecimal getPlanAccountWait() {
        return planAccountWait;
    }

    public void setPlanAccountWait(BigDecimal planAccountWait) {
        this.planAccountWait = planAccountWait;
    }

    //用户账户信息
    public class UserAccountInfo {

        //"张先生" "王女士"
        private String trueUserName;

        //用户名
        private String userName;

        //汇付天下用户
        private boolean chinapnrUser;
        //银行托管用户
        private boolean bankUser;
        //是否设置交易密码
        private boolean setPassword;
        //是否绑定邮箱
        private boolean setEmail;
        //是否测评过
        private String evaluated;
        // 是否服务费授权
        private Integer paymentAuthStatus;
        

        //头像图片URL
        private String iconUrl;

        //分享链接
        private String qrcodeUrl;

        //邀請碼
        private Integer invitationCode;
        
        //是否绑卡
        private boolean isBindCard;


        public String getTrueUserName() {
            return trueUserName;
        }

        public void setTrueUserName(String trueUserName) {
            this.trueUserName = trueUserName;
        }

        public boolean isChinapnrUser() {
            return chinapnrUser;
        }

        public void setChinapnrUser(boolean chinapnrUser) {
            this.chinapnrUser = chinapnrUser;
        }

        public boolean isBankUser() {
            return bankUser;
        }

        public void setBankUser(boolean bankUser) {
            this.bankUser = bankUser;
        }

        public boolean isSetPassword() {
            return setPassword;
        }

        public void setSetPassword(boolean setPassword) {
            this.setPassword = setPassword;
        }

        public boolean isSetEmail() {
            return setEmail;
        }

        public void setSetEmail(boolean setEmail) {
            this.setEmail = setEmail;
        }

        public String isEvaluated() {
            return evaluated;
        }

        public void setEvaluated(String evaluated) {
            this.evaluated = evaluated;
        }

		public Integer getPaymentAuthStatus() {
			return paymentAuthStatus;
		}

		public void setPaymentAuthStatus(Integer paymentAuthStatus) {
			this.paymentAuthStatus = paymentAuthStatus;
		}

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getQrcodeUrl() {
            return qrcodeUrl;
        }

        public void setQrcodeUrl(String qrcodeUrl) {
            this.qrcodeUrl = qrcodeUrl;
        }

        public Integer getInvitationCode() {
            return invitationCode;
        }

        public void setInvitationCode(Integer invitationCode) {
            this.invitationCode = invitationCode;
        }

		public boolean getIsBindCard() {
			return isBindCard;
		}

		public void setIsBindCard(boolean isBindCard) {
			this.isBindCard = isBindCard;
		}

    }
}
