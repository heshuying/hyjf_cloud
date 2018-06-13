package com.hyjf.cs.trade.bean;

/**
 * <p>
 * BaseBean
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class BaseBean {
	
	public static final Integer PAGE_STRAT = 0;
	public static final Integer PAGE_MAXSIZE = 100;
    /**
     * mq每条消息的编号
     */
    private String mqMsgId;
    /**
     * 随机字符串
     */
    private String randomString;
    /**
     * 安全码
     */
    private String secretKey;
    
    /**
     * 验签
     */
    private String chkValue;
    
    /**
     * 分页码
     */
    private Integer page = 1;
    
    /**
     * 当前页码
     */
    private Integer pageSize = 10;
    
    /**
     * 用户编号
     */
    private Integer userId;
    /**
     * 电子账号
     */
    private String accountId;
    
    /**
     * 访问渠道
     * 000001渠道 app 000002渠道 pc  000003渠道 wechat
     */
    private String channel="000002";

    /**
     * 平台
     * 0：PC  1：微官网  2：Android  3：iOS  4：其他
     */
    private String platform;

    /**
     * 当前时间戳（10位）
     */
    private Long timestamp;
    
    /**
     * 第三方同步回调接口
     */
    private String retUrl;
    
    /**
     * 第三方异步回调接口
     */
    private String bgRetUrl;
    /**
     * 保留域
     */
    private String acqRes;//
    
    /**
     * 第三方接口机构编号
     */
    private String instCode;
    
	/**
	 * 检索开始行(必传默认0)
	 */
	private Integer limitStart = PAGE_STRAT;
	
	/**
	 * 检索步长(必传默认100)
	 */
	private Integer limitEnd = PAGE_MAXSIZE;

    /**
     * 忘记密码Url
     */
    private String  forgotPwdUrl;

    public String getRandomString() {
        return randomString;
    }

    public void setRandomString(String randomString) {
        this.randomString = randomString;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

	public String getChkValue() {
		return chkValue;
	}

	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRetUrl() {
        return retUrl;
    }

    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }

    public String getBgRetUrl() {
        return bgRetUrl;
    }

    public void setBgRetUrl(String bgRetUrl) {
        this.bgRetUrl = bgRetUrl;
    }

    public String getMqMsgId() {
        return mqMsgId;
    }

    public void setMqMsgId(String mqMsgId) {
        this.mqMsgId = mqMsgId;
    }

    public String getAcqRes() {
        return acqRes;
    }

    public void setAcqRes(String acqRes) {
        this.acqRes = acqRes;
    }


	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }


	public Integer getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}

	public Integer getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
	}
   

    public String getForgotPwdUrl() {
        return this.forgotPwdUrl;
    }

    public void setForgotPwdUrl(String forgotPwdUrl) {
        this.forgotPwdUrl = forgotPwdUrl;
    }

	@Override
	public String toString() {
		return "BaseBean [randomString=" + randomString + ", secretKey=" + secretKey + ", chkValue=" + chkValue
				+ ", page=" + page + ", pageSize=" + pageSize + ", userId=" + userId + ", timestamp=" + timestamp + "]";
	}
    
}
