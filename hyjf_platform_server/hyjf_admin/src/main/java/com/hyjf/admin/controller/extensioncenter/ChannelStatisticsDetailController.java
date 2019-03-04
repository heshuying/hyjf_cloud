/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.extensioncenter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.ChannelStatisticsDetailService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.ChannelStatisticsDetailVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tanyy
 * @version ChannelStatisticsDetailController, v0.1 2018/7/16 16:03
 */
@Api(value = "推广中心-PC统计明细", tags = "推广中心-PC统计明细")
@RestController
@RequestMapping("/hyjf-admin/channelstatisticsdetail")
public class ChannelStatisticsDetailController extends BaseController {

	@Resource
	private ChannelStatisticsDetailService channelStatisticsDetailService;
	// 查看权限
	public static final String PERMISSIONS = "channeldetail";

	@ApiOperation(value = "PC统计明细", notes = "PC统计明细列表")
	@PostMapping("/searchaction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
	public AdminResult searchAction(@RequestBody ChannelStatisticsDetailRequest channelStatisticsDetailRequest,
			HttpServletRequest request) {
		logger.info("PC统计明细查询开始......");
		AdminResult adminResult = new AdminResult();
//		AdminSystemVO user = getUser(request);
//		// 根据用户Id查询渠道账号管理
//		AdminUtmReadPermissionsVO adminUtmReadPermissions = null;
//		if (user != null && StringUtils.isNotEmpty(user.getId())) {
//			adminUtmReadPermissions = this.channelStatisticsDetailService
//					.selectAdminUtmReadPermissions(Integer.valueOf(user.getId()));
//		}
//		if (adminUtmReadPermissions != null) {
//			channelStatisticsDetailRequest.setUtmIds(adminUtmReadPermissions.getUtmIds());// 封装到页面
//		}
		JSONObject json = channelStatisticsDetailService.searchAction(channelStatisticsDetailRequest);
		if (json == null) {
			adminResult.setStatus(FAIL);
			adminResult.setStatusDesc(FAIL_DESC);
		}
		adminResult.setData(json);
		return adminResult;

	}

	// @AuthorityAnnotation(key = PERMISSIONS, value =
	// ShiroConstants.PERMISSION_EXPORT)
	public void exportActiono(HttpServletResponse response, @RequestBody ChannelStatisticsDetailRequest form)
			throws Exception {
		// 表格sheet名称
		String sheetName = "PC渠道统计明细";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE
				+ GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		// 查询
		JSONObject json = channelStatisticsDetailService.searchAction(form);
		List<ChannelStatisticsDetailVO> recordList = (List<ChannelStatisticsDetailVO>) json.get("recordList");
		// 列头
		String[] titles = new String[] { "序号", "推广链接ID", "渠道", "用户ID", "用户名", "性别", "注册时间", "开户时间", "首次出借时间", "首投项目类型",
				"首投项目期限", "首投金额", "累计出借金额" };
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
					sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
							(sheetName + "_第" + sheetCount + "页"));
					rowNum = 1;
				}

