package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.admin.coupon.ParamName;

import java.io.Serializable;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/16.
 */
public class AdminVersionRequest implements Serializable {
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
    /*数据字典*/
    private List<ParamName> versionNames;

    private List<ParamName> isUpdates;
    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

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

    public int getPaginatorPage() {
        return paginatorPage;
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

    public List<ParamName> getVersionNames() {
        return versionNames;
    }

    public void setVersionNames(List<ParamName> versionNames) {
        this.versionNames = versionNames;
    }

    public List<ParamName> getIsUpdates() {
        return isUpdates;
    }

    public void setIsUpdates(List<ParamName> isUpdates) {
        this.isUpdates = isUpdates;
    }
}
