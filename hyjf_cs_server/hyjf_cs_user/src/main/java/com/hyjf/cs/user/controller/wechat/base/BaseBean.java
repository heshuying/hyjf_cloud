package com.hyjf.cs.user.controller.wechat.base;

/**
 * <p>
 * BaseBean
 * </p>
 *
 * @author gogtz
 * @version 1.0.0
 */
public class BaseBean {

    /**
     * 版本号
     */
    private String version;
    /**
     * 网络状态
     */
    private String netStatus;
    /**
     * 平台
     */
    private String platform;
    /**
     * 随机字符串
     */
    private String randomString;
    /**
     * 唯一标识
     */
    private String sign;
    /**
     * order
     */
    private String order;
	/**
	 * version
	 * @return the version
	 */
	
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * netStatus
	 * @return the netStatus
	 */
	
	public String getNetStatus() {
		return netStatus;
	}
	/**
	 * @param netStatus the netStatus to set
	 */
	
	public void setNetStatus(String netStatus) {
		this.netStatus = netStatus;
	}
	/**
	 * platform
	 * @return the platform
	 */
	
	public String getPlatform() {
		return platform;
	}
	/**
	 * @param platform the platform to set
	 */
	
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	/**
	 * randomString
	 * @return the randomString
	 */
	
	public String getRandomString() {
		return randomString;
	}
	/**
	 * @param randomString the randomString to set
	 */
	
	public void setRandomString(String randomString) {
		this.randomString = randomString;
	}
	/**
	 * sign
	 * @return the sign
	 */
	
	public String getSign() {
		return sign;
	}
	/**
	 * @param sign the sign to set
	 */
	
	public void setSign(String sign) {
		this.sign = sign;
	}
	/**
	 * order
	 * @return the order
	 */
	
	public String getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	
	public void setOrder(String order) {
		this.order = order;
	}
}
