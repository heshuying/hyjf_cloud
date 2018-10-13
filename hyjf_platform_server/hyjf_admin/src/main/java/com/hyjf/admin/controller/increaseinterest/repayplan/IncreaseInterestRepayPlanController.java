package com.hyjf.admin.controller.increaseinterest.repayplan;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.IncreaseInterestRepayPlanService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.IncreaseInterestRepayPlanResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayPlanRequest;
import com.hyjf.am.vo.admin.IncreaseInterestRepayDetailVO;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;


/**
 * @author wenxin
 * @version V1.0  
 * @package com.hyjf.admin.controller.increaseinterest.repayplan
 * @date 2018/08/30 17:00
 */

@Api(value = "产品中心-加息还款计划",tags ="产品中心-加息还款计划")
@RestController
@RequestMapping("/hyjf-admin/borrow/increaseinterest/increaseInterestRepayPlan")
public class IncreaseInterestRepayPlanController extends BaseController {

	@Autowired
	private IncreaseInterestRepayPlanService increaseInterestRepayPlanService;

	/**
	 * 分页显示
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "产品中心-加息还款计划", notes = "产品中心-加息还款计划")
	@PostMapping("/search")
	public AdminResult<ListResult<IncreaseInterestRepayDetailVO>> search(@RequestBody IncreaseInterestRepayPlanRequest request){
		IncreaseInterestRepayPlanResponse response = new IncreaseInterestRepayPlanResponse();
		response = increaseInterestRepayPlanService.searchPage(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<ListResult<IncreaseInterestRepayDetailVO>>(ListResult.build(response.getResultList(), response.getTotal())) ;
	}

	/**
	 * 导出日报功能
	 *
	 * @param form
	 */
	@ApiOperation(value = "产品中心-加息还款计划", notes = "产品中心-加息还款计划 导出还款计划详情")
	@GetMapping("/export")
	public void exportAction(HttpServletResponse response, IncreaseInterestRepayPlanRequest form) throws Exception {
		// 表格sheet名称
		String sheetName = "加息还款计划";

		List<IncreaseInterestRepayDetailVO> resultList = increaseInterestRepayPlanService.selectRecordList(form);

		String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		//序号/借款编号/借款人/借款期限/还款期数/还款方式/年化收益率/加息收益率/应还加息收益/应还时间/转账状态/实际还款时间
		String[] titles = new String[] { "序号", "借款编号", "借款人" ,"借款期限", "还款期数", "还款方式","年化收益率", "加息收益率", "应还加息收益", "应还时间", "转账状态", "实际还款时间" };
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
					IncreaseInterestRepayDetailVO increaseInterestRepayDetail = resultList.get(i);
					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 借款编号
					else if (celLength == 1) {
						cell.setCellValue(StringUtils.isEmpty(increaseInterestRepayDetail.getBorrowNid()) ? "" : increaseInterestRepayDetail.getBorrowNid());
					}
					// 借款人
					else if (celLength == 2) {
						cell.setCellValue(StringUtils.isEmpty(increaseInterestRepayDetail.getUserName()) ? "" : increaseInterestRepayDetail.getUserName());
					}
					// 借款期限
					else if (celLength == 3) {
						if ("endday".equals(increaseInterestRepayDetail.getBorrowStyle())) {
							cell.setCellValue(increaseInterestRepayDetail.getBorrowPeriod() + "天");
						} else {
							cell.setCellValue(increaseInterestRepayDetail.getBorrowPeriod() + "个月");
						}
					}
					// 还款期数
					else if (celLength == 4) {
						cell.setCellValue("第" + increaseInterestRepayDetail.getRepayPeriod() + "期");
					}
					// 还款方式
					else if (celLength == 5) {
						cell.setCellValue(increaseInterestRepayDetail.getBorrowStyleName());
					}

					// 年化收益率
					else if (celLength == 6) {
						cell.setCellValue(increaseInterestRepayDetail.getBorrowApr() + "%");
					}
					// 产品加息收益率
					else if (celLength == 7) {
						cell.setCellValue(increaseInterestRepayDetail.getBorrowExtraYield() + "%");
					}
					// 应还加息收益
					else if (celLength == 8) {
						cell.setCellValue(increaseInterestRepayDetail.getRepayInterest()==null ? "" : increaseInterestRepayDetail.getRepayInterest().toString());
					}
					// 应还时间
					else if (celLength == 9) {
						cell.setCellValue(increaseInterestRepayDetail.getRepayTime()==null ? "" : GetDate.getDateMyTimeInMillis(Integer.parseInt(increaseInterestRepayDetail.getRepayTime())));
					}
					// 转账状态
					else if (celLength == 10) {
						if (increaseInterestRepayDetail.getRepayStatus() == 0) {
							cell.setCellValue("未回款");
						} else if (increaseInterestRepayDetail.getRepayStatus() == 1) {
							cell.setCellValue("已回款");
						}
					}
					// 实际还款时间
					else if (celLength == 11) {
						cell.setCellValue(increaseInterestRepayDetail.getRepayActionTime()==null ? "" :  GetDate.getDateMyTimeInMillis(Integer.parseInt(increaseInterestRepayDetail.getRepayActionTime())));
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}

}