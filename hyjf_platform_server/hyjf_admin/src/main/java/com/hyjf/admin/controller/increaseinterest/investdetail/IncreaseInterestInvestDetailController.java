package com.hyjf.admin.controller.increaseinterest.investdetail;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.List2Result;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.IncreaseInterestInvestDetailService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.IncreaseInterestInvestDetailResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestInvestDetailRequest;
import com.hyjf.am.vo.admin.IncreaseInterestInvestVO;
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
 * @package com.hyjf.admin.controller.increaseinterest.investdetail
 * @date 2018/08/30 17:00
 */

@Api(value = "产品中心-加息出借明细",tags ="产品中心-加息出借明细")
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
	@ApiOperation(value = "产品中心-加息出借明细", notes = "产品中心-加息出借明细")
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
		return new AdminResult<List2Result<IncreaseInterestInvestVO,String>>(List2Result.build(response.getResultList(), response.getTotal() == null ? 0 : response.getTotal(),response.getSumAccount())) ;
	}

	/**
	 * 导出日报功能
	 *
	 * @param form
	 */
	@ApiOperation(value = "产品中心-加息出借明细", notes = "产品中心-加息出借明细 导出出借明细")
	@PostMapping("/export")
	public void exportAction(HttpServletRequest request, HttpServletResponse response,@RequestBody IncreaseInterestInvestDetailRequest form) throws Exception {
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "加息出借明细";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

		form.setCurrPage(1);
		form.setPageSize(defaultRowMaxCount);
		IncreaseInterestInvestDetailResponse detailResponse = increaseInterestInvestDetailService.searchPage(form);
		Integer totalCount = detailResponse.getTotal() == null ? 0 : detailResponse.getTotal();

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
			IncreaseInterestInvestDetailResponse interestInvestDetailResponse = increaseInterestInvestDetailService.searchPage(form);
			List<IncreaseInterestInvestVO> record = interestInvestDetailResponse.getResultList();
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
		map.put("investUserName", "出借人");
		map.put("inviteUserName", "推荐人");
		map.put("borrowNid", "项目编号");
		map.put("borrowApr", "出借利率");
		map.put("borrowExtraYield", "加息收益率");
		map.put("borrowPeriodByStyle", "项目期限");
		map.put("borrowStyleName", "还款方式");
		map.put("orderId", "出借订单号");
		map.put("account", "出借金额");
		map.put("repayInterest", "加息收益");
		map.put("client", "操作平台");
		map.put("createTime", "出借时间");
		map.put("repayTime", "回款时间");
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

		IValueFormatter clientAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer client = (Integer) object;
				// 客户端,0PC,1微官网,2Android,3iOS,4其他
				if (client == 0) {
					return "PC";
				} else if (client == 1) {
					return "微官网";
				} else if (client == 2) {
					return "Android";
				} else if (client == 3) {
					return "iOS";
				} else {
					return "其他";
				}
			}
		};
		IValueFormatter bigDecimalToStrAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				BigDecimal bigDecimal = (BigDecimal) object;
				return bigDecimal.toString();
			}
		};

		IValueFormatter createTimeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Date createTime = (Date) object;
				return createTime==null ? "" :GetDate.datetimeFormat.format(createTime);
			}
		};
		IValueFormatter repayTimeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer repayTime = (Integer) object;
				return (repayTime==null ? 0 : repayTime) == 0 ? "" :  GetDate.getDateMyTimeInMillis(repayTime);
			}
		};

		mapAdapter.put("investUserName", strAdapter);
		mapAdapter.put("inviteUserName", strAdapter);
		mapAdapter.put("borrowNid", strAdapter);
		mapAdapter.put("borrowApr", percentAdapter);
		mapAdapter.put("borrowExtraYield", percentAdapter);
		mapAdapter.put("borrowStyleName", strAdapter);
		mapAdapter.put("orderId", strAdapter);
		mapAdapter.put("account", bigDecimalToStrAdapter);
		mapAdapter.put("repayInterest", bigDecimalToStrAdapter);
		mapAdapter.put("client", clientAdapter);
		mapAdapter.put("createTime", createTimeAdapter);
		mapAdapter.put("repayTime", repayTimeAdapter);
		return mapAdapter;
	}

}