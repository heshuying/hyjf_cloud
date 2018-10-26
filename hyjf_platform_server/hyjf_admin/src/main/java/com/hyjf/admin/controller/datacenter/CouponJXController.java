/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.datacenter;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.DataCenterCouponBean;
import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.DataCenterCouponService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DataCenterCouponResponse;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Yinhui
 * @version CouponJXController, v0.1 2018/7/18 18:18
 */
@Api(tags = "数据中心-优惠券-加息券")
@RestController
@RequestMapping("/hyjf-admin/datacenter/coupon_jx")
public class CouponJXController extends BaseController {
	@Autowired
	private DataCenterCouponService couponService;

	@ApiOperation(value = "数据中心-加息券", notes = "数据中心-加息券列表查询")
	@PostMapping("/get_coupon_list")
	public AdminResult<ListResult<DataCenterCouponCustomizeVO>> getCouponList(@RequestBody DadaCenterCouponRequestBean requestBean) {
		DataCenterCouponResponse response = couponService.searchAction(requestBean, "JX");
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}


//	public void exportJXAction(HttpServletRequest request, HttpServletResponse response, DataCenterCouponBean form) throws Exception {
//		// 表格sheet名称
//		String sheetName = "加息券列表";
//		DataCenterCouponCustomizeVO dataCenterCouponCustomize =createDataCenterCouponCustomize(form);
//		List<DataCenterCouponCustomizeVO> resultList  = this.couponService.getRecordListJX(dataCenterCouponCustomize);
//		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
//		String[] titles = new String[] {"序号", "来源", "已发放数量","已使用数量","已失效数量","使用率","失效率","总收益","已发放收益" ,"待发放收益" ,"累计真实投资金额" };
//		// 声明一个工作薄
//		HSSFWorkbook workbook = new HSSFWorkbook();
//
//		// 生成一个表格
//		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
//
//		if (resultList != null && resultList.size() > 0) {
//
//			int sheetCount = 1;
//			int rowNum = 0;
//
//			for (int i = 0; i < resultList.size(); i++) {
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
//					DataCenterCouponCustomizeVO pInfo = resultList.get(i);
//					// 创建相应的单元格
//					Cell cell = row.createCell(celLength);
//					// 序号
//					if (celLength == 0) {
//						cell.setCellValue(i + 1);
//					}
//					else if (celLength == 1) {
//						cell.setCellValue(pInfo.getTitle());
//					}
//					else if (celLength == 2) {
//						cell.setCellValue(pInfo.getGrantNum());
//					}
//					else if (celLength == 3) {
//						cell.setCellValue(pInfo.getUsedNum());
//					}
//					else if (celLength == 4) {
//						cell.setCellValue(pInfo.getExpireNum());
//					}
//					else if (celLength == 5) {
//						cell.setCellValue(pInfo.getUtilizationRate());
//					}
//					else if (celLength == 6) {
//						cell.setCellValue(pInfo.getFailureRate());
//					}
//					else if (celLength == 7) {
//						cell.setCellValue(pInfo.getRecoverInterest());
//					}
//					else if (celLength == 8) {
//						cell.setCellValue(pInfo.getRecivedMoney());
//					}
//					else if (celLength == 9) {
//						cell.setCellValue(pInfo.getNorecivedMoney());
//					}
//					else if (celLength == 10) {
//						cell.setCellValue(pInfo.getRealTenderMoney());
//					}
//				}
//			}
//		}
//		// 导出
//		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//
//	}

	private DataCenterCouponCustomizeVO createDataCenterCouponCustomize(DataCenterCouponBean form) {
		DataCenterCouponCustomizeVO dataCenterCouponCustomize = new DataCenterCouponCustomizeVO();

       /* if(StringUtils.isNotEmpty(form.getOrderId())){
            couponBackMoneyCustomize.setNid(form.getOrderId());
        }
        if(StringUtils.isNotEmpty(form.getUsername())){
            couponBackMoneyCustomize.setUsername(form.getUsername());
        }
        if(StringUtils.isNotEmpty(form.getCouponCode())){
            couponBackMoneyCustomize.setCouponCode(form.getCouponCode());
        }
        if(StringUtils.isNotEmpty(form.getBorrowNid())){
            couponBackMoneyCustomize.setBorrowNid(form.getBorrowNid());
        }
        if(StringUtils.isNotEmpty(form.getCouponReciveStatus())){
            couponBackMoneyCustomize.setReceivedFlg(form.getCouponReciveStatus());
        }
        if(StringUtils.isNotEmpty(form.getTimeStartSrch())){
            couponBackMoneyCustomize.setTimeStartSrch(form.getTimeStartSrch());
        }
        if(StringUtils.isNotEmpty(form.getTimeEndSrch())){
            couponBackMoneyCustomize.setTimeEndSrch(form.getTimeEndSrch());
        }*/
		return dataCenterCouponCustomize;
	}
	@ApiOperation(value = "导出加息券列表", notes = "导出加息券列表")
	@GetMapping("/export_jx_action")
	 public void exportToExcel(HttpServletRequest request, HttpServletResponse response, DataCenterCouponBean form) throws Exception {
	        //sheet默认最大行数
	        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
			// 表格sheet名称
			String sheetName = "代金券列表";
	        // 文件名称
	        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
	        // 声明一个工作薄
	        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
	        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

			DataCenterCouponCustomizeVO dataCenterCouponCustomize =createDataCenterCouponCustomize(form);
			List<DataCenterCouponCustomizeVO> resultList  = this.couponService.getRecordListJX(dataCenterCouponCustomize);
	        
	        Integer totalCount = resultList.size();

	        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
	        Map<String, String> beanPropertyColumnMap = buildMap();
	        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
	        String sheetNameTmp = sheetName + "_第1页";
	        if (totalCount == 0) {
	        	
	            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
	        }else {
	        	 helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList.subList(0, defaultRowMaxCount));
	        }
	        for (int i = 1; i < sheetCount; i++) {
	
	                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
	                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList.subList(defaultRowMaxCount*i, defaultRowMaxCount*(i+1)));
	            } 
	        
	        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	    }

	    private Map<String, String> buildMap() {
	        Map<String, String> map = Maps.newLinkedHashMap();
	        map.put("title", "来源");
	        map.put("grantNum", "已发放数量");
	        map.put("usedNum", "已使用数量");
	        map.put("expireNum", "已失效数量");
	        map.put("utilizationRate", "使用率");
	        map.put("failureRate", "失效率");
	        map.put("recoverInterest", "总收益");
	        map.put("recivedMoney", "已发放收益");
	        map.put("norecivedMoney", "待发放收益");
	        map.put("realTenderMoney", "累计真实投资金额");
	    
	        return map;
	    }
	    private Map<String, IValueFormatter> buildValueAdapter() {
	        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
	        return mapAdapter;
	    }
}
