package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @author pangchengchao
 * @version CertAccountListIdCustomize, v0.1 2019/1/31 10:56
 */
public class CertAccountListIdCustomize implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1035131990766081594L;
    private Integer limitMinId;
    private Integer sumCount;
    private Integer limitMaxId;
    private Integer maxId;
    public Integer getLimitMinId() {
        return limitMinId;
    }
    public void setLimitMinId(Integer limitMinId) {
        this.limitMinId = limitMinId;
    }
    public Integer getLimitMaxId() {
        return limitMaxId;
    }
    public void setLimitMaxId(Integer limitMaxId) {
        this.limitMaxId = limitMaxId;
    }
    public Integer getMaxId() {
        return maxId;
    }
    public void setMaxId(Integer maxId) {
        this.maxId = maxId;
    }
    public Integer getSumCount() {
        return sumCount;
    }
    public void setSumCount(Integer sumCount) {
        this.sumCount = sumCount;
    }
}
