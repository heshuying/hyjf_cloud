package com.hyjf.cs.trade.service.myasset;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.QueryMyProjectVO;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * @author pangchengchao
 * @version WechatMyProjectService, v0.1 2018/7/24 14:05
 */
public interface WechatMyProjectService extends BaseTradeService {
    QueryMyProjectVO selectCurrentHoldObligatoryRightList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo);

    QueryMyProjectVO selectRepaymentList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo);

    QueryMyProjectVO selectCreditRecordList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo);

    QueryMyProjectVO selectCurrentHoldPlanList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo);

    QueryMyProjectVO selectRepayMentPlanList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo);


}
