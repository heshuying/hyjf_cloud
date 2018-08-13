/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.hyjf.admin.beans.request.ProtocolsRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ProtocolsService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.FddTempletCustomizeResponse;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.FddTempletCustomizeVO;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version ProtocolsController, v0.1 2018/7/10 16:03
 */
@Api(value = "协议管理", tags = "协议管理")
@RestController
@RequestMapping("/hyjf-admin/protocols")
public class ProtocolsController extends BaseController {
	@Autowired
	private ProtocolsService protocolsService;

	@ApiOperation(value = "展示协议管理列表", notes = "展示协议管理列表")
	@RequestMapping("/init")
	public AdminResult<ListResult<FddTempletCustomizeVO>> selectFddTempletList(
			@RequestBody ProtocolsRequestBean request) {
		FddTempletCustomizeResponse response = protocolsService.selectFddTempletList(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "添加协议管理", notes = "添加协议管理")
	@RequestMapping("/insert")
	public AdminResult insert(@RequestBody ProtocolsRequestBean requestBean, HttpServletRequest request) {
		AdminSystemVO user = getUser(request);
		requestBean.setCreateUserId(Integer.valueOf(user.getId()));
		requestBean.setCreateUserName(user.getUsername());
		FddTempletCustomizeResponse response = protocolsService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "修改协议管理", notes = "修改协议管理")
	@RequestMapping("/update")
	public AdminResult update(@RequestBody ProtocolsRequestBean requestBean, HttpServletRequest request) {
		AdminSystemVO user = getUser(request);
		requestBean.setCreateUserId(Integer.valueOf(user.getId()));
		requestBean.setCreateUserName(user.getUsername());
		FddTempletCustomizeResponse response = protocolsService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	/**
	 * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
	 * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
	 * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ApiOperation(value = "导出excel", notes = "导出excel")
	@RequestMapping("/exportaction")
	public void exportExcel(@ModelAttribute ProtocolsRequestBean form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 表格sheet名称
		String sheetName = "协议管理";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		// 需要输出的结果列表
		List<FddTempletCustomizeVO> recordList = new ArrayList<>();
		FddTempletCustomizeResponse fddResponse = this.protocolsService.selectFddTempletList(form);
		if(fddResponse != null) {
			recordList = fddResponse.getResultList();
		}
		String[] titles = new String[] { "序号", "模版编号", "协议类型", "启用状态", "CA认证", "认证时间", "操作人", "操作时间", "备注" };
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

		if (recordList != null && recordList.size() > 0) {

			int sheetCount = 1;
			int rowNum = 0;

			for (int i = 0; i < recordList.size(); i++) {
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
					FddTempletCustomizeVO data = recordList.get(i);
					// 创建相应的单元格
					Cell cell = row.createCell(celLength);
					if (celLength == 0) {// 序号
						cell.setCellValue(i + 1);
					} else if (celLength == 1) {// 模版编号
						cell.setCellValue(data.getTempletId());
					} else if (celLength == 2) {// 协议类型
						cell.setCellValue(data.getProtocolTypeName());
					} else if (celLength == 3) {// 启用状态
						cell.setCellValue(data.getIsActive().compareTo(1) == 0 ? "启用" : "关闭");
					} else if (celLength == 4) {// CA认证
						cell.setCellValue(data.getCaFlagName());
					} else if (celLength == 5) {// 认证时间
						if (data.getCertificateTime() == null){
							cell.setCellValue("");
						}else{
							cell.setCellValue(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(data.getCertificateTime()));
						}
					} else if (celLength == 6) {// 操作人
						cell.setCellValue(data.getCreateUserName());
					} else if (celLength == 7) {// 操作时间
						Long time = data.getCreateTime().getTime() / 1000;
						cell.setCellValue(GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(time.intValue()));
					} else if (celLength == 8) {// 备注
						cell.setCellValue(data.getRemark());
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}

}
