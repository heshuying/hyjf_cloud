package com.hyjf.cs.trade.service.projectlist;

import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.bean.api.ApiBorrowReqBean;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * api端 业务service
 * @author zhangyk
 * @date 2018/8/27 10:05
 */
public interface ApiProjectListService extends BaseTradeService {

    /**
     * 查询标的列表
     * @author zhangyk
     * @date 2018/8/27 10:11
     */
    ApiResult getBorrowList(ApiBorrowReqBean reqBean);

    /**
     * 查询标的详情
     * @author zhangyk
     * @date 2018/8/29 13:45
     */
    ApiResult getBorrowDetail(ApiBorrowReqBean reqBean);
}
