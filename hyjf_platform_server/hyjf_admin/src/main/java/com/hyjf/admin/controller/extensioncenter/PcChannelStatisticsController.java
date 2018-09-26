/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.extensioncenter;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.promotion.PcChannelStatisticsService;
import com.hyjf.am.response.admin.promotion.PcChannelStatisticsResponse;
import com.hyjf.am.resquest.admin.PcChannelStatisticsRequest;
import com.hyjf.am.vo.datacollect.PcChannelStatisticsVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.ExportExcel;
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

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author fq
 * @version PcChannelStatisticsController, v0.1 2018/9/26 11:47
 */
@Api(tags = "推广中心-PC渠道统计")
@RestController
@RequestMapping("/hyjf-admin/pc_channel_statistics")
public class PcChannelStatisticsController extends BaseController {
    @Autowired
    private PcChannelStatisticsService pcChannelStatisticsService;

    @ApiOperation(value = "查询pc渠道统计", notes = "查询pc渠道统计")
    @RequestMapping("/search")
    public AdminResult searchAction(@RequestBody PcChannelStatisticsRequest request) {
        PcChannelStatisticsResponse response = pcChannelStatisticsService.searchPcChannelStatistics(request);
        return new AdminResult(response);
    }

    @ApiOperation(value = "导出功能", notes = "导出功能")
    public void export(@RequestBody PcChannelStatisticsRequest request, HttpServletResponse response) {
        // 表格sheet名称
        String sheetName = "渠道统计";

        PcChannelStatisticsResponse pcChannelStatisticsResponse = this.pcChannelStatisticsService.searchPcChannelStatistics(request);
        if (pcChannelStatisticsResponse != null) {
            List<PcChannelStatisticsVO> recordList = pcChannelStatisticsResponse.getResultList();
            String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

            String[] titles = new String[] { "序号", "渠道", "访问数", "注册数", "开户数", "投资人数", "累计充值", "累计投资", "汇直投投资金额", "汇消费投资金额", "汇天利投资金额", "汇添金投资金额", "汇金理财投资金额", "汇转让投资金额" };
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
                        PcChannelStatisticsVO record = recordList.get(i);
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
                            cell.setCellValue(record.getOpenAccountNumber());
                        }
                        // 投资人数
                        else if (celLength == 5) {
                            cell.setCellValue(record.getTenderNumber());
                        }
                        // 累计充值
                        else if (celLength == 6) {
                            cell.setCellValue(String.valueOf(record.getCumulativeRecharge()));
                        }
                        // 累计投资
                        else if (celLength == 7) {
                            cell.setCellValue(String.valueOf(record.getCumulativeInvestment()));
                        }
                        // 汇直投投资金额
                        else if (celLength == 8) {
                            cell.setCellValue(String.valueOf(record.getHztTenderPrice()));
                        }
                        // 汇消费投资金额
                        else if (celLength == 9) {
                            cell.setCellValue(String.valueOf(record.getHxfTenderPrice()));
                        }
                        // 汇天利投资金额
                        else if (celLength == 10) {
                            cell.setCellValue(String.valueOf(record.getHtlTenderPrice()));
                        }
                        // 汇添金投资金额
                        else if (celLength == 11) {
                            cell.setCellValue(String.valueOf(record.getHtjTenderPrice()));
                        }
                        // 汇金理财投资金额
                        else if (celLength == 12) {
                            cell.setCellValue(String.valueOf(record.getRtbTenderPrice()));
                        }
                        // 汇转让投资金额
                        else if (celLength == 13) {
                            cell.setCellValue(String.valueOf(record.getHzrTenderPrice()));
                        }
                    }
                }
            }
            // 导出
            ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        }
    }
}
