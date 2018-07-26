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

    /**
     * 当前页码
     */
    private int currPage;

    /**
     * 当前页条数
     */
    private int pageSize;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
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

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
