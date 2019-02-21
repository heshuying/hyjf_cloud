/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.admin.controller.exception.cert;

/**
 * @Description
 * @Author sss
 * @Date 2019/2/21 17:35
 */
public class CertSendMqReqBean {

    private Integer id;
    private String dataType;
    private String mqValue;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getMqValue() {
        return mqValue;
    }

    public void setMqValue(String mqValue) {
        this.mqValue = mqValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
