/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.paginator.Paginator;

import java.util.List;

/**
 * @author yaoyong
 * @version UserPortraitScoreRequest, v0.1 2018/8/9 11:26
 */
public class UserPortraitScoreRequest extends BasePage {

    private String userName;

    private List<ParamNameVO> paramNameVOList;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ParamNameVO> getParamNameVOList() {
        return paramNameVOList;
    }

    public void setParamNameVOList(List<ParamNameVO> paramNameVOList) {
        this.paramNameVOList = paramNameVOList;
    }
}
