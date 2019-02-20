package com.hyjf.admin.controller.assetcenter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.AssetListViewRequest;
import com.hyjf.admin.beans.vo.AdminAssetListCustomizeVO;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.AssetListService;
import com.hyjf.admin.service.UserCenterService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AssetListCustomizeResponse;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.AssetListCustomizeVO;
import com.hyjf.am.vo.admin.HjhAssetTypeVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
/**
 * @author libin
 * @version AssetListController, v0.1 2018/6/27 15:16
 */
@Api(value = "资产中心-资产列表",tags = "资产中心-资产列表")
@RestController
@RequestMapping("/hyjf-admin/assetcenter")
public class AssetListController extends BaseController {

	@Autowired
	private AssetListService assetListService;
    @Autowired
    private AdminCommonService adminCommonService;
	@Autowired
	private UserCenterService userCenterService;
	// 开户状态
	private static final  String ACCOUNT_STATUS = "ACCOUNT_STATUS";
	// 审核状态
	private static final  String ASSET_APPLY_STATUS = "ASSET_APPLY_STATUS";
	// 项目状态
	private static final  String ASSET_STATUS = "ASSET_STATUS";
	// 查看权限
	public static final String PERMISSIONS = "assetlist";

	/**
	 * 画面初始化
	 *
	 * @param request
	 * @return 进入资产列表页面   已测试
	 */
	@ApiOperation(value = "资产列表页面初始化", notes = "资产列表页面初始化")
	@PostMapping(value = "/init")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public JSONObject init(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		// 初始化下拉菜单
		// 1.资产来源(可复用)
/*		List<HjhInstConfigVO> hjhInstConfigList = this.assetListService.getHjhInstConfigList();*/
        List<DropDownVO> hjhInstConfigList = adminCommonService.selectHjhInstConfigList();
		// 2.产品类型(请求下拉联动接口-可复用)
		// List<HjhAssetTypeVO> assetTypeList = this.assetListService.hjhAssetTypeList(map.get("instCodeSrch").toString());
		// jsonObject.put("assetTypeList", assetTypeList);
		// 3.开户状态
		Map<String, String> accountStatusMap = this.assetListService.getParamNameMap(ACCOUNT_STATUS);
		// 4.审核状态
		Map<String, String> assetApplyStatusMap = this.assetListService.getParamNameMap(ASSET_APPLY_STATUS);
		// 5.项目状态
		Map<String, String> assetStatusMap = this.assetListService.getParamNameMap(ASSET_STATUS);
		if(CollectionUtils.isEmpty(hjhInstConfigList) &&  accountStatusMap.isEmpty() && assetApplyStatusMap.isEmpty() && assetStatusMap.isEmpty()){
			jsonObject.put("status", FAIL);
		} else {
			jsonObject.put("资产来源下拉列表", "hjhInstConfigList");
			jsonObject.put("hjhInstConfigList", hjhInstConfigList);
			jsonObject.put("开户状态下拉列表", "accountStatusMap");
			jsonObject.put("accountStatusMap", accountStatusMap);
			jsonObject.put("审核状态下拉列表", "assetApplyStatusMap");
			jsonObject.put("assetApplyStatusMap", assetApplyStatusMap);
			jsonObject.put("项目状态下拉列表", "assetStatusMap");
			jsonObject.put("assetStatusMap", assetStatusMap);
			jsonObject.put("status", SUCCESS);
		}
		return jsonObject;
	}

