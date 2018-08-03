package com.hyjf.cs.trade.service.credit;

import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.resquest.trade.MyCreditListRequest;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.CreditDetailsRequestBean;
import com.hyjf.cs.trade.bean.TenderBorrowCreditCustomize;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * @Description  债转列表
 * @Author sunss
 * @Date 2018/6/30 10:02
 */
public interface MyCreditListService extends BaseTradeService {

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

    /**
     * 用户中心查询 债转详细预计服务费计算
     * @param request
     * @param userId
     * @return
     */
    WebResult getExpectCreditFee(TenderBorrowCreditCustomize request, Integer userId);

    /**
     * 发送短信验证码（ajax请求） 短信验证码数据保存
     * @param request
     * @param userId
     * @return
     */
    WebResult sendCreditCode(TenderBorrowCreditCustomize request, Integer userId);

    /**
     * 短信验证码校验
     * @param request
     * @param userId
     * @return
     */
    WebResult checkCode(TenderBorrowCreditCustomize request, Integer userId);
}
