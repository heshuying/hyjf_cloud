package com.hyjf.admin.controller.assetcenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.hyjf.am.response.Response;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AssetListService;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.alibaba.fastjson.JSONObject;
/**
 * @author libin
 * @version AssetListController, v0.1 2018/6/27 15:16
 */
@Api(value = "资产列表")
@RestController
@RequestMapping("/hyjf-admin/assetcenter")
public class AssetListController extends BaseController {

	@Autowired
	private AssetListService assetListService;
	/*@Autowired
	AssetListRequest request;*/
	// 开户状态
	private static final  String ACCOUNT_STATUS = "ACCOUNT_STATUS";
	// 审核状态
	private static final  String ASSET_APPLY_STATUS = "ASSET_APPLY_STATUS";
	// 项目状态
	private static final  String ASSET_STATUS = "ASSET_STATUS";

	/**
	 * 画面初始化
	 *
	 * @param request
	 * @return 进入资产列表页面
	 */
	@ApiOperation(value = "资产列表", notes = "资产列表页面初始化")
	@PostMapping(value = "/init")
	@ResponseBody
	public JSONObject init(HttpServletRequest request, AssetListRequest form) {
		JSONObject jsonObject = new JSONObject();
		// 初始化下拉菜单
		// 1.资产来源(可复用)
		List<HjhInstConfigVO> hjhInstConfigList = this.assetListService.hjhInstConfigList(form);
		jsonObject.put("hjhInstConfigList", hjhInstConfigList);
		// 2.产品类型(可复用)
		List<HjhAssetTypeVO> assetTypeList = this.assetListService.hjhAssetTypeList(form.getInstCodeSrch());
		jsonObject.put("assetTypeList", assetTypeList);
		// 3.开户状态
		Map<String, String> accountStatusMap = this.assetListService.getParamNameMap(ACCOUNT_STATUS);
		jsonObject.put("accountStatusMap", accountStatusMap);
		// 4.审核状态
		Map<String, String> assetApplyStatusMap = this.assetListService.getParamNameMap(ASSET_APPLY_STATUS);
		jsonObject.put("assetApplyStatusMap", assetApplyStatusMap);
		// 5.项目状态
		Map<String, String> assetStatusMap = this.assetListService.getParamNameMap(ASSET_STATUS);
		jsonObject.put("assetStatusMap", assetStatusMap);
		return jsonObject;
	}

	/**
	 * 下拉联动
	 *
	 * @param request
	 * @return 进入资产列表页面
	 */
	@ApiOperation(value = "资产列表", notes = "资产列表页面下拉联动")
	@PostMapping(value = "/assetTypeAction")
	@ResponseBody
	public JSONObject assetTypeAction(HttpServletRequest request, AssetListRequest form) {
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		// 资金来源动态下拉框传入机构编号再查产品类型表
		List<HjhAssetTypeVO> assetTypeList = this.assetListService.hjhAssetTypeList(form.getInstCodeSrch());
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
	}


	/**
	 * 列表查询(初始无参/查询带参 共用)
	 *
	 * @param request
	 * @return 进入资产列表页面
	 */
	@ApiOperation(value = "资产列表", notes = "资产列表查询")
	@PostMapping(value = "/searchAssetList")
	@ResponseBody
	public JSONObject selectAssetList(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		AssetListRequest assetListRequest = setRequese(map);
		List<AssetListCustomizeVO> assetList = assetListService.findAssetList(assetListRequest);
		String status = Response.FAIL;
		if (null != assetList && assetList.size() > 0) {
			jsonObject.put("record", assetList);
			status = Response.SUCCESS;
		}
		jsonObject.put("status", status);
		return jsonObject;
	}

