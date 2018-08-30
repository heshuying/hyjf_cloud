package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.admin.AdsVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author yinhui
 * Created by yinhui on 2018/7/19.
 */
public class ContentAdsRequest extends BasePage implements Serializable {

    private static final long serialVersionUID = 3803722754627036581L;

    @ApiModelProperty(value = "广告类型 6-首页BANNER，9-活动BANNER，10-着陆页BANNER1，11-着陆页BANNER2")
    private Integer typeid;

    @ApiModelProperty(value = "广告名称")
    private String name;

    private Short order;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "添加的开始时间")
    private String startCreate;

    @ApiModelProperty(value = "添加的结束时间")
    private String endCreate;

    @ApiModelProperty(value = "广告管理实体类VO")
    private AdsVO ads;

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getOrder() {
        return order;
    }

    public void setOrder(Short order) {
        this.order = order;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public AdsVO getAds() {
        return ads;
    }

    public void setAds(AdsVO ads) {
        this.ads = ads;
    }
}
