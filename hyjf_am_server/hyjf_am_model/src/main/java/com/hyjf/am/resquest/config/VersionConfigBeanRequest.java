package com.hyjf.am.resquest.config;

import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.vo.config.VersionVO;

import java.io.Serializable;
import java.util.List;

/**
 * @author lisheng
 * @version VersionConfigBeanRequest, v0.1 2018/7/13 18:01
 */

public class VersionConfigBeanRequest extends VersionVO implements Serializable {


    /**
     * serialVersionUID
     */

    private static final long serialVersionUID = 387630498860089653L;

    private List<VersionVO> recordList;

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

    public List<VersionVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<VersionVO> recordList) {
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
