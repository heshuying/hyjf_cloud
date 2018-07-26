package com.hyjf.am.trade.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @package com.hyjf.admin.maintenance.AlllBorrowCustomize;
 * @author GOGTZ-Z
 * @date 2015/07/09 17:00
 * @version V1.0  
 */
public class BorrowCommonFile implements Serializable {

	/**
	 * serialVersionUID
	 */

	private static final long serialVersionUID = 387630498860089653L;

	private List<BorrowCommonFileData> data;
	private String name;

	/**
	 * data
	 * 
	 * @return the data
	 */

	public List<BorrowCommonFileData> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */

	public void setData(List<BorrowCommonFileData> data) {
		this.data = data;
	}

	/**
	 * name
	 * 
	 * @return the name
	 */

	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */

	public void setName(String name) {
		this.name = name;
	}
}
