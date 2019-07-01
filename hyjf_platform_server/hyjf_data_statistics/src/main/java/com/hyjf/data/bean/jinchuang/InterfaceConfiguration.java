package com.hyjf.data.bean.jinchuang;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @Auther:yangchangwei
 * @Date:2019/6/25
 * @Description: 接口配置
 */
@Document(collection = "ht_interface_configuration")
public class InterfaceConfiguration implements Serializable {

    /**
     * 接口名称
     */
    private String interfaceName;
    /**
     * 接口方法
     */
    private String methodName;
    /**
     * 接口路径
     */
    private String url;
    /**
     * 域名
     */
    private String domain;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
