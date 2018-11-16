package com.hyjf.cs.trade.service.agreement.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.resquest.trade.UserInvestListBeanRequest;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.borrow.DebtBorrowCustomizeVO;
import com.hyjf.cs.trade.service.agreement.CreateAgreementService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version CreateAgreementServiceImpl, v0.1 2018/11/6 10:18
 */
@Service
public class CreateAgreementServiceImpl extends BaseTradeServiceImpl implements CreateAgreementService {
    @Override
    public List<TenderAgreementVO> getIntermediaryAgreementPDFUrl(String nid) {

        return amTradeClient.selectTenderAgreementByNid(nid);
    }
}
