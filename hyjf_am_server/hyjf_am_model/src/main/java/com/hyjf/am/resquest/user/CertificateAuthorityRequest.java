package com.hyjf.am.resquest.user;

import com.hyjf.am.resquest.Request;

/**
 * @author jun
 * @date 20180627
 */
public class CertificateAuthorityRequest extends Request {

    private String trueName;
    private String idNo;
    private Integer idType;

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

}
