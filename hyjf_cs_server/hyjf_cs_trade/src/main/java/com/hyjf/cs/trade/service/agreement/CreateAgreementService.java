package com.hyjf.cs.trade.service.agreement;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.UserInvestListBeanRequest;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.borrow.DebtBorrowCustomizeVO;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.List;

/**
 * @author pangchengchao
 * @version CreateAgreementService, v0.1 2018/11/6 10:17
 */
public interface CreateAgreementService extends BaseTradeService {

    List<TenderAgreementVO> getIntermediaryAgreementPDFUrl(String nid);
}
