/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.aems.invest;

import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.vo.api.ApiRepayListCustomizeVO;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.trade.bean.api.ApiInvestListReqBean;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.List;

/**
 * AEMS系统:第三方查询投资记录Service
 *
 * @author liuyang
 * @version AemsBorrowListService, v0.1 2018/12/13 17:01
 */
public interface AemsBorrowListService extends BaseTradeService {

    /**
     * 获取回款记录
     *
     * @param request
     * @return
     */
    List<ApiRepayListCustomizeVO> searchRepayList(ApiRepayListRequest request);

    /**
     * 获取投资记录
     *
     * @param bean
     * @return
     */
    ApiResult getInvestList(ApiInvestListReqBean bean);
}
