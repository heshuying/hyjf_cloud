package com.hyjf.cs.trade.service;

import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.resquest.trade.MyCreditListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.CreditDetailsRequestBean;

import java.util.Map;

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
}
