/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.bifa;

import java.io.Serializable;

/**
 * @author jun
 * @version BifaBorrowUserInfoVO, v0.1 2019/1/16 10:36
 */
public class BifaBorrowUserInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String trueName;
    private String sex;
    private String idCard;

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
