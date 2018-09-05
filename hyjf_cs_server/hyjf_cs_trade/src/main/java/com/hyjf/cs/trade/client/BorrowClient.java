package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.trade.bean.BatchCenterCustomize;
import com.hyjf.cs.trade.bean.repay.ProjectBean;

import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version BorrowClient, v0.1 2018/6/19 15:33
 */
public interface BorrowClient {
    BorrowVO selectBorrowByNid(String borrowNid);

    ProjectBean searchRepayProjectDetail(ProjectBean form);

    BankOpenAccountVO getBankOpenAccount(String bankAccount);

    List<WebUserRepayProjectListCustomizeVO> selectUserRepayProjectList(Map<String, Object> params);

    List<WebUserRepayProjectListCustomizeVO> selectOrgRepayProjectList(Map<String, Object> params);

    Long countBatchCenter (BatchCenterCustomize batchCenterCustomize);

    List<BatchCenterCustomize> selectBatchCenterList (BatchCenterCustomize batchCenterCustomize);

    String getborrowIdByProductId (Map<String, Object> params);
}
