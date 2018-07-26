package com.hyjf.am.trade.bean;

import java.io.Serializable;

/**
 * @package com.hyjf.admin.maintenance.AlllBorrowCustomize;
 * @author GOGTZ-Z
 * @date 2015/07/09 17:00
 * @version V1.0  
 */
public class BorrowCommonNameAccount implements Serializable {

	/**
	 * serialVersionUID
	 */

	private static final long serialVersionUID = 387630498860089653L;

	/**
	 * 借款标题
	 */
	private String names;

	/**
	 * 借款金额
	 */
	private String accounts;

	/**
	 * names
	 * 
	 * @return the names
	 */

	public String getNames() {
		return names;
	}

	/**
	 * @param names
	 *            the names to set
	 */

	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * accounts
	 * 
	 * @return the accounts
	 */

	public String getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts
	 *            the accounts to set
	 */

	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}

}
