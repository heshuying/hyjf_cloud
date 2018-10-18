package com.hyjf.am.resquest.market;

import com.hyjf.am.vo.BasePage;

/**
 * @author lisheng
 * @version AppBannerRequest, v0.1 2018/7/11 14:30
 */

public class AppBannerRequest extends BasePage {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3803722754627032581L;

    private Integer id;

    //广告名称
    private String name;
    // 广告类型
    private Integer typeId;
    //广告状态
    private Integer status;

    private String startCreate;

    private String endCreate;

    /**
     * 平台类型
     */
    private Integer platformType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartCreate() {
        return startCreate;
    }

    public void setStartCreate(String startCreate) {
        this.startCreate = startCreate;
    }

    public String getEndCreate() {
        return endCreate;
    }

    public void setEndCreate(String endCreate) {
        this.endCreate = endCreate;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
