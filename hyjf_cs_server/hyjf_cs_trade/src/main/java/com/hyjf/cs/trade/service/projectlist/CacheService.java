package com.hyjf.cs.trade.service.projectlist;

import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;

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
}
