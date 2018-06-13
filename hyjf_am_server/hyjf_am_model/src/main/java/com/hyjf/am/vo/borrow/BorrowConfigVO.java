/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.borrow;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author fuqiang
 * @version BorrowConfigVO, v0.1 2018/6/12 19:26
 */
public class BorrowConfigVO extends BaseVO implements Serializable {
    private String configCd;

    private String configName;

    private String configValue;

    private String remark;

    private String delFlag;

    private Integer createTime;

    private Integer updateTime;

    private static final long serialVersionUID = 1L;

    public String getConfigCd() {
        return configCd;
    }

    public void setConfigCd(String configCd) {
        this.configCd = configCd == null ? null : configCd.trim();
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName == null ? null : configName.trim();
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }
}
