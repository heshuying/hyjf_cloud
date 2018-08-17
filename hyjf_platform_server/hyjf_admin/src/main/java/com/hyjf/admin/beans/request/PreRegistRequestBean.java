/**
 * Description:用户预注册注册记录
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 朱晓东
 * @version: 1.0
 * Created at: 2016年06月23日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.admin.beans.request;

import java.io.Serializable;

import com.hyjf.admin.beans.BaseRequest;
import com.hyjf.am.vo.BaseVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dongzeshan
 */

public class PreRegistRequestBean extends BaseRequest implements Serializable {
    

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "主键")
    public String id;
	@ApiModelProperty(value = "手机号")
	public String mobile;
	@ApiModelProperty(value = "推荐人")
	public String referrer;
	@ApiModelProperty(value = "关键词")
    public String utm;
	@ApiModelProperty(value = "主键")
	public String source;
	@ApiModelProperty(value = "是否已注册 0:否,1:是")
	public String registFlag;
	@ApiModelProperty(value = "备注")
    public String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getReferrer() {
		return referrer;
	}
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	public String getUtm() {
		return utm;
	}
	public void setUtm(String utm) {
		this.utm = utm;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getRegistFlag() {
		return registFlag;
	}
	public void setRegistFlag(String registFlag) {
		this.registFlag = registFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

    
}