				// 新建一行
				Row row = sheet.createRow(rowNum);
				// 循环数据
				for (int celLength = 0; celLength < titles.length; celLength++) {
					ChannelStatisticsDetailVO record = recordList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 推广链接ID
					else if (celLength == 1) {
						cell.setCellValue(record.getUtmId());
					}
					// 渠道
					else if (celLength == 2) {
						cell.setCellValue(record.getSourceName());
					}
					// 用户ID
					else if (celLength == 3) {
						cell.setCellValue(record.getUserId());
					}
					// 用户名
					else if (celLength == 4) {
						cell.setCellValue(record.getUserName());
					}
					// 性别
					else if (celLength == 5) {
						cell.setCellValue(record.getSex());
					}
					// 注册时间
					else if (celLength == 6) {
						if (record.getRegisterTime() == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(record.getRegisterTime());
						}
					}
					// 开户时间
					else if (celLength == 7) {
						if (record.getOpenAccountTime() == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(record.getOpenAccountTime());
						}
					}
					// 首次出借时间
					else if (celLength == 8) {
						if (record.getFirstInvestTime() == null) {
							cell.setCellValue("");
						} else {
							cell.setCellValue(record.getFirstInvestTime());
						}
					}
					// 首投项目类型
					else if (celLength == 9) {
						if (StringUtils.isNotEmpty(record.getInvestProjectType())) {
							cell.setCellValue(record.getInvestProjectType());
						} else {
							cell.setCellValue("");
						}
					}
					// 首投项目期限
					else if (celLength == 10) {
						if (StringUtils.isNotEmpty(record.getInvestProjectPeriod())) {
							cell.setCellValue(record.getInvestProjectPeriod());
						} else {
							cell.setCellValue("");
						}
					}
					// 首投金额
					else if (celLength == 11) {
						cell.setCellValue(
								record.getInvestAmount() == null ? "0.00" : record.getInvestAmount().toString());
					}
					// 累计出借金额
					else if (celLength == 12) {
						BigDecimal cumulativeInvest = record.getCumulativeInvest() == null ? new BigDecimal(0)
								: record.getCumulativeInvest();
						BigDecimal htjInvest = record.getHtjInvest() == null ? new BigDecimal(0)
								: record.getHtjInvest();
						BigDecimal hzrInvest = record.getHzrInvest() == null ? new BigDecimal(0)
								: record.getHzrInvest();
						cell.setCellValue(cumulativeInvest.add(htjInvest).add(hzrInvest).toString());
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);

	}

	/**
	 * @Description 数据导出--PC统计明细
	 * @Author
	 * @Version v0.1
	 * @Date
	 */
	@ApiOperation(value = "数据导出--PC统计明细", notes = "带条件导出EXCEL")
	@PostMapping(value = "/exportAction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportAction(HttpServletResponse response, @RequestBody ChannelStatisticsDetailRequest form,
			HttpServletRequest request) throws Exception {
		// 表格sheet名称
		String sheetName = "PC渠道统计明细";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE
				+ GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		// 查询
		form.setLimitStart(-1);
		JSONObject json = channelStatisticsDetailService.searchAction(form);
		List<ChannelStatisticsDetailVO> recordList = (List<ChannelStatisticsDetailVO>) json.get("recordList");
		// sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());

		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

		Integer totalCount = Integer.parseInt(json.get("count") == null ? "0" : json.get("count").toString());

		int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount
				: totalCount / defaultRowMaxCount + 1;
		Map<String, String> beanPropertyColumnMap = buildMap();
		Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
		String sheetNameTmp = sheetName + "_第1页";
		if (totalCount == 0) {
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		} else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList);
		}

        for (int i = 1; i < sheetCount; i++) {
            //请求第一页5000条
            form.setPageSize(defaultRowMaxCount);
            form.setCurrPage(i+1);
            // 封装查询条件
            JSONObject json2 = channelStatisticsDetailService.searchAction(form);
            List<ChannelStatisticsDetailVO> recordList2 = (List<ChannelStatisticsDetailVO>) json2.get("recordList");
            if (recordList2 != null && recordList2.size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  recordList2);
            } else {
                break;
            }
        }

		DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	}

	private Map<String, String> buildMap() {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("utmId", "推广链接ID");
		map.put("sourceName", "渠道");
		map.put("userId", "用户ID");
		map.put("userName", "用户名");
		map.put("sex", "性别");
		map.put("registerTime", "注册时间");
		map.put("openAccountTime", "开户时间");
		map.put("firstInvestTime", "首次出借时间");
		map.put("investProjectType", "首投项目类型");
		map.put("investProjectPeriod", "首投项目期限");
		map.put("investAmount", "首投金额");
		map.put("cumulativeInvestExcel", "累计出借金额");


		return map;
	}

	private Map<String, IValueFormatter> buildValueAdapter() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		return mapAdapter;
	}
}
