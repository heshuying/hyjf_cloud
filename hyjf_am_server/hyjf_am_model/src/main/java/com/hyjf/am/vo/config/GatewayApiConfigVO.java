package com.hyjf.am.vo.config;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author xiasq
 * @version GatewayApiConfigVO, v0.1 2018/4/13 9:56
 */
public class GatewayApiConfigVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 7326476174923393500L;

    private String id;

	private String path;

	private String serviceId;

	private String url;

	private Boolean retryable;

	private Boolean enabled;

	private Integer stripPrefix;

	private String apiName;

    private Integer secureVisitFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId == null ? null : serviceId.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Boolean getRetryable() {
        return retryable;
    }

    public void setRetryable(Boolean retryable) {
        this.retryable = retryable;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getStripPrefix() {
        return stripPrefix;
    }

    public void setStripPrefix(Integer stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName == null ? null : apiName.trim();
    }

    public Integer getSecureVisitFlag() {
        return secureVisitFlag;
    }

    public void setSecureVisitFlag(Integer secureVisitFlag) {
        this.secureVisitFlag = secureVisitFlag;
    }

    @Override
    public String toString() {
        return "GatewayApiConfigVO{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", url='" + url + '\'' +
                ", retryable=" + retryable +
                ", enabled=" + enabled +
                ", stripPrefix=" + stripPrefix +
                ", apiName='" + apiName + '\'' +
                ", secureVisitFlag=" + secureVisitFlag +
                '}';
    }
}