	/**
	 * 产品类型下拉联动
	 *
	 * @param request
	 * @return 进入资产列表页面   已测试
	 */
	@ApiOperation(value = "资产列表页面产品类型下拉联动", notes = "资产列表页面产品类型下拉联动")
	@PostMapping(value = "/link")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public JSONObject assetTypeAction(HttpServletRequest request, @RequestBody AssetListViewRequest form) {
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
	 * 列表查询(初始无参/查询带参 共用)
	 *
	 * @param request
	 * @return 进入资产列表页面
	 */
	@ApiOperation(value = "资产列表查询", notes = "资产列表查询")
	@PostMapping(value = "/search")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
	public AdminResult<ListResult<AdminAssetListCustomizeVO>> init(HttpServletRequest request, @RequestBody AssetListViewRequest viewRequest) { 
		// 初始化原子层请求实体
		AssetListRequest form = new AssetListRequest();
		// 初始化返回LIST
		List<AdminAssetListCustomizeVO> volist = null;
		// 将画面检索参数request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		if(null != viewRequest.getUserTypeSrch()){
			form.setUserTypeSrch(String.valueOf(viewRequest.getUserTypeSrch()));
		}
		// 查询不改动是因为多处有调用
        AssetListCustomizeResponse response = assetListService.findAssetList(form);
		if(response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		if(CollectionUtils.isNotEmpty(response.getResultList())){
			// 将原子层返回集合转型为组合层集合用于返回 response为原子层 AssetListCustomizeVO，在此转成组合层AdminAssetListCustomizeVO
			volist = CommonUtils.convertBeanList(response.getResultList(), AdminAssetListCustomizeVO.class);
			return new AdminResult<ListResult<AdminAssetListCustomizeVO>>(ListResult.build(volist, response.getCount()));
		} else {
			return new AdminResult<ListResult<AdminAssetListCustomizeVO>>(ListResult.build(volist, 0));
		}
	}
	
	/**
	 * 列总计查询
	 *
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "列总计查询", notes = "列总计查询")
	@PostMapping(value = "/sum")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
	public JSONObject sum(HttpServletRequest request, HttpServletResponse response, @RequestBody AssetListViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		String status = Response.FAIL;
		// 初始化原子层请求实体
		AssetListRequest form = new AssetListRequest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		BigDecimal sumAccount = this.assetListService.sumAccount(form);
		if(sumAccount != null){
			// 总计数大于0
			if(sumAccount.compareTo(BigDecimal.ZERO)==1){
				jsonObject.put("借款金额(元)列总计", "sumAccount");
				jsonObject.put("sumAccount", sumAccount);
				status = Response.SUCCESS;
			}
		} else {
			jsonObject.put("msg", "查询为空");
			jsonObject.put("status", status);
		}
		return jsonObject;
	}

	/**
	 * 查询详情
	 *
	 * @param request
	 * @return 查询详情
	 */
	@ApiOperation(value = "资产列表页面查询详情", notes = "资产列表页面查询详情")
	@PostMapping(value = "/detail")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
	public JSONObject searchUserDetail(HttpServletRequest request, @RequestBody AssetListViewRequest viewRequest) {
		JSONObject jsonObject = new JSONObject();
		AssetListRequest assetListRequest = new AssetListRequest();
		/*AdminAssetDetailCustomizeVO vo = null;*/
		// 将画面请求request赋值给原子层 request 
		if(StringUtils.isNotEmpty(viewRequest.getAssetIdSrch())  && StringUtils.isNotEmpty(viewRequest.getInstCodeSrch())){
			assetListRequest.setAssetIdSrch(viewRequest.getAssetIdSrch());
			assetListRequest.setInstCodeSrch(viewRequest.getInstCodeSrch());
			// 获取到原子层查询的VO(多处调用)
			AssetDetailCustomizeVO assetDetailCustomizeVO = assetListService.getDetailById(assetListRequest);
			// 借款用途 code 转 中文
			Map<String, String> useages = RedisUtils.hgetall(RedisConstants.CACHE_PARAM_NAME+CustomConstants.FINANCE_PURPOSE);
            String useageName = (String) useages.get(assetDetailCustomizeVO.getUseage());
            if (StringUtils.isNotBlank(useageName)){
                assetDetailCustomizeVO.setUseage(useageName);
            }
            // 将原子层查询的VO转型为组合层VO
			/*BeanUtils.copyProperties(assetDetailCustomizeVO,vo);*/
			jsonObject.put("返回实体类", "assetDetailCustomizeVO");
			jsonObject.put("assetDetailCustomizeVO", assetDetailCustomizeVO);
			jsonObject.put("status", SUCCESS);
		} else {
			jsonObject.put("status", FAIL);
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
//	@ApiOperation(value = "资产列表", notes = "带条件导出EXCEL")
//	@PostMapping(value = "/export")
//	@ResponseBody
//	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
//	public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody AssetListViewRequest viewRequest) throws Exception {
//		// 表格sheet名称
//		String sheetName = "资产列表";
//		// 文件名称
//		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
//		// 封装查询条件
//		AssetListRequest form = new AssetListRequest();
//		// 初始化查询列表
//		List<AssetListCustomizeVO> assetList = null;
//		// 将画面请求request赋值给原子层 request
//		BeanUtils.copyProperties(viewRequest, form);
//		// 获取查询的列表
//		AssetListCustomizeResponse res = assetListService.findAssetListWithoutPage(form);
//		if(res != null) {
//			assetList = res.getResultList();
//		}
//		// 列头
//		String[] titles = new String[] { "序号", "资产编号", "资产来源", "产品类型", "项目编号", "计划编号", "用户名", "手机号", "银行电子账号", "借款类型", "开户状态", "姓名", "身份证号", "借款金额（元）", "借款期限", "还款方式", "审核状态", "项目状态", "标的标签", "推送时间" };
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		// 生成一个表格
//		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
//		if (assetList != null && assetList.size() > 0) {
//			int sheetCount = 1;
//			int rowNum = 0;
//			for (int i = 0; i < assetList.size(); i++) {
//				rowNum++;
//				if (i != 0 && i % 60000 == 0) {
//					sheetCount++;
//					sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
//					rowNum = 1;
//				}
//				// 新建一行
//				Row row = sheet.createRow(rowNum);
//				// 循环数据
//				for (int celLength = 0; celLength < titles.length; celLength++) {
//					AssetListCustomizeVO data = assetList.get(i);
//					// 创建相应的单元格
//					Cell cell = row.createCell(celLength);
//					if (celLength == 0) {// 序号
//						cell.setCellValue(i + 1);
//					} else if (celLength == 1) {// 资产编号
//						cell.setCellValue(data.getAssetId());
//					} else if (celLength == 2) {// 资产来源
//						cell.setCellValue(data.getInstName());
//					} else if (celLength == 3) {// 产品类型
//						cell.setCellValue(data.getAssetTypeName());
//					} else if (celLength == 4) {// 项目编号
//						cell.setCellValue(data.getBorrowNid());
//					} else if (celLength == 5) {// 计划编号
//						cell.setCellValue(data.getPlanNid());
//					} else if (celLength == 6) {// 用户名
//						cell.setCellValue(data.getUserName());
//					} else if (celLength == 7) {// 手机号
//						cell.setCellValue(data.getMobile());
//					} else if (celLength == 8) {// 银行电子账号
//						cell.setCellValue(data.getAccountId());
//					} else if (celLength == 9) {// 借款类型
//						cell.setCellValue(data.getUserType());
//					} else if (celLength == 10) {// 开户状态
//						cell.setCellValue(data.getBankOpenAccount());
//					} else if (celLength == 11) {// 姓名
//						cell.setCellValue(data.getTruename());
//					} else if (celLength == 12) {// 身份证号
//						cell.setCellValue(data.getIdcard());
//					} else if (celLength == 13) {// 借款金额（元）
//						cell.setCellValue(data.getAccount());
//					} else if (celLength == 14) {// 借款期限
//						cell.setCellValue(data.getBorrowPeriod());
//					} else if (celLength == 15) {// 还款方式
//						cell.setCellValue(data.getBorrowStyleName());
//					} else if (celLength == 16) {// 审核状态
//						cell.setCellValue(data.getVerifyStatus());
//					} else if (celLength == 17) {// 项目状态
//						cell.setCellValue(data.getStatus());
//					} else if (celLength == 18) {// 标的标签
//						cell.setCellValue(data.getLabelName());
//					} else if (celLength == 19) {// 推送时间
//						cell.setCellValue(data.getRecieveTime());
//					}
//				}
//			}
//		}
//		// 导出
//		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//	}
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
	@ApiOperation(value = "带条件导出EXCEL", notes = "带条件导出EXCEL")
	@PostMapping(value = "/export")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	    public void exportToExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody AssetListViewRequest viewRequest) throws Exception {
	        //sheet默认最大行数
	        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
	        // 表格sheet名称
	        String sheetName = "资产列表";
	        // 文件名称
	        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
	        // 声明一个工作薄
	        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
	        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

			// 封装查询条件
			AssetListRequest form = new AssetListRequest();
			// 初始化查询列表
			List<AssetListCustomizeVO> assetList = null;
			// 将画面请求request赋值给原子层 request
			BeanUtils.copyProperties(viewRequest, form);
	        //请求第一页5000条
			form.setPageSize(defaultRowMaxCount);
			form.setCurrPage(1);
			// 获取查询的列表
			AssetListCustomizeResponse res = assetListService.findAssetListWithoutPage(form);
	        Integer totalCount = res.getCount();

	        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
	        Map<String, String> beanPropertyColumnMap = buildMap();
	        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
	        String sheetNameTmp = "";
	        if(totalCount==0) {
	        	 helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
	        }
	        for (int i = 1; i <= sheetCount; i++) {
	        	sheetNameTmp = sheetName + "_第" + (i) + "页";
	        	if(sheetCount==1) {
	        		helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  res.getResultList());
	        	}else {
	        		form.setCurrPage(sheetCount);
	        		helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  assetListService.findAssetListWithoutPage(form).getResultList());
	        	}
				
				
	        }
	        
	        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	    }
    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("assetId", "资产编号");
        map.put("instName", "资产来源");
        map.put("assetTypeName", "产品类型");
        map.put("borrowNid", "项目编号");
        map.put("planNid", "智投编号");
        map.put("userName", "用户名");
        map.put("mobile", "手机号");
        map.put("accountId", "银行电子账号");
        map.put("userType", "借款类型");
        map.put("bankOpenAccount", "开户状态");
        map.put("truename", "姓名");
        map.put("idcard", "身份证号");
        map.put("account", "借款金额（元）");
        map.put("borrowPeriod", "借款期限");
        map.put("borrowStyleName", "还款方式");
        map.put("verifyStatus", "审核状态");
        map.put("status", "项目状态");
        map.put("labelName", "标的标签");
        map.put("recieveTime", "推送时间");
        return map;
    }
	   private Map<String, IValueFormatter> buildValueAdapter() {
	        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
	        return mapAdapter;
	    }
		
}
