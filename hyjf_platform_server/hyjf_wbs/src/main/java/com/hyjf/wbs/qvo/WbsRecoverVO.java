/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo;

/**
 *
 * @author kou
 * @version WbsRecoverVO, v0.1 2019/4/30 11:02
 */
public class WbsRecoverVO extends WbsCommonVO{
    //当前请求
    //页码
    private  String currentPage;
    //总页码
    private  String totalPages;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }
}
