/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.admin.DuibaPointsVO;

import java.io.Serializable;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsRequest, v0.1 2019/5/29 10:46
 */
public class DuibaPointsRequest extends BasePage implements Serializable {

    private String userNameSrch;

    private String trueNameSrch;

    private List<DuibaPointsVO> recordList;

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

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getTrueNameSrch() {
        return trueNameSrch;
    }

    public void setTrueNameSrch(String trueNameSrch) {
        this.trueNameSrch = trueNameSrch;
    }

    public List<DuibaPointsVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<DuibaPointsVO> recordList) {
        this.recordList = recordList;
    }
}
