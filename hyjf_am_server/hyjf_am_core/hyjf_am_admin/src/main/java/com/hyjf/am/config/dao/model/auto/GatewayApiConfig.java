package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;

public class GatewayApiConfig implements Serializable {
    private Integer id;

    private String path;

    private String serviceId;

    private String url;

    private Integer retryable;

    private Integer enabled;

    private Integer stripPrefix;

    private Integer secureVisitFlag;

    private String apiName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getRetryable() {
        return retryable;
    }

    public void setRetryable(Integer retryable) {
        this.retryable = retryable;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getStripPrefix() {
        return stripPrefix;
    }

    public void setStripPrefix(Integer stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    public Integer getSecureVisitFlag() {
        return secureVisitFlag;
    }

    public void setSecureVisitFlag(Integer secureVisitFlag) {
        this.secureVisitFlag = secureVisitFlag;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName == null ? null : apiName.trim();
    }
}