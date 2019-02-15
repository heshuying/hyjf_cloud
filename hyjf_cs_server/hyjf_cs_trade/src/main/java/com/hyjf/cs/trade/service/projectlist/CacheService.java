package com.hyjf.cs.trade.service.projectlist;

import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.BorrowInvestReqBean;

public interface CacheService {

    /**
     * 封装借款人企业信息缓存方法
     * @author zhangyk
     * @date 2019/1/22 17:13
     */
    BorrowUserVO getCacheBorrowUser(String borrowNid);
    /**
     * 封装借款人主体信息缓存方法
     * @author zhangyk
     * @date 2019/1/22 17:13
     */
    BorrowManinfoVO getCacheBorrowManInfo(String borrowNid);

    /**
     * 标的投资记录缓存
     * @author zhangyk
     * @date 2019/1/23 11:25
     */
    WebResult getBorrowInvest(BorrowInvestReqBean form, String userId);
}
