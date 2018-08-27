package com.hyjf.am.config.dao.model.customize;

import java.io.Serializable;
import java.util.List;

import com.hyjf.am.config.dao.model.auto.Version;
import com.hyjf.common.paginator.Paginator;

/**
 * @author lisheng
 * @version VersionConfigBean, v0.1 2018/7/14 14:20
 */

public class VersionConfigBean extends Version implements Serializable {

    /**
     * serialVersionUID
     */

    private static final long serialVersionUID = 387630498860089653L;

    private List<Version> recordList;

    private String ids;

    private String nameSrh;
    private String versionSrh;
    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

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

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public List<Version> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Version> recordList) {
        this.recordList = recordList;
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
}
