package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.ProjectCompanyDetailVO;
import com.hyjf.am.vo.trade.WebProjectPersonDetailVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;

public interface BorrowUserClient {

    public BorrowUserVO getBorrowUser(String borrowNid);

    /**
     * 公司项目详情
     * @author zhangyk
     * @date 2018/6/26 15:58
     */
    public ProjectCompanyDetailVO searchProjectCompanyDetail(String borrowNid);

    /**
     * 个人项目详情
     * @author zhangyk
     * @date 2018/6/26 15:59
     */
    public WebProjectPersonDetailVO searchProjectPersonDetail(String borrowNid);
}
