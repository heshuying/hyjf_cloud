/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年12月7日 下午3:16:12
 * Modification History:
 * Modified by : 
 */

package com.hyjf.common.file;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Administrator
 */

@JsonIgnoreProperties({ "bytes" })
public class FileMeta {
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件大小
	 */
	private String fileSize;
	/**
	 * 文件类型
	 */
	private String fileType;
	/**
	 * 文件路径
	 */
	private String filePath;
	/**
	 * 文件路径
	 */
	private String fileSrc;
	/**
	 * 文件错误消息
	 */
	private String errorMessage;

	/**
	 * 文件内容
	 */
	private byte[] bytes;

	/**
	 * fileSrc
	 * 
	 * @return the fileSrc
	 */

	public String getFileSrc() {
		return fileSrc;
	}

	/**
	 * @param fileSrc
	 *            the fileSrc to set
	 */

	public void setFileSrc(String fileSrc) {
		this.fileSrc = fileSrc;
	}

	/**
	 * errorMessage
	 * 
	 * @return the errorMessage
	 */

	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * filePath
	 * 
	 * @return the filePath
	 */

	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * fileName
	 * 
	 * @return the fileName
	 */

	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * fileSize
	 * 
	 * @return the fileSize
	 */

	public String getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize
	 *            the fileSize to set
	 */

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * fileType
	 * 
	 * @return the fileType
	 */

	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * bytes
	 * 
	 * @return the bytes
	 */

	public byte[] getBytes() {
		return bytes;
	}

	/**
	 * @param bytes
	 *            the bytes to set
	 */

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}
