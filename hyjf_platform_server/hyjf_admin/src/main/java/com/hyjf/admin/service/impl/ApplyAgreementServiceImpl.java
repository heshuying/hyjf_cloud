package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.ApplyAgreementRequestBean;
import com.hyjf.admin.beans.BorrowCreditRepayResultBean;
import com.hyjf.admin.beans.BorrowRepayAgreementRequestBean;
import com.hyjf.admin.beans.request.ApplyAgreementRequest;
import com.hyjf.admin.beans.request.BorrowRepayAgreementRequest;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.ApplyAgreementService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.ApplyAgreemenResponse;
import com.hyjf.am.response.trade.BorrowCreditTenderResponse;
import com.hyjf.am.response.trade.BorrowRepayAgreementResponse;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.resquest.admin.ApplyAgreementAmRequest;
import com.hyjf.am.resquest.admin.BorrowCreditRepayAmRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementAmRequest;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.BorrowRepayAgreementVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.common.service.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version ApplyAgreementServiceImpl, v0.1 2018/8/9 16:51
 * @Author: Zha Daojian
 */
public class ApplyAgreementServiceImpl implements ApplyAgreementService {

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private AmTradeClient amTradeClient;

    public static final String BASE_URL = "http://AM-TRADE/am-trade/applyAgreement";

    /*垫付协议申请列表*/
    public static final String AGREEMENT_LIST_URL = BASE_URL + "/getApplyAgreementList";

    /*垫付协议申请明细列表页count*/
    public static final String AGREEMENT_COUNT_URL = BASE_URL + "/getApplyAgreementCount";

    /*垫付协议申请列表*/
    public static final String ADD_AGREEMENT_LIST_URL = BASE_URL + "/getAddApplyAgreementList";

    /*垫付协议申请明细列表页count*/
    public static final String ADD_AGREEMENT_COUNT_URL = BASE_URL + "/getAddApplyAgreementCount";

    /**
     * 查询垫付协议申请列表
     * @author Zha Daojian
     * @date 2018/7/11 14:34
     */
    @Override
    public AdminResult getApplyAgreementList(ApplyAgreementRequest request){
        AdminResult result = new AdminResult();
        ApplyAgreementRequestBean bean = new ApplyAgreementRequestBean();
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        ApplyAgreementAmRequest req = CommonUtils.convertBean(request, ApplyAgreementAmRequest.class);
        req.setLimitStart(page.getOffset());
        req.setLimitEnd(page.getLimit());
        ApplyAgreemenResponse response = baseClient.postExe(AGREEMENT_COUNT_URL, request, ApplyAgreemenResponse.class);
        Integer count = response.getCount();
        if (count > 0) {
            response = baseClient.postExe(AGREEMENT_LIST_URL, request, ApplyAgreemenResponse.class);
            List<ApplyAgreementVO> list = response.getResultList();
            bean.setRecordList(list);
        }
        bean.setTotal(count);
        result.setData(bean);
        return result;
    }


    /**
     * 垫付协议申请明细列表页
     * @author Zha Daojian
     * @date 2018/7/12 10:52
     */
    @Override
    public AdminResult getAddApplyAgreementListDetail(BorrowRepayAgreementRequest request){
        AdminResult result = new AdminResult();
        BorrowRepayAgreementRequestBean bean = new BorrowRepayAgreementRequestBean();
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        BorrowRepayAgreementAmRequest req = CommonUtils.convertBean(request, BorrowRepayAgreementAmRequest.class);
        req.setLimitStart(page.getOffset());
        req.setLimitEnd(page.getLimit());
        // 根据标的编号查询标的详情
        BorrowVO borrowVO = amTradeClient.searchBorrowByBorrowNid(request.getBorrowNidSrch());
        if (borrowVO == null) {
            throw new RuntimeException("根据标的编号查询标的详情失败,标的编号:[" + request.getBorrowNidSrch() + "].");
        }else{
            // 还款方式
            String borrowStyle = borrowVO.getBorrowStyle();
            // 是否分期(true:分期, false:不分期)
            boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                    || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
            if(isMonth) {//分期
            }else{//不分期

            }
            BorrowRepayAgreementResponse response = baseClient.postExe(ADD_AGREEMENT_COUNT_URL, req, BorrowRepayAgreementResponse.class);
            Integer count = response.getCount();
            if (count > 0) {
                response = baseClient.postExe(ADD_AGREEMENT_LIST_URL, req, BorrowRepayAgreementResponse.class);
                List<BorrowRepayAgreementVO> list = response.getResultList();
                bean.setRecordList(list);
            }
            bean.setTotal(count);
            result.setData(bean);
        }
        return result;
    }

}
