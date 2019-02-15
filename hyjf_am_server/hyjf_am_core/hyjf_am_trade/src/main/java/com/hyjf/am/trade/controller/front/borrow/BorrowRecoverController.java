/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.borrow;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowRecoverPlanResponse;
import com.hyjf.am.response.trade.BorrowRecoverResponse;
import com.hyjf.am.trade.bean.repay.RepayBean;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.BorrowRecover;
import com.hyjf.am.trade.dao.model.auto.BorrowRecoverPlan;
import com.hyjf.am.trade.dao.model.auto.BorrowRepay;
import com.hyjf.am.trade.service.front.borrow.BorrowRecoverService;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.RightBorrowVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * @author jijun
 * @date 20180630
 */

@Api(value = "借款信息")
@RestController
@RequestMapping("/am-trade/borrowRecover")
public class BorrowRecoverController extends BaseController {

	@Autowired
	BorrowRecoverService borrowRecoverService;

	@GetMapping("/selectBorrowRecoverByTenderNid/{tenderAgreementID}")
	public BorrowRecoverResponse selectBorrowRecoverByTenderNid(@PathVariable String tenderAgreementID){
		BorrowRecoverResponse response = new BorrowRecoverResponse();
		BorrowRecover borrowRecover=borrowRecoverService.selectBorrowRecoverByTenderNid(tenderAgreementID);
		if (Validator.isNotNull(borrowRecover)){
			response.setResult(CommonUtils.convertBean(borrowRecover,BorrowRecoverVO.class));
		}
		return response;
	}

	@GetMapping("/select_by_id/{id}")
	public BorrowRecoverResponse selectBorrowRecoverById(@PathVariable Integer id){
		BorrowRecoverResponse response = new BorrowRecoverResponse();
		BorrowRecover borrowRecover=borrowRecoverService.selectBorrowRecoverById(id);
		if (Validator.isNotNull(borrowRecover)){
			response.setResult(CommonUtils.convertBean(borrowRecover,BorrowRecoverVO.class));
		}
		return response;
	}

	@GetMapping("/select_by_borrownid/{borrowNid}")
	public BorrowRecoverResponse selectByBorrowNid(@PathVariable String borrowNid) {
		BorrowRecoverResponse response = new BorrowRecoverResponse();
		List<BorrowRecover> recoverList=borrowRecoverService.selectByBorrowNid(borrowNid);
		if (recoverList != null){
			List<BorrowRecoverVO> resultList = CommonUtils.convertBeanList(recoverList,BorrowRecoverVO.class);
			response.setResultList(resultList);
		}
		return response;
	}

	@GetMapping("/select_by_nid/{nid}")
	public BorrowRecoverResponse selectBorrowRecoverByNid(@PathVariable String nid){
		BorrowRecoverResponse response = new BorrowRecoverResponse();
		BorrowRecover borrowRecover=borrowRecoverService.selectBorrowRecoverByNid(nid);
		if (Validator.isNotNull(borrowRecover)){
			response.setResult(CommonUtils.convertBean(borrowRecover,BorrowRecoverVO.class));
		}
		return response;
	}


