/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.exception.accountexception;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AccountExceptionService;
import com.hyjf.am.resquest.admin.AccountExceptionRequest;
import com.hyjf.am.vo.admin.AccountExceptionVO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: AccountExceptionController, v0.1 2018/7/11 15:02
 */
@RestController
@RequestMapping("/hyjf-admin/accountexception")
@Api(value = "异常中心-汇付对账")
public class AccountExceptionController extends BaseController {

    @Autowired
    private AccountExceptionService accountExceptionService;

    /**
     * 查询汇付对账异常list
     * @auth sunpeikai
     * @param request 异常列表筛选检索条件
     * @return
     */
    @ApiOperation(value = "汇付对账列表", notes = "汇付对账列表")
    @PostMapping(value = "/accountexceptionlist")
    public JSONObject accountExceptionList(@RequestBody AccountExceptionRequest request){
        JSONObject jsonObject = new JSONObject();
        // 数据总数
        Integer count = accountExceptionService.getAccountExceptionCount(request);
        jsonObject.put("count",count);
        // 异常列表list
        List<AccountExceptionVO> accountExceptionVOList = accountExceptionService.searchAccountExceptionList(request);
        jsonObject.put("accountExceptionVOList",accountExceptionVOList);
        return jsonObject;
    }

    /**
     * 更新信息
     * @auth sunpeikai
     * @param request 包含id AccountException的主键
     * @return
     */
    @ApiOperation(value = "更新信息", notes = "更新信息")
    @PostMapping(value = "/sync")
    public JSONObject sync(@RequestBody AccountExceptionRequest request){
        JSONObject jsonObject = new JSONObject();
        accountExceptionService.syncAccount(request.getId());
        return jsonObject;
    }
    /**
     * 汇付对账列表导出
     * @auth sunpeikai
     * @param request 异常列表筛选检索条件
     * @return
     */
    @ApiOperation(value = "汇付对账列表导出", notes = "汇付对账列表导出")
    @PostMapping(value = "/accountexceptionlistexport")
    public void exportAccountExceptionList(HttpServletResponse response, @RequestBody AccountExceptionRequest request){
        // currPage<0 为全部,currPage>0 为具体某一页
        request.setCurrPage(-1);
        // 表格sheet名称
        String sheetName = "注册信息";
        // 文件名称
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date())
                + CustomConstants.EXCEL_EXT;

        List<AccountExceptionVO> recordList = accountExceptionService.searchAccountExceptionList(request);
        String[] titles = new String[] { "序号", "用户名", "客户号", "手机号","角色", "平台可用金额", "平台冻结金额", "汇付可用金额", "汇付冻结金额" };
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
                    AccountExceptionVO exceptionAccount = recordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {
                        cell.setCellValue(exceptionAccount.getUsername());
                    } else if (celLength == 2) {
                        cell.setCellValue(exceptionAccount.getCustomId());
                    } else if (celLength == 3) {
                        cell.setCellValue(exceptionAccount.getMobile());
                    } else if (celLength == 4) {
                        cell.setCellValue(exceptionAccount.getRole());
                    } else if (celLength == 5) {
                        cell.setCellValue(exceptionAccount.getBalancePlat().toString());
                    } else if (celLength == 6) {
                        cell.setCellValue(exceptionAccount.getFrostPlat().toString());
                    } else if (celLength == 7) {
                        cell.setCellValue(exceptionAccount.getBalanceHuifu().toString());
                    } else if (celLength == 8) {
                        cell.setCellValue(exceptionAccount.getFrostHuifu().toString());
                    }else {
                        long time = Long.valueOf(exceptionAccount.getCreateTime()) * 1000;
                        Date date = new Date(time);
                        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String s = dateformat.format(date);
                        cell.setCellValue(s);
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}