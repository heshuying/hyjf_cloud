/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.HjhLabelViewRequest;
import com.hyjf.admin.beans.vo.AdminHjhLabelCustomizeVO;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.AssetListService;
import com.hyjf.admin.service.HjhLabelService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhLabelCustomizeResponse;
import com.hyjf.am.resquest.admin.HjhLabelInfoRequest;
import com.hyjf.am.resquest.admin.HjhLabelRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.admin.HjhLabelCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.calculate.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * @author libin
 * @version HjhLabelController.java, v0.1 2018年6月30日 上午9:14:22 test
 */
@Api(value = "产品中心-标签配置",tags = "产品中心-标签配置")
@RestController
@RequestMapping("/hyjf-admin/label")
public class HjhLabelController extends BaseController{

	@Autowired
	private HjhLabelService labelService;
	@Autowired
	private AssetListService assetListService;
    @Autowired
    private AdminCommonService adminCommonService;
	// 权限名称
	private static final String PERMISSIONS = "label";

	/**
	 * 画面初始化
	 *
	 * @param request
	 * @return 标签配置列表  已测试
	 */
	@ApiOperation(value = "标签配置列表", notes = "标签配置列表初始化")
	@PostMapping(value = "/init")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public JSONObject init(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		// 初始化下拉菜单
		// 1.资产来源(可复用)
		//List<HjhInstConfigVO> hjhInstConfigList = this.assetListService.getHjhInstConfigList();
        // 资产来源
        List<DropDownVO> hjhInstConfigList = adminCommonService.selectHjhInstConfigList();
		// 2.产品类型(可复用)
		/*List<HjhAssetTypeVO> assetTypeList = this.assetListService.hjhAssetTypeList(map.get("instCodeSrch").toString());*/
		/*jsonObject.put("assetTypeList", assetTypeList);*/
		// 3.项目类型(可复用)
		//List<BorrowProjectTypeVO> borrowProjectTypeList = this.labelService.getBorrowProjectTypeList();
		List<DropDownVO> borrowProjectTypeList = adminCommonService.selectProjectType();
		// 4.还款方式(可复用)
		//List<BorrowStyleVO> borrowStyleList = this.labelService.getBorrowStyleList();
        // 还款方式
        List<DropDownVO> borrowStyleList = adminCommonService.selectBorrowStyleList();
		if(CollectionUtils.isEmpty(hjhInstConfigList) && CollectionUtils.isEmpty(borrowProjectTypeList) && CollectionUtils.isEmpty(borrowStyleList)){
			jsonObject.put("status", FAIL);
		} else {
			jsonObject.put("资产来源下拉列表", "hjhInstConfigList");
			jsonObject.put("hjhInstConfigList", hjhInstConfigList);
			jsonObject.put("项目类型下拉列表", "borrowProjectTypeList");
			jsonObject.put("borrowProjectTypeList", borrowProjectTypeList);
			jsonObject.put("还款方式下拉列表", "borrowStyleList");
			jsonObject.put("borrowStyleList", borrowStyleList);
			jsonObject.put("status", SUCCESS);
		}
		return jsonObject;
	}
	
	/**
	 * 下拉联动
	 *
	 * @param request
	 * @return 进入资产列表页面  已测试
	 */
	@ApiOperation(value = "标签配置列表", notes = "标签配置列表下拉联动")
	@PostMapping(value = "/link")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public JSONObject assetTypeAction(HttpServletRequest request, @RequestBody HjhLabelViewRequest form) {
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if(StringUtils.isNotEmpty(form.getInstCodeSrch())){
			// 资金来源动态下拉框传入机构编号再查产品类型表
			List<HjhAssetTypeVO> assetTypeList = this.assetListService.hjhAssetTypeList(form.getInstCodeSrch());
			if (assetTypeList != null && assetTypeList.size() > 0) {
				for (HjhAssetTypeVO hjhAssetTypeVO : assetTypeList) {
					Map<String, Object> mapTemp = new HashMap<String, Object>();
					mapTemp.put("id", hjhAssetTypeVO.getAssetType());
					mapTemp.put("text", hjhAssetTypeVO.getAssetTypeName());
					resultList.add(mapTemp);
				}
				jsonObject.put("产品类型下拉列表", "assetTypeList");
				jsonObject.put("assetTypeList", assetTypeList);	
				jsonObject.put("status", SUCCESS);
			}
			return jsonObject;
		} else {
			jsonObject.put("未传入机构编号", "未传入机构编号");
			jsonObject.put("status", FAIL);
			return jsonObject;
		}
	}
	
