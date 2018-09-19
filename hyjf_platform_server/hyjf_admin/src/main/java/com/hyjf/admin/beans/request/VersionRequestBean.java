package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/16.
 */
public class VersionRequestBean extends BaseRequest implements Serializable {
    private static final long serialVersionUID = 387630498860089653L;
    @ApiModelProperty(value = "删除条件 ")
    private List<Integer> delids;
    private String ids;
    @ApiModelProperty(value = "系统名称--检索条件")
    private String nameSrh;
    @ApiModelProperty(value = "版本号--检索条件")
    private String versionSrh;
    @ApiModelProperty(value = "版本id")
    private Integer id;
    @ApiModelProperty(value = "版本类型 ")
    private Integer type;
    @ApiModelProperty(value = "版本号")
    private String version;
    @ApiModelProperty(value = "是否需要更新 ")
    private Integer isupdate;
    @ApiModelProperty(value = "url")
    private String url;
    @ApiModelProperty(value = "版本描述")
    private String content;

    public List<Integer> getDelids() {
        return delids;
    }

    public void setDelids(List<Integer> delids) {
        this.delids = delids;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getNameSrh() {
        return nameSrh;
    }

    public void setNameSrh(String nameSrh) {
        this.nameSrh = nameSrh;
    }

    public String getVersionSrh() {
        return versionSrh;
    }

    public void setVersionSrh(String versionSrh) {
        this.versionSrh = versionSrh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(Integer isupdate) {
        this.isupdate = isupdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
