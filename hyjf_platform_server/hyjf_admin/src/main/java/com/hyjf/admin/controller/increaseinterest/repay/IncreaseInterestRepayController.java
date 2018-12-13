package com.hyjf.admin.controller.increaseinterest.repay;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.List2Result;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.IncreaseInterestRepayService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.IncreaseInterestRepayResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayRequest;
import com.hyjf.am.vo.admin.IncreaseInterestRepayVO;
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
	@PostMapping("/export")
	public void exportAction(HttpServletRequest request, HttpServletResponse response,@RequestBody IncreaseInterestRepayRequest form) throws Exception {
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "加息还款信息";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

//		form.setCurrPage(1);
//		form.setPageSize(defaultRowMaxCount);
		IncreaseInterestRepayResponse repayResponse = increaseInterestRepayService.searchPage(form);
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
			IncreaseInterestRepayResponse interestRepayResponse = increaseInterestRepayService.searchPage(form);
			List<IncreaseInterestRepayVO> record = interestRepayResponse.getResultList();
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
		map.put("borrowNid", "项目编号");
		map.put("userName", "借款人用户名");
		map.put("borrowPeriodByStyle", "项目期限");
		map.put("borrowAccount", "借款金额");
		map.put("borrowStyleName", "还款方式");
		map.put("borrowApr", "出借利率");
		map.put("borrowExtraYield", "加息收益率");
		map.put("repayInterest", "应还加息收益");
		map.put("repayTime", "应还日期");
		map.put("repayStatus", "转账状态");
		map.put("loanActionTime", "放款时间");
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

		IValueFormatter loanActionTimeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer loanActionTime = (Integer) object;
				return loanActionTime==null ? "" :GetDate.getDateMyTimeInMillis(loanActionTime);
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
				Integer repayTime = (Integer) object;
				if (repayTime == 0) {
					return "未转账";
				} else if (repayTime == 1) {
					return "已转账";
				}
				return "";
			}
		};
		IValueFormatter repayActionTimeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer repayActionTime = (Integer) object;
				return StringUtils.isEmpty(repayActionTime) ? "" : GetDate.getDateTimeMyTimeInMillis(repayActionTime);
			}
		};
		mapAdapter.put("borrowNid", strAdapter);
		mapAdapter.put("userName", strAdapter);
		mapAdapter.put("borrowAccount", bigDecimalToStrAdapter);
		mapAdapter.put("borrowApr", percentAdapter);
		mapAdapter.put("borrowExtraYield", percentAdapter);
		mapAdapter.put("repayInterest", bigDecimalToStrAdapter);
		mapAdapter.put("repayTime", repayTimeAdapter);
		mapAdapter.put("repayStatus", repayStatusAdapter);
		mapAdapter.put("loanActionTime", loanActionTimeAdapter);
		mapAdapter.put("repayActionTime", repayActionTimeAdapter);
		return mapAdapter;
	}


}