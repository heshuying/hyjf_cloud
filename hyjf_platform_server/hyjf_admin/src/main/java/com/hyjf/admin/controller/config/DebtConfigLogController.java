package com.hyjf.admin.controller.config;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.DebtConfigService;
import com.hyjf.am.response.config.DebtConfigLogResponse;
import com.hyjf.am.resquest.admin.DebtConfigRequest;
import com.hyjf.am.vo.config.DebtConfigLogVO;
import com.hyjf.common.paginator.Paginator;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author tanyy
 * @version DebtConfigController, v0.1 2018/9/27 14:28
 */
@Api(value = "汇转让-债转日志配置", tags = "汇转让-债转日志配置")
@RestController
@RequestMapping("/hyjf-admin/debtconfiglog")
public class DebtConfigLogController extends BaseController {


	@Autowired
	private DebtConfigService debtConfigService;


	@ApiOperation(value = "债转日志配置查询", notes = "债转日志配置查询")
	@PostMapping("/pageinit")
	public AdminResult pageInit(@RequestBody DebtConfigRequest debtConfigRequest) {
		DebtConfigLogResponse response = new DebtConfigLogResponse();
		int count = debtConfigService.countDebtConfigLogTotal();
		List<DebtConfigLogVO> list = new ArrayList<>();
		if(count>0){
			Paginator paginator = new Paginator(debtConfigRequest.getCurrPage(), count, debtConfigRequest.getPageSize() == 0 ? 10 : debtConfigRequest.getPageSize());
			debtConfigRequest.setPaginator(paginator);
			debtConfigRequest.setLimitStart(paginator.getOffset());
			debtConfigRequest.setLimitEnd(paginator.getLimit());
		 	list = debtConfigService.getDebtConfigLogList(debtConfigRequest);
		}
		response.setCount(count);
		response.setLogVOList(list);
		response.setDebtConfigRequest(debtConfigRequest);
		return new AdminResult(response);
	}
	/**
	 * @Description 数据导出--债转日志配置查询
	 * @Author
	 * @Version v0.1
	 * @Date
	 */
	@ApiOperation(value = "数据导出--债转日志配置查询", notes = "导出EXCEL")
	@GetMapping(value = "/debtconfiglogExport")
	//@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
	public void exportAction(HttpServletResponse response) throws Exception {
		// 表格sheet名称
		String sheetName = "汇转让配置记录";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		// 查询
		DebtConfigRequest form = new DebtConfigRequest();
		form.setLimitStart(-1);
		form.setLimitEnd(-1);
		List<DebtConfigLogVO> resultList = debtConfigService.getDebtConfigLogList(form);
		String[] titles =
				new String[] { "序号", "转让服务费率", "折让率下限", "折让率上限", "债转开关", "修改人", "修改时间", "IP地址"};
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
					sheet =
							com.hyjf.common.util.ExportExcel
									.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
					rowNum = 1;
				}
				// 新建一行
				Row row = sheet.createRow(rowNum);

				// 循环数据
				for (int celLength = 0; celLength < titles.length; celLength++) {
					DebtConfigLogVO log = resultList.get(i);
					// 创建相应的单元格
					Cell cell = row.createCell(celLength);
					if (celLength == 0) {// 序号
						cell.setCellValue(i + 1);
					} else if (celLength == 1) {
						cell.setCellValue(String.valueOf(log.getAttornRate()));
					} else if (celLength == 2) {
						cell.setCellValue(String.valueOf(log.getConcessionRateDown()));
					} else if (celLength == 3) {
						cell.setCellValue(String.valueOf(log.getConcessionRateUp()));
					} else if (celLength == 4) {
						cell.setCellValue(log.getToggle()==1?"开":"关");
					} else if (celLength == 5) {
						cell.setCellValue(log.getUpdateUserName());
					} else if (celLength == 6) {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						cell.setCellValue(df.format(log.getUpdateTime()));
					} else if (celLength == 7) {
						cell.setCellValue(log.getIpAddress());
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);

	}

}
