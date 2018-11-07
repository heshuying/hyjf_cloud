package com.hyjf.am.vo.trade;

/**
 * @author pangchengchao
 * @version ProjectRepayListBeanVO, v0.1 2018/11/6 14:19
 */
public class ProjectRepayListBeanVO {
    /**
     *
     */
    private static final long serialVersionUID = 3278149257478770256L;

    // 用户id
    public String userId;
    // 项目编号
    public String borrowNid;
    //用户投资的唯一标示
    public String nid;

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;
    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }


    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }
}
