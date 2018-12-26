/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.EvaluationBorrowLevelConfigVO;
import com.hyjf.common.paginator.Paginator;

import java.util.List;
import java.util.Map;

/**
 * 风险测评配置:风险测评等级Response
 *
 * @author liuyang
 * @version EvaluationBorrowLevelConfigResponse, v0.1 2018/12/25 9:11
 */
public class EvaluationBorrowLevelConfigResponse extends Response<EvaluationBorrowLevelConfigVO> {

    private EvaluationBorrowLevelConfigVO form;

    private boolean updateResult;

    private int count;

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

    public EvaluationBorrowLevelConfigVO getForm() {
        return form;
    }

    public void setForm(EvaluationBorrowLevelConfigVO form) {
        this.form = form;
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


    public boolean isUpdateResult() {
        return updateResult;
    }

    public void setUpdateResult(boolean updateResult) {
        this.updateResult = updateResult;
    }
}
