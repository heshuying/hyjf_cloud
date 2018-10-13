package com.hyjf.admin.controller.increaseinterest.investdetail;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.List2Result;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.IncreaseInterestInvestDetailService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.IncreaseInterestInvestDetailResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestInvestDetailRequest;
import com.hyjf.am.vo.admin.IncreaseInterestInvestVO;
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
 * @package com.hyjf.admin.controller.increaseinterest.investdetail
 * @date 2018/08/30 17:00
 */

@Api(value = "产品中心-加息投资明细",tags ="产品中心-加息投资明细")
@RestController
@RequestMapping("/hyjf-admin/borrow/increaseinterest/investdetail")
public class IncreaseInterestInvestDetailController extends BaseController {

	@Autowired
	private IncreaseInterestInvestDetailService increaseInterestInvestDetailService;

	/**
	 * 分页显示
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "产品中心-加息投资明细", notes = "产品中心-加息投资明细")
	@PostMapping("/search")
	public AdminResult<List2Result<IncreaseInterestInvestVO,String>> search(@RequestBody IncreaseInterestInvestDetailRequest request){
		IncreaseInterestInvestDetailResponse response = new IncreaseInterestInvestDetailResponse();
		response = increaseInterestInvestDetailService.searchPage(request);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<List2Result<IncreaseInterestInvestVO,String>>(List2Result.build(response.getResultList(), response.getTotal(),response.getSumAccount())) ;
	}

	/**
	 * 导出日报功能
	 *
	 * @param form
	 */
	@ApiOperation(value = "产品中心-加息投资明细", notes = "产品中心-加息投资明细 导出投资明细")
	@GetMapping("/export")
	public void exportAction(HttpServletResponse response, IncreaseInterestInvestDetailRequest form) throws Exception {
		// 表格sheet名称
		String sheetName = "加息投资明细";

		//获取返回参数
		EnumMap<AmTradeClient.IncreaseProperty,Object> voList = increaseInterestInvestDetailService.selectRecordList(form);
		voList.entrySet().iterator();
		List<IncreaseInterestInvestVO> resultList = (List<IncreaseInterestInvestVO>) voList.get(AmTradeClient.IncreaseProperty.VO);

		String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		//20180730增加导出字段：推荐人/项目期限/还款方式/投资订单号/加息收益/回款时间
		String[] titles = new String[] { "序号", "投资人","推荐人", "借款编号", "年化收益率", "加息收益率", "项目期限","还款方式","投资订单号","投资金额","加息收益", "操作平台", "投资时间" ,"回款时间" };
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
					IncreaseInterestInvestVO investDetail = resultList.get(i);

					// 创建相应的单元格
					Cell cell = row.createCell(celLength);

					// 序号
					if (celLength == 0) {
						cell.setCellValue(i + 1);
					}
					// 用户名
					else if (celLength == 1) {
						cell.setCellValue(StringUtils.isEmpty(investDetail.getInvestUserName()) ? "" : investDetail.getInvestUserName());
					}
					//推荐人(投资)++++++++++
					else if (celLength == 2) {
						cell.setCellValue(StringUtils.isEmpty(investDetail.getInviteUserName()) ? "" : investDetail.getInviteUserName());
					}
					// 借款编号
					else if (celLength == 3) {
						cell.setCellValue(StringUtils.isEmpty(investDetail.getBorrowNid()) ? "" : investDetail.getBorrowNid());
					}
					// 年化收益率
					else if (celLength == 4) {
						cell.setCellValue(investDetail.getBorrowApr() + "%");
					}
					// 产品加息收益率
					else if (celLength == 5) {
						cell.setCellValue(investDetail.getBorrowExtraYield() + "%");
					}
					//项目期限+++++++++
					else if (celLength == 6) {
						//cell.setCellValue(StringUtils.isEmpty(investDetail.getBorrowPeriod().toString()) ? "" : investDetail.getBorrowPeriod().toString());
						if ("endday".equals(investDetail.getBorrowStyle())) {
							cell.setCellValue(investDetail.getBorrowPeriod() + "天");
						} else {
							cell.setCellValue(investDetail.getBorrowPeriod() + "个月");
						}
					}
					// 还款方式++++++++
					else if (celLength == 7) {
						cell.setCellValue(StringUtils.isEmpty(investDetail.getBorrowStyleName()) ? "" : investDetail.getBorrowStyleName());
					}
					// 投资订单号++++++++
					else if (celLength == 8) {
						cell.setCellValue(StringUtils.isEmpty(investDetail.getOrderId()) ? "" : investDetail.getOrderId());
					}
					// 投资金额
					else if (celLength == 9) {
						cell.setCellValue(investDetail.getAccount().toString());
					}
					// 加息收益++++++++
					else if (celLength == 10) {
						cell.setCellValue(investDetail.getRepayInterest()==null ? "" : investDetail.getRepayInterest().toString());
					}
					// 操作平台
					else if (celLength == 11) {
						// 客户端,0PC,1微官网,2Android,3iOS,4其他
						if (investDetail.getClient() == 0) {
							cell.setCellValue("PC");
						} else if (investDetail.getClient() == 1) {
							cell.setCellValue("微官网");
						} else if (investDetail.getClient() == 2) {
							cell.setCellValue("Android");
						} else if (investDetail.getClient() == 3) {
							cell.setCellValue("iOS");
						} else {
							cell.setCellValue("其他");
						}
					}
					// 投资时间
					else if (celLength == 12) {
						cell.setCellValue(investDetail.getCreateTime()==null ? "" :GetDate.getDateTimeMyTime(investDetail.getCreateTime()));
					}
					// 回款时间++++++++++
					else if (celLength == 13) {
						cell.setCellValue((investDetail.getRepayTime()==null ? 0 : investDetail.getRepayTime()) == 0 ? "" :  GetDate.getDateTimeMyTime(investDetail.getRepayTime()));
					}
				}
			}
		}
		// 导出
		ExportExcel.writeExcelFile(response, workbook, titles, fileName);
	}
}