	@PostMapping("updateBorrowRecover")
	public boolean updateBorrowRecover(@RequestBody BorrowRecoverVO borrowRecover) {
		int count=borrowRecoverService.updateBorrowRecover(CommonUtils.convertBean(borrowRecover,BorrowRecover.class));
		if (count>0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 根据tenderNid和bidNid查询
	 * @param tenderNid
	 * @param bidNid
	 * @return
	 */
	@GetMapping("/getBorrowRecoverByTenderNidBidNid/{tenderNid}/{bidNid}")
	public BorrowRecoverResponse getBorrowRecoverByTenderNidBidNid(@PathVariable String tenderNid,@PathVariable String bidNid){
		BorrowRecoverResponse response = new BorrowRecoverResponse();
		BorrowRecover borrowRecover=borrowRecoverService.getBorrowRecoverByTenderNidBidNid(tenderNid,bidNid);
		if (Validator.isNotNull(borrowRecover)){
			response.setResult(CommonUtils.convertBean(borrowRecover,BorrowRecoverVO.class));
		}
		return response;
	}

	/**
	 * 根据tenderNid查询
	 * @param tenderNid
	 * @return
	 */
	@GetMapping("/getBorrowRecoverByTenderNid/{tenderNid}")
	public BorrowRecoverResponse getBorrowRecoverByTenderNid(@PathVariable String tenderNid){
		BorrowRecoverResponse response = new BorrowRecoverResponse();
		BorrowRecover borrowRecover=borrowRecoverService.getBorrowRecoverByTenderNid(tenderNid);
		if (Validator.isNotNull(borrowRecover)){
			response.setResult(CommonUtils.convertBean(borrowRecover,BorrowRecoverVO.class));
		}
		return response;
	}

	/**
	 * 获取borrow_recover_plan更新每次还款时间
	 * @param bidNid
	 * @param creditTenderNid
	 * @param periodNow
	 * @return
	 */
	@GetMapping("/getPlanByBidTidPeriod/{bidNid}/{creditTenderNid}/{periodNow}")
	public BorrowRecoverPlanResponse getPlanByBidTidPeriod(@PathVariable String bidNid, @PathVariable String creditTenderNid, @PathVariable Integer periodNow){
		BorrowRecoverPlanResponse response = new BorrowRecoverPlanResponse();
		BorrowRecoverPlan borrowRecover=borrowRecoverService.getPlanByBidTidPeriod(bidNid,creditTenderNid,periodNow);
		if (Validator.isNotNull(borrowRecover)){
			response.setResult(CommonUtils.convertBean(borrowRecover,BorrowRecoverPlanVO.class));
		}
		return response;
	}
	/**
	 * 判断是否逾期 逾期或延期时返回false 逾期或延期时不计算提前还款提前还款减息
	 * @param borrow
	 * @return
	 */
	@RequestMapping("/getOverDueFlag")
	public BooleanResponse getOverDueFlag(@RequestBody RightBorrowVO borrow){
		Boolean flag=true;
		RepayBean repay = new RepayBean();
		BooleanResponse response=new BooleanResponse();
		// 获取还款总表数据
		BorrowRepay borrowRepay = borrowRecoverService.getRepayPlanByUserIdAndBorrowNid(borrow.getUserId(),borrow.getBorrowNid());
		// 判断是否存在还款数据
		if (borrowRepay != null) {
			// 获取相应的还款信息
			BeanUtils.copyProperties(borrowRepay, repay);
			// 计划还款时间
			Integer repayTimeStr = borrowRepay.getRepayTime();
			// 获取用户申请的延期天数
			int delayDays = borrowRepay.getDelayDays().intValue();
			repay.setBorrowPeriod(String.valueOf(borrow.getBorrowPeriod()));
			// 未分期默认传分期为0
			try {
				flag =this.calculateRecover(repay, borrow, repayTimeStr, delayDays,flag);
			}catch (Exception e){
				logger.info("判断用户是否逾期失败......");
				e.printStackTrace();
				return null;
			}
		}
		response.setResultBoolean(flag);
		return response;
	}


	public  Boolean calculateRecover(RepayBean repay, RightBorrowVO borrow, Integer repayTimeStr, int delayDays, Boolean flag)throws ParseException {
		int time = GetDate.getNowTime10();
		// 用户计划还款时间
		String repayTime = GetDate.getDateTimeMyTimeInMillis(repayTimeStr);
		// 用户实际还款时间
		String factRepayTime = GetDate.getDateTimeMyTimeInMillis(time);
		int distanceDays = GetDate.daysBetween(factRepayTime, repayTime);
		String borrowStyle = borrow.getBorrowStyle();
		if (distanceDays < 0) {// 用户延期或者逾期了
			flag =false;
		}
		return flag;
	}

	/**
	 * 根据borrowNid，tenderNid，accedeOrderId查找放款记录
	 * add by nxl(应急中心)
	 * @param borrowRecoverRequest
	 * @return
	 */
	@PostMapping("getRecoverDateByTenderNid")
	public BorrowRecoverResponse getRecoverDateByTenderNid(@RequestBody BorrowRecoverVO borrowRecoverRequest) {
		BorrowRecoverResponse response = new BorrowRecoverResponse();
		response.setRtn(Response.FAIL);
		BorrowRecover borrowRecover = borrowRecoverService.getRecoverDateByTenderNid(borrowRecoverRequest.getNid(),borrowRecoverRequest.getBorrowNid(),borrowRecoverRequest.getAccedeOrderId());
		if(Validator.isNotNull(borrowRecover)){
			response.setResult(CommonUtils.convertBean(borrowRecover,BorrowRecoverVO.class));
			response.setRtn(Response.SUCCESS);
		}
		return response;
	}

	/**
	 * 服务费=放款服务费+还款服务费
	 */
	@GetMapping("/selectServiceCostSum/{borrowNid}")
	public String selectServiceCostSum(@PathVariable String borrowNid){
		BigDecimal serviceFee=borrowRecoverService.selectServiceCostSum(borrowNid);
		return serviceFee.toString();
	}

}
