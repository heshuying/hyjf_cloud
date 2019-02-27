/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.AllocationEngineViewRequest;
import com.hyjf.admin.beans.request.HjhPlanCapitalRequestBean;
import com.hyjf.admin.beans.vo.AdminHjhRegionVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AllocationEngineService;
import com.hyjf.admin.service.HjhLabelService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhAllocationEngineResponse;
import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.response.admin.HjhRegionResponse;
import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.resquest.admin.HjhLabelRequest;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.vo.admin.HjhAllocationEngineVO;
import com.hyjf.am.vo.admin.HjhLabelCustomizeVO;
import com.hyjf.am.vo.admin.HjhRegionVO;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author libin
 * @version AllocationEngineController.java, v0.1 2018年7月3日 上午11:46:27
 */
@Api(value = "产品中心-汇计划-标的分配规则引擎",tags = "产品中心-汇计划-标的分配规则引擎")
@RestController
@RequestMapping("/hyjf-admin/allocation")
public class AllocationEngineController extends BaseController{

	@Autowired
	private AllocationEngineService allocationEngineService;
	@Autowired
	private HjhLabelService labelService;
	// 查看权限
	public static final String PERMISSIONS = "allocationengine";
    /**
     * 画面初始化
     *
     * @param request
     * @return 标的分配规则引擎列表       已测试
     */
    @ApiOperation(value = "计划专区列表初始化", notes = "计划专区列表初始化")
    @PostMapping(value = "/search")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<AdminHjhRegionVO>> search(HttpServletRequest request, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
    	// 初始化原子层请求实体
    	AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 初始化返回LIST
		List<AdminHjhRegionVO> volist = null;
		// 将画面检索参数request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
    	// 画面检索 计划编号/计划名称/添加时间/专区状态 无需初始化
    	// 根据删选条件获取计划专区列表
    	HjhRegionResponse response = this.allocationEngineService.getHjhRegionList(form);
		if(response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		if(CollectionUtils.isNotEmpty(response.getResultList())){
			// 将原子层返回集合转型为组合层集合用于返回 response为原子层 AssetListCustomizeVO，在此转成组合层AdminAssetListCustomizeVO
			volist = CommonUtils.convertBeanList(response.getResultList(), AdminHjhRegionVO.class);
			return new AdminResult<ListResult<AdminHjhRegionVO>>(ListResult.build(volist, response.getCount()));
		} else {
			return new AdminResult<ListResult<AdminHjhRegionVO>>(ListResult.build(volist, 0));
		}
    }
    
	/**
	 * 计划专区列表 添加/修改 info画面   不需要
	 *
	 * @param request
	 * @return 
	 */
/*	@ApiOperation(value = "计划专区列表", notes = "生成添加/修改画面")
	@PostMapping(value = "/reginfo")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	// 计划专区根据业务要求只有添加计划而没有修改计划
	public JSONObject getAddOrModifyView(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
		JSONObject jsonObject = new JSONObject();
		// 初始画面没有需要初期化的数据
		// 计划编号 和 状态
		return jsonObject;
	}*/
	
	/**
	 * 计划专区列表 info画面确认后添加计划到专区   已测试
	 *
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "添加画面确认后添加到计划专区", notes = "添加画面确认后添加到计划专区")
	@PostMapping(value = "/insert")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public JSONObject addPlanToRegion(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
		JSONObject jsonObject = new JSONObject();
		// 初始化原子层请求实体
		AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面检索参数request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 与实体bean一致 用于转型
		HjhRegionVO VOrequest = new HjhRegionVO();
		// 获取当前登陆者id
		int userId = Integer.valueOf(this.getUser(request).getId());
		// 画面验证
		this.validatorFieldCheck(jsonObject, form);
		/*准备数据*/
		//1.计划编号
		VOrequest.setPlanNid(form.getPlanNidSrch());
		//2.计划名称
		String planName = this.allocationEngineService.getPlanNameByPlanNid(form);
		VOrequest.setPlanName(planName);
		//3.添加时间
		VOrequest.setConfigAddTime(GetDate.getNowTime10());
		//4.计划专区(是否配置)状态 0：停用   1：启用 这个字段在前台是二选一 必须传入
		if(StringUtils.isEmpty(form.getConfigStatus())){
			VOrequest.setConfigStatus(0);
		} else {
			VOrequest.setConfigStatus(Integer.valueOf(form.getConfigStatus()));
		}
		//5.添加人
		VOrequest.setCreateUser(userId);
		// 插表
		int flg = this.allocationEngineService.insertRecord(VOrequest);
		if(flg > 0){
			jsonObject.put("retMsg", SUCCESS_DESC);
			jsonObject.put("retCode", SUCCESS);
		}
		return jsonObject;
	}
	
