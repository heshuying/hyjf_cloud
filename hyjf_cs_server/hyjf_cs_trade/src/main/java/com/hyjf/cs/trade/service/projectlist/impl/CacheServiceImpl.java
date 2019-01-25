package com.hyjf.cs.trade.service.projectlist.impl;

import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.BorrowInvestReqBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.projectlist.CacheService;
import com.hyjf.cs.trade.service.projectlist.WebProjectListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *  缓存service, 所有缓存方法可以写在该类
 * @author zhangyk
 * @date 2019/1/23 9:10
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private WebProjectListService webProjectListService;


    @Override
    @Cached(name="projectBorrowUserCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = CustomConstants.PROJECT_BORROW_USER_CACHE_TIME, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)
    public BorrowUserVO getCacheBorrowUser(String borrowNid) {
        BorrowUserVO tempBorrowUsers = amTradeClient.getBorrowUser(borrowNid);
        if (tempBorrowUsers != null){
            return  CommonUtils.convertBean(tempBorrowUsers,BorrowUserVO.class);
        }
        return null;
    }

    @Override
    @Cached(name="projectBorrowManInfoCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = CustomConstants.PROJECT_BORROW_USER_CACHE_TIME, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)
    public BorrowManinfoVO getCacheBorrowManInfo(String borrowNid) {
        BorrowManinfoVO tempBorrowManinfo = amTradeClient.getBorrowManinfo(borrowNid);
        if (tempBorrowManinfo != null){
            return CommonUtils.convertBean(tempBorrowManinfo,BorrowManinfoVO.class);
        }
        return null;
    }


    @Override
    @Cached(name="projectInvestRecordCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = CustomConstants.PROJECT_BORROW_INVEST_CACHE_TIME, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)
    public WebResult getBorrowInvest(BorrowInvestReqBean form, String userId) {
        return webProjectListService.getBorrowInvest(form,userId);
    }
}
