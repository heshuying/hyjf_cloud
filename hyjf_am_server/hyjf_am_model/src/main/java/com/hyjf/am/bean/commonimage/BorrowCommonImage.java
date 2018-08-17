package com.hyjf.am.bean.commonimage;

import java.io.Serializable;

/**
 * @package com.hyjf.am.bean.commonimage;
 * @author tanyy
 * @date 2018/07/19 17:00
 * @version V1.0  
 */
public class BorrowCommonImage implements Serializable {

	/**
	 * serialVersionUID
	 */

	private static final long serialVersionUID = 387630498860089653L;

	/**
	 * 展示顺序
	 */
	private String imageSort;

	/**
	 * 资料名称
	 */
	private String imageName;

	/**
	 * 资料名称
	 */
	private String imageRealName;

	/**
	 * 图片路径
	 */
	private String imagePath;

	/**
	 * 图片路径
	 */
	private String imageSrc;

	/**
	 * 图片大小
	 */
	private String imageSize;

	/**
	 * 图片类型
	 */
	private String imageType;

	/**
	 * 错误消息
	 */
	private String errorMessage;

	/**
	 * 文件内容
	 */
	private byte[] bytes;

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
	 * imageType
	 * 
	 * @return the imageType
	 */

	public String getImageType() {
		return imageType;
	}

	/**
	 * @param imageType
	 *            the imageType to set
	 */

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	/**
	 * imageSize
	 * 
	 * @return the imageSize
	 */

	public String getImageSize() {
		return imageSize;
	}

	/**
	 * @param imageSize
	 *            the imageSize to set
	 */

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	/**
	 * imageSrc
	 * 
	 * @return the imageSrc
	 */

	public String getImageSrc() {
		return imageSrc;
	}

	/**
	 * @param imageSrc
	 *            the imageSrc to set
	 */

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	/**
	 * imageRealName
	 * 
	 * @return the imageRealName
	 */

	public String getImageRealName() {
		return imageRealName;
	}

	/**
	 * @param imageRealName
	 *            the imageRealName to set
	 */

	public void setImageRealName(String imageRealName) {
		this.imageRealName = imageRealName;
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

	/**
	 * imageName
	 * 
	 * @return the imageName
	 */

	public String getImageName() {
		return imageName;
	}

	/**
	 * @param imageName
	 *            the imageName to set
	 */

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * imagePath
	 * 
	 * @return the imagePath
	 */

	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath
	 *            the imagePath to set
	 */

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * serialversionuid
	 * 
	 * @return the serialversionuid
	 */

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
