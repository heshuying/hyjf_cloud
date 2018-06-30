package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.FddTempletResponse;
import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.FddTemplet;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.trade.service.BorrowTenderService;
import springfox.documentation.service.ApiListing;

import java.util.List;

@RestController
@RequestMapping("/am-trade/borrowTender")
public class BorrowTenderController {

    @Autowired
    private BorrowTenderService borrowTenderService;


    /**
     * 获取投资笔数
     * @author zhangyk
     * @date 2018/6/26 9:31
     */
    @GetMapping("/countUserInvest/{borrowNid}/{userId}")
    public BorrowTenderResponse countUserInvest(@PathVariable String borrowNid,@PathVariable Integer userId) {
        BorrowTenderResponse response = new BorrowTenderResponse();
        Integer count  = borrowTenderService.getCountBorrowTenderService(userId, borrowNid);
        response.setTenderCount(count);
        return response;
    }


    @PostMapping("/selectBorrowTender")
    public BorrowTenderResponse selectBorrowTender(@RequestBody BorrowTenderRequest request){
        BorrowTenderResponse response = new BorrowTenderResponse();
        BorrowTender borrowTender =borrowTenderService.selectBorrowTender(request);
        if (Validator.isNotNull(borrowTender)){
            response.setResult(CommonUtils.convertBean(borrowTender,BorrowTenderVO.class));
        }
        return response;
    }

    @GetMapping("/getFddTempletList/{protocolType}")
    public FddTempletResponse getFddTempletList(@PathVariable Integer protocolType){
        FddTempletResponse response = new FddTempletResponse();
        List<FddTemplet> fddTempletList = borrowTenderService.getFddTempletList(protocolType);
        if (CollectionUtils.isNotEmpty(fddTempletList)){
            response.setResultList(CommonUtils.convertBeanList(fddTempletList,FddTempletVO.class));
        }
        return response;
    }


    @PostMapping("/saveTenderAgreement")
    public int saveTenderAgreement(@RequestBody TenderAgreementVO info){
        TenderAgreement tenderAgreement=CommonUtils.convertBean(info,TenderAgreement.class);
        return borrowTenderService.saveTenderAgreement(tenderAgreement);
    }

    @PostMapping("/updateTenderAgreement")
    public int updateTenderAgreement(@RequestBody TenderAgreementVO tenderAgreement){
        TenderAgreement ta = CommonUtils.convertBean(tenderAgreement,TenderAgreement.class);
        return borrowTenderService.updateTenderAgreement(ta);
    }


    @GetMapping("/getBorrowTenderListByNid/{nid}")
    public BorrowTenderResponse getBorrowTenderListByNid(@PathVariable String nid){
        BorrowTenderResponse response = new BorrowTenderResponse();
        List<BorrowTender> tenderList = borrowTenderService.getBorrowTenderListByNid(nid);
        if (CollectionUtils.isNotEmpty(tenderList)){
            response.setResultList(CommonUtils.convertBeanList(tenderList,BorrowTenderVO.class));
        }
        return response;
    }


}