	/**
	 * 拼装查询参数
	 *
	 * @param request
	 * @return 进入资产列表页面
	 */
	private AssetListRequest setRequese(Map<String, Object> mapParam) {
		AssetListRequest assetListRequest = new AssetListRequest();
		if (null != mapParam && mapParam.size() > 0) {
			// 资产编号
			if (mapParam.containsKey("assetIdSrch")) {
				assetListRequest.setAssetIdSrch(mapParam.get("assetIdSrch").toString());
			}
			// 资产来源
			if (mapParam.containsKey("instCodeSrch")) {
				assetListRequest.setInstCodeSrch(mapParam.get("instCodeSrch").toString());
			}
			// 产品类型
			if (mapParam.containsKey("assetTypeSrch")) {
				assetListRequest.setAssetTypeSrch(mapParam.get("assetTypeSrch").toString());
			}
			// 项目编号
			if (mapParam.containsKey("borrowNidSrch")) {
				assetListRequest.setBorrowNidSrch(mapParam.get("borrowNidSrch").toString());
			}
			// 计划编号
			if (mapParam.containsKey("planNidSrch")) {
				assetListRequest.setPlanNidSrch(mapParam.get("planNidSrch").toString());
			}
			// 用户名
			if (mapParam.containsKey("userNameSrch")) {
				assetListRequest.setUserNameSrch(mapParam.get("userNameSrch").toString());
			}
			// 标的标签
			if (mapParam.containsKey("labelNameSrch")) {
				assetListRequest.setLabelNameSrch(mapParam.get("labelNameSrch").toString());
			}
			// 开户状态
			if (mapParam.containsKey("bankOpenAccountSrch")) {
				assetListRequest.setBankOpenAccountSrch(mapParam.get("bankOpenAccountSrch").toString());
			}
			// 审核状态
			if (mapParam.containsKey("verifyStatusSrch")) {
				assetListRequest.setVerifyStatusSrch(mapParam.get("verifyStatusSrch").toString());
			}
			// 项目状态
			if (mapParam.containsKey("statusSrch")) {
				assetListRequest.setStatusSrch(mapParam.get("statusSrch").toString());
			}
			// 推送时间
			if (mapParam.containsKey("recieveTimeStartSrch")) {
				assetListRequest.setRecieveTimeStartSrch(mapParam.get("recieveTimeStartSrch").toString());
			}
			// 推送时间
			if (mapParam.containsKey("recieveTimeEndSrch")) {
				assetListRequest.setRecieveTimeEndSrch(mapParam.get("recieveTimeEndSrch").toString());
			}
			if (mapParam.containsKey("limit") && StringUtils.isNotBlank(mapParam.get("limit").toString())) {
				assetListRequest.setLimit(Integer.parseInt(mapParam.get("limit").toString()));
			}
		}
		return assetListRequest;
	}

	/**
	 * 查询详情
	 *
	 * @param request
	 * @return 查询详情
	 */
	@ApiOperation(value = "资产列表", notes = "资产列表页面查询详情")
	@PostMapping(value = "/detailAction")
    /*public static final String DETAIL_ACTION = "/{instCode}/{assetId}/detailAction";*/
	@ResponseBody
	public JSONObject searchUserDetail(@PathVariable("assetId") String assetId, @PathVariable("instCode") String instCode) {
		JSONObject jsonObject = new JSONObject();
		AssetDetailCustomizeVO assetDetailCustomizeVO = assetListService.getDetailById(assetId, instCode);
		jsonObject.put("assetDetailCustomizeVO", assetDetailCustomizeVO);
		return jsonObject;
	}


	/**
	 * 带条件导出
	 * 1.无法指定相应的列的顺序，
	 * 2.无法配置，excel文件名，excel sheet名称
	 * 3.目前只能导出一个sheet
	 * 4.列的宽度的自适应，中文存在一定问题
	 * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
	 * @param request
	 * @return 带条件导出
	 */
	@ApiOperation(value = "资产列表", notes = "带条件导出EXCEL")
	@PostMapping(value = "/exportAction")
	@ResponseBody
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> map) throws Exception {
		// 表格sheet名称
		String sheetName = "资产列表";
		// 文件名称
		String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		// 封装查询条件
		AssetListRequest assetListRequest = setRequese(map);
		// 查询
		List<AssetListCustomizeVO> assetList = assetListService.findAssetList(assetListRequest);
		// 列头
		String[] titles = new String[] { "序号", "资产编号", "资产来源", "产品类型", "项目编号", "计划编号", "用户名", "手机号", "银行电子账号", "开户状态", "姓名", "身份证号", "借款金额（元）", "借款期限", "审核状态", "项目状态", "推送时间" };
		// 声明一个工作薄



	}


}
