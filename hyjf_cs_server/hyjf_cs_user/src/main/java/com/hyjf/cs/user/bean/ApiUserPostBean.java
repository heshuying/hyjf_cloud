/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年9月15日 上午9:23:07
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.cs.user.bean;



/**
 * @author liubin
 */

public class ApiUserPostBean  extends BaseBean {
	/** 加密的汇晶社用户ID */
	private String bindUniqueIdScy;
	
	/** 返回Url */
    private String retUrl;
    
	/** 平台id */
    private Integer pid;
    
    /** 手机号码*/
    private String mobile;
    
    /** 身份证号码*/
    private String idCard;
    
    /** 姓名*/
    private String name;
    
	//来源
	private String from;
	//风车理财用户ID
	private String wrb_user_id;
	//风车理财用户名
	private String wrb_user_name;
	//邮箱
	private String email;
	//身份证号码
	private String id_no;
	//真实姓名
	private String true_name;
	//目标url
	private String target_url;  
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getWrb_user_id() {
		return wrb_user_id;
	}

	public void setWrb_user_id(String wrb_user_id) {
		this.wrb_user_id = wrb_user_id;
	}

	public String getWrb_user_name() {
		return wrb_user_name;
	}

	public void setWrb_user_name(String wrb_user_name) {
		this.wrb_user_name = wrb_user_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public String getTrue_name() {
		return true_name;
	}

	public void setTrue_name(String true_name) {
		this.true_name = true_name;
	}

	public String getTarget_url() {
		return target_url;
	}

	public void setTarget_url(String target_url) {
		this.target_url = target_url;
	}    
	/**
	 * bindUniqueIdScy
	 * @return the bindUniqueIdScy
	 */
	public String getBindUniqueIdScy() {
		return bindUniqueIdScy;
	}
	
	public void setBindUniqueIdScy(String bindUniqueIdScy) {
		this.bindUniqueIdScy = bindUniqueIdScy;
	}

	/**
	 * retUrl
	 * @return the retUrl
	 */
	
	@Override
    public String getRetUrl() {
		return retUrl;
	}

	/**
	 * @param retUrl the retUrl to set
	 */
	
	@Override
    public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	/**
	 * pid
	 * @return the pid
	 */
	
	public Integer getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	
	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 执行前每个方法前需要添加BusinessDesc描述
	 * @return
	 * @author lb
	 */
		
	@Override
	public String toString() {
		return "ApiUserPostBean [bindUniqueIdScy=" + bindUniqueIdScy + ", retUrl=" + retUrl + ", pid=" + pid + ", timestamp=" + super.getTimestamp() +", chkValue=" + super.getChkValue() + "]";
	}
}

	