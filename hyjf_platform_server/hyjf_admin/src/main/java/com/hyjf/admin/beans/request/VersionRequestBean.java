package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

import java.io.Serializable;

/**
 * @author by xiehuili on 2018/7/16.
 */
public class VersionRequestBean extends BaseRequest implements Serializable {
    private static final long serialVersionUID = 387630498860089653L;
    private String ids;
    private String nameSrh;
    private String versionSrh;
    private Integer id;

    private Integer type;

    private String version;

    private Integer isupdate;

    private String url;

    private String content;

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
