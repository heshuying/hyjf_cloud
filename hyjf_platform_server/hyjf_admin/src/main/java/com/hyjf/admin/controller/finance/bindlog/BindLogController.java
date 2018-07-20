/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.bindlog;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BindLogService;
import com.hyjf.am.resquest.admin.BindLogListRequest;
import com.hyjf.am.vo.admin.BindLogVO;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: BindLogController, v0.1 2018/7/5 15:36
 */
@Api(value = "资金中心-定向转账-绑定日志")
@RestController
@RequestMapping(value = "/hyjf-admin/bindlog")
public class BindLogController extends BaseController {

    @Autowired
    private BindLogService bindLogService;

    /**
     * 查询绑定日志列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    @ApiOperation(value = "查询绑定日志列表",notes = "查询绑定日志列表")
    @PostMapping(value = "/getbindloglist")
    public AdminResult getBindLogList(@RequestBody BindLogListRequest request){
        Map<String,Object> map = new HashMap<>();
        Integer count = bindLogService.getBindLogCount(request);
        count = (count == null)?0:count;
        map.put("count",count);
        List<BindLogVO> bindLogVOList = bindLogService.searchBindLogList(request);
        map.put("bindLogVOList",bindLogVOList);
        return new AdminResult(map);
    }

    /**
     * 根据筛选条件导出绑定日志list
     * @auth sunpeikai
     * @param
     * @return
     */
    @ApiOperation(value = "导出绑定日志列表",notes = "根据筛选条件导出绑定日志list")
    @GetMapping(value = "/bindloglistexport")
    public void exportBindLogList(HttpServletResponse response, @ModelAttribute BindLogListRequest request){
        // currPage<0 为全部,currPage>0 为具体某一页
        request.setCurrPage(-1);

        // 表格sheet名称
        String sheetName = "绑定日志列表";
        // 文件名称
        String fileName =
                sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        // 检索列表
        List<BindLogVO> bindLogVOList = bindLogService.searchBindLogList(request);
        String[] titles =
                new String[] { "序号", "转出账户", "转出账户手机", "转出账户客户号", "转入账户", "转入账户手机", "转入账户客户号", "关联状态", "关联时间", "说明" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (bindLogVOList != null && bindLogVOList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < bindLogVOList.size(); i++) {
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
                    BindLogVO bindLogVO = bindLogVOList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) { // 转出账户
                        cell.setCellValue(bindLogVO.getTurnOutUsername());
                    } else if (celLength == 2) { // 转出账户手机
                        cell.setCellValue(bindLogVO.getTurnOutMobile());
                    } else if (celLength == 3) { // 转出账户客户号
                        cell.setCellValue(String.valueOf(bindLogVO.getTurnOutChinapnrUsrcustid()));
                    } else if (celLength == 4) {// 转入账户
                        cell.setCellValue(bindLogVO.getShiftToUsername());
                    } else if (celLength == 5) {// 转入账户手机
                        cell.setCellValue(bindLogVO.getShiftToMobile());
                    } else if (celLength == 6) {// 转入账户客户号
                        cell.setCellValue(String.valueOf(bindLogVO.getShiftToChinapnrUsrcustid()));
                    } else if (celLength == 7) {// 关联状态
                        if (bindLogVO.getAssociatedState() == 0) {
                            cell.setCellValue("未授权");
                        } else if (bindLogVO.getAssociatedState() == 1) {
                            cell.setCellValue("成功");
                        } else if (bindLogVO.getAssociatedState() == 2) {
                            cell.setCellValue("失败");
                        }
                    } else if (celLength == 8) {// 关联时间
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cell.setCellValue(df.format(bindLogVO.getAssociatedTime()));
                    } else if (celLength == 9) {// 说明
                        cell.setCellValue(bindLogVO.getRemark());
                    }
                }
            }
        }

        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }
}
