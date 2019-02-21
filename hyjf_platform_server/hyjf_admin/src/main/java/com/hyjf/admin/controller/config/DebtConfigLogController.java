package com.hyjf.admin.controller.config;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.DebtConfigService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
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
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


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
	public static final String PERMISSIONS = "debtconfiglog";

	@ApiOperation(value = "债转日志配置查询", notes = "债转日志配置查询")
	@PostMapping("/pageinit")
	@AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
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
	public void exportAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		DebtConfigRequest form = new DebtConfigRequest();
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "汇转让配置记录";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

//		List<DebtConfigLogVO> resultList = debtConfigService.getDebtConfigLogList(form);
//		Integer totalCount = resultList.size();
        Integer totalCount = debtConfigService.countDebtConfigLogTotal();

		int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
		Map<String, String> beanPropertyColumnMap = buildMap();
		Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();

		if (totalCount == 0) {
			String sheetNameTmp = sheetName + "_第1页";
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		}for (int i = 1; i <= sheetCount; i++) {
			form.setPageSize(defaultRowMaxCount);
			form.setCurrPage(i);
			List<DebtConfigLogVO> record = debtConfigService.getDebtConfigLogList(form);
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
		map.put("attornRate", "转让服务费率");
		map.put("concessionRateDown", "折让率下限");
		map.put("concessionRateUp", "折让率上限");
		map.put("toggle", "债转开关");
		map.put("updateUsername", "修改人");
		map.put("updateTime", "修改时间");
		map.put("ipAddress", "IP地址");
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
		IValueFormatter bigDecimalToStrAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				BigDecimal bigDecimal = (BigDecimal) object;
				return bigDecimal.toString();
			}
		};
		IValueFormatter toggleAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Integer toggle = (Integer) object;
				if (toggle == 1) {
					return "开";
				} else {
					return "关";
				}
			}
		};
		IValueFormatter timeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				Date updateTime = (Date) object;
				return updateTime == null ? "" : GetDate.getDateTimeMyTimeInMillis(updateTime);
			}
		};
		mapAdapter.put("attornRate", bigDecimalToStrAdapter);
		mapAdapter.put("concessionRateDown", bigDecimalToStrAdapter);
		mapAdapter.put("concessionRateUp", bigDecimalToStrAdapter);
		mapAdapter.put("toggle", toggleAdapter);
		mapAdapter.put("updateUsername", strAdapter);
		mapAdapter.put("updateTime", timeAdapter);
		mapAdapter.put("ipAddress", strAdapter);
		return mapAdapter;
	}

}
