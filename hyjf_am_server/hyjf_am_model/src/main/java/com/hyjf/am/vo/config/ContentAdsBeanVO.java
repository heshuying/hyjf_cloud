package com.hyjf.am.vo.config;

import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.admin.AdsTypeVO;
import com.hyjf.am.vo.admin.AdsVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yinhui on 2018/7/19.
 */
public class ContentAdsBeanVO extends BaseVO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3803722754627032581L;

    /**
     * 前台时间接收
     */
    private String startCreate;

    private String endCreate;

    private String ids;

    private List<AdsTypeVO> adsTypeList;

    private List<AdsVO> recordList;

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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<AdsTypeVO> getAdsTypeList() {
        return adsTypeList;
    }

    public void setAdsTypeList(List<AdsTypeVO> adsTypeList) {
        this.adsTypeList = adsTypeList;
    }

    public List<AdsVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<AdsVO> recordList) {
        this.recordList = recordList;
    }
}
