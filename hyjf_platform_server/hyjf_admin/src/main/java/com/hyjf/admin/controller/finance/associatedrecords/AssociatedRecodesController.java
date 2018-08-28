/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.associatedrecords;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AssociatedRecordsService;
import com.hyjf.am.resquest.admin.AssociatedRecordListRequest;
import com.hyjf.am.vo.admin.AssociatedRecordListVo;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: AssociatedRecodesController, v0.1 2018/7/5 11:25
 */
@Api(value = "资金中心-定向转账-关联记录",tags = "资金中心-定向转账-关联记录")
@RestController
@RequestMapping(value = "/hyjf-admin/finance/associatedrecords")
public class AssociatedRecodesController extends BaseController {

    @Autowired
    private AssociatedRecordsService associatedRecordsService;

    /**
     * 查询关联记录列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @ApiOperation(value = "查询关联记录列表",notes = "查询关联记录列表")
    @PostMapping(value = "/getassociatedrecordlist")
    public AdminResult<ListResult<AssociatedRecordListVo>> getAssociatedRecordList(@RequestBody AssociatedRecordListRequest request){
        Integer count = associatedRecordsService.getAssociatedRecordsCount(request);
        count = (count == null)?0:count;
        List<AssociatedRecordListVo> associatedRecordListVoList = associatedRecordsService.getAssociatedRecordList(request);
        return new AdminResult<>(ListResult.build(associatedRecordListVoList,count));
    }
    /**
     * 关联记录列表导出
     * @param request 筛选条件
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出关联记录列表",notes = "导出关联记录列表")
    @PostMapping(value = "/associatedrecordlistexport")
    public void exportAssociatedRecordListExcel(@RequestBody AssociatedRecordListRequest request, HttpServletResponse response) throws Exception {
        // currPage<0 为全部,currPage>0 为具体某一页
        request.setCurrPage(-1);

        // 表格sheet名称
        String sheetName = "关联记录列表";
        // 文件名称
        String fileName =
                URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        // 检索列表
        List<AssociatedRecordListVo> associatedRecordListVos = associatedRecordsService.getAssociatedRecordList(request);
        String[] titles =
                new String[] { "序号", "转出账户", "转出账户手机", "转出账户客户号", "转入账户", "转入账户手机", "转入账户客户号", "关联状态", "关联时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (associatedRecordListVos != null && associatedRecordListVos.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < associatedRecordListVos.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet =
                            ExportExcel
                                    .createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }
                // 新建一行
                Row row = sheet.createRow(rowNum);

                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    AssociatedRecordListVo associatedRecordListVo = associatedRecordListVos.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) { // 转出账户
                        cell.setCellValue(associatedRecordListVo.getTurnOutUsername());
                    } else if (celLength == 2) { // 转出账户手机
                        cell.setCellValue(associatedRecordListVo.getTurnOutMobile());
                    } else if (celLength == 3) { // 转出账户客户号
                        cell.setCellValue(String.valueOf(associatedRecordListVo.getTurnOutChinapnrUsrcustid()));
                    } else if (celLength == 4) {// 转入账户
                        cell.setCellValue(associatedRecordListVo.getShiftToUsername());
                    } else if (celLength == 5) {// 转入账户手机
                        cell.setCellValue(associatedRecordListVo.getShiftToMobile());
                    } else if (celLength == 6) {// 转入账户客户号
                        cell.setCellValue(String.valueOf(associatedRecordListVo.getShiftToChinapnrUsrcustid()));
                    } else if (celLength == 7) {// 关联状态
                        if (associatedRecordListVo.getAssociatedState() == 0) {
                            cell.setCellValue("未授权");
                        } else if (associatedRecordListVo.getAssociatedState() == 1) {
                            cell.setCellValue("成功");
                        } else if (associatedRecordListVo.getAssociatedState() == 2) {
                            cell.setCellValue("失败");
                        }
                    } else if (celLength == 8) {// 关联时间
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cell.setCellValue(df.format(associatedRecordListVo.getAssociatedTime()));
                    }
                }
            }
        }

        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }
}
