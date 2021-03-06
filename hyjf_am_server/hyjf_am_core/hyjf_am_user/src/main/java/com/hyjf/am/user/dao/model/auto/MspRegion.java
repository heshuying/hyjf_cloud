package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class MspRegion implements Serializable {
    /**
     * 城市区号
     *
     * @mbggenerated
     */
    private String regionId;

    /**
     * 城市名称
     *
     * @mbggenerated
     */
    private String regionName;

    private static final long serialVersionUID = 1L;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }
}