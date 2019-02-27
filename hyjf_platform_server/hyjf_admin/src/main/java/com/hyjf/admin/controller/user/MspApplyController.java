package com.hyjf.admin.controller.user;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.MspApplytRequestBean;
import com.hyjf.admin.beans.response.MspApplytResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.MspApplyService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.user.MspApplytResponse;
import com.hyjf.am.response.user.MspResponse;
import com.hyjf.am.resquest.user.MspApplytRequest;
import com.hyjf.am.resquest.user.MspRequest;
import com.hyjf.am.vo.user.MspApplyVO;
import com.hyjf.am.vo.user.MspConfigureVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 
 * @author
 *
 */
@Api(value = "会员中心-安融反欺诈",tags = "会员中心-安融反欺诈")
@RestController
@RequestMapping("/hyjf-admin/msp")
public class MspApplyController extends BaseController {

	private static final String PERMISSIONS = "mspapply";
	private static final String PERMISSIONS2 = "msp";
	@Autowired
	private MspApplyService mspApplyService;

	/**
	 * 列表维护画面初始化
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@ApiOperation(value = "预注册用户页面载入", notes = "预注册用户页面载入")
	@PostMapping(value = "/init")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<MspApplyVO>> init(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {
		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);

		MspApplytResponse prs = mspApplyService.getRecordList(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<ListResult<MspApplyVO>>(ListResult.build(prs.getResultList(), prs.getRecordTotal()));
	}

	/**
	 * 画面迁移(含有id更新，不含有id添加)
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@ApiOperation(value = "新增页面初始化", notes = "新增页面初始化")
	@PostMapping("/infoAction")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<MspApplytResponseBean> info(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {
		MspApplytResponse prs = mspApplyService.infoAction();
		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		MspApplytResponseBean marb = new MspApplytResponseBean();
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		marb.setApplyDate(formatter.format(currentTime));

		marb.setRegionList(prs.getRegionList());
		marb.setConfigureList(prs.getConfigureList());
		return new AdminResult<MspApplytResponseBean>(marb);
	}

	/**
	 * 添加活动信息
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@ApiOperation(value = "新增", notes = "新增")
	@PostMapping("/insertAction")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public AdminResult insertAction(HttpServletRequest request,
			@RequestBody MspApplytRequest mspApplytRequestBean) {
		// 可以直接使用
		mspApplytRequestBean.setAdminId(this.getUser(request).getId());
		mspApplytRequestBean.setAdmin(this.getUser(request).getTruename());
		MspApplytResponse prs = mspApplyService.insertAction(mspApplytRequestBean);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<>();
	}

	

	/**
	 * 修改活动维护信息
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@ApiOperation(value = "修改", notes = "修改")
	@PostMapping("/updateAction")
	@ResponseBody
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult updateAction(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {
		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);
		// form.setLogo("1");
		// 更新
		MspApplytResponse prs=mspApplyService.updateAction(aprlr);
		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<>();
	}

	/**
	 * 删除胡配置信息
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@ApiOperation(value = "删除", notes = "删除")
	@PostMapping("/deleteAction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
	public AdminResult deleteRecordAction(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {

		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);
		// form.setLogo("1");
		// 更新
		MspApplytResponse prs=mspApplyService.deleteRecordAction(aprlr);
		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<>();
	}

	

	@ResponseBody
	@PostMapping("/validateBeforeAction")
	@ApiOperation(value = "验证", notes = "验证")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult validateBeforeAction(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {
		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);
		mspApplytRequestBean.setAdminId(this.getUser(request).getId());
		MspApplytResponse prs = mspApplyService.validateBeforeAction(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<>();
	}

	/**
	 * 导出功能
	 * 
	 * @param request
	 * @param
	 * @param
	 */
	//@PostMapping("/exportAction")
	//@ApiOperation(value = "导出", notes = "导出")
	/*@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportAction(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean,HttpServletResponse response) throws Exception {
	
		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		aprlr.setPageSize(-1);
		aprlr.setCurrPage(-1);
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);

		MspApplytResponse prs = mspApplyService.getRecordList(aprlr);

		List<MspApplyVO> recordList = this.mspApplyService.getRecordList(aprlr).getResultList();

		// 表格sheet名称
		String sheetName = "安融反欺诈查询";

		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
				+ CustomConstants.EXCEL_EXT;
		String[] titles = new String[] { "序号", "姓名", "身份证号", "操作人", "查询时间" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (recordList != null && recordList.size() > 0) {

			int sheetCount = 1;
			int rowNum = 0;

			for (int i = 0; i < recordList.size(); i++) {
				rowNum++;
				if (i != 0 && i % 60000 == 0) {
					sheetCount++;
					sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
							(sheetName + "_第" + sheetCount + "页"));
					rowNum = 1;
				}

				// 新建一行
				Row row = sheet.createRow(rowNum);
				// 循环数据
				for (int celLength = 0; celLength < titles.length; celLength++) {
					MspApplyVO pInfo = recordList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					} else if (celLength == 1) {
						cell.setCellValue(pInfo.getName());
					} else if (celLength == 2) {
						cell.setCellValue(pInfo.getIdentityCard());
					} else if (celLength == 3) {
						cell.setCellValue(pInfo.getCreateUser());
					} else if (celLength == 4) {
						Long time1 = new Long(pInfo.getCreateTime());
						String d = format.format(time1 * 1000);
						cell.setCellValue(d);
					}

				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}*/

