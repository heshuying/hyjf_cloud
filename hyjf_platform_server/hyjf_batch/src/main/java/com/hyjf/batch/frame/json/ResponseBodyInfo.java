package com.hyjf.batch.frame.json;

/**
 * @author xiasq
 * @version ResponseBodyInfo, v0.1 2018/1/20 22:20
 */
public class ResponseBodyInfo<T> {
	/**
	 * 错误代码
	 */
	private int errorCode;
	/**
	 * 错误提示
	 */
	private String errorText;

	/**
	 * 返回对象
	 */
	private T data;

	protected ResponseBodyInfo() {
	}

	protected ResponseBodyInfo(int errorCode, String errorText, T data) {
		this.errorCode = errorCode;
		this.errorText = errorText;
		this.data = data;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ResponseBodyInfo{");
		sb.append("errorCode=").append(errorCode);
		sb.append(", errorText='").append(errorText).append('\'');
		sb.append(", data=").append(data);
		sb.append('}');
		return sb.toString();
	}
}
