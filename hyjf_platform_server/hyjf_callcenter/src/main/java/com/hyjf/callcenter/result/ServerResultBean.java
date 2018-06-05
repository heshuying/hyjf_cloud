package com.hyjf.callcenter.result;

/**
 * @author xiasq
 * @version ServerResult, v0.1 2018/4/25 19:42
 */
public class ServerResultBean extends BaseResultBean {

	public ServerResultBean() {
		super();
	}

	public ServerResultBean(String request) {
		super(request);
	}

	public ServerResultBean(String status, String statusDesc) {
		super(status, statusDesc);
	}

	private String serverIp;
	private String initKey;
	private String sign;

	private String key;

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getInitKey() {
		return initKey;
	}

	public void setInitKey(String initKey) {
		this.initKey = initKey;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