	/**
	 * 导出excel
	 *
	 * @param mspApplytRequestBean
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@PostMapping("/exportAction")
	@ApiOperation(value = "导出", notes = "导出")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportExcelAccount(HttpServletRequest request,
								   @RequestBody MspApplytRequestBean mspApplytRequestBean,HttpServletResponse response) throws Exception {
		// 封装查询条件
		MspApplytRequest aprlr = new MspApplytRequest();
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "安融反欺诈查询";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
		aprlr.setLimitFlg(true);
		//请求第一页5000条
		aprlr.setPageSize(defaultRowMaxCount);
		aprlr.setCurrPage(1);
		// 需要输出的结果列表
		MspApplytResponse recordList = this.mspApplyService.getRecordList(aprlr);
		Integer totalCount = recordList.getRecordTotal();
		int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
		Map<String, String> beanPropertyColumnMap = buildMap();
		Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
		String sheetNameTmp = sheetName + "_第1页";
		if (totalCount == 0) {
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		}else{
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList.getResultList());
		}
		for (int i = 1; i < sheetCount; i++) {
			aprlr.setPageSize(defaultRowMaxCount);
			aprlr.setCurrPage(i+1);
			MspApplytResponse recordList2 = this.mspApplyService.getRecordList(aprlr);
			if (recordList2 != null && recordList2.getResultList().size()> 0) {
				sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
				helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  recordList2.getResultList());
			} else {
				break;
			}
		}
		DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	}

	private Map<String, String> buildMap() {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("name", "姓名");
		map.put("identityCard", "身份证号");
		map.put("createUser", "操作人");
		map.put("createTime", "查询时间");
		return map;
	}

	private Map<String, IValueFormatter> buildValueAdapter() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		IValueFormatter createTimeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Integer createTime = (Integer) object;
				if(createTime != null){
					Long time1 = new Long(createTime);
					return format.format(time1 * 1000);
				}else{
					return "";
				}
			}
		};

		mapAdapter.put("createTime", createTimeAdapter);
		return mapAdapter;
	}


	/**
	 * 画面迁移(含有id更新，不含有id添加)
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@ResponseBody
	@PostMapping("/applyAction")
	@ApiOperation(value = "共享", notes = "共享")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public AdminResult<MspApplytResponseBean> applyInfo(HttpServletRequest request,
			@RequestBody MspApplytRequest mspApplytRequestBean) {
		mspApplytRequestBean.setAdminId(this.getUser(request).getId());
		mspApplytRequestBean.setAdmin(this.getUser(request).getTruename());
		MspApplytResponse prs = mspApplyService.applyInfo(mspApplytRequestBean);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		MspApplytResponseBean mspapply=new MspApplytResponseBean();
		BeanUtils.copyProperties(prs.getResult(),mspapply);
		mspapply.setRegionList(prs.getRegionList());
		return new AdminResult<MspApplytResponseBean>(mspapply);
	}

	// 安融共享
	@ResponseBody
	@PostMapping("/shareUser")
	@ApiOperation(value = "安融共享", notes = "安融共享")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
	public AdminResult shareUser(HttpServletRequest request,
			@RequestBody MspApplytRequest mspApplytRequestBean) {
		mspApplytRequestBean.setAdminId(this.getUser(request).getId());
		mspApplytRequestBean.setAdmin(this.getUser(request).getTruename());
		MspApplytResponse prs = mspApplyService.shareUser(mspApplytRequestBean);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<>();

	}

	/**
	 * 列表维护画面初始化
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@ResponseBody
	@PostMapping("/downloadFile")
	@ApiOperation(value = "安融下載", notes = "安融下載")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<MspApplytResponse> download(HttpServletRequest request,
			@RequestBody MspApplytRequestBean mspApplytRequestBean) {
		MspApplytRequest aprlr = new MspApplytRequest();
		// 可以直接使用
		BeanUtils.copyProperties(mspApplytRequestBean, aprlr);
		mspApplytRequestBean.setAdminId(this.getUser(request).getId());
		MspApplytResponse prs = mspApplyService.download(aprlr);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<>(prs);
	
	}
	
	@ResponseBody
	@PostMapping("/mspSearchAction")
	@ApiOperation(value = "安融配置列表", notes = "安融配置列表")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<ListResult<MspConfigureVO>> searchAction(HttpServletRequest request,
			@RequestBody MspRequest mspRequestBean) {


		 MspResponse prs = mspApplyService.searchAction(mspRequestBean);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult<ListResult<MspConfigureVO>>(ListResult.build(prs.getResultList(), prs.getRecordTotal()));
		
	}
	@ResponseBody
	@PostMapping("/mspInfoAction")
	@ApiOperation(value = "配置新增页面初始化", notes = "配置新增页面初始化")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult<Map<String, Object>> infoAction(HttpServletRequest request,
			@RequestBody MspRequest mspRequestBean) {
		Map<String, Object> map = new HashMap<String, Object>();

		 MspResponse prs = mspApplyService.infoAction(mspRequestBean);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		if( prs.getResult()!=null) {
			map.put("record", prs.getResult());
		}
		
		map.put("regionList", prs.getRegionList());
		return new AdminResult<Map<String, Object>>(map);
	}
	@ResponseBody
	@PostMapping("/mspInsertAction")
	@ApiOperation(value = "新增配置", notes = "新增配置")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_ADD)
	public AdminResult insertAction(HttpServletRequest request,
			@RequestBody MspRequest mspRequestBean) {


		 MspResponse prs = mspApplyService.insertAction(mspRequestBean);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult();
	}
	@ResponseBody
	@PostMapping("/mspUpdateAction")
	@ApiOperation(value = "配置修改", notes = "配置修改")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult updateAction(HttpServletRequest request,
			@RequestBody MspRequest mspRequestBean) {

		 MspResponse prs = mspApplyService.updateAction(mspRequestBean);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult();
	}
	@ResponseBody
	@PostMapping("/configureNameError")
	@ApiOperation(value = "检测标名", notes = "检测标名")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_MODIFY)
	public AdminResult configureNameError(HttpServletRequest request,
			@RequestBody MspRequest mspRequestBean) {

		 MspResponse prs = mspApplyService.configureNameError(mspRequestBean);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult();
	}
	@ResponseBody
	@PostMapping("/mspDeleteAction")
	@ApiOperation(value = "删除配置", notes = "删除配置")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_DELETE)
	public AdminResult deleteAction(HttpServletRequest request,
			@RequestBody MspRequest mspRequestBean) {

		 MspResponse prs = mspApplyService.deleteAction(mspRequestBean);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult();
	}
	@ResponseBody
	@PostMapping("/mspCheckAction")
	@ApiOperation(value = "检查编号唯一性", notes = "检查编号唯一性")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_VIEW)
	public AdminResult checkAction(HttpServletRequest request,
			@RequestBody MspRequest mspRequestBean) {

		 MspResponse prs = mspApplyService.checkAction(mspRequestBean);

		if (prs == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!AdminResponse.isSuccess(prs)) {
			return new AdminResult<>(FAIL, prs.getMessage());

		}
		return new AdminResult();
	}

	/**
	 * 数据导出
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	/*@SuppressWarnings("deprecation")
	@PostMapping("/mspexportAction")
	@ApiOperation(value = "安融反欺诈查询配置表", notes = "安融反欺诈查询配置表")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportAction(HttpServletRequest request,
			@RequestBody MspRequest aprlr,HttpServletResponse response) throws Exception {
		

		aprlr.setPageSize(-1);
		aprlr.setCurrPage(-1);
		 MspResponse prs = mspApplyService.searchAction(aprlr);
		// 表格sheet名称
		String sheetName = "安融反欺诈查询配置表";


		List<MspConfigureVO> resultList = prs.getResultList();


		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

		String[] titles = new String[] { "序号", "标的名称", "业务类型", "借款类型(借款用途)", "审批结果 ", "借款金额（合同金额）（元）", "借款/还款期数（月）", "借款城市(借款地点)", "担保类型", "未偿还本金", "当前还款状态"};
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

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
					MspConfigureVO record = resultList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 标的名称
					else if (celLength == 1) {
						cell.setCellValue(record.getConfigureName());
					}
					// 业务类型
					else if (celLength == 2) {
						cell.setCellValue(record.getServiceTypeName());
					}
					// 借款类型
					else if (celLength == 3) {
						cell.setCellValue(record.getLoanTypeName());
					}
					// 审批结果
					else if (celLength == 4) {
						cell.setCellValue(record.getApprovalResultName());
					}
					// 借款金额
					else if (celLength == 5) {
						cell.setCellValue(record.getLoanMoney().toString());
					}
					// 期数
					else if (celLength == 6) {
						cell.setCellValue(record.getLoanTimeLimit());
					}
					// 借款地点
					else if (celLength == 7) {
						cell.setCellValue(record.getCreditAddress());
					}
					// 担保类型
					else if (celLength == 8) {
						cell.setCellValue(record.getGuaranteeTypeName());
					}

					// 未偿还本金
					else if (celLength == 9) {
						cell.setCellValue(record.getUnredeemedMoney().toString());
					}
					// 当前还款状态
					else if (celLength == 10) {
						cell.setCellValue(record.getRepaymentStatusName());
					}
					
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}*/


