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

    /**
     * 前台时间接收
     */


    //广告名称
    private String name;
    // 广告类型
    private Integer typeid;
    //广告状态
    private Integer status;

    private String startCreate;

    private String endCreate;

    private Integer platformType;
    /*

    //创建时间
    private Integer createTime;
    private String ids;

    private String content;

    private String shareContent;

    private Short id;

    private String url;

    private String code;

    private String image;

    private Short order;

    private Integer hits;

    private String shareUrl;

    private String shareImage;

    private Short isIndex;

    private Integer updateTime;

    private String startTime;

    private String endTime;

    private Integer isEnd;

    private String shareTitle;

    private String activitiImage;

    private String activitiDesc;

    private Integer clientType;

    private Integer newuserShow;*/


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
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
}
