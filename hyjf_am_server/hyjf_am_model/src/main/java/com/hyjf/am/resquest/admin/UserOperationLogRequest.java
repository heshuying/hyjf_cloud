/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author yaoyong
 * @version UserOperationLogBean, v0.1 2018/10/10 10:29
 */
public class UserOperationLogRequest extends UserOperationLogEntityVO implements Serializable {

    private int offset;
    private int limit;
    //检索条件
    /**
     * 操作时间开始
     */
    private String operationTimeStart;

    /**
     * 操作时间结束
     */
    private String operationTimeEnd;
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页")
    private int currPage;

    /**
     * 当前页条数
     */
    @ApiModelProperty(value = "当前页条数")
    private int pageSize = 10;

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


    public String getOperationTimeStart() {
        return operationTimeStart;
    }

    public void setOperationTimeStart(String operationTimeStart) {
        this.operationTimeStart = operationTimeStart;
    }

    public String getOperationTimeEnd() {
        return operationTimeEnd;
    }

    public void setOperationTimeEnd(String operationTimeEnd) {
        this.operationTimeEnd = operationTimeEnd;
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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
