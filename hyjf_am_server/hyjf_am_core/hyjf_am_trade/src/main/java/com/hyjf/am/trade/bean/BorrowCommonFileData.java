/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年12月9日 下午12:52:08
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.trade.bean;

import java.io.Serializable;

/**
 * @author Administrator
 */

public class BorrowCommonFileData implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 387630498860089653L;

	/**
	 * 文件名称
	 */
	private String name;
	/**
	 * 文件类型
	 */
	private String filetype;
	/**
	 * 文件保存后名称
	 */
	private String fileRealName;
	/**
	 * 文件名称
	 */
	private String filename;
	/**
	 * 文件大小
	 */
	private String filesize;
	/**
	 * 文件路径
	 */
	private String fileurl;
	/**
	 * 展示顺序
	 */
	private String imageSort;

	/**
	 * fileRealName
	 * 
	 * @return the fileRealName
	 */

	public String getFileRealName() {
		return fileRealName;
	}

	/**
	 * @param fileRealName
	 *            the fileRealName to set
	 */

	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
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

	/**
	 * filetype
	 * 
	 * @return the filetype
	 */

	public String getFiletype() {
		return filetype;
	}

	/**
	 * @param filetype
	 *            the filetype to set
	 */

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	/**
	 * filename
	 * 
	 * @return the filename
	 */

	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename
	 *            the filename to set
	 */

	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * filesize
	 * 
	 * @return the filesize
	 */

	public String getFilesize() {
		return filesize;
	}

	/**
	 * @param filesize
	 *            the filesize to set
	 */

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	/**
	 * fileurl
	 * 
	 * @return the fileurl
	 */

	public String getFileurl() {
		return fileurl;
	}

	/**
	 * @param fileurl
	 *            the fileurl to set
	 */

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	/**
	 * imageSort
	 * 
	 * @return the imageSort
	 */

	public String getImageSort() {
		return imageSort;
	}

	/**
	 * @param imageSort
	 *            the imageSort to set
	 */

	public void setImageSort(String imageSort) {
		this.imageSort = imageSort;
	}
}
