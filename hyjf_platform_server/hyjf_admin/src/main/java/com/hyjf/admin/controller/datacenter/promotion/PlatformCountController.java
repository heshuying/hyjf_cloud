/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.datacenter.promotion;

import com.hyjf.admin.beans.request.PlatformCountRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlatformCountService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.PlatformCountCustomizeResponse;
import com.hyjf.am.vo.admin.PlatformCountCustomizeVO;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fuqiang
 * @version PlatformCountController, v0.1 2018/7/18 17:46
 */
@Api(description = "数据中心-平台统计")
@RestController
@RequestMapping("/hyjf-admin/promotion/platformcount")
public class PlatformCountController extends BaseController {
    @Autowired
    private PlatformCountService platformCountService;

    @ApiOperation(value = "数据中心-平台统计", notes = "数据中心-平台统计列表查询")
    @RequestMapping("/searchaction")
    public AdminResult<ListResult<PlatformCountCustomizeVO>> searchAction(@RequestBody PlatformCountRequestBean requestBean) {
        PlatformCountCustomizeResponse response = platformCountService.searchAction(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
    }

    /**
     * 导出功能
     * @param request
     * @param response
     * @param form
     * @throws Exception
     */
    @RequestMapping("/exportAction")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, PlatformCountRequestBean form) throws Exception {
        // 表格sheet名称
        String sheetName = "平台统计";

        PlatformCountCustomizeVO platformCountCustomize = new PlatformCountCustomizeVO();

        List<PlatformCountCustomizeVO> recordList = new ArrayList<>();
        PlatformCountCustomizeResponse countCustomizeResponse = platformCountService.searchAction(form);
        if (countCustomizeResponse != null) {
            recordList = countCustomizeResponse.getResultList();
        }

        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        String[] titles = new String[] { "序号", "平台", "访问数", "注册数", "开户数", "投资人数", "累计充值", "累计投资", "汇直投投资金额", "汇消费投资金额", "汇天利投资金额" };
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
                    PlatformCountCustomizeVO record = recordList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 平台
                    else if (celLength == 1) {
                        cell.setCellValue(record.getSourceName());
                    }
                    // 访问数
                    else if (celLength == 2) {
                        cell.setCellValue(record.getAccessNumber());
                    }
                    // 注册数
                    else if (celLength == 3) {
                        cell.setCellValue(record.getRegistNumber());
                    }
                    // 开户数
                    else if (celLength == 4) {
                        cell.setCellValue(record.getAccountNumber());
                    }
                    // 投资人数

                    else if (celLength == 5) {
                        cell.setCellValue(record.getTenderNumber());
                    }
                    // 累计充值
                    else if (celLength == 6) {
                        cell.setCellValue(record.getRechargePrice());
                    }
                    // 累计投资
                    else if (celLength == 7) {
                        cell.setCellValue(record.getTenderPrice());
                    }
                    // 汇直投投资金额
                    else if (celLength == 8) {
                        cell.setCellValue(record.getHztTenderPrice());
                    }
                    // 汇消费投资金额
                    else if (celLength == 9) {
                        cell.setCellValue(record.getHxfTenderPrice());
                    }
                    // 汇天利投资金额
                    else if (celLength == 10) {
                        cell.setCellValue(record.getHtlTenderPrice());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }

}
