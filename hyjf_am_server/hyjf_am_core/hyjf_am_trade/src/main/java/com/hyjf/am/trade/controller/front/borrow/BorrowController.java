/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.borrow;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.admin.BorrowCustomizeResponse;
import com.hyjf.am.response.admin.WebProjectRepayListCustomizeResponse;
import com.hyjf.am.response.admin.WebUserInvestListCustomizeResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.account.BorrowAccountResponse;
import com.hyjf.am.response.user.RecentPaymentListCustomizeResponse;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.resquest.trade.BatchCenterCustomizeRequest;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.resquest.user.BorrowFinmanNewChargeRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BatchCenterCustomize;
import com.hyjf.am.trade.dao.model.customize.RecentPaymentListCustomize;
import com.hyjf.am.trade.dao.model.customize.WebProjectRepayListCustomize;
import com.hyjf.am.trade.service.front.borrow.BorrowService;
import com.hyjf.am.trade.service.front.borrow.BorrowStyleService;
import com.hyjf.am.trade.service.front.hjh.HjhInstConfigService;
import com.hyjf.am.vo.admin.BorrowCustomizeVO;
import com.hyjf.am.vo.admin.WebProjectRepayListCustomizeVO;
import com.hyjf.am.vo.admin.WebUserInvestListCustomizeVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;
import com.hyjf.am.vo.user.RecentPaymentListCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
		Borrow borrow = borrowService.getBorrowByNid(borrowNid);
		BorrowInfo borrowInfo = borrowService.getBorrowInfoByNid(borrowNid);
		BorrowAndInfoVO borrowVO = new BorrowAndInfoVO();
		if (Validator.isNotNull(borrow)) {
			borrowVO = CommonUtils.convertBean(borrow,BorrowAndInfoVO.class);
            borrowVO.setVerifyTimeInteger(borrow.getVerifyTime());
			borrowVO.setReverifyTime(borrow.getReverifyTime());
			borrowVO.setCanTransactionAndroid(borrowInfo.getCanTransactionAndroid());
			borrowVO.setCanTransactionIos(borrowInfo.getCanTransactionIos());
			borrowVO.setCanTransactionPc(borrowInfo.getCanTransactionPc());
			borrowVO.setCanTransactionWei(borrowInfo.getCanTransactionWei());
			borrowVO.setTenderAccountMin(borrowInfo.getTenderAccountMin());
			borrowVO.setTenderAccountMax(borrowInfo.getTenderAccountMax());
            logger.info("VerifyTime:"+borrow.getVerifyTime());
		}
		if (Validator.isNotNull(borrowInfo)){
			borrowVO.setInstCode(borrowInfo.getInstCode());
			borrowVO.setCompanyOrPersonal(borrowInfo.getCompanyOrPersonal() + "");
			borrowVO.setBorrowLevel(borrowInfo.getBorrowLevel());
			borrowVO.setProjectName(borrowInfo.getProjectName());
			borrowVO.setAssetAttributes(borrowInfo.getAssetAttributes());
			borrowVO.setFinancePurpose(borrowInfo.getFinancePurpose());
		}
		response.setResult(borrowVO);
		return response;
	}

	@GetMapping("/doGetBorrow/{borrowNid}")
	public BorrowResponse doGetBorrow(@PathVariable String borrowNid) {
		BorrowResponse response = new BorrowResponse();
		Borrow borrow = borrowService.doGetBorrowByNid(borrowNid);
		BorrowInfo borrowInfo = borrowService.doGetBorrowInfoByNid(borrowNid);
		BorrowAndInfoVO borrowVO = new BorrowAndInfoVO();
		if (Validator.isNotNull(borrowInfo)){
			borrowVO=CommonUtils.convertBean(borrowInfo,BorrowAndInfoVO.class);
		}
		if (Validator.isNotNull(borrow)){
			borrowVO=CommonUtils.convertBean(borrow,BorrowAndInfoVO.class);
		}
		response.setResult(borrowVO);
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
			response.setResultList(CommonUtils.convertBeanList(borrowList, BorrowAndInfoVO.class));
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
		List<BorrowAndInfoVO> borrows = borrowService.selectBorrowList();
		if (borrows != null) {
			List<BorrowAndInfoVO> borrowVO = CommonUtils.convertBeanList(borrows,BorrowAndInfoVO.class);
			response.setResultList(borrowVO);
		}
		return response;
	}


	@GetMapping("/getBorrowByNid/{borrowId}")
	public BorrowResponse getBorrowByNid(@PathVariable String borrowId){
		BorrowResponse response = new BorrowResponse();
		Borrow borrow = borrowService.getBorrowByNid(borrowId);
		BorrowInfo borrowInfo=borrowService.getBorrowInfoByNid(borrowId);
		BorrowAndInfoVO borrowAndInfoVo = new BorrowAndInfoVO();
		if (Validator.isNotNull(borrow)){
            borrowAndInfoVo=CommonUtils.convertBean(borrow,BorrowAndInfoVO.class);
		}
		if (Validator.isNotNull(borrowInfo)){
            borrowAndInfoVo.setInstCode(borrowInfo.getInstCode());
            borrowAndInfoVo.setCompanyOrPersonal(String.valueOf(borrowInfo.getCompanyOrPersonal()));
			borrowAndInfoVo.setFinancePurpose(borrowInfo.getFinancePurpose());
		}
		response.setResult(borrowAndInfoVo);
		return response;
	}

	@GetMapping("/getBorrowVOByNid/{borrowId}")
	public BorrowResponse getBorrowVOByNid(@PathVariable String borrowId){
		BorrowResponse response = new BorrowResponse();
		Borrow borrow = borrowService.getBorrowByNid(borrowId);
		BorrowInfo borrowInfo=borrowService.getBorrowInfoByNid(borrowId);
		BorrowAndInfoVO borrowAndInfoVo = null;
		if (Validator.isNotNull(borrow)){
			borrowAndInfoVo=CommonUtils.convertBean(borrow,BorrowAndInfoVO.class);
		}
		if (Validator.isNotNull(borrowInfo)){
			borrowAndInfoVo.setInstCode(borrowInfo.getInstCode());
		}
		response.setResult(borrowAndInfoVo);
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
		Borrow borrow = borrowService.getBorrowByNid(borrowId);
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
	 * 出借之前插入tmp表
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
			logger.error(e.getMessage());
			result.setResultInt(0);
			return result;
		}
	}

	/**
	 * 散标出借操作数据库表
	 * @param tenderBg
	 * @return
	 */
	@PostMapping("/borrowTender")
	public IntegerResponse borrowTender(@RequestBody TenderBgVO tenderBg) {
		logger.info("原子层  散标出借 开始操作数据库表");
		IntegerResponse result = new IntegerResponse();
		try{
			borrowService.updateTenderAfter(tenderBg);
			result.setResultInt(1);
			logger.info("原子层  散标出借 操作数据库表成功");
			return result;
		}catch (Exception e){
			logger.error("散标出借   原子层操作失败  ",e);
			result.setResultInt(0);
			logger.info("原子层  散标出借 操作数据库表失败");
			return result;
		}
	}

	/**
	 * 散标出借异步返回结果
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
	 * 获取散标出借异步结果
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
     * 根据用户id获取用户总出借笔数
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
			response.setResult(CommonUtils.convertBean(borrow,BorrowAndInfoVO.class));
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


	@PostMapping("/countBatchCenter")
	public Long countBatchCenter(@RequestBody BatchCenterCustomizeRequest batchCenterCustomize) {
		 Long borrow = borrowService.countBatchCenter(batchCenterCustomize);
		if (borrow != null) {
			return borrow;
		}
		return null;
	}

	 @PostMapping("/selectBatchCenterList")
	 public BatchCenterCustomizeResponse selectBatchCenterList(@RequestBody BatchCenterCustomizeRequest batchCenterCustomize) {
		 BatchCenterCustomizeResponse response = new BatchCenterCustomizeResponse();
		 List<BatchCenterCustomize> ustomize = borrowService.selectBatchCenterList(batchCenterCustomize);
		 if (Validator.isNotNull(ustomize)){
			 List<BatchCenterCustomizeVO> borrowVO = new ArrayList<BatchCenterCustomizeVO>();
			 BeanUtils.copyProperties(ustomize, borrowVO);
			 response.setResultList(borrowVO);
		 }
		 return response;
	 }

	 @PostMapping("/getborrowIdByProductId")
	 public String getborrowIdByProductId(@RequestBody Map<String, Object> params) {
		 String borrowNid = borrowService.getborrowIdByProductId(params);
		 if (Validator.isNotNull(borrowNid)){
			 return borrowNid;
		 }
		 return null;
	 }

	@PostMapping("/getborrowByProductId")
	public BorrowResponse getborrowByProductId(@RequestBody Map<String, Object> params) {
		BorrowResponse response =  new BorrowResponse();
		List<BorrowAndInfoVO> list = borrowService.getborrowByProductId(params);
		if (Validator.isNotNull(list)){
			response.setResultList(list);
		}
		return null;
	}

	@PostMapping("/selectOrgRepayProjectList")
	public  WebUserRepayProjectListCustomizeResponse  selectOrgRepayProjectList(@RequestBody Map<String, Object> params) {
		WebUserRepayProjectListCustomizeResponse  response = new WebUserRepayProjectListCustomizeResponse();
		List<WebUserRepayProjectListCustomizeVO> list = borrowService.selectOrgRepayProjectList(params);
		if (Validator.isNotNull(list)){
			response.setResultList(list);
		}
		return response;
	}

	@PostMapping("/selectUserRepayProjectList")
	public  WebUserRepayProjectListCustomizeResponse  selectUserRepayProjectList(@RequestBody Map<String, Object> params) {
		WebUserRepayProjectListCustomizeResponse  response = new WebUserRepayProjectListCustomizeResponse();
		List<WebUserRepayProjectListCustomizeVO> list = borrowService.selectUserRepayProjectList(params);
		if (Validator.isNotNull(list)){
			response.setResultList(list);
		}
		return response;
	}

	@PostMapping("/getRepayProjectDetail")
	public ProjectBeanResponse getRepayProjectDetail(@RequestBody ProjectBeanVO form) {
		ProjectBeanResponse response = new ProjectBeanResponse();
		try {
			ProjectBeanVO beanVO = borrowService.searchRepayProjectDetail(form);
			if (Validator.isNotNull(beanVO)){
				response.setResult(beanVO);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return response;
	}

	@PostMapping("/getBorrowList")
	public BorrowListResponse getBorrowList(@RequestBody Map<String,Object> param){
		logger.info("getBorrowList param = {}",JSON.toJSON(param));
		BorrowListResponse response = new BorrowListResponse();
		List<BorrowListVO> list = borrowService.getBorrowList(param);
		if (CollectionUtils.isNotEmpty(list)){
			response.setResultList(list);
		}
		return response;
	}

	@PostMapping("/searchBorrowList")
	public BorrowCustomizeXYResponse searchBorrowList(@RequestBody BorrowCommonCustomizeVO borrowCommonCustomizeVO){
		logger.info("searchBorrowList param = {}",JSON.toJSON(borrowCommonCustomizeVO));
		BorrowCustomizeXYResponse response = new BorrowCustomizeXYResponse();
		List<BorrowCustomizeVO> list = borrowService.searchBorrowList(borrowCommonCustomizeVO);
		if (CollectionUtils.isNotEmpty(list)){
			response.setResultList(list);
		}
		return response;
	}

	@PostMapping("/getDebtBorrowList")
	public DebtBorrowListResponse getDebtBorrowList(@RequestBody Map<String,Object> param){
		DebtBorrowListResponse response = new DebtBorrowListResponse();
		List<DebtBorrowCustomizeVO> list = borrowService.getDebtBorrowList(param);
		if (CollectionUtils.isNotEmpty(list)){
			response.setResultList(list);
		}
		return response;
	}

	@PostMapping("/selectProjectLoanDetailList")
	public WebProjectRepayListCustomizeResponse selectProjectLoanDetailList(@RequestBody Map<String,Object> param){
		WebProjectRepayListCustomizeResponse response = new WebProjectRepayListCustomizeResponse();
		List<WebProjectRepayListCustomizeVO> list  = borrowService.selectProjectLoanDetailList(param);
		if (CollectionUtils.isNotEmpty(list)){
			response.setResultList(list);
		}
		return response;
	}


	@PostMapping("/selectUserDebtInvestList")
	public WebUserInvestListCustomizeResponse selectUserDebtInvestList(@RequestBody Map<String,Object> param){
		WebUserInvestListCustomizeResponse response = new WebUserInvestListCustomizeResponse();
		List<WebUserInvestListCustomizeVO> list = borrowService.selectUserDebtInvestList(param);
		if (CollectionUtils.isNotEmpty(list)){
			response.setResultList(list);
		}
		return response;
	}

	@PostMapping("/planInfoCountProjectRepayPlanRecordTotal")
	public IntegerResponse planInfoCountProjectRepayPlanRecordTotal(@RequestBody Map<String,Object> param){
		IntegerResponse response = new IntegerResponse();
		int count = borrowService.planInfoCountProjectRepayPlanRecordTotal(param);
		response.setResultInt(count);
		return response;
	}

	@PostMapping("/selectUserInvestLsit")
	public WebUserInvestListCustomizeResponse selectUserInvestLsit(@RequestBody Map<String,Object> param){
		WebUserInvestListCustomizeResponse response = new WebUserInvestListCustomizeResponse();
		List<WebUserInvestListCustomizeVO> list = borrowService.selectUserInvestList(param);
		if (CollectionUtils.isNotEmpty(list)){
			response.setResultList(list);
		}
		return response;
	}

	@PostMapping("/myTenderCountProjectRepayPlanRecordTotal")
	public IntegerResponse myTenderCountProjectRepayPlanRecordTotal(@RequestBody Map<String,Object> param){
		IntegerResponse response = new IntegerResponse();
		int count = borrowService.myTenderCountProjectRepayPlanRecordTotal(param);
		response.setResultInt(count);
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
		int count = borrowService.countProjectRepayPlanRecordTotal(borrowInvestRequest.getBorrowNid(),String.valueOf(borrowInvestRequest.getUserId()),borrowInvestRequest.getNid());
		response.setTotal(count);
		return response;
	}

	@RequestMapping("/selectProjectRepayPlanList")
	public WebProjectRepayListCustomizeResponse selectProjectRepayPlanList(@RequestBody @Valid BorrowInvestRequest borrowInvestRequest){
		WebProjectRepayListCustomizeResponse response = new WebProjectRepayListCustomizeResponse();
		List<WebProjectRepayListCustomize> resultList = borrowService.selectProjectRepayPlanList(borrowInvestRequest.getBorrowNid(),String.valueOf(borrowInvestRequest.getUserId()),borrowInvestRequest.getNid());
		if (!CollectionUtils.isEmpty(resultList)) {
			List<WebProjectRepayListCustomizeVO> voList = CommonUtils.convertBeanList(resultList, WebProjectRepayListCustomizeVO.class);
			response.setResultList(voList);
		}
		return response;
	}

	@ApiOperation(value = "查询借款列表")
	@RequestMapping("/searchBorrowCustomizeList")
	public BorrowCustomizeResponse searchBorrowCustomizeList(@RequestBody @Valid BorrowCommonCustomizeVO corrowCommonCustomize) {
		BorrowCustomizeResponse mcr=new BorrowCustomizeResponse();
		List<BorrowCustomizeVO> recordList = this.borrowService.selectBorrowList(corrowCommonCustomize);
		mcr.setResultList(recordList);
		return mcr;

	}

}