	/**
	 * 专区画面入力校验
	 * @param jsonObject,map
	 * @return
	 */
	private void validatorFieldCheck(JSONObject jsonObject, AllocationEngineRuquest form) {
		// 计划编号非空判断(状态是二选一不需要判断)
		if(StringUtils.isEmpty(form.getPlanNidSrch())){
			jsonObject.put("validatorMsg", "在专区中添加智投服务时请输入智投编号!");
		}
	}
	
	
    /**
     * 校验入力的计划编号相关AJAX   已测试
     *
     * @param planNid
     * @return
     */
	@ApiOperation(value = "校验入力的计划编号相关", notes = "校验入力的计划编号相关")
	@PostMapping(value = "/ajaxcheck")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_UPDATE)
	/*@ApiImplicitParam(name = "planNidSrch", value = "计划编号查询", required = true, dataType = "String")*/
	public AdminResult<String> planNidAjaxCheck(HttpServletRequest request, @RequestBody AllocationEngineViewRequest  viewRequest) {
		HjhRegionResponse response = this.allocationEngineService.getPlanNidAjaxCheck(viewRequest.getPlanNidSrch());
		if(response==null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<String>(response.getMessage());
	}
	
    /**
     * 计划专区停用/启用状态修改   已测试
     *
     * @param planNid
     * @return
     */
	@ApiOperation(value = "计划专区停用/启用状态修改", notes = "计划专区停用/启用状态修改")
	@PostMapping(value = "/status")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	// 注意 ：此 id 并非画面序号，而是画面上未显示的 计划专区表主键
	/*@ApiImplicitParam(name = "id", value = "计划专区表主键", required = true, dataType = "String")*/
	public AdminResult<String> statusChange(HttpServletRequest request, @RequestBody AllocationEngineViewRequest  viewRequest) { // 注意 ：这里的传值可以改为 form 形式
		HjhRegionResponse response = new HjhRegionResponse();
		// 修改状态
		if (StringUtils.isNotEmpty(viewRequest.getId())) {
			// 首先获取更新之前的 HjhRegion
			HjhRegionVO vo = this.allocationEngineService.getHjhRegionVOById(viewRequest.getId());
			if (vo.getConfigStatus() == 1) {
				vo.setConfigStatus(0);
			} else {
				vo.setConfigStatus(1);
			}
			// 使用 HjhRegionVO(实体中包含主键) 实体去更新计划专区表
			int flg = this.allocationEngineService.updateHjhRegionRecord(vo);
			if(flg > 0){
				// 计划专区表更新成功后，再更新引擎表
				//通过record的planNid去更新引擎表的 ---计划状态和标签状态
				response = this.allocationEngineService.updateAllocationEngineRecord(vo);
			}
		}
		if(response==null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<String>(response.getMessage());
	}
	
	/**
	 * 计划专区带条件导出      已测试
	 * @param request
	 * @return 计划专区带条件导出
	 */
	/*@ApiOperation(value = "带条件导出EXCEL", notes = "带条件导出EXCEL")
	@PostMapping(value = "/regionexport")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) throws Exception {
		// 表格sheet名称
		String sheetName = "计划专区";
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		String[] titles = new String[] { "序号","计划编号", "计划名称","添加时间", "状态" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
		AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 带检索条件的列表查询(无分页)
		List<HjhRegionVO> resultList = this.allocationEngineService.getHjhRegionListWithOutPage(form);
		if (resultList != null && resultList.size() > 0) {
			int sheetCount = 1;
			int rowNum = 0;
			for (int i = 0; i < resultList.size(); i++) {
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
					HjhRegionVO hjhRegion = resultList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 计划编号
					else if (celLength == 1) {
						cell.setCellValue(StringUtils.isEmpty(hjhRegion.getPlanNid()) ? StringUtils.EMPTY : hjhRegion.getPlanNid());
					}
					// 计划名称
					else if (celLength == 2) {
						cell.setCellValue(StringUtils.isEmpty(hjhRegion.getPlanName()) ? StringUtils.EMPTY : hjhRegion.getPlanName());
					}
					// 添加时间
					else if (celLength == 3) {
						if (hjhRegion.getConfigAddTime() != null) {//数据库默认为空
							String configAddTime = GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(hjhRegion.getConfigAddTime());
							cell.setCellValue(configAddTime);
						} else {
							cell.setCellValue(0);
						}
					}
					// 状态
					else if (celLength == 4) {
						if (0 == hjhRegion.getConfigStatus()) {//数据库默认为0
							cell.setCellValue("停用");
						} else {
							cell.setCellValue("启用");
						} 
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}*/

	/**
	 * 计划专区带条件导出      已测试
	 * @param request
	 * @return 计划专区带条件导出
	 */
	@ApiOperation(value = "带条件导出EXCEL", notes = "带条件导出EXCEL")
	@PostMapping(value = "/regionexport")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) throws Exception {
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "智投专区";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
		AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 带检索条件的列表查询(无分页)
		List<HjhRegionVO> resultList = this.allocationEngineService.getHjhRegionListWithOutPage(form);


		Integer totalCount = resultList.size();

//		int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
		Map<String, String> beanPropertyColumnMap = buildMap();
		Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
		String sheetNameTmp = sheetName + "_第1页";
		if (totalCount == 0) {

			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		}else {
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList);
		}
		DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	}

	private Map<String, String> buildMap() {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("planNid", "智投编号");
		map.put("planName", "智投名称");
		map.put("configAddTime", "添加时间");
		map.put("configStatus", "状态");

		return map;
	}
	private Map<String, IValueFormatter> buildValueAdapter() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		IValueFormatter configAddTimeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer value = (Integer) object;
				return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(value);
			}
		};

		IValueFormatter configStatusAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer value = (Integer) object;
				if (0 == value) {//数据库默认为0
					return  "停用";
				} else {
					return "启用";
				}
			}
		};


		mapAdapter.put("configAddTime", configAddTimeAdapter);
		mapAdapter.put("configStatus", configStatusAdapter);
		return mapAdapter;
	}


	
	                                     /*--------以下为计划专区下属 引擎配置画面各项机能----------*/
    /**
     * 计划专区-计划引擎配置画面初始化     已测试
     *
     * @param request
     * @return 计划引擎配置列表
     */
    @ApiOperation(value = "计划引擎配置列表初始化", notes = "计划引擎配置列表初始化")
    @PostMapping(value = "/searchengine")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<HjhAllocationEngineVO>> searchEngine(HttpServletRequest request,@RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
    	AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
    	HjhAllocationEngineResponse response = new HjhAllocationEngineResponse();
		// 初始化返回LIST
		List<HjhAllocationEngineVO> volist = null;
    	// 计划引擎配置列表无过滤条件直接查询
    	// 根据计划专区传入的计划编号获取计划名称返回前台展示
    	if(StringUtils.isNotEmpty(form.getPlanNidSrch())){
        	String planName = this.allocationEngineService.getPlanNameByPlanNid(form);
        	response.setPlanName(planName);
    	}
    	// 根据计划专区传入的计划编号获取计划引擎配置列表
    	response = this.allocationEngineService.getHjhAllocationEngineList(form);
		if(response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		if(CollectionUtils.isNotEmpty(response.getResultList())){
			// 将原子层返回集合转型为组合层集合用于返回 response为原子层 AssetListCustomizeVO，在此转成组合层AdminAssetListCustomizeVO
			volist = CommonUtils.convertBeanList(response.getResultList(), HjhAllocationEngineVO.class);
			return new AdminResult<ListResult<HjhAllocationEngineVO>>(ListResult.build(volist, response.getCount()));
		} else {
			return new AdminResult<ListResult<HjhAllocationEngineVO>>(ListResult.build(volist, 0));
		}
    }
	
	
	/**
	 * 计划配置画面带条件导出      已测试
	 * @param request
	 * @return 计划专区带条件导出
	 */
	/*@ApiOperation(value = "带条件导出EXCEL", notes = "带条件导出EXCEL")
	@PostMapping(value = "/engineexport")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportPlanConfigAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) throws Exception {
		// 表格sheet名称
		String sheetName = "计划配置";
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		String[] titles = new String[] { "序号","标签编号", "标签名称","添加时间", "状态","排序" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
		
		AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		List<HjhAllocationEngineVO> resultList = null;
    	if(StringUtils.isNotEmpty(form.getPlanNidSrch())){
    		// 不带分页的查询 
    		resultList = this.allocationEngineService.getAllocationList(form);
    	}
		if (resultList != null && resultList.size() > 0) {
			int sheetCount = 1;
			int rowNum = 0;
			for (int i = 0; i < resultList.size(); i++) {
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
					HjhAllocationEngineVO hjhAllocationEngine = resultList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 标签编号
					else if (celLength == 1) {
						cell.setCellValue(hjhAllocationEngine.getLabelId());//DB标签id默认为0
					}
					// 标签名称
					else if (celLength == 2) {
						cell.setCellValue(StringUtils.isEmpty(hjhAllocationEngine.getLabelName()) ? StringUtils.EMPTY : hjhAllocationEngine.getLabelName());
					}
					// 添加时间
					else if (celLength == 3) {
						if (hjhAllocationEngine.getAddTime() != null) {//数据库默认为0
							String addTime = GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(hjhAllocationEngine.getAddTime());
							cell.setCellValue(addTime);
						} else {
							cell.setCellValue(0);
						}
					}
					// 状态
					else if (celLength == 4) {
						if (0 == hjhAllocationEngine.getLabelStatus()) {//数据库默认为0
							cell.setCellValue("停用");
						} else {
							cell.setCellValue("启用");
						} 
					}
					// 排序
					else if (celLength == 5) {
						cell.setCellValue(hjhAllocationEngine.getLabelSort());//DB标签默认为0
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);	
	}*/

	/**
	 * 计划配置画面带条件导出      已测试
	 * @param request
	 * @return 计划专区带条件导出
	 */
	@ApiOperation(value = "带条件导出EXCEL", notes = "带条件导出EXCEL")
	@PostMapping(value = "/engineexport")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportPlanConfigAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) throws Exception {
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "智投配置";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
		AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		List<HjhAllocationEngineVO> resultList = null;
		if(StringUtils.isNotEmpty(form.getPlanNidSrch())){
			// 不带分页的查询
			resultList = this.allocationEngineService.getAllocationList(form);
		}

		Integer totalCount = 0;
		if(resultList != null){
			totalCount = resultList.size();
		}

//		int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
		Map<String, String> beanPropertyColumnMap = buildMapEnginee();
		Map<String, IValueFormatter> mapValueAdapter = buildValueEngineeAdapter();
		String sheetNameTmp = sheetName + "_第1页";
		if (totalCount == 0) {

			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		}else {
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList);
		}
		DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	}

	private Map<String, String> buildMapEnginee() {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("labelId", "标签编号");
		map.put("labelName", "标签名称");
		map.put("addTime", "添加时间");
		map.put("labelStatus", "状态");
		map.put("labelSort", "排序");

		return map;
	}
	private Map<String, IValueFormatter> buildValueEngineeAdapter() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		IValueFormatter addTimeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer value = (Integer) object;
				return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(value);
			}
		};

		IValueFormatter labelStatusAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer value = (Integer) object;
				if (0 == value) {//数据库默认为0
					return  "停用";
				} else {
					return "启用";
				}
			}
		};

		mapAdapter.put("addTime", addTimeAdapter);
		mapAdapter.put("labelStatus", labelStatusAdapter);
		return mapAdapter;
	}

    /**
     * 计划配置画面 停用/启用状态修改    已测试
     *
     * @param planNid
     * @return
     */
	@ApiOperation(value = "计划配置画面停用/启用状态修改", notes = "计划配置画面停用/启用状态修改")
	@PostMapping(value = "/labelstatus")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	// 注意 ：此 id 并非画面序号，而是计划引擎配置画面上 未显示的 计划引擎配置表主键
	/*@ApiImplicitParam(name = "engineId", value = "计划引擎配置表主键", required = true, dataType = "String")*/
	public AdminResult<String> labelStatusChange(HttpServletRequest request, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
		HjhAllocationEngineResponse response = new HjhAllocationEngineResponse();
		// 修改状态
		if (viewRequest.getEngineId() != null) {
			HjhAllocationEngineVO vo = this.allocationEngineService.getPlanConfigRecord(viewRequest.getEngineId());
			if (vo.getLabelStatus() == 1) {//标签状态
				vo.setLabelStatus(0);
			} else {
				vo.setLabelStatus(1);
			}
			// 使用 HjhRegionVO(实体中包含主键) 实体去更新计划专区表
			int flg = this.allocationEngineService.updateHjhAllocationEngineRecord(vo);
			if(flg > 0){
				response.setMessage(Response.SUCCESS);
			}
			/*原来的逻辑是修改完状态后再查询一遍引擎列表返回给前台，现在只需要前台带着计划编号 请求一次 searchengine 接口 即可*/	
		}
		return new AdminResult<String>(response.getMessage());
	}
	
	/**
	 * 计划配置画面 添加/修改 初始化info画面   已测试
	 *
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "生成计划配置添加/修改画面", notes = "生成计划配置添加/修改画面")
	@PostMapping(value = "/info")
	@ResponseBody
	public JSONObject getEngineAddOrModifyView(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
		JSONObject jsonObject = new JSONObject();
		AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		//1.添加计划配置时，往画面放一个隐藏域 planNid
		// 添加或修改 0：添加 1：修改
		if(viewRequest.getAddOrModify().equals("0")){
			if(form.getPlanNidSrch()!= null){
				jsonObject.put("planNid", form.getPlanNidSrch());
				jsonObject.put("result", SUCCESS);
				jsonObject.put("msg", SUCCESS_DESC);
			}
			return jsonObject;
		} else if(viewRequest.getAddOrModify().equals("1")){
			//2.修改计划配置时
			String planNid = form.getPlanNid();
			String labelId = form.getLabelId();
			if(StringUtils.isNotEmpty(labelId) && StringUtils.isNotEmpty(planNid)){
				HjhAllocationEngineVO vo = this.allocationEngineService.getPlanConfigRecordByParam(form);
				jsonObject.put("hjhAllocationEngine", vo);
				jsonObject.put("result", SUCCESS);
				jsonObject.put("msg", SUCCESS_DESC);
			} else {
				jsonObject.put("error", "修改智投服务引擎配置需传入PlanNidSrch和labelId");
			}
			return jsonObject;
		}
		return jsonObject;
	}

	/**
	 * 计划配置info画面入力校验   已测试
	 *
	 * @param labelName
	 * @return 
	 */
	@ApiOperation(value = "计划配置info画面标签名称相关输入校验", notes = "计划配置info画面标签名称相关输入校验")
	@PostMapping(value = "/checkinputlabelname")
	@ResponseBody
	public JSONObject checkInputlabelname(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
		HjhLabelRequest hjhLabelRequest = new HjhLabelRequest();
		HjhLabelCustomizeVO hjhLabel = new HjhLabelCustomizeVO();
		JSONObject jsonObject = new JSONObject();
		AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		String labelName = form.getLabelName();
		String planNid = form.getPlanNid();
		if(StringUtils.isEmpty(labelName)){
			jsonObject.put("info", "未获取标签名称，请重新输入！");
			jsonObject.put("status", "99");
			return jsonObject;
		}
		if(StringUtils.isEmpty(planNid)){
			jsonObject.put("info", "未获取智投编号，请重新输入！");
			jsonObject.put("status", "99");
			return jsonObject;
		}
		// 校验一个计划下不能用重复的标签名称 参数 labelName 和 planNid 放在
		/*boolean existflg = this.allocationEngineService.checkRepeat(form);*/
		int existflg = this.allocationEngineService.checkRepeat(form);
		if(existflg>0){
			jsonObject.put("info", "该标签已经被使用，无法再次添加");
			jsonObject.put("status", "99");
			return jsonObject;
		}
		hjhLabelRequest.setLabelNameSrch(labelName);
		List<HjhLabelCustomizeVO> list = this.labelService.getHjhLabelListByLabelName(hjhLabelRequest);
		if(CollectionUtils.isEmpty(list)){
			jsonObject.put("info", "标签数据不存在，请先查看标签列表是否已经添加");
			jsonObject.put("status", "99");
			return jsonObject;
		} else {
			hjhLabel = list.get(0);
			//校验
			if(hjhLabel.getLabelState() == 0){
				jsonObject.put("info", "标签已停用，请先启用");
				jsonObject.put("status", "99");
				return jsonObject;
			}
			if(hjhLabel.getDelFlag() == 1){
				jsonObject.put("info", "标签已删除");
				jsonObject.put("status", "99");
				return jsonObject;
			}
			if(StringUtils.isEmpty(hjhLabel.getBorrowStyle())){
				jsonObject.put("info", "该标签的还款方式为空,请查询标签列表");
				jsonObject.put("status", "99");
				return jsonObject;
			}
			//标签存在的情况下：
			//先查询汇计划表获取该计划的还款方式
			String planBorrowStyle = this.allocationEngineService.getPlanBorrowStyle(planNid);//planNid已经校验非空
			if(StringUtils.isEmpty(planBorrowStyle)){
				jsonObject.put("info", "该智投服务的还款方式为空，请查询智投服务列表");
				jsonObject.put("status", "99");
				return jsonObject;
			}
			jsonObject.put("status", "000");
		}
		return jsonObject;
	}
	
	/**
	 * 计划配置info画面标签排序入力校验   已测试
	 *
	 * @param labelName
	 * @return 
	 */
	@ApiOperation(value = "计划配置info画面标签排序相关输入校验", notes = "计划配置info画面标签排序相关输入校验")
	@PostMapping(value = "/checkinputlabelSort")
	@ResponseBody
	public JSONObject checkInputlabelSort(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
		JSONObject jsonObject = new JSONObject();
		HjhAllocationEngineResponse hjhAllocationEngineResponse = new HjhAllocationEngineResponse();
		AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		String labelSort = form.getLabelSort();
		String planNid = form.getPlanNid();
		String addOrModify = form.getAddOrModify();
		
		if(StringUtils.isEmpty(labelSort)){
			jsonObject.put("info", "请输入标签排序");
			jsonObject.put("status", "99");
			return jsonObject;
		}
		if(StringUtils.isEmpty(planNid)){
			jsonObject.put("info", "该标签所属智投编号不存在，请查询智投专区");
			jsonObject.put("status", "99");
			return jsonObject;
		}
		if(StringUtils.isEmpty(addOrModify)){
			jsonObject.put("info", "前端未传此行为是修改还是添加标识！");
			jsonObject.put("status", "99");
			return jsonObject;
		}
		form.setPlanNidSrch(planNid);
		//如果是修改则需要校验引擎表里面的排序，如果是添加则不需要  0 添加；1修改
		if(form.getAddOrModify().equalsIgnoreCase("1")){
			hjhAllocationEngineResponse = this.allocationEngineService.getHjhAllocationEngineList(form);
			List<HjhAllocationEngineVO> hjhAllocationEngineList = hjhAllocationEngineResponse.getResultList();
			if (hjhAllocationEngineList != null) {
				for(HjhAllocationEngineVO object : hjhAllocationEngineList){
					//取自DB的LabelSort
					Integer planLabelSort = object.getLabelSort();
					//如果 取自DB的LabelSort 等同于 画面传入的 labelSort,那说明重复，则不能插入
					if(planLabelSort !=null && planLabelSort.equals(Integer.valueOf(labelSort))){
						jsonObject.put("info", "该智投服务已有标签使用此排序,请重新输入排序");
						jsonObject.put("status", "99");
						return jsonObject;
					}
				}
			} else{
				jsonObject.put("info", "修改此标签排序时查询引擎发现此智投编号不存在于引擎中！");
				jsonObject.put("status", "99");
				return jsonObject;
			}
		} 
		// else 属于 0 添加时，此时该计划还未添加到引擎中，不需要校验
		jsonObject.put("status", "000");
		return jsonObject;
	}
	
	/**
	 * 计划配置info画面 确认后添加计划配置列表   已测试
	 *
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "将Info入力数据添加到计划引擎配置", notes = "将Info入力数据添加到计划引擎配置")
	@PostMapping(value = "/insertconfig")
	@ResponseBody
	public JSONObject insertConfigAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
		JSONObject jsonObject = new JSONObject();
		HjhLabelRequest hjhLabelRequest = new HjhLabelRequest();
		// 与实体bean一致 用于转型
		HjhAllocationEngineVO newForm = new HjhAllocationEngineVO();
		AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		// 获取当前登陆者id
		int userId = Integer.valueOf(this.getUser(request).getId());
		
		// 防止多次提交，做一次校验
		if(StringUtils.isNotEmpty(form.getLabelName().trim())  && StringUtils.isNotEmpty(form.getPlanNid())){
			int existflg = this.allocationEngineService.checkRepeat(form);
			if(existflg>0){
				jsonObject.put("error", "该标签已经被使用，无法再次添加!");
				jsonObject.put("status", FAIL);
				jsonObject.put("msg", FAIL_DESC);
				return jsonObject;
			}
		}
		
		// 校验已经在前面异步校验了
		// (1).从专区表中查出必要信息
		HjhRegionVO record = this.allocationEngineService.getHjhRegionRecordByPlanNid(form.getPlanNid());
		//1.
		newForm.setPlanNid(record.getPlanNid());
		//2.
		newForm.setPlanName(record.getPlanName());
		//3.
		newForm.setConfigAddTime(record.getConfigAddTime());
		//4.
		newForm.setConfigStatus(record.getConfigStatus());
		// (2).从标签表中查出必要信息
		hjhLabelRequest.setLabelNameSrch(form.getLabelName().trim());
		// 通过标签名称只能查到一条记录，因为标签名称在标签表时唯一的
		List<HjhLabelCustomizeVO> list = this.labelService.getHjhLabelListByLabelName(hjhLabelRequest);
		if(CollectionUtils.isEmpty(list)){
			jsonObject.put("error", "此标签名称在标签列表不存在！请从标签列表选择已经存在的标签使用！");
			jsonObject.put("status", FAIL);
			jsonObject.put("msg", FAIL_DESC);
			return jsonObject;
		}
		HjhLabelCustomizeVO  hjhLabelCustomizeVO = list.get(0);
		//5.
		newForm.setLabelId(hjhLabelCustomizeVO.getId());
		//6.
		newForm.setLabelName(hjhLabelCustomizeVO.getLabelName());
		//7.
		newForm.setAddTime(GetDate.getNowTime10());
		//8.
		newForm.setLabelSort(Integer.valueOf(form.getLabelSort()));//已经校验过必须入力的LabelSort
		//9.
		if(StringUtils.isNotEmpty(form.getTransferTimeSort())){//表单入力时
			newForm.setTransferTimeSort(Integer.valueOf(form.getTransferTimeSort()));
		}
		//10.
		if(StringUtils.isNotEmpty(form.getTransferTimeSortPriority())){//表单入力时
			newForm.setTransferTimeSortPriority(Integer.valueOf(form.getTransferTimeSortPriority()));
		}
		//11.
		if(StringUtils.isNotEmpty(form.getAprSort())){//表单入力时
			newForm.setAprSort(Integer.valueOf(form.getAprSort()));
		}
		//12.
		if(StringUtils.isNotEmpty(form.getAprSortPriority())){//表单入力时
			newForm.setAprSortPriority(Integer.valueOf(form.getAprSortPriority()));
		}
		//13.
		if(StringUtils.isNotEmpty(form.getActulPaySort())){//表单入力时
			newForm.setActulPaySort(Integer.valueOf(form.getActulPaySort()));
		}
		//14.
		if(StringUtils.isNotEmpty(form.getActulPaySortPriority())){//表单入力时
			newForm.setActulPaySortPriority(Integer.valueOf(form.getActulPaySortPriority()));
		}
		//15.
		if(StringUtils.isNotEmpty(form.getInvestProgressSort())){//表单入力时
			newForm.setInvestProgressSort(Integer.valueOf(form.getInvestProgressSort()));
		}
		//16.
		if(StringUtils.isNotEmpty(form.getInvestProgressSortPriority())){//表单入力时
			newForm.setInvestProgressSortPriority(Integer.valueOf(form.getInvestProgressSortPriority()));
		}
		//17.
		newForm.setLabelStatus(Integer.valueOf(form.getLabelStatus()));//必须入力
		
		//18.
		newForm.setCreateUser(userId);
		
		int count = this.allocationEngineService.insertHjhAllocationEngineRecord(newForm);
		if(count > 0){
			jsonObject.put("status", SUCCESS);
			jsonObject.put("msg", SUCCESS_DESC);
		}
		return jsonObject;
	}
	
	/**
	 * 点击换绑迁按钮迁移到换绑详情info画面     已测试
	 * 
	 * @Title moveToInfoAction
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "生成换绑详情info画面", notes = "生成换绑详情info画面")
	@PostMapping(value = "/change")
	@ResponseBody
	public JSONObject getEngineChangeView(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
		JSONObject jsonObject = new JSONObject();
		AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		if(form.getPlanNidSrch()!= null){
			jsonObject.put("planNid", form.getPlanNidSrch());
			HjhAllocationEngineVO vo = this.allocationEngineService.getPlanConfigRecord(Integer.parseInt(form.getId()));
			jsonObject.put("labelName", vo.getLabelName());
		}
		if (StringUtils.isNotEmpty(form.getLabelId())) {
			HjhAllocationEngineVO hjhAllocationEngineVO = this.allocationEngineService.getPlanConfigRecordByParam(form);
			jsonObject.put("hjhAllocationEngineVO", hjhAllocationEngineVO);
		}
		return jsonObject;
	}
	
	
	/**
	 * 点击换绑后的保存
	 * 根据获取到的leableName到allocation-engine查询出来相对应的数据,执行删除操作
	 * 根据planNid去新加一条数据,
	 * @Title moveToInfoAction
	 * @param request
	 * @param form
	 * @return
	 */
	@ApiOperation(value = "计划配置画面换绑确认后修改DB", notes = "计划配置画面换绑确认后修改DB")
	@PostMapping(value = "/updateconfigactioninfo")
	@ResponseBody
	public AdminResult<String> updateConfigActionInfo(HttpServletRequest request, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
		HjhAllocationEngineResponse response = new HjhAllocationEngineResponse();
		AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		//获取原计划的计划编号
		String planNidSrch = form.getPlanNidSrch();
		//获取到当前输入的订单号
		String planNid = form.getPlanNid();
		String newLabelStatus = form.getLabelStatus();
		String id = form.getId();
		form.setPlanNid(planNidSrch);
		form.setLabelStatus("0");
		//根据LabelName进行更改状态
		HjhAllocationEngineVO hjhAllocationEngine = new HjhAllocationEngineVO();
		BeanUtils.copyProperties(form,hjhAllocationEngine);	
		hjhAllocationEngine.setLabelStatus(0);
		hjhAllocationEngine.setId(Integer.parseInt(id));
		this.allocationEngineService.updateHjhAllocationEngineRecord(hjhAllocationEngine);
		hjhAllocationEngine.setPlanNid(planNid);
		form.setPlanNameSrch(planNid);
		String planNameM = this.allocationEngineService.getPlanNameByPlanNid(form);
		hjhAllocationEngine.setPlanName(planNameM);
		hjhAllocationEngine.setLabelStatus(Integer.parseInt(newLabelStatus));
		hjhAllocationEngine.setLabelSort(Integer.parseInt(form.getLabelSort()));
/*		hjhAllocationEngine.setUpdateTime(GetDate.getNowTime10());
		hjhAllocationEngine.setCreateTime(GetDate.getNowTime10());*/
		int flag = this.allocationEngineService.updateHjhAllocationEngineRecord(hjhAllocationEngine);
		if(flag > 0){
			response.setMessage(Response.SUCCESS);
		}
		return new AdminResult<String>(response.getMessage());
	}
	
	/** 
	 * 修改畫面     已测试
	 * 
	 * @param request
	 * @param form
	 * @return
	 */	
	@ApiOperation(value = "计划配置画面修改完后更新DB", notes = "计划配置画面修改完后更新DB")
	@PostMapping(value = "/updateconfigaction")
	@ResponseBody
	public AdminResult<String> updateConfigAction(HttpServletRequest request, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
		HjhAllocationEngineResponse response = new HjhAllocationEngineResponse();
		// 开始插表
		HjhAllocationEngineVO record = new HjhAllocationEngineVO();
		AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		if (StringUtils.isNotEmpty(form.getPlanNid()) && StringUtils.isNotEmpty(form.getLabelName())) {
			// 通过 计划编号 和 标签名称 查询 引擎表
/*			record = this.allocationEngineService.getPlanConfigRecordByParam(form);*/
			record = this.allocationEngineService.getPlanConfigRecordByPlanNidLabelName(form);
			//1.
			record.setLabelName(form.getLabelName());
			//2.
			if(StringUtils.isNotEmpty(form.getTransferTimeSort())){
				record.setTransferTimeSort(Integer.valueOf(form.getTransferTimeSort()));
			} 
			//3.
			if(StringUtils.isNotEmpty(form.getTransferTimeSortPriority())){
				record.setTransferTimeSortPriority(Integer.valueOf(form.getTransferTimeSortPriority()));
			} 
			//4.
			if(StringUtils.isNotEmpty(form.getAprSort())){
				record.setAprSort(Integer.valueOf(form.getAprSort()));
			}
			//5.
			if(StringUtils.isNotEmpty(form.getAprSortPriority())){
				record.setAprSortPriority(Integer.valueOf(form.getAprSortPriority()));
			}
			//6.
			if(StringUtils.isNotEmpty(form.getActulPaySort())){
				record.setActulPaySort(Integer.valueOf(form.getActulPaySort()));
			}
			//7.
			if(StringUtils.isNotEmpty(form.getActulPaySortPriority())){
				record.setActulPaySortPriority(Integer.valueOf(form.getActulPaySortPriority()));
			}
			//8.
			if(StringUtils.isNotEmpty(form.getInvestProgressSort())){
				record.setInvestProgressSort(Integer.valueOf(form.getInvestProgressSort()));
			}
			//9.
			if(StringUtils.isNotEmpty(form.getInvestProgressSortPriority())){
				record.setInvestProgressSortPriority(Integer.valueOf(form.getInvestProgressSortPriority()));
			}
			//9.x
			if(StringUtils.isNotEmpty(form.getPlanNid())){
				record.setPlanNid(form.getPlanNid());
			}
			if(StringUtils.isNotEmpty(form.getConfigStatus())){
				record.setConfigStatus(Integer.valueOf(form.getConfigStatus()));
			}
			if(StringUtils.isNotEmpty(form.getLabelSort())){
				record.setLabelSort(Integer.valueOf(form.getLabelSort()));
			}
			//10.
			record.setLabelSort(Integer.valueOf(form.getLabelSort()));
			//11.
			record.setLabelStatus(Integer.valueOf(form.getLabelStatus()));
		
			int flg = this.allocationEngineService.updateHjhAllocationEngineRecord(record);
	
			if(flg > 0){
				response.setMessage(Response.SUCCESS);
			}
		}
		return new AdminResult<String>(response.getMessage());
	}
	
	
	/**
	 * 计划配置info画面标签排序入力校验   已测试
	 *
	 * @param labelName
	 * @return 
	 */
	@ApiOperation(value = "计划配置info画面修改前报消息", notes = "计划配置info画面修改前报消息")
	@PostMapping(value = "/report")
	@ResponseBody
	public JSONObject reportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
		JSONObject jsonObject = new JSONObject();
		HjhLabelRequest label = new HjhLabelRequest();
		AllocationEngineRuquest form = new AllocationEngineRuquest();
		// 将画面请求request赋值给原子层 request
		BeanUtils.copyProperties(viewRequest, form);
		//原始画面已经存在，不需要校验为空
		String labelId = form.getLabelId();
		String planNid = form.getPlanNid();
		// (计划专区)原始专区画面已经有此计划了，不需要为空校验
		HjhRegionVO hjhRegion  = this.allocationEngineService.getHjhRegionRecordByPlanNid(planNid);
		label.setLabelIdSrch(Integer.valueOf(labelId));
		// (标签列表)原始画面已经有此标签了通过标签id只能检索出一条记录
		List<HjhLabelCustomizeVO>  hjhLabelList = this.labelService.getHjhLabelListById(label);
		HjhLabelCustomizeVO hjhLabel = hjhLabelList.get(0);
		// (引擎)
		HjhAllocationEngineVO hjhAllocationEngine = this.allocationEngineService.getPlanConfigRecordByParam(form);
		if(hjhRegion.getConfigStatus() == 0 && hjhLabel.getLabelState()  == 1 && hjhAllocationEngine.getLabelStatus() == 0){
			jsonObject.put("info", "智投服务已被停用需重新启用");
			jsonObject.put("status", "0");
			return jsonObject;
		}
		if(hjhRegion.getConfigStatus() == 0 && hjhLabel.getLabelState()  == 0 && hjhAllocationEngine.getLabelStatus() == 0){
			jsonObject.put("info", "智投服务和标签都已被停用需重新启用");
			jsonObject.put("status", "1");
			return jsonObject;
		}
		if(hjhRegion.getConfigStatus() == 1 && hjhLabel.getLabelState()  == 0 && hjhAllocationEngine.getLabelStatus() == 0){
			jsonObject.put("info", "标签已被停用需重新启用");
			jsonObject.put("status", "2");
			return jsonObject;
		}
		if(hjhRegion.getConfigStatus() == 1 && hjhLabel.getLabelState()  == 1 && hjhAllocationEngine.getLabelStatus() == 0){
/*			jsonObject.put("info", "确定要执行本次操作吗！");
			jsonObject.put("status", "3");*/
			jsonObject.put("info", SUCCESS);
			jsonObject.put("status", SUCCESS_DESC);
			return jsonObject;
		}
		return jsonObject;
	}
		
	/**
	 * 计划配置info画面标签排序入力校验    已测试
	 *
	 * @param labelName
	 * @return 
	 */
	@ApiOperation(value = "校验计划编号是否存在", notes = "校验计划编号是否存在")
	@PostMapping(value = "/isexistsplanenumber")
	@ResponseBody
	public JSONObject isExistsPlaneNumber(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid AllocationEngineViewRequest  viewRequest) {
		JSONObject jsonObject = new JSONObject();
		// request实际从前台传入的就是 planNid
		/*String planNid = request.getParameter("param");*/
		String planNid = viewRequest.getPlanNidSrch();
		if (StringUtils.isEmpty(planNid)) {
			jsonObject.put("error", "未传入智投编号！");
			return jsonObject;
		}
		int flag = this.isExistsPlanNumber(planNid);
		if (flag == 1) {
			jsonObject.put("error", "该智投编号不存在！");
			return jsonObject;
		} else if (flag == 2) {
			jsonObject.put("error", "该智投服务已经被禁用！");
			return jsonObject;
		} else if (flag == 3) {
			jsonObject.put("error", "该智投服务不存在于智投服务专区，请在智投服务专区添加！");
			return jsonObject;
		} else{
			jsonObject.put("success", SUCCESS);
			jsonObject.put("success", SUCCESS_DESC);
		}
		return jsonObject;
	}
	
	/**
	 * 去数据库查询计划编号是不是存在
	 * @param planNid
	 * @return
	 */
	public int isExistsPlanNumber(String planNid) {
		if (StringUtils.isNotEmpty(planNid)) {
			List<HjhPlanVO> list = this.allocationEngineService.getHjhPlanByPlanNid(planNid);
			if(list == null || list.size()==0) {
				// 该计划不存在。
				return 1;
			}
			HjhPlanVO hjhPlan = list.get(0);
			if (hjhPlan.getDelFlag()==1) {
				//该计划已经被禁用
				return 2;
			}
			List<HjhRegionVO> regionList = this.allocationEngineService.getHjhRegioByPlanNid(planNid);
			if(regionList == null || regionList.size()==0) {
				// 该计划编号不存在与计划专区
				return 3;
			}
		}
		return 0;
	}	
								/*--------以上为计划专区下属 引擎配置画面各项机能----------*/
}
