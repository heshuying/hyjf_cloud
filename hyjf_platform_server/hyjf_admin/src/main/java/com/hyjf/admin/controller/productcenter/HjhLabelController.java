/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AssetListService;
import com.hyjf.admin.service.HjhLabelService;
import com.hyjf.am.resquest.admin.HjhLabelInfoRequest;
import com.hyjf.am.resquest.admin.HjhLabelRequest;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.admin.HjhLabelCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.calculate.DateUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * @author libin
 * @version HjhLabelController.java, v0.1 2018年6月30日 上午9:14:22
 */
@Api(value = "标签配置列表")
@RestController
@RequestMapping("/hyjf-admin/label")
public class HjhLabelController extends BaseController{

	@Autowired
	private HjhLabelService labelService;
	@Autowired
	private AssetListService assetListService;

	/**
	 * 画面初始化
	 *
	 * @param request
	 * @return 标签配置列表
	 */
	@ApiOperation(value = "标签配置列表", notes = "标签配置列表初始化")
	@PostMapping(value = "/init")
	@ResponseBody
	public JSONObject init(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		// 初始化下拉菜单
		// 1.资产来源(可复用)
		List<HjhInstConfigVO> hjhInstConfigList = this.assetListService.getHjhInstConfigList();
		jsonObject.put("hjhInstConfigList", hjhInstConfigList);
		// 2.产品类型(可复用)
		/*List<HjhAssetTypeVO> assetTypeList = this.assetListService.hjhAssetTypeList(map.get("instCodeSrch").toString());*/
		/*jsonObject.put("assetTypeList", assetTypeList);*/
		// 3.项目类型(可复用)
		List<BorrowProjectTypeVO> borrowProjectTypeList = this.labelService.getBorrowProjectTypeList();
		jsonObject.put("borrowProjectTypeList", borrowProjectTypeList);
		// 4.还款方式(可复用)
		List<BorrowStyleVO> borrowStyleList = this.labelService.getBorrowStyleList();
		jsonObject.put("borrowStyleList", borrowStyleList);
		return jsonObject;
	}
	
	/**
	 * 下拉联动
	 *
	 * @param request
	 * @return 进入资产列表页面
	 */
	@ApiOperation(value = "标签配置列表", notes = "标签配置列表下拉联动")
	@PostMapping(value = "/link")
	@ResponseBody
	public JSONObject assetTypeAction(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if(StringUtils.isNotEmpty(map.get("instCodeSrch").toString())){
			// 资金来源动态下拉框传入机构编号再查产品类型表
			List<HjhAssetTypeVO> assetTypeList = this.assetListService.hjhAssetTypeList(map.get("instCodeSrch").toString());
			if (assetTypeList != null && assetTypeList.size() > 0) {
				for (HjhAssetTypeVO hjhAssetTypeVO : assetTypeList) {
					Map<String, Object> mapTemp = new HashMap<String, Object>();
					mapTemp.put("id", hjhAssetTypeVO.getAssetType());
					mapTemp.put("text", hjhAssetTypeVO.getAssetTypeName());
					resultList.add(mapTemp);
				}
			}
			jsonObject.put("assetTypeList", assetTypeList);
			return jsonObject;
		} else {
			jsonObject.put("未传入机构编号", "未传入机构编号");
			return jsonObject;
		}
	}
	
