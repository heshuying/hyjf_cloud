package com.hyjf.admin.controller.user;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.AuthBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AuthService;
import com.hyjf.admin.service.UserauthService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.response.user.AdminUserAuthLogListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.resquest.user.AdminUserAuthLogListRequest;
import com.hyjf.am.vo.user.AdminUserAuthListVO;
import com.hyjf.am.vo.user.AdminUserAuthLogListVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author 董泽杉
 * @version V1.0  
 * @package
 * @date 2018/6/27
 */
@Api(value = "会员中心-授权状态&授权记录",tags ="会员中心-授权状态&授权记录")
@RestController
@RequestMapping("/hyjf-admin/userauth")
public class UserauthController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(UserauthController.class);
	@Autowired
	private UserauthService userauthService;

	@Autowired
	private AuthService authService;

	/**
	 * 权限维护画面初始化
	 *
	 * @param request
	 * @param
	 * @return
	 */
	@ApiOperation(value = "授权状态", notes = "授权状态集合")
	@PostMapping(value = "/userauthlist")
	@ResponseBody
	public AdminResult<ListResult<AdminUserAuthListVO>> userManagerInit(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AdminUserAuthListRequest adminUserAuthListRequest) {
		AdminUserAuthListResponse rqes = userauthService.userauthlist(adminUserAuthListRequest);
		return new AdminResult<>(ListResult.build(rqes.getResultList(), rqes.getRecordTotal()));

	}


	/**
	 * 自动出借解约
	 *
	 * @param
	 */
	@ApiOperation(value = "授权状态", notes = "自动出借解约")
	@PostMapping(value = "/userinvescancel")
	@ResponseBody
	public AdminResult cancelInvestAuth(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Integer userId) {
		logger.info("自动出借解约开始，用户：{}", userId);
		if ("00".equals(userauthService.canCancelAuth(userId).getRtn())) {

			return new AdminResult<>("99",  "当前用户存在持有中计划，不能解约！");
		}

		//插入数据库
		AdminUserAuthListResponse uu =userauthService.cancelInvestAuth(userId);
		if(AdminUserAuthListResponse.isSuccess(uu)) {
			return new AdminResult<>();
		}else {
			return new AdminResult<>(AdminResult.FAIL, uu.getMessage());
		}
		
	}

	/**
	 * 自动债转解约
	 *
	 * @param
	 */
	@ApiOperation(value = "授权状态", notes = "自动债转解约")
	@PostMapping(value = "/usercreditcancel")
	@ResponseBody
	public AdminResult cancelCreditAuth(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Integer userId) {
		

		// 返回结果
		logger.info("自动债转授权开始，用户：{}", userId);
		if ("00".equals(userauthService.canCancelAuth(userId).getRtn())) {
			return new AdminResult<>("99",  "当前用户存在持有中计划，不能解约！");
		}
		//插入数据库
		AdminUserAuthListResponse uu = userauthService.cancelCreditAuth(userId);
		if(AdminUserAuthListResponse.isSuccess(uu)) {
			return new AdminResult<>();
		}else {
			return new AdminResult<>(AdminResult.FAIL, uu.getMessage());
		}
		
		
	}
	/**
	 * 自动债转解约
	 *
	 * @param
	 */
	@ApiOperation(value = "授权状态", notes = "授权日志流水")
	@ResponseBody
	@PostMapping("/userauthloglist")
	public AdminResult<ListResult<AdminUserAuthLogListVO>> cancelCreditAuth(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AdminUserAuthLogListRequest form) {
		AdminUserAuthLogListResponse rqes = userauthService.userauthLoglist(form);
		List<AdminUserAuthLogListVO> list = rqes.getResultList();
		 Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
		Map<String, String> invert = CacheUtil.getParamNameMap("AUTO_INVER_TYPE");
		 List<AdminUserAuthLogListVO> list2=new  ArrayList<AdminUserAuthLogListVO>();
		 for (AdminUserAuthLogListVO adminUserAuthLogListVO : list) {
			 AdminUserAuthLogListVO avo=new AdminUserAuthLogListVO();
			 avo=adminUserAuthLogListVO;
			 avo.setAuthType(invert.get(adminUserAuthLogListVO.getAuthType()));
			 avo.setOperateEsb(client.get(adminUserAuthLogListVO.getOperateEsb()));
			 list2.add(avo);
		}
		return new AdminResult<ListResult<AdminUserAuthLogListVO>>(ListResult.build(list2, rqes.getRecordTotal()));
		
	}
	@ApiOperation(value = "授权状态", notes = "授权下拉列表")
	@ResponseBody
	@PostMapping("/map")
	public AdminResult<List<DropDownVO>> map() {

		return new AdminResult<List<DropDownVO>>(com.hyjf.admin.utils.ConvertUtils.convertParamMapToDropDown(CacheUtil.getParamNameMap("AUTO_INVER_TYPE")));
		
	}
	/**
	 * 自动授权查询 - 调用江西银行接口查询
	 *
	 * @param userId
	 */
	@ResponseBody
	@GetMapping("/userauthquery/{userId}")
	public AdminResult queryUserAuth(@PathVariable Integer userId) {
		// 返回结果
		logger.info("授权查询开始，查询用户：{}", userId);
		BankCallBean retBean = authService.getTermsAuthQuery(userId, BankCallConstant.CHANNEL_PC);
		logger.info("getTermsAuthQuery return:" + JSON.toJSONString(retBean));
		try {
			if(authService.checkDefaultConfig(retBean,AuthBean.AUTH_TYPE_AUTO_BID)){
				logger.info("checkDefaultConfig return");
				return new AdminResult();
			}

			if (retBean != null && BankCallConstant.RESPCODE_SUCCESS.equals(retBean.get(BankCallConstant.PARAM_RETCODE))) {
				authService.updateUserAuth(userId, retBean,AuthBean.AUTH_TYPE_AUTO_BID);
				logger.info("授权状态更新成功");
				return new AdminResult();
			} else {
				logger.info("请求银行接口失败，retcode：" + retBean.get(BankCallConstant.PARAM_RETCODE));
				return new AdminResult<>(FAIL, "请求银行接口失败");
			}
		} catch (Exception e) {
			logger.error("授权查询出错", e);
			return new AdminResult<>(FAIL, "授权查询出错");
		}
	}

	 /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param request
     * @param response
     * @throws Exception
     */
	//@ApiOperation(value = "授权状态", notes = "授权导出")
	//@ResponseBody
	//@PostMapping("/exportExcel")
    /*public void exportExcel(@RequestBody AdminUserAuthListRequest from, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        // 表格sheet名称
        String sheetName = "授权状态";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        // 需要输出的结果列表

        // 封装查询条件
        from.setCurrPage(-1);
        from.setPageSize(-1);
		 List<AdminUserAuthListVO> recordList = userauthService.userauthlist(from).getResultList();
        String[] titles = new String[]{"序号", "用户名", "手机号", "自动投标交易金额", "自动投标总金额", "自动投标到期日", "自动投标授权状态", "自动债转授权状态", "授权时间"};
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
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
                            (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                	AdminUserAuthListVO user = recordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 用户名
                        cell.setCellValue(user.getUserName());
                    } else if (celLength == 2) {// 手机号
                        cell.setCellValue(user.getMobile());
                    } else if (celLength == 3) {// 自动投标交易金额
                        cell.setCellValue("2000000");
                    } else if (celLength == 4) {// 自动投标总金额
                        cell.setCellValue("1000000000");
                    } else if (celLength == 5) {// 自动投标到期日
                        cell.setCellValue(user.getAutoInvesEndTime());
                    } else if (celLength == 6) {// 自动投标授权状态
                        cell.setCellValue(user.getAutoInvesStatus());
                    } else if (celLength == 7) {// 自动债转授权状态
                        cell.setCellValue(user.getAutoCreditStatus());
                    } else if (celLength == 8) {// 债转授权时间
                        cell.setCellValue(user.getAutoCreateTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }*/

	/**
	 * 导出excel
	 *
	 * @param from
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ApiOperation(value = "授权状态", notes = "授权导出")
	@ResponseBody
	@PostMapping("/exportExcel")
	public void exportExcelSq(@RequestBody AdminUserAuthListRequest from, HttpServletRequest request,
							HttpServletResponse response) throws Exception {
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "授权状态";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
		from.setLimitFlg(true);
		//请求第一页5000条
		from.setPageSize(defaultRowMaxCount);
		from.setCurrPage(1);
		// 需要输出的结果列表
		AdminUserAuthListResponse recordList = userauthService.userauthlist(from);
		Integer totalCount = recordList.getRecordTotal();
		int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
		Map<String, String> beanPropertyColumnMap = buildMapSq();
		Map<String, IValueFormatter> mapValueAdapter = buildValueAdapterSq();
        String sheetNameTmp = sheetName + "_第1页";
		if (totalCount == 0) {
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		}else{
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList.getResultList());
        }
		for (int i = 1; i < sheetCount; i++) {
			from.setPageSize(defaultRowMaxCount);
			from.setCurrPage(i+1);
			AdminUserAuthListResponse recordList2 = userauthService.userauthlist(from);
			if (recordList2 != null && recordList2.getResultList().size()> 0) {
				sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
				helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  recordList2.getResultList());
			} else {
				break;
			}
		}
		DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	}

	private Map<String, String> buildMapSq() {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("userName", "用户名");
		map.put("mobile", "手机号");
		map.put("invesMaxAmt", "自动投标交易金额");
		map.put("creditMaxAmt", "自动债转交易金额");
		map.put("autoInvesEndTime", "自动投标到期日");
		map.put("autoInvesStatus", "自动投标授权状态");
		map.put("autoCreditStatus", "自动债转授权状态");
		map.put("autoCreateTime", "授权时间");
		return map;
	}

	private Map<String, IValueFormatter> buildValueAdapterSq() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		return mapAdapter;
	}

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param request
     * @param response
     * @throws Exception
     */
	//@ApiOperation(value = "授权状态", notes = "授权导出")
	//@ResponseBody
	//@PostMapping("/exportLogExcel")
    /*public void exportExcel(@RequestBody AdminUserAuthLogListRequest form, HttpServletRequest request, HttpServletResponse response) throws Exception {


        // 表格sheet名称
        String sheetName = "授权记录";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
		Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
		Map<String, String> invert = CacheUtil.getParamNameMap("AUTO_INVER_TYPE");
        form.setCurrPage(-1);
        form.setPageSize(-1);
		 List<AdminUserAuthLogListVO> recordList = userauthService.userauthLoglist(form).getResultList();
        String[] titles = new String[]{"序号", "订单号", "类型", "用户名", "操作平台", "订单状态", "授权时间"};
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
                	AdminUserAuthLogListVO user = recordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 订单号
                        cell.setCellValue(user.getOrderId());
                    } else if (celLength == 2) {// 类型
                        cell.setCellValue(invert.get(user.getAuthType()));
                    } else if (celLength == 3) {// 用户名
                        cell.setCellValue(user.getUserName());
                    } else if (celLength == 4) {// 操作平台
                        cell.setCellValue(client.get(user.getOperateEsb()));
                    } else if (celLength == 5) {// 订单状态
                        cell.setCellValue(user.getOrderStatus());
                    } else if (celLength == 6) {// 授权时间
                        cell.setCellValue(user.getCreditTime());
                    }

                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }*/

	/**
	 * 导出excel
	 *
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ApiOperation(value = "授权状态", notes = "授权导出")
	@ResponseBody
	@PostMapping("/exportLogExcel")
	public void exportExcelLog(@RequestBody AdminUserAuthLogListRequest form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//sheet默认最大行数
		int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
		// 表格sheet名称
		String sheetName = "授权记录";
		// 文件名称
		String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
		// 声明一个工作薄
		SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
		DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
		form.setLimitFlg(true);
		//请求第一页5000条
		form.setPageSize(defaultRowMaxCount);
		form.setCurrPage(1);
		// 需要输出的结果列表
		AdminUserAuthLogListResponse adminUserAuthLogListResponse = userauthService.userauthLoglist(form);
		Integer totalCount = adminUserAuthLogListResponse.getRecordTotal();
		int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
		Map<String, String> beanPropertyColumnMap = buildMapLog();
		Map<String, IValueFormatter> mapValueAdapter = buildValueAdapterLog();
        String sheetNameTmp = sheetName + "_第1页";
		if (totalCount == 0) {
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
		}else{
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, adminUserAuthLogListResponse.getResultList());
        }
		for (int i = 1; i < sheetCount; i++) {
			form.setPageSize(defaultRowMaxCount);
			form.setCurrPage(i+1);
			AdminUserAuthLogListResponse adminUserAuthLogListResponse2 = userauthService.userauthLoglist(form);
			if (adminUserAuthLogListResponse2 != null && adminUserAuthLogListResponse2.getResultList().size()> 0) {
				sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
				helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  adminUserAuthLogListResponse2.getResultList());
			} else {
				break;
			}
		}
		DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	}

	private Map<String, String> buildMapLog() {
		Map<String, String> map = Maps.newLinkedHashMap();
		map.put("orderId", "订单号");
		map.put("authType", "类型");
		map.put("userName", "用户名");
		map.put("operateEsb", "操作平台");
		map.put("orderStatus", "订单状态");
		map.put("creditTime", "授权时间");
		return map;
	}

	private Map<String, IValueFormatter> buildValueAdapterLog() {
		Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
		IValueFormatter authTypeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String authType = (String) object;
				Map<String, String> invert = CacheUtil.getParamNameMap("AUTO_INVER_TYPE");
				return invert.get(authType);
			}
		};

		IValueFormatter operateEsbAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String operateEsb = (String) object;
				Map<String, String> client = CacheUtil.getParamNameMap("CLIENT");
				return client.get(operateEsb);
			}
		};

		mapAdapter.put("authType", authTypeAdapter);
		mapAdapter.put("operateEsb", operateEsbAdapter);
		return mapAdapter;
	}
}