	/**
	 * 标签配置列表查询
	 *
	 * @param request
	 * @return 进入标签配置列表页面    已测试
	 */
	@ApiOperation(value = "标签配置列表", notes = "标签配置列表查询")
	@PostMapping(value = "/search")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<AdminHjhLabelCustomizeVO>> selectLabelConfigList(HttpServletRequest request, @RequestBody HjhLabelViewRequest viewRequest) {
		// 初始化原子层请求实体
		HjhLabelRequest form = new HjhLabelRequest();
		// 初始化返回LIST
		List<AdminHjhLabelCustomizeVO> volist = null;
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		HjhLabelCustomizeResponse response = this.labelService.getHjhLabelList(form);
		if(response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		if(CollectionUtils.isNotEmpty(response.getResultList())){
			// 将原子层返回集合转型为组合层集合用于返回 response为原子层 AssetListCustomizeVO，在此转成组合层AdminAssetListCustomizeVO
			volist = CommonUtils.convertBeanList(response.getResultList(), AdminHjhLabelCustomizeVO.class);
			return new AdminResult<ListResult<AdminHjhLabelCustomizeVO>>(ListResult.build(volist, response.getCount()));
		} else {
			return new AdminResult<ListResult<AdminHjhLabelCustomizeVO>>(ListResult.build(volist, 0));
		}
	}
	
	/**
	 * 标签列表 添加/修改 初始化画面   已测试
	 *
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "标签配置列表", notes = "生成添加/修改画面")
	@PostMapping(value = "/info")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
	public JSONObject getAddOrModifyView(HttpServletRequest request, HttpServletResponse response, @RequestBody HjhLabelViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		HjhLabelRequest hjhLabelRequest = new HjhLabelRequest();
		/*AdminHjhLabelCustomizeVO adminHjhLabelCustomizeVO = null;*/
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		/*(1)添加时只返回下拉菜单*/
		// 1.资产来源
		List<HjhInstConfigVO> hjhInstConfigList = this.assetListService.getHjhInstConfigList();
		jsonObject.put("资产来源下拉列表", "hjhInstConfigList");
		jsonObject.put("hjhInstConfigList", hjhInstConfigList);
		// 2.产品类型调用联动方法
		// 3.项目类型(可复用)
		List<BorrowProjectTypeVO> borrowProjectTypeList = this.labelService.getBorrowProjectTypeList();
		jsonObject.put("项目类型下拉列表", "borrowProjectTypeList");
		jsonObject.put("borrowProjectTypeList", borrowProjectTypeList);
		// 4.还款方式(可复用)
		List<BorrowStyleVO> borrowStyleList = this.labelService.getBorrowStyleList();
		jsonObject.put("还款方式下拉列表", "borrowStyleList");
		jsonObject.put("borrowStyleList", borrowStyleList);
		/*(2)修改时返回此条记录的详情*/
		// 这里注意 先定义一个参数，前端需要传入 LabelId(标签编号) 参数
		if (StringUtils.isNotEmpty(viewRequest.getLabelId())) {
			hjhLabelRequest.setLabelIdSrch(Integer.valueOf(viewRequest.getLabelId()));
			List<HjhLabelCustomizeVO> list = this.labelService.getHjhLabelListById(hjhLabelRequest);
			if (list != null && list.size() > 0) {
				HjhLabelCustomizeVO resultVO = list.get(0);
				/*组装1*/
				if(StringUtils.isEmpty(resultVO.getLabelisUsed())){
					// LabelisUsed 为 0时 说明此标签没用在分配引擎中使用 未使用
					resultVO.setLabelisUsed("0");
				} else {
					// 已使用
					resultVO.setLabelisUsed("1");
				}
				/*组装2*/
				if(resultVO.getPushTimeStart()!=null){
					resultVO.setPushTimeStartString(DateUtils.getNowDateByHH(resultVO.getPushTimeStart()));
				}
	            if(resultVO.getPushTimeEnd()!=null){
	            	resultVO.setPushTimeEndString(DateUtils.getNowDateByHH(resultVO.getPushTimeEnd()));
	            }
	            if(resultVO != null){
	            	jsonObject.put("resultVO", resultVO);
	            	jsonObject.put("status", SUCCESS);
	            }
	            if(resultVO.getInstCode() == null || StringUtils.isEmpty(resultVO.getInstCode())){
					jsonObject.put("assetTypeList", ""); 
					jsonObject.put("status", SUCCESS);
	            } else {
		            /*组装3  产品类型(联动)*/
					List<HjhAssetTypeVO> assetTypeList = this.assetListService.hjhAssetTypeList(resultVO.getInstCode());
					if (assetTypeList != null && assetTypeList.size() > 0) {
						for (HjhAssetTypeVO hjhAssetTypeVO : assetTypeList) {
							Map<String, Object> mapTemp = new HashMap<String, Object>();
							mapTemp.put("id", hjhAssetTypeVO.getAssetType());
							mapTemp.put("text", hjhAssetTypeVO.getAssetTypeName());
							resultList.add(mapTemp);
						}
						jsonObject.put("assetTypeList", assetTypeList); 
						jsonObject.put("status", SUCCESS);
					}
	            }
			} else {
				jsonObject.put("status", FAIL);
				jsonObject.put("message", "根据labelId查询为空！");
			}
		} else {
			jsonObject.put("message", "未传入labelId");
		}
		return jsonObject;
	}
	
