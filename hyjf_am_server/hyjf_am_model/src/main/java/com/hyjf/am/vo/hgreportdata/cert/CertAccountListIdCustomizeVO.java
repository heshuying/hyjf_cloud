package com.hyjf.am.vo.hgreportdata.cert;

/**
 * @author pangchengchao
 * @version CertAccountListIdCustomizeVO, v0.1 2019/1/31 10:08
 */
public class CertAccountListIdCustomizeVO {

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
