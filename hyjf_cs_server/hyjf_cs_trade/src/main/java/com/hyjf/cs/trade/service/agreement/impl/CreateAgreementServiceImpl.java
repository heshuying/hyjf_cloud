package com.hyjf.cs.trade.service.agreement.impl;

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
    public List<BorrowCustomizeVO> selectBorrowList(BorrowCommonCustomizeVO borrowCommonCustomize) {
        return amTradeClient.searchBorrowCustomizeList(borrowCommonCustomize);
    }

    @Override
    public List<TenderAgreementVO> selectTenderAgreementByNid(String nid) {
        return this.amTradeClient.selectTenderAgreementByNid(nid);
    }

    @Override
    public List<WebUserInvestListCustomizeVO> selectUserInvestList(UserInvestListBeanRequest form) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("borrowNid", form.getBorrowNid());
        params.put("userId", form.getUserId());
        params.put("nid", form.getNid());

        return amTradeClient.selectUserInvestList(params);
    }

    @Override
    public ProjectCustomeDetailVO selectProjectDetail(String borrowNid) {
        return amTradeClient.selectProjectDetail(borrowNid);
    }

    @Override
    public List<DebtBorrowCustomizeVO> selectDebtBorrowList(DebtBorrowCommonCustomizeVO debtBorrowCommonCustomize) {
        Map<String,Object> param = new HashMap<>();
        String borrowNid = debtBorrowCommonCustomize.getBorrowNid();
        param.put("borrowNidSrch",borrowNid);
        return amTradeClient.searchDebtBorrowList4Protocol(param);
    }

    @Override
    public int countProjectRepayPlanRecordTotal(ProjectRepayListBeanVO bean) {
        Map<String,Object> paraBean = new HashMap<>();
        paraBean.put("userId", bean.getUserId());
        paraBean.put("borrowNid", bean.getBorrowNid());
        paraBean.put("nid", bean.getNid());
        int recordTotal = amTradeClient.countProjectRepayPlanRecordTotal(paraBean);
        return recordTotal;
    }

    @Override
    public List<WebProjectRepayListCustomizeVO> selectProjectRepayPlanList(ProjectRepayListBeanVO bean) {
        Map<String,Object> paraBean = new HashMap<>();
        paraBean.put("userId", bean.getUserId());
        paraBean.put("borrowNid", bean.getBorrowNid());
        paraBean.put("nid", bean.getNid());
        return  amTradeClient.selectProjectRepayPlanList(paraBean);
    }
}
