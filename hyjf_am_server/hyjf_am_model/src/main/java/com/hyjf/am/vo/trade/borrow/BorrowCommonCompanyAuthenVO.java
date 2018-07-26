package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;

/**
 * @package com.hyjf.admin.maintenance.AlllBorrowCustomize;
 * @author GOGTZ-Z
 * @date 2015/07/09 17:00
 * @version V1.0  
 */
public class BorrowCommonCompanyAuthenVO extends BaseVO {


	/**
	 * 认证项目名称
	 */
	private String authenName;

	/**
	 * 认证时间
	 */
	private String authenTime;

	/**
	 * 展示顺序
	 */
	private String authenSortKey;

	/**
	 * authenName
	 * 
	 * @return the authenName
	 */

	public String getAuthenName() {
		return authenName;
	}

	/**
	 * @param authenName
	 *            the authenName to set
	 */

	public void setAuthenName(String authenName) {
		this.authenName = authenName;
	}

	/**
	 * authenTime
	 * 
	 * @return the authenTime
	 */

	public String getAuthenTime() {
		return authenTime;
	}

	/**
	 * @param authenTime
	 *            the authenTime to set
	 */

	public void setAuthenTime(String authenTime) {
		this.authenTime = authenTime;
	}

	/**
	 * authenSortKey
	 * 
	 * @return the authenSortKey
	 */

	public String getAuthenSortKey() {
		return authenSortKey;
	}

	/**
	 * @param authenSortKey
	 *            the authenSortKey to set
	 */

	public void setAuthenSortKey(String authenSortKey) {
		this.authenSortKey = authenSortKey;
	}

}
