package com.hyjf.admin.controller.increaseinterest.repay;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.List2Result;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.IncreaseInterestRepayService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.IncreaseInterestRepayResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayRequest;
import com.hyjf.am.vo.admin.IncreaseInterestRepayVO;
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
 * @package com.hyjf.admin.controller.increaseinterest.repay
 * @date 2018/08/30 17:00
 */

@Api(value = "产品中心-加息还款信息",tags ="产品中心-加息还款信息")
@RestController
@RequestMapping("/hyjf-admin/borrow/increaseinterest/repay")
public class IncreaseInterestRepayController extends BaseController {

	@Autowired
	private IncreaseInterestRepayService increaseInterestRepayService;

	/**
	 * 分页显示
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "产品中心-加息还款信息", notes = "产品中心-加息还款信息")
	@PostMapping("/search")
	public AdminResult<List2Result<IncreaseInterestRepayVO,String>> search(@RequestBody IncreaseInterestRepayRequest request){
		IncreaseInterestRepayResponse response = new IncreaseInterestRepayResponse();
		response = increaseInterestRepayService.searchPage(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<List2Result<IncreaseInterestRepayVO,String>>(List2Result.build(response.getResultList(), response.getTotal() == null ? 0 : response.getTotal(),response.getSumAccount())) ;
	}

	/**
	 * 导出日报功能
	 *
	 * @param form
	 */
	@ApiOperation(value = "产品中心-加息还款信息", notes = "产品中心-加息还款信息 导出还款明细")
	@GetMapping("/export")
	public void exportAction(HttpServletResponse response, IncreaseInterestRepayRequest form) throws Exception {
		// 表格sheet名称
		String sheetName = "加息还款信息";

		//List<IncreaseInterestRepayVO> resultList = increaseInterestRepayService.selectRecordList(form);
		//获取返回参数
		EnumMap<AmTradeClient.IncreaseProperty,Object> voList = increaseInterestRepayService.selectRecordList(form);
		voList.entrySet().iterator();
		List<IncreaseInterestRepayVO> resultList = (List<IncreaseInterestRepayVO>) voList.get(AmTradeClient.IncreaseProperty.VO);

		String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		//20180730导出增加字段：借款金额/放款时间
		//序号/借款编号/借款人/项目期限/借款金额/还款方式/年化收益率/加息收益率/应加息收益/应还日期/转账状态/放款时间/实际还款时间
		String[] titles = new String[] { "序号","借款编号", "借款人" , "项目期限","借款金额", "还款方式", "年化收益率", "加息收益率", "应还加息收益", "应还日期", "转账状态","放款时间","实际还款时间" };
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
					IncreaseInterestRepayVO increaseInterestRepay = resultList.get(i);
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
					// 借款人
					else if (celLength == 2) {
						cell.setCellValue(StringUtils.isEmpty(increaseInterestRepay.getUserName()) ? "" : increaseInterestRepay.getUserName());
					}
					// 借款期限
					else if (celLength == 3) {
						if ("endday".equals(increaseInterestRepay.getBorrowStyle())) {
							cell.setCellValue(increaseInterestRepay.getBorrowPeriod() + "天");
						} else {
							cell.setCellValue(increaseInterestRepay.getBorrowPeriod() + "个月");
						}
					}
					// 借款金额
					else if (celLength == 4) {
						cell.setCellValue(increaseInterestRepay.getBorrowAccount()==null ? "" : increaseInterestRepay.getBorrowAccount().toString());
					}
					// 还款方式
					else if (celLength == 5) {
						cell.setCellValue(increaseInterestRepay.getBorrowStyleName());
					}
					// 年化收益率
					else if (celLength == 6) {
						cell.setCellValue(increaseInterestRepay.getBorrowApr() + "%");
					}
					// 产品加息收益率
					else if (celLength == 7) {
						cell.setCellValue(increaseInterestRepay.getBorrowExtraYield() + "%");
					}
					// 应还加息收益
					else if (celLength == 8) {
						cell.setCellValue(increaseInterestRepay.getRepayInterest().toString());
					}
					// 应还时间
					else if (celLength == 9) {
						cell.setCellValue(StringUtils.isEmpty(increaseInterestRepay.getRepayTime()) ? "" : GetDate.getDateMyTimeInMillis(Integer.parseInt(increaseInterestRepay.getRepayTime())));
					}
					// 转账状态
					else if (celLength == 10) {
						if (increaseInterestRepay.getRepayStatus() == 0) {
							cell.setCellValue("未转账");
						} else if (increaseInterestRepay.getRepayStatus() == 1) {
							cell.setCellValue("已转账");
						}
					}
					// 放款时间
					else if (celLength == 11) {
						cell.setCellValue(increaseInterestRepay.getLoanActionTime()==null  ? "" : GetDate.getDateMyTimeInMillis(increaseInterestRepay.getLoanActionTime()));
					}
					// 实际还款时间
					else if (celLength == 12) {
						cell.setCellValue(increaseInterestRepay.getRepayActionTime()==null ? "" : GetDate.getDateTimeMyTimeInMillis(Integer.parseInt(increaseInterestRepay.getRepayActionTime())));
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}

}