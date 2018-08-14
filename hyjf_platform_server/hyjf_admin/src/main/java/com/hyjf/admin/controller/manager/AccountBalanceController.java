package com.hyjf.admin.controller.manager;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AccountBalanceService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhInfoAccountBalanceResponse;
import com.hyjf.am.resquest.admin.HjhAccountBalanceRequest;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author：yinhui
 * @Date: 2018/8/7  9:32
 */
@Api(value = "数据中心-汇计划统计", tags = "数据中心-汇计划统计")
@RestController
@RequestMapping("/hyjf-admin/manager/statis")
public class AccountBalanceController extends BaseController {

    @Autowired
    private AccountBalanceService accountBalanceService;

    @ApiOperation(value = "数据中心-汇计划统计", notes = "数据中心-汇计划统计 查询")
    @PostMapping("/search")
    public AdminResult<ListResult<HjhAccountBalanceVO>> search(HttpServletRequest httpServletRequest, HjhAccountBalanceRequest request) {
        String time = httpServletRequest.getParameter("time");
        HjhInfoAccountBalanceResponse response = null;
        if (("month").equals(time)) {
            response = accountBalanceService.getSearchListByMonth(request);
            if (response == null) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            if (!Response.isSuccess(response)) {
                return new AdminResult<>(FAIL, response.getMessage());

            }
        } else {
            response = accountBalanceService.getSearchListByDay(request);
            if (response == null) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            if (!Response.isSuccess(response)) {
                return new AdminResult<>(FAIL, response.getMessage());

            }
        }
        return new AdminResult<ListResult<HjhAccountBalanceVO>>(ListResult.build(response.getResultList(), response.getCount()));
    }

    /**
     * 导出日报功能
     *
     * @param request
     * @param form
     */
    @ApiOperation(value = "数据中心-汇计划统计", notes = "数据中心-汇计划统计 导出日")
    @PostMapping("/exportActionByDay")
    public void exportActionByDay(HttpServletRequest request, HttpServletResponse response, HjhAccountBalanceRequest form) throws Exception {
        // 表格sheet名称
        String sheetName = "每日交易量";
        List<HjhAccountBalanceVO> resultList = accountBalanceService.getHjhAccountBalanceList(form);
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[]{"序号", "日期", "原始资产交易额(元)", "债转资产交易额(元)", "复投资金额(元)", "新加入资金额(元)"};
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
                    HjhAccountBalanceVO AccountBalanceCustomize = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    Double investAccount = AccountBalanceCustomize.getInvestAccount().doubleValue();
                    Double creditAccount = AccountBalanceCustomize.getCreditAccount().doubleValue();
                    Double reinvestAccount = AccountBalanceCustomize.getReinvestAccount().doubleValue();
                    Double addAccount = AccountBalanceCustomize.getAddAccount().doubleValue();
                    Date date = AccountBalanceCustomize.getRptDate();
                    String format = new SimpleDateFormat("yyyy-MM-dd ").format(date);
                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }

                    // 日期
                    else if (celLength == 1) {
                        cell.setCellValue(StringUtils.isEmpty(format) ? StringUtils.EMPTY : format);
                    }
                    // 原始资产交易额(元)
                    else if (celLength == 2) {
                        cell.setCellValue((investAccount != null ? investAccount : 0));
                    }
//					债转资产交易额(元)
                    else if (celLength == 3) {
                        cell.setCellValue((creditAccount != null ? creditAccount : 0));
                    }
//					复投资金额(元)
                    else if (celLength == 4) {
                        cell.setCellValue((reinvestAccount != null ? reinvestAccount : 0));
                    }
//					新加入资金额(元)
                    else if (celLength == 5) {
                        cell.setCellValue((addAccount != null ? addAccount : 0));
                    }

                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }

    /**
     * 按月导出功能
     *
     * @param request
     * @param form
     */
    @ApiOperation(value = "数据中心-汇计划统计", notes = "数据中心-汇计划统计 导出月")
    @PostMapping("/exportActionMonth")
    public void exportActionMonth(HttpServletRequest request, HttpServletResponse response, HjhAccountBalanceRequest form) throws Exception {
        // 表格sheet名称
        String sheetName = "每月交易量";
        List<HjhAccountBalanceVO> resultList = accountBalanceService.getHjhAccountBalanceMonthList(form);
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[]{"序号", "日期", "原始资产交易额(元)", "债转资产交易额(元)", "复投资金额(元)", "新加入资金额(元)"};
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
                    HjhAccountBalanceVO AccountBalanceCustomize = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);


                    Double investAccount = AccountBalanceCustomize.getInvestAccount().doubleValue();
                    Double creditAccount = AccountBalanceCustomize.getCreditAccount().doubleValue();
                    Double reinvestAccount = AccountBalanceCustomize.getReinvestAccount().doubleValue();
                    Double addAccount = AccountBalanceCustomize.getAddAccount().doubleValue();
                    String dataFormt = AccountBalanceCustomize.getDataFormt();
                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }

                    // 日期
                    else if (celLength == 1) {
                        cell.setCellValue(StringUtils.isEmpty(dataFormt) ? StringUtils.EMPTY : dataFormt);
                    }
                    // 原始资产交易额(元)
                    else if (celLength == 2) {
                        cell.setCellValue((investAccount != null ? investAccount : 0));
                    }
//					债转资产交易额(元)
                    else if (celLength == 3) {
                        cell.setCellValue((creditAccount != null ? creditAccount : 0));
                    }
//					复投资金额(元)
                    else if (celLength == 4) {
                        cell.setCellValue((reinvestAccount != null ? reinvestAccount : 0));
                    }
//					新加入资金额(元)
                    else if (celLength == 5) {
                        cell.setCellValue((addAccount != null ? addAccount : 0));
                    }

                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }
}
