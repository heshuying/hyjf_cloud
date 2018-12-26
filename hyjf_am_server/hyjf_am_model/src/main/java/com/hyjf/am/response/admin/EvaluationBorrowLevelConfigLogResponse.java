/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.EvaluationBorrowLevelConfigLogVO;
import com.hyjf.am.vo.admin.EvaluationMoneyConfigVO;
import com.hyjf.common.paginator.Paginator;

import java.util.Map;

/**
 * @author liuyang
 * @version EvaluationBorrowLevelConfigLogResponse, v0.1 2018/12/25 15:54
 */
public class EvaluationBorrowLevelConfigLogResponse extends Response<EvaluationBorrowLevelConfigLogVO> {

    private int count;

    private int cuttype;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCuttype() {
        return cuttype;
    }

    public void setCuttype(int cuttype) {
        this.cuttype = cuttype;
    }
}
