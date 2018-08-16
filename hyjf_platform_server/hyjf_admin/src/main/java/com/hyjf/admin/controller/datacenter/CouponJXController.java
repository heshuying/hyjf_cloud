/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.datacenter;

import com.hyjf.admin.beans.DataCenterCouponBean;
import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.DataCenterCouponService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version CouponJXController, v0.1 2018/7/18 18:18
 */
@Api(tags = "数据中心-加息券")
@RestController
@RequestMapping("/hyjf-admin/datacenter/coupon_jx")
public class CouponJXController extends BaseController {
	@Autowired
	private DataCenterCouponService couponService;

	@ApiOperation(value = "数据中心-加息券", notes = "数据中心-加息券列表查询")
	@PostMapping("/get_coupon_list")
	public AdminResult<ListResult<DataCenterCouponCustomizeVO>> getCouponList(DadaCenterCouponRequestBean requestBean) {
		DataCenterCouponResponse response = couponService.searchAction(requestBean, "JX");
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "导出加息券列表", notes = "导出加息券列表")
	@PostMapping("/export_jx_action")
	public void exportJXAction(HttpServletRequest request, HttpServletResponse response, DataCenterCouponBean form) throws Exception {
		// 表格sheet名称
		String sheetName = "加息券列表";
		DataCenterCouponCustomizeVO dataCenterCouponCustomize =createDataCenterCouponCustomize(form);
		List<DataCenterCouponCustomizeVO> resultList  = this.couponService.getRecordListDJ(dataCenterCouponCustomize);
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		String[] titles = new String[] {"序号", "来源", "已发放数量","已使用数量","已失效数量","使用率","失效率","总收益","已发放收益" ,"待发放收益" ,"累计真实投资金额" };
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
					DataCenterCouponCustomizeVO pInfo = resultList.get(i);
					// 创建相应的单元格
					Cell cell = row.createCell(celLength);
					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					else if (celLength == 1) {
						cell.setCellValue(pInfo.getTitle());
					}
					else if (celLength == 2) {
						cell.setCellValue(pInfo.getGrantNum());
					}
					else if (celLength == 3) {
						cell.setCellValue(pInfo.getUsedNum());
					}
					else if (celLength == 4) {
						cell.setCellValue(pInfo.getExpireNum());
					}
					else if (celLength == 5) {
						cell.setCellValue(pInfo.getUtilizationRate());
					}
					else if (celLength == 6) {
						cell.setCellValue(pInfo.getFailureRate());
					}
					else if (celLength == 7) {
						cell.setCellValue(pInfo.getRecoverInterest());
					}
					else if (celLength == 8) {
						cell.setCellValue(pInfo.getRecivedMoney());
					}
					else if (celLength == 9) {
						cell.setCellValue(pInfo.getNorecivedMoney());
					}
					else if (celLength == 10) {
						cell.setCellValue(pInfo.getRealTenderMoney());
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);

	}

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

}