	/**
	 * 标签配置列表查询
	 *
	 * @param request
	 * @return 进入标签配置列表页面
	 */
	@ApiOperation(value = "标签配置列表", notes = "标签配置列表查询")
	@PostMapping(value = "/search")
	@ResponseBody
	public JSONObject selectLabelConfigList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		HjhLabelRequest hjhLabelRequest = setRequese(map);
		List<HjhLabelCustomizeVO> list = this.labelService.getHjhLabelList(hjhLabelRequest);
        if (null != list && list.size() > 0) {
            jsonObject.put("record", list);
            success();
        } else {
        	fail("标签配置列表查询为空！");
        }
        return jsonObject;
	}
	
	/**
	 * 拼裝參數
	 *
	 * @param request
	 * @return 进入标签配置列表页面
	 */
    private HjhLabelRequest setRequese(Map<String, Object> mapParam) {
    	HjhLabelRequest request = new HjhLabelRequest();
        if (null != mapParam && mapParam.size() > 0) {
        	if (mapParam.containsKey("labelNameSrch")) {
        		request.setLabelNameSrch(mapParam.get("labelNameSrch").toString());
        	}
        	if (mapParam.containsKey("instCodeSrch")) {
        		request.setInstCodeSrch(mapParam.get("instCodeSrch").toString());
        	}
        	if (mapParam.containsKey("assetTypeSrch")) {
        		request.setAssetTypeSrch(mapParam.get("assetTypeSrch").toString());
        	}
        	if (mapParam.containsKey("projectTypeSrch")) {
        		request.setProjectTypeSrch(mapParam.get("projectTypeSrch").toString());
        	}
        	if (mapParam.containsKey("borrowStyleSrch")) {
        		request.setBorrowStyleSrch(mapParam.get("borrowStyleSrch").toString());
        	}
        	if (mapParam.containsKey("labelStateSrch")) {
        		request.setLabelStateSrch(mapParam.get("labelStateSrch").toString());
        	}
        	if (mapParam.containsKey("engineIdSrch")) {
        		request.setEngineIdSrch(mapParam.get("engineIdSrch").toString());
        	}
        	if (mapParam.containsKey("createTimeStartSrch")) {
        		request.setCreateTimeStartSrch(mapParam.get("createTimeStartSrch").toString());
        	}
        	if (mapParam.containsKey("createTimeEndSrch")) {
        		request.setCreateTimeEndSrch(mapParam.get("createTimeEndSrch").toString());
        	}
            if (mapParam.containsKey("limit") && StringUtils.isNotBlank(mapParam.get("limit").toString())) {
            	request.setLimit(Integer.parseInt(mapParam.get("limit").toString()));
            }
        }
        return request;
    }
    
	/**
	 * 标签列表 添加/修改 初始化画面
	 *
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "标签配置列表", notes = "生成添加/修改画面")
	@PostMapping(value = "/info")
	@ResponseBody
	public JSONObject getAddOrModifyView(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		HjhLabelRequest hjhLabelRequest = new HjhLabelRequest();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		/*(1)添加时只返回下拉菜单*/
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

		/*(2)修改时返回此条记录的详情*/
		// 这里注意 先定义一个参数，聊天让前端一致 labelId
		if (map.containsKey("labelId")) {
			hjhLabelRequest.setLabelIdSrch(Integer.valueOf(map.get("labelId").toString().trim()));
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
	            jsonObject.put("hjhLabelCustomizeVO", resultVO);
	            /*组装3  产品类型(联动)*/
				List<HjhAssetTypeVO> assetTypeList = this.assetListService.hjhAssetTypeList(resultVO.getInstCode());
				if (assetTypeList != null && assetTypeList.size() > 0) {
					for (HjhAssetTypeVO hjhAssetTypeVO : assetTypeList) {
						Map<String, Object> mapTemp = new HashMap<String, Object>();
						mapTemp.put("id", hjhAssetTypeVO.getAssetType());
						mapTemp.put("text", hjhAssetTypeVO.getAssetTypeName());
						resultList.add(mapTemp);
					}
				}
				jsonObject.put("assetTypeList", assetTypeList); 
			}
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
	@ResponseBody
	public JSONObject addNewLabel(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		HjhLabelInfoRequest hjhLabelInfoRequest = new HjhLabelInfoRequest();
		// 画面验证
		this.validatorFieldCheck(jsonObject, map);
		// 如果校验报错则从新把下拉菜单返回给info画面以供拉取
		// 暂时未做成共通
		if(jsonObject.containsKey("validatorMsg1") || jsonObject.containsKey("validatorMsg2") || jsonObject.containsKey("validatorMsg3")
				|| jsonObject.containsKey("validatorMsg4") || jsonObject.containsKey("validatorMsg5") || jsonObject.containsKey("validatorMsg6")
				|| jsonObject.containsKey("validatorMsg7")
				){
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
		}
		// 准备插表--拼装info画面参数
		hjhLabelInfoRequest = setInfoParam(jsonObject,map);
		this.labelService.insertHjhLabelRecord(hjhLabelInfoRequest);
		success();
		return jsonObject;
	}
	
	
	/**
	 * 画面入力校验
	 * @param jsonObject,map
	 * @return
	 */
	private void validatorFieldCheck(JSONObject jsonObject, Map<String, Object> map) {
		HjhLabelRequest hjhLabelRequest = new HjhLabelRequest();
		// 1.标的最大与最小期限校验
		if (map.containsKey("labelTermEnd") && map.containsKey("labelTermStart")) {
			Integer labelTermEnd = Integer.valueOf(map.get("labelTermEnd").toString().trim());
			Integer labelTermStart = Integer.valueOf(map.get("labelTermStart").toString().trim());
			if(labelTermEnd < labelTermStart){
				// 目前先单写后期做成枚举
				jsonObject.put("validatorMsg1", "标签的结束期限必须大于开始期限!");
			}
		}
		
		// 2.标的实际利率最小范围与最大范围校验
		if (map.containsKey("labelAprEnd") && map.containsKey("labelAprStart")) {
			BigDecimal labelAprEnd = new BigDecimal(map.get("labelAprEnd").toString().trim());
			BigDecimal labelAprStart = new BigDecimal(map.get("labelAprStart").toString().trim());	
			if(labelAprEnd.compareTo(labelAprStart) < 0){
				jsonObject.put("validatorMsg2", "标签的最大实际利率必须大于实最小际利率!");
			}
		}
		
		// 3.标的实际最小金额与最大金额校验
		if (map.containsKey("labelPaymentAccountEnd") && map.containsKey("labelPaymentAccountStart")) {
			BigDecimal labelPaymentAccountEnd = new BigDecimal(map.get("labelPaymentAccountEnd").toString().trim());
			BigDecimal labelPaymentAccountStart = new BigDecimal(map.get("labelPaymentAccountStart").toString().trim());
			if(labelPaymentAccountEnd.compareTo(labelPaymentAccountStart)< 0){
				jsonObject.put("validatorMsg3", "标签的最大实际支付金额必须大于最小实际支付金额!");
			}
		}
		
		// 4.推送时间节点校验
		if (map.containsKey("pushTimeEndString") && map.containsKey("pushTimeStartString")) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			try {
				Date pushTimeStart = sdf.parse(map.get("pushTimeStartString").toString().trim());
				Date pushTimeEnd =  sdf.parse(map.get("pushTimeEndString").toString().trim());
				 if (pushTimeEnd.compareTo(pushTimeStart) < 0) {
					 jsonObject.put("validatorMsg4", "标签的结束推送时间节点必须大于开始的推送时间节点!");
				 }
			} catch (ParseException e) {
				jsonObject.put("validatorMsg5", "推送时间节点格式必须为：00:00:00");
            }
		}
		
		// 5.剩余最小天数与最大天数校验
		if (map.containsKey("remainingDaysEnd") && map.containsKey("remainingDaysStart")) {
			Integer remainingDaysEnd = Integer.valueOf(map.get("remainingDaysEnd").toString().trim());
			Integer remainingDaysStart = Integer.valueOf(map.get("remainingDaysStart").toString().trim());
			if (remainingDaysEnd < remainingDaysStart) {
				jsonObject.put("validatorMsg6", "标签的剩余天数结束范围必须大于剩余天数开始范围!");
			}
		}
		
		// 6.标签名称重复检验
		if (map.containsKey("labelName")) {
			hjhLabelRequest.setLabelNameSrch(map.get("labelName").toString().trim());
			List<HjhLabelCustomizeVO> list = this.labelService.getHjhLabelListByLabelName(hjhLabelRequest);
			// 通过传入的 labelName 查询如果不为空说明此 labelName 已经存在
			if(CollectionUtils.isNotEmpty(list)){
				jsonObject.put("validatorMsg7", "标签名称已存在!");
			}
		}
	}
	
	/**
	 * 拼裝info參數
	 *
	 * @param request
	 * @return 进入标签配置列表页面
	 */
	private HjhLabelInfoRequest setInfoParam(JSONObject jsonObject ,Map<String, Object> mapParam) {
		HjhLabelInfoRequest request = new HjhLabelInfoRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		if (null != mapParam && mapParam.size() > 0) {
			//标签名称
        	if (mapParam.containsKey("labelName")) {
        		request.setLabelName(mapParam.get("labelName").toString());
        	}
        	//标的期限最小
        	if (mapParam.containsKey("labelTermStart")) {
        		request.setLabelTermStart(Integer.valueOf(mapParam.get("labelTermStart").toString()));
        	}
        	//标的期限最大
        	if (mapParam.containsKey("labelTermEnd")) {
        		request.setLabelTermEnd(Integer.valueOf(mapParam.get("labelTermEnd").toString()));
        	}
        	// 日/月
        	if (mapParam.containsKey("labelTermType")) {
        		request.setLabelTermType(mapParam.get("labelTermType").toString());
        	}
        	// 标的实际利率最小
        	if (mapParam.containsKey("labelAprStart")) {
        		request.setLabelAprStart(new BigDecimal(mapParam.get("labelAprStart").toString()));
        	}
        	// 标的实际利率最大
        	if (mapParam.containsKey("labelAprEnd")) {
        		request.setLabelAprEnd(new BigDecimal(mapParam.get("labelAprEnd").toString()));
        	}
        	// 还款方式
        	if (mapParam.containsKey("borrowStyle")) {
        		request.setBorrowStyle(mapParam.get("borrowStyle").toString());
        	}
        	// 还款方式名称
        	if (mapParam.containsKey("borrowStyleName")) {
        		request.setBorrowStyleName(mapParam.get("borrowStyleName").toString());
        	}
        	// 标的实际支付金额最小
        	if (mapParam.containsKey("labelPaymentAccountStart")) {
        		request.setLabelPaymentAccountStart(new BigDecimal(mapParam.get("labelPaymentAccountStart").toString()));
        	}
        	// 标的实际支付金额最大
        	if (mapParam.containsKey("labelPaymentAccountEnd")) {
        		request.setLabelPaymentAccountEnd(new BigDecimal(mapParam.get("labelPaymentAccountEnd").toString()));
        	}
        	// 资产来源Code
        	if (mapParam.containsKey("instCode")) {
        		request.setInstCode(mapParam.get("instCode").toString());
        	}
        	// 资产来源名称
        	if (mapParam.containsKey("instName")) {
        		request.setInstName(mapParam.get("instName").toString());
        	}
        	// 产品类型Code
        	if (mapParam.containsKey("assetType")) {
        		request.setAssetType(Integer.valueOf(mapParam.get("assetType").toString()));
        	}
        	// 产品类型名称
        	if (mapParam.containsKey("assetTypeName")) {
        		request.setAssetTypeName(mapParam.get("assetTypeName").toString());
        	}
        	// 项目类型Code
        	if (mapParam.containsKey("projectType")) {
        		request.setProjectType(Integer.valueOf(mapParam.get("projectType").toString()));
        	}
        	// 项目类型名称
        	if (mapParam.containsKey("projectTypeName")) {
        		request.setProjectTypeName(mapParam.get("projectTypeName").toString());
        	}
        	// 标的是否发生债转
        	if (mapParam.containsKey("isCredit")) {
        		request.setIsCredit(Integer.valueOf(mapParam.get("isCredit").toString()));
        	}
        	// 债转次数不超过
        	if (mapParam.containsKey("creditSumMax")) {
        		request.setCreditSumMax(Integer.valueOf(mapParam.get("creditSumMax").toString()));
        	}
        	// 标的是否逾期
           	if (mapParam.containsKey("isLate")) {
        		request.setIsLate(Integer.valueOf(mapParam.get("isLate").toString()));
        	}
           	// 推送时间节点
            try {
            	if(mapParam.containsKey("pushTimeStartString")){
            		request.setPushTimeStart(sdf.parse(mapParam.get("pushTimeStartString").toString()));
            	}
            	if(mapParam.containsKey("pushTimeEndString")){
            		request.setPushTimeEnd(sdf.parse(mapParam.get("pushTimeEndString").toString()));
            	}
            } catch (ParseException e) {
                jsonObject.put("validatorMsg4", "标签的结束推送时间节点必须大于开始的推送时间节点!");
            }
            // 剩余最小天数
           	if (mapParam.containsKey("remainingDaysStart")) {
        		request.setRemainingDaysStart(Integer.valueOf(mapParam.get("remainingDaysStart").toString()));
        	}
            // 剩余最大天数
           	if (mapParam.containsKey("remainingDaysEnd")) {
        		request.setRemainingDaysEnd(Integer.valueOf(mapParam.get("remainingDaysEnd").toString()));
        	}
           	// 标签状态
           	if (mapParam.containsKey("labelState")) {
        		request.setLabelState(Integer.valueOf(mapParam.get("labelState").toString()));
        	}
		}
		return request;
	}
	
	/**
	 * 修改画面确认后修改标签
	 * 
	 * @param 
	 * @param map
	 */
	@ApiOperation(value = "标签配置列表", notes = "修改画面确认后修改标签")
	@PostMapping(value = "/update")
	@ResponseBody
	public JSONObject modifyLabel(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		HjhLabelInfoRequest infoRequest = new HjhLabelInfoRequest();
		HjhLabelRequest hjhLabelRequest = new HjhLabelRequest();
		// 获取当前登陆者id
		int userId = Integer.valueOf(this.getUser(request).getId());
		// 画面验证
		this.validatorFieldCheck(jsonObject, map);
		if(jsonObject.containsKey("validatorMsg1") || jsonObject.containsKey("validatorMsg2") || jsonObject.containsKey("validatorMsg3")
				|| jsonObject.containsKey("validatorMsg4") || jsonObject.containsKey("validatorMsg5") || jsonObject.containsKey("validatorMsg6")
				|| jsonObject.containsKey("validatorMsg7")
				){
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
		}
		// 准备插表--拼装info画面参数
		infoRequest = setInfoParam(jsonObject,map);
		infoRequest.setUserId(userId);
		// 前端必须传入标签编号
		if (map.containsKey("labelId")) {
			hjhLabelRequest.setLabelIdSrch(Integer.valueOf(map.get("labelId").toString().trim()));
			List<HjhLabelCustomizeVO> list = this.labelService.getHjhLabelListById(hjhLabelRequest);
			if (list != null && list.size() > 0) {
				HjhLabelCustomizeVO resultVO = list.get(0);
				// 前面的入力校验已经校验了 labelName 的 非空
				// 此处校验：如果info里面将要修改的标签名称与DB既存的标签名称一致，则不需要再修改，报消息
				if(resultVO.getLabelName().trim().equals(map.get("labelName").toString())){
					jsonObject.put("validatorMsg7", "标签名称已存在!");
				} else {
					// 用info的修改后信息去修改标签表
					infoRequest.setId(Integer.valueOf(map.get("labelId").toString().trim()));
					int flg = this.labelService.updateHjhLabelRecord(infoRequest);
					if(flg > 0){
						// TODO
						// 通过传入的标签id和标签名称查出一条引擎表的记录
						infoRequest.setId(Integer.valueOf(map.get("labelId").toString().trim()));
						// 传入标签id 和 标签名称 进  infoRequest
						int allocation = this.labelService.updateAllocationRecord(infoRequest);
						if(allocation > 0){
							success();
						}
					}
				}
			}
		}
		return jsonObject;
	}
	
	/**
	 * 启用/禁用
	 * 
	 * @param 
	 * @param map
	 */
	@ApiOperation(value = "标签配置列表", notes = "启用/禁用")
	@PostMapping(value = "/status")
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		HjhLabelInfoRequest infoRequest = new HjhLabelInfoRequest();
		HjhLabelRequest hjhLabelRequest = new HjhLabelRequest();
		// 前端必须传入标签编号
		if (map.containsKey("labelId")) {
			// 修改状态
			hjhLabelRequest.setLabelIdSrch(Integer.valueOf(map.get("labelId").toString().trim()));
			List<HjhLabelCustomizeVO> list = this.labelService.getHjhLabelListById(hjhLabelRequest);
			if (list != null && list.size() > 0) {
				HjhLabelCustomizeVO resultVO = list.get(0);
				if(resultVO.getLabelState() == 1){
					infoRequest.setLabelState(0);
					// LabelName 用来检索
					infoRequest.setLabelName(resultVO.getLabelName());
				} else {
					infoRequest.setLabelState(1);
					// LabelName 用来检索
					infoRequest.setLabelName(resultVO.getLabelName());
				}
				int flg = this.labelService.updateHjhLabelRecord(infoRequest);
				if(flg > 0){
					success();
				}
			}
		}
		return jsonObject;
	}
}
