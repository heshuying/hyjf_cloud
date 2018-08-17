package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.ApplyAgreementRequestBean;
import com.hyjf.admin.beans.request.BorrowRepayAgreementRequestBean;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.ApplyAgreementService;
import com.hyjf.admin.service.TenderCancelExceptionService;
import com.hyjf.admin.utils.Page;
import com.hyjf.am.response.trade.ApplyAgreementResponse;
import com.hyjf.am.response.trade.BorrowRepayAgreementResponse;
import com.hyjf.am.resquest.admin.ApplyAgreementAmRequest;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementAmRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementRequest;
import com.hyjf.am.vo.trade.BorrowRepayAgreementVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.common.service.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version ApplyAgreementServiceImpl, v0.1 2018/8/9 16:51
 * @Author: Zha Daojian
 */
@Service
public class ApplyAgreementServiceImpl implements ApplyAgreementService {

    @Autowired
    protected TenderCancelExceptionService tenderCancelExceptionService;

    @Autowired
    private BaseClient baseClient;

    @Autowired
    private AmTradeClient amTradeClient;

    public static final String BASE_URL = "http://AM-TRADE/am-trade/applyAgreement";

    /*垫付协议申请列表*/
    public static final String AGREEMENT_LIST_URL = BASE_URL + "/getApplyAgreementList";

    /*垫付协议申请明细列表页count*/
    public static final String AGREEMENT_COUNT_URL = BASE_URL + "/getApplyAgreementCount";

    /*垫付协议申请列表-分期*/
    public static final String ADD_AGREEMENT_LIST_URL_PLAN = BASE_URL + "/getAddApplyAgreementPlanList";

    /*垫付协议申请明细列表页-不分期count*/
    public static final String ADD_AGREEMENT_COUNT_URL_PLAN = BASE_URL + "/getAddApplyAgreementPlanCount";

    /*垫付协议申请列表*/
    public static final String ADD_AGREEMENT_LIST_URL= BASE_URL + "/getAddApplyAgreementList";

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
        ApplyAgreementResponse response = baseClient.postExe(AGREEMENT_COUNT_URL, request, ApplyAgreementResponse.class);
        Integer count = response.getCount();
        if (count > 0) {
            response = baseClient.postExe(AGREEMENT_LIST_URL, request, ApplyAgreementResponse.class);
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
            String countUrl = "";//查询列表数量url
            String ListUrl = "";//查询列表url
            if(isMonth) {//分期
                countUrl =ADD_AGREEMENT_COUNT_URL_PLAN;
                ListUrl =ADD_AGREEMENT_LIST_URL_PLAN;
            }else{//不分期
                countUrl =ADD_AGREEMENT_COUNT_URL;
                ListUrl =ADD_AGREEMENT_LIST_URL;
            }
            BorrowRepayAgreementResponse response = baseClient.postExe(countUrl, req, BorrowRepayAgreementResponse.class);
            Integer count = response.getCount();
            if (count > 0) {
                response = baseClient.postExe(ListUrl, req, BorrowRepayAgreementResponse.class);
                List<BorrowRepayAgreementVO> list = response.getResultList();
                bean.setRecordList(list);
            }
            bean.setTotal(count);
            result.setData(bean);
        }
        return result;
    }

    /**
     * 批量生成垫付债转协议
     *
     * @param request
     * @author Zha Daojian
     * @date 2018/7/12 10:52
     */
    @Override
    public AdminResult generateContract(BorrowRepayAgreementRequest request) {

        AdminResult result = new AdminResult();
        return null;
    }

}
