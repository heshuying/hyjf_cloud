package com.hyjf.cs.trade.service.projectlist;

import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.response.trade.HjhAccedeListResponse;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.BorrowInvestReqBean;

import java.util.Map;

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

    /**
     * 计划标的组成count
     * @author zhangyk
     * @date 2019/3/11 11:02
     */
    HjhAccedeResponse getPlanBorrowListCount(Map<String, Object> params);

    /**
     * 计划标的组成list
     * @author zhangyk
     * @date 2019/3/11 11:03
     */
    BorrowResponse getPlanBorrowList(Map<String, Object> params);

    /**
     * 计划标的服务记录count
     * @author zhangyk
     * @date 2019/3/11 11:17
     */
    HjhAccedeResponse getPlanAccedeCount(Map<String, Object> params);

    /**
     * 计划标的服务记录list
     * @author zhangyk
     * @date 2019/3/11 11:19
     */
    HjhAccedeListResponse getPlanAccedeList(Map<String, Object> params);
}
