package com.hyjf.cs.trade.service.myasset;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.QueryMyProjectVO;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * @author pangchengchao
 * @version WechatMyProjectService, v0.1 2018/7/24 14:05
 */
public interface WechatMyProjectService extends BaseTradeService {
    void selectCurrentHoldObligatoryRightList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo);

    void selectRepaymentList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo);

    void selectCreditRecordList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo);

    void selectCurrentHoldPlanList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo);

    void selectRepayMentPlanList(Integer userId, int currentPage, int pageSize, QueryMyProjectVO vo);


}
