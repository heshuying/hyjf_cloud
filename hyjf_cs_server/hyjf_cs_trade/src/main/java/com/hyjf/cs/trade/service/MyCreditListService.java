package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.resquest.trade.MyCreditListRequest;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.CreditDetailsRequestBean;
import com.hyjf.cs.trade.bean.TenderBorrowCreditCustomize;

/**
 * @Description  债转列表
 * @Author sunss
 * @Date 2018/6/30 10:02
 */
public interface MyCreditListService extends BaseTradeService{

    /**
     * 我要债转列表页 获取参数
     * @param request
     * @param userId
     * @return
     */
    WebResult getCreditListData(MyCreditListRequest request, Integer userId);

    /**
     * 我要债转列表页 获取列表
     * @param request
     * @param userId
     * @return
     */
    WebResult getCreditList(MyCreditListQueryRequest request, Integer userId);

    /**
     * 用户中心查询投资可债转详情
     * @param request
     * @param userId
     * @return
     */
    WebResult tenderToCreditDetail(CreditDetailsRequestBean request, Integer userId);

    /**
     * 用户中心验证投资人当天是否可以债转
     * @param request
     * @param userId
     * @return
     */
    WebResult tenderAbleToCredit(CreditDetailsRequestBean request, Integer userId);

    /**
     * 检查是否可债转
     * @param request
     * @param userId
     * @return
     */
    WebResult checkCanCredit(CreditDetailsRequestBean request, Integer userId);

    /**
     * 债转提交保存
     * @param request
     * @param userId
     * @return
     */
    WebResult saveTenderToCredit(TenderBorrowCreditCustomize request, Integer userId);
}
