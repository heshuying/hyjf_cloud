/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.borrow;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.account.BorrowAccountResponse;
import com.hyjf.am.response.user.RecentPaymentListCustomizeResponse;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.RecentPaymentListCustomize;
import com.hyjf.am.trade.service.front.borrow.BorrowService;
import com.hyjf.am.trade.service.front.borrow.BorrowStyleService;
import com.hyjf.am.trade.service.front.hjh.HjhInstConfigService;
import com.hyjf.am.vo.trade.ProjectCompanyDetailVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.WebProjectPersonDetailVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.user.RecentPaymentListCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
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
public class BorrowController extends BaseController {

	@Autowired
	HjhInstConfigService hjhInstConfigService;

	@Autowired
	BorrowService borrowService;

	@Autowired
	BorrowStyleService borrowStyleService;

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
		List<RecentPaymentListCustomize> recentPaymentListCustomizeList = hjhInstConfigService.selectRecentPaymentList(paraMap);
		RecentPaymentListCustomizeResponse response = new RecentPaymentListCustomizeResponse();
		if(null!= recentPaymentListCustomizeList){
			List<RecentPaymentListCustomizeVO> recentPaymentListCustomizeVOS = CommonUtils.convertBeanList(recentPaymentListCustomizeList,RecentPaymentListCustomizeVO.class);
			response.setResultList(recentPaymentListCustomizeVOS);
		}
		return response;
	}

	/**
	 * 查询逾期的标的列表
	 * @return
	 */
	@GetMapping("/selectOverdueBorrowList")
	public BorrowResponse selectOverdueBorrowList(){
		BorrowResponse response = new BorrowResponse();
		List<Borrow> borrowList = borrowService.selectOverdueBorrowList();
		if (CollectionUtils.isNotEmpty(borrowList)){
			response.setResultList(CommonUtils.convertBeanList(borrowList, BorrowVO.class));
		}
		return response;
	}

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

	/**
	 * 获取正确的borrowvo
	 * @author zhangyk
	 * @date 2018/9/13 17:28
	 */
	@GetMapping("/getRightBorrowByNid/{borrowId}")
	public RightBorrowResponse getRightBorrowByNid(@PathVariable String borrowId){
		RightBorrowResponse response = new RightBorrowResponse();
		Borrow borrow = borrowService.getBorrow(borrowId);
		if (Validator.isNotNull(borrow)){
			response.setResult(CommonUtils.convertBean(borrow,RightBorrowVO.class));
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
	 * 项目详情
	 * @author zhangyk
	 * @date 2018/6/26 14:10
	 */
	@GetMapping("/getProjectDetail/{borrowNid}")
	public ProjectDetailResponse getProjectDetail(@PathVariable String borrowNid){
		ProjectDetailResponse response = new ProjectDetailResponse();
		ProjectCustomeDetailVO vo = borrowService.getProjectDetail(borrowNid);
		response.setResult(vo);
		return response;
	}


	/**
	 * 公司信息
	 * @author zhangyk
	 * @date 2018/6/26 15:26
	 */
	@GetMapping("/getProjectCompany/{borrowNid}")
	public ProjectCompanyResponse getProjectCompany(@PathVariable String borrowNid){
		ProjectCompanyResponse response = new ProjectCompanyResponse();
		ProjectCompanyDetailVO vo = borrowService.getProjectCompany(borrowNid);
		response.setResult(vo);
		return response;
	}

	/**
	 * 个人项目信息
	 * @author zhangyk
	 * @date 2018/6/26 15:26
	 */
	@GetMapping("/getProjectPserson/{borrowNid}")
	public ProjectPersonDetailResponse getProjectPserson(@PathVariable String borrowNid){
		ProjectPersonDetailResponse response = new ProjectPersonDetailResponse();
		WebProjectPersonDetailVO vo = borrowService.getProjectPerson(borrowNid);
		response.setResult(vo);
		return response;
	}

	/**
	 * 投资之前插入tmp表
	 * @param tenderRequest
	 * @return
	 */
	@PostMapping("/insertBeforeTender")
	public IntegerResponse insertBeforeTender(@RequestBody TenderRequest tenderRequest) {
		IntegerResponse result = new IntegerResponse();
		try{
			borrowService.insertBeforeTender(tenderRequest);
			result.setResultInt(1);
			return result;
		}catch (Exception e){
			e.printStackTrace();
			result.setResultInt(0);
			return result;
		}
	}

	/**
	 * 散标投资操作数据库表
	 * @param tenderBg
	 * @return
	 */
	@PostMapping("/borrowTender")
	public IntegerResponse borrowTender(@RequestBody TenderBgVO tenderBg) {
		logger.info("原子层  散标投资 开始操作数据库表");
		IntegerResponse result = new IntegerResponse();
		try{
			borrowService.updateTenderAfter(tenderBg);
			result.setResultInt(1);
			logger.info("原子层  散标投资 操作数据库表成功");
			return result;
		}catch (Exception e){
			e.printStackTrace();
			result.setResultInt(0);
			logger.info("原子层  散标投资 操作数据库表失败");
			return result;
		}
	}

	/**
	 * 散标投资异步返回结果
	 * @param tenderRetMsg
	 * @return
	 */
	@PostMapping("/updateTenderResult")
	public int updateTenderResult(@RequestBody TenderRetMsg tenderRetMsg) {
		try{
			borrowService.updateTenderResult(tenderRetMsg);
			return 1;
		}catch (Exception e){
			return 0;
		}
	}

	/**
	 * 获取散标投资异步结果
	 * @param borrowNid
	 * @return
	 */
	@GetMapping("/getBorrowTenderResult/{userId}/{logOrdId}/{borrowNid}")
	public StringResponse getBorrowTenderResult(@PathVariable Integer userId, @PathVariable String logOrdId, @PathVariable String borrowNid){
		StringResponse result = new StringResponse();
		String msg = borrowService.getBorrowTenderResult(userId,logOrdId,borrowNid);
		result.setResultStr(msg);
		return result;
	}


    /**
     * 根据用户id获取用户总投资笔数
     * @author zhangyk
     * @date 2018/7/5 18:00
     */
    @GetMapping("/inverestCount/{userId}")
    public Object getTotalInverestCount(@PathVariable String userId){
        ProjectListResponse response = new ProjectListResponse();
        int count = borrowService.getTotalInverestCount(Integer.valueOf(userId));
        response.setCount(count);
        return response;
    }

	/**
	 * 获取放款记录
	 * @author zhangyk
	 * @date 2018/7/5 18:00
	 */
	@GetMapping("/getBorrowAccountList/{borrowNid}")
	public BorrowAccountResponse getBorrowAccountList(@PathVariable String borrowNid){
		BorrowAccountResponse response = new BorrowAccountResponse();
		List<AccountBorrow> accountBorrows = borrowService.getAccountBorrowList(borrowNid);
		if (CollectionUtils.isNotEmpty(accountBorrows)){
			response.setResultList(CommonUtils.convertBeanList(accountBorrows,AccountBorrowVO.class));
		}
		return response;
	}

	@GetMapping("/getBorrowInfoBLOBByborrowNid/{borrowNid}")
	public BorrowInfoWithBLOBResponse  getBorrowInfoBLOBByborrowNid(@PathVariable String borrowNid){
		BorrowInfoWithBLOBResponse response = new BorrowInfoWithBLOBResponse();
		BorrowInfoWithBLOBs borrowInfoWithBLOBs = borrowService.getBorrowInfoWithBLOBs(borrowNid);
		if (borrowInfoWithBLOBs != null){
			response.setResult(CommonUtils.convertBean(borrowInfoWithBLOBs,BorrowInfoWithBLOBsVO.class));
		}
		return response;
	}

	/**
	 * 根据nid和系统时间查询borrow
	 * @author zhangyk
	 * @date 2018/9/3 17:31
	 */
	@GetMapping("/getByNidAndNowTime/{borrowNid}/{nowTime}")
	public BorrowResponse getByNidAndNowTime(@PathVariable String borrowNid, @PathVariable Integer nowTime){
		BorrowResponse response = new BorrowResponse();
		Borrow borrow = borrowService.selectBorrowByNidAndNowTime(borrowNid,nowTime);
		if (null != borrow){
			response.setResult(CommonUtils.convertBean(borrow,BorrowVO.class));
		}
		return response;
	}

    /**
     * 获取还款计算公式
	 *
     * @author liushouyi
     * @param borrowStyle
     * @return
     */
	@GetMapping("/select_borrow_style_with_blobs/{borrowStyle}")
	public BorrowStyleResponse selectBorrowStyleWithBLOBs(@PathVariable String borrowStyle){
		BorrowStyleResponse response = new BorrowStyleResponse();
		List<BorrowStyleWithBLOBs> borrowStyleWithBLOBs = borrowService.selectBorrowStyleWithBLOBs(borrowStyle);
		if (!CollectionUtils.isEmpty(borrowStyleWithBLOBs)) {
			List<BorrowStyleVO> voList = CommonUtils.convertBeanList(borrowStyleWithBLOBs, BorrowStyleVO.class);
			response.setResultList(voList);
		}
		return response;
	}

    /**
     * 获取还款方式
     * @param borrowStyle
     * @return
     */
	@GetMapping("/getBorrowStyle/{borrowStyle}")
    public BorrowStyleResponse getBorrowStyle(@PathVariable String borrowStyle){
        BorrowStyleResponse response = new BorrowStyleResponse();
		BorrowStyle bs=borrowStyleService.getBorrowStyle(borrowStyle);
		if (Validator.isNotNull(bs)){
			response.setResult(CommonUtils.convertBean(bs,BorrowStyleVO.class));
		}
		return response;

    }


}
