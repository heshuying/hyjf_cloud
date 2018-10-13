package com.hyjf.admin.controller.increaseinterest.repaydetail;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.IncreaseInterestRepayDetailService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.IncreaseInterestRepayDetailResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayDetailRequest;
import com.hyjf.am.vo.admin.AdminIncreaseInterestRepayCustomizeVO;
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
import java.util.EnumMap;
import java.util.List;


/**
 * @author wenxin
 * @version V1.0  
 * @package com.hyjf.admin.controller.increaseinterest.repaydetail
 * @date 2018/08/30 17:00
 */

@Api(value = "产品中心-加息还款明细",tags ="产品中心-加息还款明细")
@RestController
@RequestMapping("/hyjf-admin/borrow/increaseinterest/increaseInterestRepay")
public class IncreaseInterestRepayDetailController extends BaseController {

	@Autowired
	private IncreaseInterestRepayDetailService increaseInterestRepayDetaiService;

	/**
	 * 分页显示
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "产品中心-加息还款明细", notes = "产品中心-加息还款明细")
	@PostMapping("/search")
	public AdminResult<ListResult<AdminIncreaseInterestRepayCustomizeVO>> search(@RequestBody IncreaseInterestRepayDetailRequest request){
		IncreaseInterestRepayDetailResponse response = new IncreaseInterestRepayDetailResponse();
		response = increaseInterestRepayDetaiService.searchPage(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<ListResult<AdminIncreaseInterestRepayCustomizeVO>>(ListResult.build(response.getResultList(), response.getTotal())) ;
	}

	/**
	 * 导出日报功能
	 *
	 * @param form
	 */
	@ApiOperation(value = "产品中心-加息还款明细", notes = "产品中心-加息还款明细 导出还款详情")
	@GetMapping("/export")
	public void exportAction(HttpServletResponse response, IncreaseInterestRepayDetailRequest form) throws Exception {
		// 表格sheet名称
		String sheetName = "加息还款明细";

		//获取返回参数
		EnumMap<AmTradeClient.IncreaseProperty,Object> voList = increaseInterestRepayDetaiService.selectRecordList(form);
		voList.entrySet().iterator();
		List<AdminIncreaseInterestRepayCustomizeVO> resultList = (List<AdminIncreaseInterestRepayCustomizeVO>) voList.get(AmTradeClient.IncreaseProperty.VO);
		//List<AdminIncreaseInterestRepayCustomizeVO> resultList = increaseInterestRepayDetaiService.selectRecordList(form);

		String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		//序号/借款编号/投资人/借款期限/还款方式/年化收益率/加息收益率/应还加息收益/应还时间/转账状态/实际还款时间
		//String[] titles = new String[] { "序号", "借款编号", "借款期限", "还款方式", "投资人用户名", "年化收益率", "产品加息收益率", "应还本金", "应还加息收益", "应还时间", "转账状态" };
		String[] titles = new String[] { "序号", "借款编号", "投资人", "借款期限", "还款方式", "年化收益率", "加息收益率", "应还加息收益", "应还时间", "转账状态"  , "实际还款时间"};
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
					AdminIncreaseInterestRepayCustomizeVO increaseInterestRepay = resultList.get(i);
					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 借款编号
					else if (celLength == 1) {
						cell.setCellValue(StringUtils.isEmpty(increaseInterestRepay.getBorrowNid()) ? "" : increaseInterestRepay.getBorrowNid());
					}
					// 投资人用户名
					else if (celLength == 2) {
						cell.setCellValue(increaseInterestRepay.getInvestUserName());
					}
					// 借款期限
					else if (celLength == 3) {
						if ("endday".equals(increaseInterestRepay.getBorrowStyle())) {
							cell.setCellValue(increaseInterestRepay.getBorrowPeriod() + "天");
						} else {
							cell.setCellValue(increaseInterestRepay.getBorrowPeriod() + "个月");
						}
					}
					// 还款方式
					else if (celLength == 4) {
						cell.setCellValue(increaseInterestRepay.getRepayStyleName());
					}

					// 年化收益率
					else if (celLength == 5) {
						cell.setCellValue(increaseInterestRepay.getBorrowApr() + "%");
					}
					// 产品加息收益率
					else if (celLength == 6) {
						cell.setCellValue(increaseInterestRepay.getBorrowExtraYield() + "%");
					}
					// 应还加息收益
					else if (celLength == 7) {
						cell.setCellValue(increaseInterestRepay.getRepayInterest() == null ? "" : increaseInterestRepay.getRepayInterest());
					}
					// 应还时间
					else if (celLength == 8) {
						cell.setCellValue(increaseInterestRepay.getRepayTime()==null ? "" : GetDate.getDateMyTimeInMillisYYYYMMDD(Integer.valueOf(increaseInterestRepay.getRepayTime())));
					}
					// 转账状态
					else if (celLength == 9) {
						if ("0".equals(increaseInterestRepay.getRepayStatus())) {
							cell.setCellValue("未回款");
						} else if ("1".equals(increaseInterestRepay.getRepayStatus())) {
							cell.setCellValue("已回款");
						}
					}
					// 应还本金（20180731去除）
					/*else if (celLength == 7) {
						cell.setCellValue(increaseInterestRepay.getRepayCapital());
					}*/
					// 实还加息收益（停用）
					/*else if (celLength == 10) {
						cell.setCellValue(increaseInterestRepay.getRepayInterestYes().toString());
					}*/
					// 实际还款时间（20180731新增）
					else if (celLength == 10) {
						cell.setCellValue(increaseInterestRepay.getRepayActionTime()==null ? "" : GetDate.getDateTimeMyTimeInMillis(Integer.valueOf(increaseInterestRepay.getRepayActionTime())));
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}

}