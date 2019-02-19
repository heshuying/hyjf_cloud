/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.user.dao.model.customize;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Administrator
 */

public class ChannelCustomize implements Serializable {

	/**
	 * serialVersionUID:
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * 渠道 检索条件
	 */
	private String sourceIdSrch;
	/**
	 * 关键字 检索条件
	 */
	private String utmTermSrch;

	private String utmId;

	private String sourceId;

	private String sourceName;

	private String utmSource;

	private String utmMedium;

	private String utmTerm;

	private String utmContent;

	private String utmCampaign;

	private String utmReferrer;

	private String username;

	private String createTime;

	private String remark;

	private String linkAddress;
	
	private String status;

	private String url;

	/**
	 * username
	 * 
	 * @return the username
	 */

	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * url
	 * 
	 * @return the url
	 * @throws UnsupportedEncodingException
	 */

	public String getUrl() throws UnsupportedEncodingException {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(this.linkAddress);
		strBuf.append("?utm_id=");
		strBuf.append(this.utmId);
		if (StringUtils.isNotEmpty(this.utmSource)) {
			strBuf.append("&utm_source=");
			strBuf.append(URLEncoder.encode(this.utmSource, "UTF-8"));
		}
		if (StringUtils.isNotEmpty(this.utmMedium)) {
			strBuf.append("&utm_medium=");
			strBuf.append(URLEncoder.encode(this.utmMedium, "UTF-8"));
		}
		if (StringUtils.isNotEmpty(this.utmTerm)) {
			strBuf.append("&utm_term=");
			strBuf.append(URLEncoder.encode(this.utmTerm, "UTF-8"));
		}
		if (StringUtils.isNotEmpty(this.utmContent)) {
			strBuf.append("&utm_content=");
			strBuf.append(URLEncoder.encode(this.utmContent, "UTF-8"));
		}
		if (StringUtils.isNotEmpty(this.utmCampaign)) {
			strBuf.append("&utm_campaign=");
			strBuf.append(URLEncoder.encode(this.utmCampaign, "UTF-8"));
		}
		if (StringUtils.isNotEmpty(this.utmReferrer) && !"0".equals(this.utmReferrer)) {
			strBuf.append("&refferUserId=");
			strBuf.append(this.utmReferrer);
		}
		this.url = strBuf.toString();
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 检索条件 limitStart
	 */
	private int limitStart = -1;
	/**
	 * 检索条件 limitEnd
	 */
	private int limitEnd = -1;

	/**
	 * sourceName
	 * 
	 * @return the sourceName
	 */

	public String getSourceName() {
		return sourceName;
	}

	/**
	 * @param sourceName
	 *            the sourceName to set
	 */

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	/**
	 * limitStart
	 * 
	 * @return the limitStart
	 */

	public int getLimitStart() {
		return limitStart;
	}

	/**
	 * @param limitStart
	 *            the limitStart to set
	 */

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * limitEnd
	 * 
	 * @return the limitEnd
	 */

	public int getLimitEnd() {
		return limitEnd;
	}

	/**
	 * @param limitEnd
	 *            the limitEnd to set
	 */

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	/**
	 * sourceIdSrch
	 * 
	 * @return the sourceIdSrch
	 */

	public String getSourceIdSrch() {
		return sourceIdSrch;
	}

	/**
	 * @param sourceIdSrch
	 *            the sourceIdSrch to set
	 */

	public void setSourceIdSrch(String sourceIdSrch) {
		this.sourceIdSrch = sourceIdSrch;
	}

	/**
	 * utmTermSrch
	 * 
	 * @return the utmTermSrch
	 */

	public String getUtmTermSrch() {
		return utmTermSrch;
	}

	/**
	 * @param utmTermSrch
	 *            the utmTermSrch to set
	 */

	public void setUtmTermSrch(String utmTermSrch) {
		this.utmTermSrch = utmTermSrch;
	}

	/**
	 * utmId
	 * 
	 * @return the utmId
	 */

	public String getUtmId() {
		return utmId;
	}

	/**
	 * @param utmId
	 *            the utmId to set
	 */

	public void setUtmId(String utmId) {
		this.utmId = utmId;
	}

	/**
	 * sourceId
	 * 
	 * @return the sourceId
	 */

	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId
	 *            the sourceId to set
	 */

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * utmSource
	 * 
	 * @return the utmSource
	 */

	public String getUtmSource() {
		return utmSource;
	}

	/**
	 * @param utmSource
	 *            the utmSource to set
	 */

	public void setUtmSource(String utmSource) {
		this.utmSource = utmSource;
	}

	/**
	 * utmMedium
	 * 
	 * @return the utmMedium
	 */

	public String getUtmMedium() {
		return utmMedium;
	}

	/**
	 * @param utmMedium
	 *            the utmMedium to set
	 */

	public void setUtmMedium(String utmMedium) {
		this.utmMedium = utmMedium;
	}

	/**
	 * utmTerm
	 * 
	 * @return the utmTerm
	 */

	public String getUtmTerm() {
		return utmTerm;
	}

	/**
	 * @param utmTerm
	 *            the utmTerm to set
	 */

	public void setUtmTerm(String utmTerm) {
		this.utmTerm = utmTerm;
	}

	/**
	 * utmContent
	 * 
	 * @return the utmContent
	 */

	public String getUtmContent() {
		return utmContent;
	}

	/**
	 * @param utmContent
	 *            the utmContent to set
	 */

	public void setUtmContent(String utmContent) {
		this.utmContent = utmContent;
	}

	/**
	 * utmCampaign
	 * 
	 * @return the utmCampaign
	 */

	public String getUtmCampaign() {
		return utmCampaign;
	}

	/**
	 * @param utmCampaign
	 *            the utmCampaign to set
	 */

	public void setUtmCampaign(String utmCampaign) {
		this.utmCampaign = utmCampaign;
	}

	/**
	 * utmReferrer
	 * 
	 * @return the utmReferrer
	 */

	public String getUtmReferrer() {
		return utmReferrer;
	}

	/**
	 * @param utmReferrer
	 *            the utmReferrer to set
	 */

	public void setUtmReferrer(String utmReferrer) {
		this.utmReferrer = utmReferrer;
	}

	/**
	 * createTime
	 * 
	 * @return the createTime
	 */

	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * remark
	 * 
	 * @return the remark
	 */

	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * status
	 * 
	 * @return the status
	 */

	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	
}
