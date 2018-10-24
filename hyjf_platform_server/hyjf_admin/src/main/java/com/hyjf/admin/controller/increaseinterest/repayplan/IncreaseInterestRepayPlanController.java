package com.hyjf.admin.controller.increaseinterest.repayplan;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.IncreaseInterestRepayPlanService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.IncreaseInterestRepayPlanResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayPlanRequest;
import com.hyjf.am.vo.admin.IncreaseInterestRepayDetailVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


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
		return new AdminResult<ListResult<IncreaseInterestRepayDetailVO>>(ListResult.build(response.getResultList(), response.getTotal() == null ? 0 : response.getTotal())) ;
	}

	/**
	 * 导出日报功能
	 *
	 * @param form
	 */
	@ApiOperation(value = "产品中心-加息还款计划", notes = "产品中心-加息还款计划 导出还款计划详情")
	@GetMapping("/export")
	public void exportAction(HttpServletRequest request, HttpServletResponse response, IncreaseInterestRepayPlanRequest form) throws Exception {
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "加息还款信息";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

		form.setCurrPage(1);
		form.setPageSize(defaultRowMaxCount);
		IncreaseInterestRepayPlanResponse repayResponse = increaseInterestRepayPlanService.searchPage(form);
		Integer totalCount = repayResponse.getTotal() == null ? 0 : repayResponse.getTotal();

		int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
		Map<String, String> beanPropertyColumnMap = buildMap();
		Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
		if (totalCount == 0) {
			String sheetNameTmp = sheetName + "_第1页";
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		}
		for (int i = 1; i <= sheetCount; i++) {
			form.setPageSize(defaultRowMaxCount);
			form.setCurrPage(i);
			IncreaseInterestRepayPlanResponse repayPlanResponse = increaseInterestRepayPlanService.searchPage(form);
			List<IncreaseInterestRepayDetailVO> record = repayPlanResponse.getResultList();
			if (record != null && record.size()> 0) {
				String sheetNameTmp = sheetName + "_第" + i + "页";
				helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  record);
			} else {
				break;
			}
		}
		DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	}

	private Map<String, String> buildMap() {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("borrowNid", "借款编号");
		map.put("userName", "借款人");
		map.put("borrowPeriodByStyle", "借款期限");
		map.put("repayPeriod", "还款期数");
		map.put("borrowStyleName", "还款方式");
		map.put("borrowApr", "年化收益率");
		map.put("borrowExtraYield", "加息收益率");
		map.put("repayInterest", "应还加息收益");
		map.put("repayTime", "应还时间");
		map.put("repayStatus", "转账状态");
		map.put("repayActionTime", "实际还款时间");
		return map;
	}
	private Map<String, IValueFormatter> buildValueAdapter() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		IValueFormatter strAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String str = (String) object;
				return StringUtils.isEmpty(str) ? "" : String.valueOf(str);
			}
		};
		IValueFormatter percentAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				BigDecimal percent = (BigDecimal) object;
				return percent + "%";
			}
		};
		IValueFormatter bigDecimalToStrAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				BigDecimal bigDecimal = (BigDecimal) object;
				return bigDecimal.toString();
			}
		};
		IValueFormatter repayTimeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer repayTime = (Integer) object;
				return StringUtils.isEmpty(repayTime) ? "" : GetDate.getDateMyTimeInMillis(repayTime);
			}
		};
		IValueFormatter repayStatusAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer repayStatus = (Integer) object;
				if (repayStatus == 0) {
					return "未回款";
				} else if (repayStatus == 1) {
					return "已回款";
				}
				return "";
			}
		};
		IValueFormatter repayPeriodAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer repayPeriod = (Integer) object;
				return "第" + repayPeriod + "期";
			}
		};
		mapAdapter.put("borrowNid", strAdapter);
		mapAdapter.put("userName", strAdapter);
		mapAdapter.put("repayPeriod", repayPeriodAdapter);
		mapAdapter.put("borrowApr", percentAdapter);
		mapAdapter.put("borrowExtraYield", percentAdapter);
		mapAdapter.put("repayInterest", bigDecimalToStrAdapter);
		mapAdapter.put("repayTime", repayTimeAdapter);
		mapAdapter.put("repayStatus", repayStatusAdapter);
		mapAdapter.put("repayActionTime", repayTimeAdapter);
		return mapAdapter;
	}


}