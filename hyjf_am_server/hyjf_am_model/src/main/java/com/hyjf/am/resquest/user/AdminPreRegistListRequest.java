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

package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;


/**
 * @author dongzeshan
 */

public class AdminPreRegistListRequest extends BasePage{

	  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//主键
	    public String id;
		//手机号
		public String mobile;
		//推荐人
		public String referrer;
		//关键词
	    public String utm;
		//渠道
		public String source;
		//预注册时间
		public String preRegistTime;
		//是否已注册 0:否,1:是
		public String registFlag;
		//注册时间
		public String registTime;
		//平台ID
		public String platformId;
		//平台名称
		public String platformName;
		//备注
	    public String remark;
		//创建时间
	    public String createTime;
	    //更新时间
	    public String updateTime;
	    //更新人
	    public String updateBy;
	    
		// 注册时间 开始
		public String preRegTimeStart;
		// 注册时间 结束
		public String preRegTimeEnd;
		private int paginatorPage = 1;
		public int getPaginatorPage() {
			if (paginatorPage == 0) {
				paginatorPage = 1;
			}
			return paginatorPage;
		}

		public void setPaginatorPage(int paginatorPage) {
			this.paginatorPage = paginatorPage;
		}
	    public String getPreRegTimeStart() {
			return preRegTimeStart;
		}


		public void setPreRegTimeStart(String preRegTimeStart) {
			this.preRegTimeStart = preRegTimeStart;
		}


		public String getPreRegTimeEnd() {
			return preRegTimeEnd;
		}


		public void setPreRegTimeEnd(String preRegTimeEnd) {
			this.preRegTimeEnd = preRegTimeEnd;
		}



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

	    public String getUtm() {
	        return utm;
	    }

	    public void setUtm(String utm) {
	        this.utm = utm;
	    }

	    public String getReferrer() {
	        return referrer;
	    }

	    public void setReferrer(String referrer) {
	        this.referrer = referrer;
	    }

	    public String getSource() {
	        return source;
	    }

	    public void setSource(String source) {
	        this.source = source;
	    }

	    public String getPreRegistTime() {
	        return preRegistTime;
	    }

	    public void setPreRegistTime(String preRegistTime) {
	        this.preRegistTime = preRegistTime;
	    }

	    public String getRegistFlag() {
	        return registFlag;
	    }

	    public void setRegistFlag(String registFlag) {
	        this.registFlag = registFlag;
	    }

	    public String getRegistTime() {
	        return registTime;
	    }

	    public void setRegistTime(String registTime) {
	        this.registTime = registTime;
	    }

	    public String getPlatformId() {
	        return platformId;
	    }

	    public void setPlatformId(String platformId) {
	        this.platformId = platformId;
	    }

	    public String getPlatformName() {
	        return platformName;
	    }

	    public void setPlatformName(String platformName) {
	        this.platformName = platformName;
	    }

	    public String getRemark() {
	        return remark;
	    }

	    public void setRemark(String remark) {
	        this.remark = remark;
	    }

	    public String getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(String createTime) {
	        this.createTime = createTime;
	    }

	    public String getUpdateTime() {
	        return updateTime;
	    }

	    public void setUpdateTime(String updateTime) {
	        this.updateTime = updateTime;
	    }

	    public String getUpdateBy() {
	        return updateBy;
	    }

	    public void setUpdateBy(String updateBy) {
	        this.updateBy = updateBy;
	    }
    

}
