package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.response.trade.FddTempletResponse;
import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.resquest.trade.BorrowTenderUtmRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.auto.CreditTenderLog;
import com.hyjf.am.trade.dao.model.auto.FddTemplet;
import com.hyjf.am.trade.dao.model.auto.TenderAgreement;
import com.hyjf.am.trade.service.BorrowTenderService;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiasq
 * @version BorrowTenderController, v0.1 2018/6/23 11:42
 */
@RestController
@RequestMapping("/am-trade/borrowTender")
public class BorrowTenderController extends BaseController{

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

    /**
     * 根据nid获取一条记录
     * @auther: hesy
     * @date: 2018/7/13
     */
    @GetMapping("/getBorrowTenderByNid/{nid}")
    public BorrowTenderResponse getBorrowTenderByNid(@PathVariable String nid){
        BorrowTenderResponse response = new BorrowTenderResponse();
        List<BorrowTender> tenderList = borrowTenderService.getBorrowTenderListByNid(nid);
        if (CollectionUtils.isNotEmpty(tenderList)){
            response.setResult(CommonUtils.convertBean(tenderList.get(0),BorrowTenderVO.class));
        }
        return response;
    }

    /**
     * 根据投资订单号查询已承接金额
     * @param tenderNid
     * @return
     */
    @GetMapping("/get_assign_capital_by_tender_nid/{tenderNid}")
    public BigDecimal getAssignCapital(@PathVariable String tenderNid){
        return borrowTenderService.getAssignCapitalByTenderNid(tenderNid);
    }

    /**
     * 保存债转日志
     * @param creditTenderLogVO
     * @return
     */
    @PostMapping("/save_credit_tender_assign_log")
    public Integer saveCreditTenderAssignLog(@RequestBody CreditTenderLogVO creditTenderLogVO){
        CreditTenderLog bean = CommonUtils.convertBean(creditTenderLogVO,CreditTenderLog.class);
        return borrowTenderService.saveCreditTenderAssignLog(bean);
    }

	/**
	 * 获取utm注册用户投资次数
	 * 
	 * @return
	 */
	@PostMapping("/getutmtendernum")
	public BorrowTenderResponse getUtmTenderNum(@RequestBody BorrowTenderUtmRequest request) {
		BorrowTenderResponse response = new BorrowTenderResponse();
		// utm注册用户的userid集合
		List<Integer> list = request.getList();
        Integer tenderNum = borrowTenderService.getUtmTenderNum(list);
		if (tenderNum != null) {
			response.setTenderCount(tenderNum);
		}
		return response;
	}
}
