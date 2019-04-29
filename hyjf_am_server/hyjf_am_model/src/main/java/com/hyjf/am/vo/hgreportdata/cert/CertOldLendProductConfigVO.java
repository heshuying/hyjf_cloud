/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.hgreportdata.cert;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * //合规数据上报 CERT  国家互联网应急中心    CERT 用户信息上报
 *
 * @author sss
 * @version BaseHgDataReportEntity, v0.1 2018/6/27 10:06
 */
public class CertOldLendProductConfigVO implements Serializable {

    /**
     * 版本号
     */
    private String version;
    private String sourceCode;
    private String sourceFinancingCode;
    private String finClaimId;
    private String configId;
    private String userIdcardHash;
    private String groupByDate;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceFinancingCode() {
        return sourceFinancingCode;
    }

    public void setSourceFinancingCode(String sourceFinancingCode) {
        this.sourceFinancingCode = sourceFinancingCode;
    }

    public String getFinClaimId() {
        return finClaimId;
    }

    public void setFinClaimId(String finClaimId) {
        this.finClaimId = finClaimId;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getUserIdcardHash() {
        return userIdcardHash;
    }

    public void setUserIdcardHash(String userIdcardHash) {
        this.userIdcardHash = userIdcardHash;
    }

    public String getGroupByDate() {
        return groupByDate;
    }

    public void setGroupByDate(String groupByDate) {
        this.groupByDate = groupByDate;
    }
}
