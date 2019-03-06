/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.repair.accountrepair;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AccountExceptionService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.AccountExceptionRequest;
import com.hyjf.am.vo.admin.AccountExceptionVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.hyjf.admin.common.util.ShiroConstants.PERMISSION_VIEW;

/**
 * @author: sunpeikai
 * @version: AccountExceptionController, v0.1 2018/7/11 15:02
 */
@RestController
@RequestMapping("/hyjf-admin/exception/accountexception")
@Api(value = "异常中心-汇付对账",tags = "异常中心-汇付对账")
public class AccountRepairController extends BaseController {

    @Autowired
    private AccountExceptionService accountExceptionService;
	private static final String PERMISSIONS = "exceptionaccount";

    /**
     * 查询汇付对账异常list
     * @auth sunpeikai
     * @param request 异常列表筛选检索条件
     * @return
     */
    @ApiOperation(value = "汇付对账列表", notes = "汇付对账列表")
    @PostMapping(value = "/accountexceptionlist")
	@AuthorityAnnotation(key = PERMISSIONS,value = PERMISSION_VIEW)
    public AdminResult<ListResult<AccountExceptionVO>> accountExceptionList(@RequestBody AccountExceptionRequest request){
        // 数据总数
        Integer count = accountExceptionService.getAccountExceptionCount(request);
        // 异常列表list
        List<AccountExceptionVO> accountExceptionVOList = accountExceptionService.searchAccountExceptionList(request);
        return new AdminResult<>(ListResult.build(accountExceptionVOList,count));
    }

    /**
     * 更新信息
     * @auth sunpeikai
     * @param request 包含id AccountException的主键
     * @return
     */
    @ApiOperation(value = "更新信息", notes = "更新信息")
    @PostMapping(value = "/sync")
    public AdminResult sync(@RequestBody AccountExceptionRequest request){
        accountExceptionService.syncAccount(request.getId());
        return new AdminResult(SUCCESS,SUCCESS_DESC);
    }

//    public void exportAccountExceptionList(HttpServletResponse response, @RequestBody AccountExceptionRequest request) throws UnsupportedEncodingException {
//        // currPage<0 为全部,currPage>0 为具体某一页
//        request.setCurrPage(-1);
//        // 表格sheet名称
//        String sheetName = "注册信息";
//        // 文件名称
//        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
//                + CustomConstants.EXCEL_EXT;
//
//        List<AccountExceptionVO> recordList = accountExceptionService.searchAccountExceptionList(request);
//        String[] titles = new String[] { "序号", "用户名", "客户号", "手机号","角色", "平台可用金额", "平台冻结金额", "汇付可用金额", "汇付冻结金额" };
//        // 声明一个工作薄
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        // 生成一个表格
//        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
//
//        if (recordList != null && recordList.size() > 0) {
//
//            int sheetCount = 1;
//            int rowNum = 0;
//
//            for (int i = 0; i < recordList.size(); i++) {
//                rowNum++;
//                if (i != 0 && i % 60000 == 0) {
//                    sheetCount++;
//                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles,
//                            (sheetName + "_第" + sheetCount + "页"));
//                    rowNum = 1;
//                }
//
//                // 新建一行
//                Row row = sheet.createRow(rowNum);
//                // 循环数据
//                for (int celLength = 0; celLength < titles.length; celLength++) {
//                    AccountExceptionVO exceptionAccount = recordList.get(i);
//                    // 创建相应的单元格
//                    Cell cell = row.createCell(celLength);
//                    if (celLength == 0) {// 序号
//                        cell.setCellValue(i + 1);
//                    } else if (celLength == 1) {
//                        cell.setCellValue(exceptionAccount.getUsername());
//                    } else if (celLength == 2) {
//                        cell.setCellValue(exceptionAccount.getCustomId());
//                    } else if (celLength == 3) {
//                        cell.setCellValue(exceptionAccount.getMobile());
//                    } else if (celLength == 4) {
//                        cell.setCellValue(exceptionAccount.getRole());
//                    } else if (celLength == 5) {
//                        cell.setCellValue(exceptionAccount.getBalancePlat().toString());
//                    } else if (celLength == 6) {
//                        cell.setCellValue(exceptionAccount.getFrostPlat().toString());
//                    } else if (celLength == 7) {
//                        cell.setCellValue(exceptionAccount.getBalanceHuifu().toString());
//                    } else if (celLength == 8) {
//                        cell.setCellValue(exceptionAccount.getFrostHuifu().toString());
//                    }else {
//                        long time = Long.valueOf(exceptionAccount.getCreateTime()) * 1000;
//                        Date date = new Date(time);
//                        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        String s = dateformat.format(date);
//                        cell.setCellValue(s);
//                    }
//                }
//            }
//        }
//        // 导出
//        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//    }
    /**
     * 汇付对账列表导出
     * @auth sunpeikai
     * @param request 异常列表筛选检索条件
     * @return
     */
    @ApiOperation(value = "汇付对账列表导出", notes = "汇付对账列表导出")
    @PostMapping(value = "/accountexceptionlistexport")
	 public void exportToExcel(HttpServletResponse response, @RequestBody AccountExceptionRequest request,HttpServletRequest requestt) throws Exception {
	        //sheet默认最大行数
	        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
			// 表格sheet名称
			String sheetName = "代金券列表";
	        // 文件名称
	        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
	        // 声明一个工作薄
	        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
	        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

	        List<AccountExceptionVO> recordList = accountExceptionService.searchAccountExceptionList(request);
	        
	        Integer totalCount = recordList.size();

	        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
	        Map<String, String> beanPropertyColumnMap = buildMap();
	        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
	        String sheetNameTmp = sheetName + "_第1页";
	        if (totalCount == 0) {
	        	
	            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
	        }else {
	        	 helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList.subList(0, defaultRowMaxCount));
	        }
	        for (int i = 1; i < sheetCount; i++) {
	
	                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
	                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList.subList(defaultRowMaxCount*i, defaultRowMaxCount*(i+1)));
	            } 
	        
	        DataSet2ExcelSXSSFHelper.write2Response(requestt, response, fileName, workbook);
	    }

	    private Map<String, String> buildMap() {
	        Map<String, String> map = Maps.newLinkedHashMap();
	        map.put("title", "来源");
	        map.put("grantNum", "已发放数量");
	        map.put("usedNum", "已使用数量");
	        map.put("expireNum", "已失效数量");
	        map.put("utilizationRate", "使用率");
	        map.put("failureRate", "失效率");
	        map.put("recoverInterest", "总收益");
	        map.put("recivedMoney", "已发放收益");
	        map.put("norecivedMoney", "待发放收益");
	        map.put("realTenderMoney", "累计真实出借金额");
	    
	        return map;
	    }
	    private Map<String, IValueFormatter> buildValueAdapter() {
	        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
	        return mapAdapter;
	    }
}
