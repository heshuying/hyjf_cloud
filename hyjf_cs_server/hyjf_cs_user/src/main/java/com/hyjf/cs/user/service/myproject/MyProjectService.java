package com.hyjf.cs.user.service.myproject;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.QueryMyProjectVO;
import com.hyjf.cs.common.service.BaseService;

/**
 *  jijun
 *  20180727
 */
public interface MyProjectService extends BaseService {


    void selectCurrentHoldObligatoryRightList(String userId, int currentPage, int pageSize, QueryMyProjectVO vo);

    void selectRepaymentList(String userId, int currentPage, int pageSize, QueryMyProjectVO vo);

    void selectCreditRecordList(String userId, int currentPage, int pageSize, QueryMyProjectVO vo);

    void selectCurrentHoldPlanList(String userId, int currentPage, int pageSize, QueryMyProjectVO vo);

    void selectRepayMentPlanList(String userId, int currentPage, int pageSize, QueryMyProjectVO vo);

    AccountVO getAccount(Integer userId);
}
