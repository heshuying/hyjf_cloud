package com.hyjf.am.assetpush;

import java.io.Serializable;

public class InfoBean implements Serializable {
		
	private static final long serialVersionUID = 155405841080295756L;
	// 下面是必须参数
    private String assetId;
    //亚马逊
    private String amazonInfo;
    //易贝
    private String ebayInfo;
    //京东
    private String jingdongInfo;
    //淘宝
    private String taobaoInfo;
    //天猫
    private String tianmaoInfo;
    
    private int createUser;
    private int createTime;
    private int updateUserId;
    private int updateTime;
    private int delFlg;
	/**
	 * assetId
	 * @return the assetId
	 */
	
	public String getAssetId() {
		return assetId;
	}
	/**
	 * @param assetId the assetId to set
	 */
	
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	/**
	 * amazonInfo
	 * @return the amazonInfo
	 */
	
	public String getAmazonInfo() {
		return amazonInfo;
	}
	/**
	 * @param amazonInfo the amazonInfo to set
	 */
	
	public void setAmazonInfo(String amazonInfo) {
		this.amazonInfo = amazonInfo;
	}
	/**
	 * ebayInfo
	 * @return the ebayInfo
	 */
	
	public String getEbayInfo() {
		return ebayInfo;
	}
	/**
	 * @param ebayInfo the ebayInfo to set
	 */
	
	public void setEbayInfo(String ebayInfo) {
		this.ebayInfo = ebayInfo;
	}
	/**
	 * jingdongInfo
	 * @return the jingdongInfo
	 */
	
	public String getJingdongInfo() {
		return jingdongInfo;
	}
	/**
	 * @param jingdongInfo the jingdongInfo to set
	 */
	
	public void setJingdongInfo(String jingdongInfo) {
		this.jingdongInfo = jingdongInfo;
	}
	/**
	 * taobaoInfo
	 * @return the taobaoInfo
	 */
	
	public String getTaobaoInfo() {
		return taobaoInfo;
	}
	/**
	 * @param taobaoInfo the taobaoInfo to set
	 */
	
	public void setTaobaoInfo(String taobaoInfo) {
		this.taobaoInfo = taobaoInfo;
	}
	/**
	 * tianmaoInfo
	 * @return the tianmaoInfo
	 */
	
	public String getTianmaoInfo() {
		return tianmaoInfo;
	}
	/**
	 * @param tianmaoInfo the tianmaoInfo to set
	 */
	
	public void setTianmaoInfo(String tianmaoInfo) {
		this.tianmaoInfo = tianmaoInfo;
	}
	/**
	 * createUser
	 * @return the createUser
	 */
	
	public int getCreateUser() {
		return createUser;
	}
	/**
	 * @param createUser the createUser to set
	 */
	
	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}
	/**
	 * createTime
	 * @return the createTime
	 */
	
	public int getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	/**
	 * updateUserId
	 * @return the updateUserId
	 */
	
	public int getUpdateUserId() {
		return updateUserId;
	}
	/**
	 * @param updateUserId the updateUserId to set
	 */
	
	public void setUpdateUserId(int updateUserId) {
		this.updateUserId = updateUserId;
	}
	/**
	 * updateTime
	 * @return the updateTime
	 */
	
	public int getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	
	public void setUpdateTime(int updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * delFlg
	 * @return the delFlg
	 */
	
	public int getDelFlg() {
		return delFlg;
	}
	/**
	 * @param delFlg the delFlg to set
	 */
	
	public void setDelFlg(int delFlg) {
		this.delFlg = delFlg;
	}
	
}