	/**
	 * 导出excel
	 *
	 * @param aprlr
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	@PostMapping("/mspexportAction")
	@ApiOperation(value = "安融反欺诈查询配置表", notes = "安融反欺诈查询配置表")
	@AuthorityAnnotation(key = PERMISSIONS2, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportActionDep(HttpServletRequest request, @RequestBody MspRequest aprlr,HttpServletResponse response) throws Exception {
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "安融反欺诈查询配置表";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
		aprlr.setLimitFlg(true);
		//请求第一页5000条
		aprlr.setPageSize(defaultRowMaxCount);
		aprlr.setCurrPage(1);
		// 需要输出的结果列表
		MspResponse prs = mspApplyService.searchAction(aprlr);
		Integer totalCount = prs.getRecordTotal();
		int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
		Map<String, String> beanPropertyColumnMap = buildMapDep();
		Map<String, IValueFormatter> mapValueAdapter = buildValueAdapterDep();
		String sheetNameTmp = sheetName + "_第1页";
		if (totalCount == 0) {
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		}else{
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, prs.getResultList());
		}
		for (int i = 1; i < sheetCount; i++) {
			aprlr.setPageSize(defaultRowMaxCount);
			aprlr.setCurrPage(i+1);
			MspResponse prs2 = mspApplyService.searchAction(aprlr);
			if (prs2 != null && prs2.getResultList().size()> 0) {
				sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
				helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  prs2.getResultList());
			} else {
				break;
			}
		}
		DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	}

	private Map<String, String> buildMapDep() {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("configureName", "标的名称");
		map.put("serviceTypeName", "业务类型");
		map.put("loanTypeName", "借款类型(借款用途)");
		map.put("approvalResultName", "审批结果");
		map.put("loanMoney", "借款金额（合同金额）（元）");
		map.put("loanTimeLimit", "借款/还款期数（月）");
		map.put("creditAddress", "借款城市(借款地点)");
		map.put("guaranteeTypeName", "担保类型");
		map.put("unredeemedMoney", "未偿还本金");
		map.put("repaymentStatusName", "当前还款状态");
		return map;
	}

	private Map<String, IValueFormatter> buildValueAdapterDep() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		return mapAdapter;
	}


}
