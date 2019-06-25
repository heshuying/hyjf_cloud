package com.hyjf.am.resquest.message;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: yinhui
 * @Date: 2019/6/12 16:02
 * @Version 1.0
 */
public class CACustomerRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 证件类型:0:身份证,1:企业证件号码
     */
    private Integer idType;

    // 证件号集合
    private List<String> idNoList;

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public List<String> getIdNoList() {
        return idNoList;
    }

    public void setIdNoList(List<String> idNoList) {
        this.idNoList = idNoList;
    }
}
