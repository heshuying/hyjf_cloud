package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

import java.io.Serializable;

/**
 * @author lisheng
 * @version AppBannerRequestBean, v0.1 2018/7/11 14:19
 */

public class AppBannerRequestBean  extends BaseRequest implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3803722754627032581L;

    @ApiModelProperty(value = "广告名称")
    private String name;
    @ApiModelProperty(value = "广告类型")
    private Integer typeId;
    @ApiModelProperty(value = "创建时间")
    private Integer createTime;
    @ApiModelProperty(value = "广告状态")
    private Integer status;
    @ApiModelProperty(value = "调用代码")
    private String code;
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "删除使用的id")
    private String ids;
    @ApiModelProperty(value = "平台类型 1 安卓 2 ios 3 微信")
    private Integer platformType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }
}
