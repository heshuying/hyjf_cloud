package com.hyjf.admin.controller.assetcenter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
import com.hyjf.admin.Utils.ExportExcel;
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
	public JSONObject init(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		// 初始化下拉菜单
		// 1.资产来源(可复用)
		List<HjhInstConfigVO> hjhInstConfigList = this.assetListService.hjhInstConfigList();
		jsonObject.put("hjhInstConfigList", hjhInstConfigList);
		// 2.产品类型(可复用)
/*		List<HjhAssetTypeVO> assetTypeList = this.assetListService.hjhAssetTypeList(map.get("instCodeSrch").toString());
		jsonObject.put("assetTypeList", assetTypeList);*/
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
	public JSONObject assetTypeAction(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		//{"instCodeSrch": ""}
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
		// 总计
		Integer count = this.assetListService.getRecordCount(assetListRequest);
		String status = Response.FAIL;
		if (count != null && count > 0) {
			jsonObject.put("count", count);
			status = Response.SUCCESS;
		}
		List<AssetListCustomizeVO> assetList = assetListService.findAssetList(assetListRequest);
		if (null != assetList && assetList.size() > 0) {
			jsonObject.put("record", assetList);
			status = Response.SUCCESS;
		}
		BigDecimal sumAccount = this.assetListService.sumAccount(assetListRequest);
		// 总计数大于0
		if(sumAccount.compareTo(BigDecimal.ZERO)==1){
			jsonObject.put("sumAccount", sumAccount);
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
	public JSONObject searchUserDetail(HttpServletRequest request, @RequestBody Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		AssetListRequest assetListRequest = new AssetListRequest();
		if(StringUtils.isNotEmpty(map.get("assetId").toString())  && StringUtils.isNotEmpty(map.get("instCode").toString())){
			assetListRequest.setAssetIdSrch(map.get("assetId").toString());
			assetListRequest.setInstCodeSrch(map.get("instCode").toString());
			AssetDetailCustomizeVO assetDetailCustomizeVO = assetListService.getDetailById(assetListRequest);
			jsonObject.put("assetDetailCustomizeVO", assetDetailCustomizeVO);
		} else {
			jsonObject.put("message", "未传入assetId和instCode");
		}
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
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

		if (assetList != null && assetList.size() > 0) {

			int sheetCount = 1;
			int rowNum = 0;

			for (int i = 0; i < assetList.size(); i++) {
				rowNum++;
				if (i != 0 && i % 60000 == 0) {
					sheetCount++;
					sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
					rowNum = 1;
				}

				// 新建一行
				Row row = sheet.createRow(rowNum);
				// 循环数据
				for (int celLength = 0; celLength < titles.length; celLength++) {
					AssetListCustomizeVO data = assetList.get(i);
					// 创建相应的单元格
					Cell cell = row.createCell(celLength);
					if (celLength == 0) {// 序号
						cell.setCellValue(i + 1);
					} else if (celLength == 1) {// 资产编号
						cell.setCellValue(data.getAssetId());
					} else if (celLength == 2) {// 资产来源
						cell.setCellValue(data.getInstName());
					} else if (celLength == 3) {// 产品类型
						cell.setCellValue(data.getAssetTypeName());
					} else if (celLength == 4) {// 项目编号
						cell.setCellValue(data.getBorrowNid());
					} else if (celLength == 5) {// 计划编号
						cell.setCellValue(data.getPlanNid());
					} else if (celLength == 6) {// 用户名
						cell.setCellValue(data.getUserName());
					} else if (celLength == 7) {// 手机号
						cell.setCellValue(data.getMobile());
					} else if (celLength == 8) {// 银行电子账号
						cell.setCellValue(data.getAccountId());
					} else if (celLength == 9) {// 开户状态
						cell.setCellValue(data.getBankOpenAccount());
					} else if (celLength == 10) {// 姓名
						cell.setCellValue(data.getTruename());
					} else if (celLength == 11) {// 身份证号
						cell.setCellValue(data.getIdcard());
					} else if (celLength == 12) {// 借款金额（元）
						cell.setCellValue(data.getAccount());
					} else if (celLength == 13) {// 借款期限
						cell.setCellValue(data.getBorrowPeriod());
					} else if (celLength == 14) {// 审核状态
						cell.setCellValue(data.getVerifyStatus());
					} else if (celLength == 15) {// 项目状态
						cell.setCellValue(data.getStatus());
					} else if (celLength == 16) {// 推送时间
						cell.setCellValue(data.getRecieveTime());
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}
}

