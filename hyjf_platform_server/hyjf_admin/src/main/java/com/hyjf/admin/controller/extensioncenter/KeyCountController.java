/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.extensioncenter;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.KeyCountService;
import com.hyjf.admin.service.promotion.channel.ChannelService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.KeyCountResponse;
import com.hyjf.am.resquest.user.KeyCountRequest;
import com.hyjf.am.vo.user.KeyCountVO;
import com.hyjf.am.vo.user.UtmPlatVO;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tanyy
 * @version KeyCountController, v0.1 2018/7/18 16:03
 */
@Api(tags ="推广中心-关键词设计")
@RestController
@RequestMapping("/hyjf-admin/keycount")
public class KeyCountController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(KeyCountController.class);
	@Resource
	private KeyCountService keyCountService;
	@Resource
	private ChannelService channelService;
	/** 查看权限 */
	public static final String PERMISSIONS = "keycount";

	@ApiOperation(value = "关键词设计", notes = "关键词设计列表")
	@PostMapping("/searchaction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
	public AdminResult searchAction(@RequestBody KeyCountRequest request) {
		logger.info("关键词设计查询开始......");
		KeyCountResponse response = keyCountService.searchAction(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));

	}
	@ApiOperation(value = "关键词设计获取渠道", notes = "关键词设计获取渠道")
	@GetMapping("/channels")
	public AdminResult getChannels() {
		AdminResult adminResult = new AdminResult();
		List<UtmPlatVO>  list = channelService.getUtmPlat();
		adminResult.setData(list);
		return adminResult;

	}

	public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody KeyCountRequest form) throws Exception {
		// 表格sheet名称
		String sheetName = "关键词统计";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		// 查询
		KeyCountResponse keyCountResponse = keyCountService.searchAction(form);
		List<KeyCountVO> recordList = keyCountResponse.getResultList();
		// 列头
		String[] titles = new String[] { "序号", "渠道", "关键词", "访问数", "注册数", "开户数" };
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
					KeyCountVO record = recordList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 渠道
					else if (celLength == 1) {
						cell.setCellValue(record.getSourceName());
					}
					// 关键词
					else if (celLength == 2) {
						cell.setCellValue(record.getKeyWord());
					}
					// 访问数
					else if (celLength == 3) {
						cell.setCellValue(record.getAccessNumber());
					}
					// 注册数
					else if (celLength == 4) {
						cell.setCellValue(record.getRegistNumber());
					}
					// 开户数
					else if (celLength == 5) {
						cell.setCellValue(record.getAccountNumber());
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);

	}
	@ApiOperation(value = "数据导出--关键词设计", notes = "带条件导出EXCEL")
	@PostMapping(value = "/exportAction")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportAction(HttpServletResponse response, @RequestBody KeyCountRequest form,
			HttpServletRequest request) throws Exception {
		// 表格sheet名称
		String sheetName = "关键词统计";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		// 查询
		KeyCountResponse keyCountResponse = keyCountService.searchAction(form);
		List<KeyCountVO> recordList = keyCountResponse.getResultList();
		// sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());

		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

		Integer totalCount = keyCountResponse.getCount();

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
            KeyCountResponse keyCountResponse2 = keyCountService.searchAction(form);
            List<KeyCountVO> recordList2 = keyCountResponse.getResultList();
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
		// "序号", "", "", "", "", "" };
		map.put("sourceName", "渠道");
		map.put("keyWord", "关键词");
		map.put("accessNumber", "访问数");
		map.put("registNumber", "注册数");
		map.put("accountNumber", "开户数");
		return map;
	}

	private Map<String, IValueFormatter> buildValueAdapter() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		return mapAdapter;
	}
}
