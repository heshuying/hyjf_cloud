/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.datacenter.electricitySales;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ElectricitySalesDataPushListService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.user.ElectricitySalesDataPushListResponse;
import com.hyjf.am.resquest.config.ElectricitySalesDataPushListRequest;
import com.hyjf.am.vo.config.ElectricitySalesDataPushListVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author: dzs
 * @version: ElectricitySalesDataPushListController, v0.1 2018/8/15 14:08
 */
@Api(tags = "数据中心-电销数据推送记录")
@RestController
@RequestMapping("/hyjf-admin/electricitySales")
public class ElectricitySalesDataPushListController  extends BaseController {
	/** 权限 */
	//public static final String PERMISSIONS = "electricitySales";
    @Autowired
    private  ElectricitySalesDataPushListService electricitySalesDataPushListService;

   
    /**
     * 线下修改信息同步查询列表list
     * @auth dzs
     * @param
     * @return
     */
    @ApiOperation(value = "list查询",notes = "list查询")
    @PostMapping(value = "/electricitySalesDataPushList")
	//@AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_VIEW , ShiroConstants.PERMISSION_SEARCH})
    public AdminResult<ElectricitySalesDataPushListResponse> electricitySalesDataPushList(@RequestBody ElectricitySalesDataPushListRequest request){
        return new AdminResult<ElectricitySalesDataPushListResponse>(electricitySalesDataPushListService.searchList(request));
    }
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
	//@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportExcel(HttpServletRequest request,
								   @RequestBody ElectricitySalesDataPushListRequest requestBean,HttpServletResponse response) throws Exception {
		
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "安融反欺诈查询";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
		//请求第一页5000条
		requestBean.setPageSize(defaultRowMaxCount);
		requestBean.setCurrPage(1);
		// 需要输出的结果列表
		ElectricitySalesDataPushListResponse recordList = electricitySalesDataPushListService.searchList(requestBean);
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
			requestBean.setPageSize(defaultRowMaxCount);
			requestBean.setCurrPage(i+1);
			ElectricitySalesDataPushListResponse recordList2 = electricitySalesDataPushListService.searchList(requestBean);
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
        map.put("ownerUserName", "坐席用户名");
        map.put("groupName", "所属客组");
        map.put("userName", "客户账号");
        map.put("trueName", "姓名");
        map.put("mobile", "联系电话");
        map.put("sex", "性别");
        map.put("age", "年龄");
        map.put("birthday", "出生年月日");
        map.put("regTime", "注册时间");
        map.put("pcSourceName", "PC渠道来源");
        map.put("appSourceName", "APP渠道来源");
        map.put("rechargeMoney", "充值金额");
        map.put("rechargeTime", "充值时间");
        map.put("channel", "是否渠道");
        map.put("uploadType", "上传方式");
        map.put("createTime", "创建时间");
        map.put("status", "状态");
        map.put("remark", "备注");
    
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        return mapAdapter;
    }
    /**
	 * 导出功能
	 * 
	 * @param request
	 * @param response
	 * @param form
	 * @throws Exception
	 */
	@ApiOperation(value = "导出模板")
	@PostMapping("/downloadAction")
	public void downloadAction(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 表格sheet名称
		String sheetName = "模板";

		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + "xls";

		String[] titles = new String[] { "坐席姓名", "客户账号" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName);

		CellStyle style = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		for (int i = 0; i < 3; i++) {
			sheet.setDefaultColumnStyle(i, style);
		}

		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}
    /**
	 * 借款内容填充
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = " 插入多条信息")
	@PostMapping("/insertAlectricitySalesDataPushList")
	public AdminResult<ElectricitySalesDataPushListResponse> insertAlectricitySalesDataPushList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		ElectricitySalesDataPushListRequest eRequest =new ElectricitySalesDataPushListRequest();
		List<ElectricitySalesDataPushListVO> electricitySalesDataPushList=new ArrayList<ElectricitySalesDataPushListVO>();
		
		MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest)request;

		Iterator<String> itr = multipartRequest.getFileNames();
		MultipartFile multipartFile = null;
		

		while (itr.hasNext()) {
			multipartFile = multipartRequest.getFile(itr.next());
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(multipartFile.getInputStream());

			if (hssfWorkbook != null) {
				for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
					HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);

					// 循环行Row
					for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {

						HSSFRow hssfRow = hssfSheet.getRow(rowNum);
						if (hssfRow == null || (hssfRow.getCell(0) == null && hssfRow.getCell(1) == null)) {
							continue;
						}
						if(StringUtils.isEmpty(this.getValue(hssfRow.getCell(0)))){
							continue;
						}
						if(StringUtils.isEmpty(this.getValue(hssfRow.getCell(1)))){
							continue;
						}
						if(this.getValue(hssfRow.getCell(0)).equals("坐席姓名")){
							continue;
						}
						ElectricitySalesDataPushListVO vo=new ElectricitySalesDataPushListVO();
						vo.setOwnerUserName(this.getValue(hssfRow.getCell(1)));
						vo.setUserName(this.getValue(hssfRow.getCell(2)));
						electricitySalesDataPushList.add(vo);
					}
				}
			}
		}
		
		eRequest.setElectricitySalesDataPushList(electricitySalesDataPushList);
		return  new AdminResult<ElectricitySalesDataPushListResponse>(electricitySalesDataPushListService.insertElectricitySalesDataPushList(eRequest));
	}

	/**
	 * 得到Excel表中的值
	 * 
	 * @param hssfCell
	 *            Excel中的每一个格子
	 * @return Excel中每一个格子中的值
	 */
	private String getValue(HSSFCell hssfCell) {
		if (hssfCell == null) {
			return "";
		}
		switch (hssfCell.getCellTypeEnum()) {
			case BOOLEAN:
				// 返回布尔类型的值
				return String.valueOf(hssfCell.getBooleanCellValue());
			case NUMERIC:
				// 返回数值类型的值
				String s = String.valueOf(hssfCell.getNumericCellValue());
				return s.replace(".0", "");
			case FORMULA:
				// 单元格为公式类型时
				if (CellType.NUMERIC == hssfCell.getCachedFormulaResultTypeEnum()) {
					// 返回数值类型的值
					return String.valueOf(hssfCell.getNumericCellValue());
				} else {
					// 返回字符串类型的值
					return String.valueOf(hssfCell.getStringCellValue());
				}
			case STRING:
				// 返回字符串类型的值
				return String.valueOf(hssfCell.getStringCellValue());
			default:
				return "";
		}
	}
}
