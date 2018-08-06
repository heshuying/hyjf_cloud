package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/16.
 */
public class VersionVO extends BaseVO implements Serializable {
    private Integer id;

    private Integer type;

    private String version;

    private Integer isUpdate;

    private String url;

    private String content;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;
    /*数据字典*/
    private List<ParamNameVO> versionNames;

    private List<ParamNameVO> isUpdates;

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

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
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

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<ParamNameVO> getVersionNames() {
        return versionNames;
    }

    public void setVersionNames(List<ParamNameVO> versionNames) {
        this.versionNames = versionNames;
    }

    public List<ParamNameVO> getIsUpdates() {
        return isUpdates;
    }

    public void setIsUpdates(List<ParamNameVO> isUpdates) {
        this.isUpdates = isUpdates;
    }

    private static final long serialVersionUID = 1L;
}
