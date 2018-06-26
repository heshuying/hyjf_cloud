/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.BorrowConfigResponse;
import com.hyjf.am.response.trade.BorrowFinmanNewChargeResponse;
import com.hyjf.am.response.trade.BorrowInfoResponse;
import com.hyjf.am.response.trade.BorrowResponse;
import com.hyjf.am.response.user.RecentPaymentListCustomizeResponse;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.web.RecentPaymentListCustomize;
import com.hyjf.am.trade.service.BorrowService;
import com.hyjf.am.trade.service.UserService;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.user.RecentPaymentListCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version BorrowController, v0.1 2018/5/28 12:42
 */

@Api(value = "借款信息")
@RestController
@RequestMapping("/am-trade/borrow")
public class BorrowController {

	@Autowired
	UserService userService;

	@Autowired
	BorrowService borrowService;

	/**
	 * 根据项目类型，期限，获取借款利率
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/selectBorrowApr")
	public BorrowFinmanNewChargeResponse selectBorrowApr(@RequestBody BorrowFinmanNewChargeRequest request) {
		BorrowFinmanNewChargeResponse response = new BorrowFinmanNewChargeResponse();
		BorrowFinmanNewCharge borrowFinmanNewCharge = borrowService.selectBorrowApr(request);
		if (borrowFinmanNewCharge != null) {
			BorrowFinmanNewChargeVO borrowFinmanNewChargeVO = new BorrowFinmanNewChargeVO();
			BeanUtils.copyProperties(borrowFinmanNewCharge, borrowFinmanNewChargeVO);
			response.setResult(borrowFinmanNewChargeVO);
		}
		return response;
	}

	/**
	 * 获取系统配置
	 * 
	 * @param configCd
	 * @return
	 */
	@RequestMapping("/getBorrowConfig/{configCd}")
	public BorrowConfigResponse getBorrowConfig(@PathVariable String configCd) {
		BorrowConfigResponse response = new BorrowConfigResponse();
		BorrowConfig borrowConfig = borrowService.getBorrowConfigByConfigCd(configCd);
		if (borrowConfig != null) {
			BorrowConfigVO borrowConfigVO = new BorrowConfigVO();
			BeanUtils.copyProperties(borrowConfig, borrowConfigVO);
			response.setResult(borrowConfigVO);
		}
		return response;
	}

	/**
	 * 借款表插入
	 * 
	 * @param borrow
	 */
	@RequestMapping("/insertBorrow")
	public int insertBorrow(@RequestBody Borrow borrow) {
		return borrowService.insertBorrow(borrow);
	}

	/**
	 * 个人信息
	 * 
	 * @param borrowManinfoVO
	 * @return
	 */
	@RequestMapping("/insertBorrowManinfo")
	public int insertBorrowManinfo(@RequestBody BorrowManinfoVO borrowManinfoVO) {
		BorrowManinfo borrowManinfo = new BorrowManinfo();
		if (borrowManinfo != null) {
			BeanUtils.copyProperties(borrowManinfoVO, borrowManinfo);
			return borrowService.insertBorrowManinfo(borrowManinfo);
		}
		return 0;
	}

	@RequestMapping("/updateBorrowRegist")
	public int updateBorrowRegist(@RequestBody BorrowRegistRequest request) {
		return borrowService.updateBorrowRegist(request);
	}

	@GetMapping("/getBorrow/{borrowNid}")
	public BorrowResponse getBorrow(@PathVariable String borrowNid) {
		BorrowResponse response = new BorrowResponse();
		Borrow borrow = borrowService.getBorrow(borrowNid);
		if (borrow != null) {
			BorrowVO borrowVO = new BorrowVO();
			BeanUtils.copyProperties(borrow, borrowVO);
			response.setResult(borrowVO);
		}
		return response;
	}

	@RequestMapping("/selectRecentPaymentList/{userId}")
	public RecentPaymentListCustomizeResponse selectRecentPaymentList(@PathVariable int userId){
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("userId", userId);
		paraMap.put("limitStart", 0);
		paraMap.put("limitEnd", 4);
		List<RecentPaymentListCustomize> recentPaymentListCustomizeList = userService.selectRecentPaymentList(paraMap);
		RecentPaymentListCustomizeResponse response = new RecentPaymentListCustomizeResponse();
		if(null!= recentPaymentListCustomizeList){
			List<RecentPaymentListCustomizeVO> recentPaymentListCustomizeVOS = CommonUtils.convertBeanList(recentPaymentListCustomizeList,RecentPaymentListCustomizeVO.class);
			response.setResultList(recentPaymentListCustomizeVOS);
		}
		return response;
	}

	/*@GetMapping("/getBorrowWithBLOBsByNid/{borrowNid}")
	public BorrowWithBLOBsResponse getBorrowWithBLOBsByNid(@PathVariable String borrowNid){
		BorrowWithBLOBsResponse response = new BorrowWithBLOBsResponse();
		BorrowWithBLOBsVO borrowWithBLOBs=borrowService.getBorrowWithBLOBsByNid(borrowNid);
		if (borrowWithBLOBs!=null){
			response.setResult(borrowWithBLOBs);
		}
		return response;
	}
*/

    /**
     * 检索正在还款中的标的
     *
     * @Author liushouyi
     * @return
     */
	@RequestMapping("/selectBorrowList")
	public BorrowResponse selectBorrowList(){

		BorrowResponse response = new BorrowResponse();
		List<Borrow> borrows = borrowService.selectBorrowList();
		if (borrows != null) {
			List<BorrowVO> borrowVO = CommonUtils.convertBeanList(borrows,BorrowVO.class);
			response.setResultList(borrowVO);
		}
		return response;
	}


	@GetMapping("/getBorrowByNid/{borrowId}")
	public BorrowResponse getBorrowByNid(@PathVariable String borrowId){
		BorrowResponse response = new BorrowResponse();
		Borrow borrow = borrowService.getBorrow(borrowId);
		if (Validator.isNotNull(borrow)){
			response.setResult(CommonUtils.convertBean(borrow,BorrowVO.class));
		}
		return response;
	}

	@GetMapping("/getBorrowInfoByNid/{borrowNid}")
	public BorrowInfoResponse getBorrowInfoByNid(@PathVariable String borrowNid) {
		BorrowInfoResponse response = new BorrowInfoResponse();
		BorrowInfo borrowInfo =borrowService.getBorrowInfoByNid(borrowNid);
		if (Validator.isNotNull(borrowInfo)){
			response.setResult(CommonUtils.convertBean(borrowInfo,BorrowInfoVO.class));
		}
		return response;
	}

	/**
	 * 投资之前插入tmp表
	 * @param tenderRequest
	 * @return
	 */
	@PostMapping("/insertBeforeTender")
	public int insertBeforeTender(@RequestBody TenderRequest tenderRequest) {
		try{
			borrowService.insertBeforeTender(tenderRequest);
			return 1;
		}catch (Exception e){
			return 0;
		}
	}
}