	/**
	 * 添加画面确认后添加标签
	 * 
	 * @param 
	 * @param map
	 */
	@ApiOperation(value = "标签配置列表", notes = "添加画面确认后添加标签")
	@PostMapping(value = "/insert")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public JSONObject addNewLabel(HttpServletRequest request, HttpServletResponse response, @RequestBody HjhLabelViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		HjhLabelInfoRequest hjhLabelInfoRequest = new HjhLabelInfoRequest();
		// 获取当前登陆者id  暂时先注掉
		if(StringUtils.isEmpty(this.getUser(request).getId())){
			jsonObject.put("errorMsg", "请先登录！");
			return jsonObject;
		}
		// 画面验证
		this.validatorFieldCheck(jsonObject, viewRequest);
		// 如果校验报错则把错误消息 + 下拉菜单返回给info画面
		if(jsonObject.containsKey("errorMsg")){
			// 1.资产来源
			List<HjhInstConfigVO> hjhInstConfigList = this.assetListService.getHjhInstConfigList();
			jsonObject.put("hjhInstConfigList", hjhInstConfigList);
			// 2.产品类型调用联动方法

			// 3.项目类型(可复用)
			List<BorrowProjectTypeVO> borrowProjectTypeList = this.labelService.getBorrowProjectTypeList();
			jsonObject.put("borrowProjectTypeList", borrowProjectTypeList);
			// 4.还款方式(可复用)
			List<BorrowStyleVO> borrowStyleList = this.labelService.getBorrowStyleList();
			jsonObject.put("borrowStyleList", borrowStyleList);
			return jsonObject;
		} 
		// 如果无错误消息，准备插表--将viewRequest 的 参数 拼装 到 HjhLabelInfoRequest 插表
		viewRequest.setCreateUserId(Integer.valueOf(this.getUser(request).getId()));
		hjhLabelInfoRequest = setInfoParam(jsonObject,viewRequest);
		int flg = this.labelService.insertHjhLabelRecord(hjhLabelInfoRequest);
		if(flg > 0 ){
			jsonObject.put("status", SUCCESS);
		} else {
			jsonObject.put("status", FAIL);
		}
		return jsonObject;
	}
	
	
	/**
	 * 画面入力校验
	 * @param jsonObject,map
	 * @return
	 */
	private void validatorFieldCheck(JSONObject jsonObject, HjhLabelViewRequest viewRequest) {
		HjhLabelRequest hjhLabelRequest = new HjhLabelRequest();
		// 1.标的最大与最小期限校验
		if (StringUtils.isNotEmpty(viewRequest.getLabelTermEnd()) && StringUtils.isNotEmpty(viewRequest.getLabelTermStart())) {
			Integer labelTermEnd = Integer.valueOf(viewRequest.getLabelTermEnd());
			Integer labelTermStart = Integer.valueOf(viewRequest.getLabelTermStart());
			if(labelTermEnd < labelTermStart){
				// 目前先单写后期做成枚举
				jsonObject.put("errorMsg", "标签的结束期限必须大于开始期限!");
			}
		}
		
		// 2.标的实际利率最小范围与最大范围校验
		if (StringUtils.isNotEmpty(viewRequest.getLabelAprEnd()) && StringUtils.isNotEmpty(viewRequest.getLabelAprStart())) {
			BigDecimal labelAprEnd = new BigDecimal(viewRequest.getLabelAprEnd());
			BigDecimal labelAprStart = new BigDecimal(viewRequest.getLabelAprStart());	
			if(labelAprEnd.compareTo(labelAprStart) < 0){
				jsonObject.put("errorMsg", "标签的最大实际利率必须大于实最小际利率!");
			}
		}
		
		// 3.标的实际最小金额与最大金额校验
		if (StringUtils.isNotEmpty(viewRequest.getLabelPaymentAccountEnd()) && StringUtils.isNotEmpty(viewRequest.getLabelPaymentAccountStart())) {
			BigDecimal labelPaymentAccountEnd = new BigDecimal(viewRequest.getLabelPaymentAccountEnd());
			BigDecimal labelPaymentAccountStart = new BigDecimal(viewRequest.getLabelPaymentAccountStart());
			if(labelPaymentAccountEnd.compareTo(labelPaymentAccountStart)< 0){
				jsonObject.put("errorMsg", "标签的最大实际支付金额必须大于最小实际支付金额!");
			}
		}
		
		// 4.推送时间节点校验
		if (StringUtils.isNotEmpty(viewRequest.getPushTimeEndString()) && StringUtils.isNotEmpty(viewRequest.getPushTimeStartString())) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date pushTimeStart = sdf.parse(viewRequest.getPushTimeStartString());
				Date pushTimeEnd =  sdf.parse(viewRequest.getPushTimeEndString());
				 if (pushTimeEnd.compareTo(pushTimeStart) < 0) {
					 jsonObject.put("errorMsg", "标签的结束推送时间节点必须大于开始的推送时间节点!");
				 }
			} catch (ParseException e) {
				jsonObject.put("errorMsg", "推送时间节点格式必须为：00:00:00");
            }
		}
		
		// 5.剩余最小天数与最大天数校验
		if (StringUtils.isNotEmpty(viewRequest.getRemainingDaysEnd()) && StringUtils.isNotEmpty(viewRequest.getRemainingDaysStart())) {
			Integer remainingDaysEnd = Integer.valueOf(viewRequest.getRemainingDaysEnd());
			Integer remainingDaysStart = Integer.valueOf(viewRequest.getRemainingDaysStart());
			if (remainingDaysEnd < remainingDaysStart) {
				jsonObject.put("errorMsg", "标签的剩余天数结束范围必须大于剩余天数开始范围!");
			}
		}
		
		// 6.标签名称重复检验  0：标签名称未改 1标签名称修改
		// 只有当传入的标签名称改动时，才校验新改的名称是否重复
		if(viewRequest.getFlag() == 1){
			if (StringUtils.isNotEmpty(viewRequest.getLabelName())) {
				hjhLabelRequest.setLabelNameSrch(viewRequest.getLabelName());
				List<HjhLabelCustomizeVO> list = this.labelService.getHjhLabelListByLabelName(hjhLabelRequest);
				// 通过传入的 labelName 查询如果不为空说明此 labelName 已经存在
				if(CollectionUtils.isNotEmpty(list)){
					jsonObject.put("errorMsg", "标签名称已存在!");
				}
			}
		}
	}
	
	/**
	 * 拼裝info參數
	 *
	 * @param request
	 * @return 进入标签配置列表页面
	 */
	private HjhLabelInfoRequest setInfoParam(JSONObject jsonObject ,HjhLabelViewRequest viewRequest) {
		HjhLabelInfoRequest request = new HjhLabelInfoRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		if (null != viewRequest) {
			// 标签名称
        	if (StringUtils.isNotEmpty(viewRequest.getLabelName())){
        		request.setLabelName(viewRequest.getLabelName());
        	} 
        	//标的期限最小
        	if (viewRequest.getLabelTermStart()!= null) {
        		request.setLabelTermStart(Integer.valueOf(viewRequest.getLabelTermStart()));
        	} else {
        		request.setLabelTermStart(null);
        	}
        	//标的期限最大
        	if (viewRequest.getLabelTermEnd()!= null) {
        		request.setLabelTermEnd(Integer.valueOf(viewRequest.getLabelTermEnd()));
        	} else {
        		request.setLabelTermEnd(null);
        	}
        	// 日/月
        	if (StringUtils.isNotEmpty(viewRequest.getLabelTermType())) {
        		request.setLabelTermType(viewRequest.getLabelTermType());
        	}
        	// 标的实际利率最小
        	if (viewRequest.getLabelAprStart()!= null) {
        		request.setLabelAprStart(new BigDecimal(viewRequest.getLabelAprStart()));
        	} else {
        		request.setLabelAprStart(null);
        	}
        	// 标的实际利率最大
        	if (viewRequest.getLabelAprEnd()!= null) {
        		request.setLabelAprEnd(new BigDecimal(viewRequest.getLabelAprEnd()));
        	} else {
        		request.setLabelAprEnd(null);
        	}
        	// 还款方式
        	if (StringUtils.isNotEmpty(viewRequest.getBorrowStyle())) {
        		request.setBorrowStyle(viewRequest.getBorrowStyle());
        	}
        	// 还款方式名称
        	if (StringUtils.isNotEmpty(viewRequest.getBorrowStyleName())) {
        		request.setBorrowStyleName(viewRequest.getBorrowStyleName());
        	}
        	// 标的实际支付金额最小
        	if (viewRequest.getLabelPaymentAccountStart()!= null) {
        		request.setLabelPaymentAccountStart(new BigDecimal(viewRequest.getLabelPaymentAccountStart()));
        	} else {
        		request.setLabelPaymentAccountStart(null);
        	}
        	// 标的实际支付金额最大
        	if (viewRequest.getLabelPaymentAccountEnd()!= null) {
        		request.setLabelPaymentAccountEnd(new BigDecimal(viewRequest.getLabelPaymentAccountEnd()));
        	} else {
        		request.setLabelPaymentAccountEnd(null);
        	}
        	// 资产来源Code
        	if (StringUtils.isNotEmpty(viewRequest.getInstCode())) {
        		request.setInstCode(viewRequest.getInstCode());
        	}
        	// 资产来源名称
        	if (StringUtils.isNotEmpty(viewRequest.getInstName())) {
        		request.setInstName(viewRequest.getInstName());
        	}
        	// 产品类型Code
        	if (StringUtils.isNotEmpty(viewRequest.getAssetType())) {
        		request.setAssetType(Integer.valueOf(viewRequest.getAssetType()));
        	}
        	// 产品类型名称
        	if (StringUtils.isNotEmpty(viewRequest.getAssetTypeName())) {
        		request.setAssetTypeName(viewRequest.getAssetTypeName());
        	}
        	// 项目类型Code
        	if (StringUtils.isNotEmpty(viewRequest.getProjectType())) {
        		request.setProjectType(Integer.valueOf(viewRequest.getProjectType()));
        	}
        	// 项目类型名称
        	if (StringUtils.isNotEmpty(viewRequest.getProjectTypeName())) {
        		request.setProjectTypeName(viewRequest.getProjectTypeName());
        	}
        	// 标的是否发生债转
        	if (StringUtils.isNotEmpty(viewRequest.getIsCredit())) {
        		request.setIsCredit(Integer.valueOf(viewRequest.getIsCredit()));
        	}
        	// 债转次数不超过
        	if (StringUtils.isNotEmpty(viewRequest.getCreditSumMax())) {
        		request.setCreditSumMax(Integer.valueOf(viewRequest.getCreditSumMax()));
        	}
        	// 标的是否逾期
           	if (StringUtils.isNotEmpty(viewRequest.getIsLate())) {
        		request.setIsLate(Integer.valueOf(viewRequest.getIsLate()));
        	}
           	// 推送时间节点
            try {
            	if(StringUtils.isNotEmpty(viewRequest.getPushTimeStartString())){
            		request.setPushTimeStart(sdf.parse(viewRequest.getPushTimeStartString()));
            	} 
            	if(StringUtils.isNotEmpty(viewRequest.getPushTimeEndString())){
            		request.setPushTimeEnd(sdf.parse(viewRequest.getPushTimeEndString()));
            	}
            } catch (ParseException e) {
                jsonObject.put("errorMsg", "标签的结束推送时间节点必须大于开始的推送时间节点!");
            }
            // 剩余最小天数
           	if (viewRequest.getRemainingDaysStart()!= null) {
        		request.setRemainingDaysStart(Integer.valueOf(viewRequest.getRemainingDaysStart()));
        	} else {
        		request.setRemainingDaysStart(null);
        	}
            // 剩余最大天数
           	if (viewRequest.getRemainingDaysEnd()!= null) {
        		request.setRemainingDaysEnd(Integer.valueOf(viewRequest.getRemainingDaysEnd()));
        	} else {
        		request.setRemainingDaysEnd(null);
        	}
           	// 标签状态
           	if (StringUtils.isNotEmpty(viewRequest.getLabelState())) {
        		request.setLabelState(Integer.valueOf(viewRequest.getLabelState()));
        	}
           	if(viewRequest.getCreateUserId()!= null){
           		request.setCreateUserId(viewRequest.getCreateUserId());
           	}	
		}
		return request;
	}
	
	/**
	 * 修改画面确认后修改标签   已测试
	 * 
	 * @param 
	 * @param viewRequest
	 */
	@ApiOperation(value = "标签配置列表", notes = "修改画面确认后修改标签")
	@PostMapping(value = "/update")
	public JSONObject modifyLabel(HttpServletRequest request, HttpServletResponse response, @RequestBody HjhLabelViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		HjhLabelInfoRequest infoRequest = new HjhLabelInfoRequest();
		HjhLabelRequest hjhLabelRequest = new HjhLabelRequest();
		// 获取当前登陆者id
		if(StringUtils.isEmpty(this.getUser(request).getId())){
			jsonObject.put("errorMsg", "请先登录！");
			return jsonObject;
		} 
		// 画面验证
		this.validatorFieldCheck(jsonObject, viewRequest);
		if(jsonObject.containsKey("errorMsg")){
			// 1.资产来源
			List<HjhInstConfigVO> hjhInstConfigList = this.assetListService.getHjhInstConfigList();
			jsonObject.put("资产来源下拉列表", "hjhInstConfigList");
			jsonObject.put("hjhInstConfigList", hjhInstConfigList);
			// 2.产品类型调用联动方法
			// 3.项目类型(可复用)
			List<BorrowProjectTypeVO> borrowProjectTypeList = this.labelService.getBorrowProjectTypeList();
			jsonObject.put("项目类型下拉列表", "borrowProjectTypeList");
			jsonObject.put("borrowProjectTypeList", borrowProjectTypeList);
			// 4.还款方式(可复用)
			List<BorrowStyleVO> borrowStyleList = this.labelService.getBorrowStyleList();
			jsonObject.put("还款方式下拉列表", "borrowStyleList");
			jsonObject.put("borrowStyleList", borrowStyleList);
			return jsonObject;
		}
		// 准备插表--拼装info画面参数
		infoRequest = setInfoParamForUpdate(jsonObject,viewRequest);
		infoRequest.setUpdateUserId(Integer.valueOf(this.getUser(request).getId()));
		// 前端必须传入标签编号
		if (StringUtils.isNotEmpty(viewRequest.getLabelId())) {
			hjhLabelRequest.setLabelIdSrch(Integer.valueOf(viewRequest.getLabelId()));
			List<HjhLabelCustomizeVO> list = this.labelService.getHjhLabelListById(hjhLabelRequest);
			if (list != null && list.size() > 0) {
				HjhLabelCustomizeVO resultVO = list.get(0);
				// 前面的入力校验已经校验了 labelName 的 非空
				// 如果 传过来的标签名跟DB的标签不一致,也就是修改了标签名称了，则要看这个而修改后的标签名称是否已存在
				if(!resultVO.getLabelName().trim().equals(viewRequest.getLabelName())){
					hjhLabelRequest.setLabelNameSrch(viewRequest.getLabelName());
					List<HjhLabelCustomizeVO> alist = this.labelService.getHjhLabelListByLabelName(hjhLabelRequest);
					if(alist.size() > 0){
						// 说明修改后的标签名已经在标签中存在
						jsonObject.put("errorMsg", "标签名称已存在!");
						return jsonObject;
					}
				} 
				// 用info的修改后信息去修改标签表
				infoRequest.setId(Integer.valueOf(viewRequest.getLabelId()));
				// 更新label表
				int flg = this.labelService.updateHjhLabelRecord(infoRequest);
				if(flg > 0){
					// 通过传入的标签id和标签名称查出一条引擎表的记录
					infoRequest.setId(Integer.valueOf(viewRequest.getLabelId()));
					// 传入标签id 和 标签名称 进  infoRequest
					int allocation = this.labelService.updateAllocationRecord(infoRequest);
					if(allocation == 0){
						jsonObject.put("status", SUCCESS);
						jsonObject.put("info", "此标签还未添加到引擎中，只更新标签列表成功！");
					} else if(allocation > 0){
						jsonObject.put("status", SUCCESS);
						jsonObject.put("info", "此标签已经添加到引擎中并且引擎表更新成功！");
					} else {
						jsonObject.put("status", FAIL);
					}
				} else {
					jsonObject.put("status", FAIL);
					jsonObject.put("error", "更新标签表失败！");
				}
			}
		}
		return jsonObject;
	}
	
	/**
	 * 启用/禁用   已测试
	 * 
	 * @param 
	 * @param map
	 */
	@ApiOperation(value = "标签配置列表", notes = "启用/禁用")
	@PostMapping(value = "/status")
	public JSONObject updateStatus(HttpServletRequest request, HttpServletResponse response, @RequestBody HjhLabelViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		HjhLabelInfoRequest infoRequest = new HjhLabelInfoRequest();
		HjhLabelRequest hjhLabelRequest = new HjhLabelRequest();
		
		// 前端必须传入标签编号
		if (StringUtils.isNotEmpty(viewRequest.getLabelId())) {
			// 修改状态
			hjhLabelRequest.setLabelIdSrch(Integer.valueOf(viewRequest.getLabelId()));
			// 通过传入的labelId查询标签表
			List<HjhLabelCustomizeVO> list = this.labelService.getHjhLabelListById(hjhLabelRequest);
			if (list != null && list.size() > 0) {
				HjhLabelCustomizeVO resultVO = list.get(0);
				//labelState启用状态 0：停用 1：启用
				if(resultVO.getLabelState() == 1){
					// 如果是启用，则改为停用
					infoRequest.setLabelState(0);
					// 设置主键
					infoRequest.setId(Integer.valueOf(viewRequest.getLabelId()));
					// 更新者
					infoRequest.setUpdateUserId(Integer.valueOf(this.getUser(request).getId()));
				} else {
					// 如果是停用，则改为启用
					infoRequest.setLabelState(1);
					// 设置主键
					infoRequest.setId(Integer.valueOf(viewRequest.getLabelId()));
					// 更新者
					infoRequest.setUpdateUserId(Integer.valueOf(this.getUser(request).getId()));
				}
				// 使用主键更新
				/*int flg = this.labelService.updateHjhLabelRecord(infoRequest);*/
				int flg = this.labelService.updateHjhLabelRecordByIdAndLabelState(infoRequest);
				if(flg > 0){
					jsonObject.put("status", SUCCESS);
				} else {
					jsonObject.put("status", FAIL);
				}
			}else{
				jsonObject.put("status", FAIL);
				jsonObject.put("statusDesc", FAIL_DESC);
				jsonObject.put("errorMsg", "通过传入的labelId未能查询到标签列表！"  );
			}
		} else {
			jsonObject.put("status", FAIL);
			jsonObject.put("statusDesc", FAIL_DESC);
			jsonObject.put("errorMsg", "未获取到前端传入的labelId！");
		}
		return jsonObject;
	}
	
	
	/**
	 * 拼裝info參數
	 *
	 * @param request
	 * @return 进入标签配置列表页面
	 */
	private HjhLabelInfoRequest setInfoParamForUpdate(JSONObject jsonObject ,HjhLabelViewRequest viewRequest) {
		HjhLabelInfoRequest request = new HjhLabelInfoRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		if (null != viewRequest) {
			// 标签名称
        	if (StringUtils.isNotEmpty(viewRequest.getLabelName())){
        		request.setLabelName(viewRequest.getLabelName());
        	} 
        	//标的期限最小
        	if (viewRequest.getLabelTermStart()!= null) {
        		request.setLabelTermStart(Integer.valueOf(viewRequest.getLabelTermStart()));
        	} else {
        		request.setLabelTermStart(null);
        	}
        	//标的期限最大
        	if (viewRequest.getLabelTermEnd()!= null) {
        		request.setLabelTermEnd(Integer.valueOf(viewRequest.getLabelTermEnd()));
        	} else {
        		request.setLabelTermEnd(null);
        	}
        	// 日/月
        	if (StringUtils.isNotEmpty(viewRequest.getLabelTermType())) {
        		request.setLabelTermType(viewRequest.getLabelTermType());
        	}
        	// 标的实际利率最小
        	if (viewRequest.getLabelAprStart()!= null) {
        		request.setLabelAprStart(new BigDecimal(viewRequest.getLabelAprStart()));
        	} else {
        		request.setLabelAprStart(null);
        	}
        	// 标的实际利率最大
        	if (viewRequest.getLabelAprEnd()!= null) {
        		request.setLabelAprEnd(new BigDecimal(viewRequest.getLabelAprEnd()));
        	} else {
        		request.setLabelAprEnd(null);
        	}
        	// 还款方式
        	if (StringUtils.isNotEmpty(viewRequest.getBorrowStyle())) {
        		request.setBorrowStyle(viewRequest.getBorrowStyle());
        	}
        	// 还款方式名称
        	if (StringUtils.isNotEmpty(viewRequest.getBorrowStyleName())) {
        		request.setBorrowStyleName(viewRequest.getBorrowStyleName());
        	}
        	// 标的实际支付金额最小
        	if (viewRequest.getLabelPaymentAccountStart()!= null) {
        		request.setLabelPaymentAccountStart(new BigDecimal(viewRequest.getLabelPaymentAccountStart()));
        	} else {
        		request.setLabelPaymentAccountStart(null);
        	}
        	// 标的实际支付金额最大
        	if (viewRequest.getLabelPaymentAccountEnd()!= null) {
        		request.setLabelPaymentAccountEnd(new BigDecimal(viewRequest.getLabelPaymentAccountEnd()));
        	} else {
        		request.setLabelPaymentAccountEnd(null);
        	}
        	// 资产来源Code
        	if (StringUtils.isNotEmpty(viewRequest.getInstCode())) {
        		request.setInstCode(viewRequest.getInstCode());
        	}
        	// 资产来源名称
        	if (StringUtils.isNotEmpty(viewRequest.getInstName())) {
        		request.setInstName(viewRequest.getInstName());
        	}
        	// 产品类型Code
        	if (StringUtils.isNotEmpty(viewRequest.getAssetType())) {
        		request.setAssetType(Integer.valueOf(viewRequest.getAssetType()));
        	}
        	// 产品类型名称
        	if (StringUtils.isNotEmpty(viewRequest.getAssetTypeName())) {
        		request.setAssetTypeName(viewRequest.getAssetTypeName());
        	}
        	// 项目类型Code
        	if (StringUtils.isNotEmpty(viewRequest.getProjectType())) {
        		request.setProjectType(Integer.valueOf(viewRequest.getProjectType()));
        	}
        	// 项目类型名称
        	if (StringUtils.isNotEmpty(viewRequest.getProjectTypeName())) {
        		request.setProjectTypeName(viewRequest.getProjectTypeName());
        	}
        	// 标的是否发生债转
        	if (StringUtils.isNotEmpty(viewRequest.getIsCredit())) {
        		request.setIsCredit(Integer.valueOf(viewRequest.getIsCredit()));
        	}
        	// 债转次数不超过
        	if (StringUtils.isNotEmpty(viewRequest.getCreditSumMax())) {
        		request.setCreditSumMax(Integer.valueOf(viewRequest.getCreditSumMax()));
        	}
        	// 标的是否逾期
           	if (StringUtils.isNotEmpty(viewRequest.getIsLate())) {
        		request.setIsLate(Integer.valueOf(viewRequest.getIsLate()));
        	}
           	
           	// 推送时间节点
            try {
            	if(StringUtils.isNotEmpty(viewRequest.getPushTimeStartString())){
            		request.setPushTimeStart(sdf.parse(viewRequest.getPushTimeStartString()));
            	} else {
            		request.setPushTimeStart(null);
            	}
            	if(StringUtils.isNotEmpty(viewRequest.getPushTimeEndString())){
            		request.setPushTimeEnd(sdf.parse(viewRequest.getPushTimeEndString()));
            	} else {
            		request.setPushTimeEnd(null);
            	}
            } catch (ParseException e) {
                jsonObject.put("errorMsg", "标签的结束推送时间节点必须大于开始的推送时间节点!");
            }
            
            // 剩余最小天数
           	if (viewRequest.getRemainingDaysStart()!= null) {
        		request.setRemainingDaysStart(Integer.valueOf(viewRequest.getRemainingDaysStart()));
        	} else {
        		request.setRemainingDaysStart(null);
        	}
            // 剩余最大天数
           	if (viewRequest.getRemainingDaysEnd()!= null) {
        		request.setRemainingDaysEnd(Integer.valueOf(viewRequest.getRemainingDaysEnd()));
        	} else {
        		request.setRemainingDaysEnd(null);
        	}
           	// 标签状态
           	if (StringUtils.isNotEmpty(viewRequest.getLabelState())) {
        		request.setLabelState(Integer.valueOf(viewRequest.getLabelState()));
        	}
           	if(viewRequest.getCreateUserId()!= null){
           		request.setCreateUserId(viewRequest.getCreateUserId());
           	}	
		}
		return request;
	}
	
	
}
