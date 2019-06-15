/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.borrow;

import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.trade.BorrowRecoverResponse;
import com.hyjf.am.response.trade.TenderAgreementResponse;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.dao.model.customize.BorrowInvestCustomize;
import com.hyjf.am.trade.dao.model.customize.BorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebProjectRepayListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebUserInvestListCustomize;
import com.hyjf.am.trade.service.admin.borrow.BorrowInvestService;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangjun
 * @version BorrowInvestController, v0.1 2018/7/10 9:33
 */
@RestController
@RequestMapping("/am-trade/borrow_invest")
public class BorrowInvestController extends BaseController {
    @Autowired
    BorrowInvestService borrowInvestService;

    /**
     * 出借明细记录 总数COUNT
     *
     * @return
     */
    @RequestMapping("/count_borrow_invest")
    public BorrowInvestCustomizeResponse countBorrowInvest(@RequestBody @Valid BorrowInvestRequest borrowInvestRequest) {
        BorrowInvestCustomizeResponse response = new BorrowInvestCustomizeResponse();
        int count = borrowInvestService.countBorrowFirst(borrowInvestRequest);
        response.setTotal(count);
        return response;
    }

    /**
     * 出借明细列表
     *
     * @return
     */
    @RequestMapping("/select_borrow_invest_list")
    public BorrowInvestCustomizeResponse selectBorrowInvestList(@RequestBody @Valid BorrowInvestRequest borrowInvestRequest) {
        BorrowInvestCustomizeResponse response = new BorrowInvestCustomizeResponse();
        List<BorrowInvestCustomize> list = borrowInvestService.selectBorrowInvestList(borrowInvestRequest);
        if (!CollectionUtils.isEmpty(list)) {
            List<BorrowInvestCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowInvestCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 出借明细列表合计
     *
     * @return
     */
    @RequestMapping("/select_borrow_invest_account")
    public BorrowInvestCustomizeResponse selectBorrowInvestAccount(@RequestBody @Valid BorrowInvestRequest borrowInvestRequest) {
        BorrowInvestCustomizeResponse response = new BorrowInvestCustomizeResponse();
        String sumAccount = borrowInvestService.selectBorrowInvestAccount(borrowInvestRequest);
        response.setSumAccount(sumAccount);
        return response;
    }

    /**
     * 出借明细列表
     *
     * @return
     */
    @RequestMapping("/export_borrow_invest_list")
    public BorrowInvestCustomizeResponse getExportBorrowInvestList(@RequestBody @Valid BorrowInvestRequest borrowInvestRequest) {
        BorrowInvestCustomizeResponse response = new BorrowInvestCustomizeResponse();
        List<BorrowInvestCustomize> list = borrowInvestService.getExportBorrowInvestList(borrowInvestRequest);
        if (!CollectionUtils.isEmpty(list)) {
            List<BorrowInvestCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowInvestCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 获取用户出借协议
     * @param nid
     * @return
     */
    @RequestMapping("/tender_agreement/{nid}")
    public TenderAgreementResponse selectTenderAgreementByNid(@PathVariable(value = "nid") String nid){
        TenderAgreementResponse response = new TenderAgreementResponse();
        TenderAgreement tenderAgreement = borrowInvestService.selectTenderAgreementByNid(nid);
        if(tenderAgreement !=null){
            TenderAgreementVO vo = new TenderAgreementVO();
            BeanUtils.copyProperties(tenderAgreement,vo);
            response.setResult(vo);
        }
        return response;
    }

    /**
     * 获取用户出借协议
     * @param nid
     * @return
     */
    @RequestMapping("/select_borrow_recover/{userId}/{borrowNid}/{nid}")
    public BorrowRecoverResponse selectBorrowRecover(@PathVariable(value = "userId") Integer userId,@PathVariable(value = "borrowNid") String borrowNid,@PathVariable(value = "nid") String nid){
        BorrowRecoverResponse response = new BorrowRecoverResponse();
        BorrowRecover borrowRecover = borrowInvestService.selectBorrowRecover(userId,borrowNid,nid);
        if(borrowRecover !=null){
            BorrowRecoverVO vo = new BorrowRecoverVO();
            BeanUtils.copyProperties(borrowRecover,vo);
            response.setResult(vo);
        }
        return response;
    }

    /**
     * 获取借款列表
     * @param borrowNid
     * @return
     */
    @RequestMapping("/select_borrow_list/{borrowNid}")
    public BorrowListCustomizeResponse selectBorrowList(@PathVariable(value = "borrowNid") String borrowNid){
        BorrowListCustomizeResponse response = new BorrowListCustomizeResponse();
        List<BorrowListCustomize> list = borrowInvestService.selectBorrowList(borrowNid);
        if (!CollectionUtils.isEmpty(list)) {
            List<BorrowListCustomizeVO> voList = CommonUtils.convertBeanList(list, BorrowListCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 标的出借信息
     * @param borrowInvestRequest
     * @return
     */
    @RequestMapping("/select_user_invest_list")
    public WebUserInvestListCustomizeResponse selectUserInvestList(@RequestBody @Valid BorrowInvestRequest borrowInvestRequest){
        WebUserInvestListCustomizeResponse response = new WebUserInvestListCustomizeResponse();
        List<WebUserInvestListCustomize> list = borrowInvestService.selectUserInvestList(borrowInvestRequest);
        if(!CollectionUtils.isEmpty(list)){
            List<WebUserInvestListCustomizeVO> voList = CommonUtils.convertBeanList(list, WebUserInvestListCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 标的放款记录-分期 count
     * @param borrowInvestRequest
     * @return
     */
    @RequestMapping("/count_project_repay")
    public WebProjectRepayListCustomizeResponse countProjectRepayPlanRecordTotal(@RequestBody @Valid BorrowInvestRequest borrowInvestRequest){
        WebProjectRepayListCustomizeResponse response = new WebProjectRepayListCustomizeResponse();
        int count = borrowInvestService.countProjectRepayPlanRecordTotal(borrowInvestRequest);
        response.setTotal(count);
        return response;
    }

    /**
     * 标的放款记录-分期
     * @param borrowInvestRequest
     * @return
     */
    @RequestMapping("/select_project_repay")
    public WebProjectRepayListCustomizeResponse selectProjectRepayPlanList(@RequestBody @Valid BorrowInvestRequest borrowInvestRequest){
        WebProjectRepayListCustomizeResponse response = new WebProjectRepayListCustomizeResponse();
        List<WebProjectRepayListCustomize> list = borrowInvestService.selectProjectRepayPlanList(borrowInvestRequest);
        if(!CollectionUtils.isEmpty(list)){
            List<WebProjectRepayListCustomizeVO> voList = CommonUtils.convertBeanList(list,WebProjectRepayListCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    /**
     * 更新标的放款记录
     * @param borrowInvestRequest
     * @return
     */
    @RequestMapping("/update_borrow_recover")
    public BorrowInvestCustomizeResponse updateBorrowRecover(@RequestBody @Valid BorrowInvestRequest borrowInvestRequest){
        BorrowInvestCustomizeResponse response = new BorrowInvestCustomizeResponse();
        int count = borrowInvestService.updateBorrowRecover(borrowInvestRequest);
        response.setTotal(count);
        return response;
    }

    /**
     * 订单id查询出借明细
     *
     * @return
     */
    @RequestMapping("/select_borrow_invest/{nid}")
    public BorrowInvestCustomizeExtResponse selectBorrowInvestList(@PathVariable(name = "nid") String nid) {
        BorrowInvestCustomizeExtResponse response = new BorrowInvestCustomizeExtResponse();
        BorrowInvestCustomizeExtVO vo = borrowInvestService.selectBorrowInvestByNid(nid);
        response.setResult(vo);
        return response;
    }
}
