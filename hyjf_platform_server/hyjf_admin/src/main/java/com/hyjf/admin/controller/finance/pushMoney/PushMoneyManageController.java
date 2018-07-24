/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.pushMoney;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.service.PushMoneyManageService;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.PushMoneyRequest;
import com.hyjf.am.vo.trade.PushMoneyVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetterUtil;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zdj
 * @version PushMoneyManageController, v0.1 2018/7/3 15:16
 * 资金中心->直投提成管理
 */

@Api(value = "直投提成管理")
@RestController
@RequestMapping("/hyjf-admin/pushMoney")
public class PushMoneyManageController {
    @Autowired
    private PushMoneyManageService pushMoneyManageService;

    //直投提成管理列表查询
    @ApiOperation(value = "直投提成管理", notes = "直投提成管理列表查询")
    @PostMapping(value = "/pushmoneylist")
    @ResponseBody
    public JSONObject getPushMoneyList(PushMoneyRequest pushMoneyRequest){
        JSONObject jsonObject = new JSONObject();
        List<PushMoneyVO> pushMoneyList =pushMoneyManageService.findPushMoneyList(pushMoneyRequest);
        String status="error";
        if(null!=pushMoneyList&&pushMoneyList.size()>0){
            jsonObject.put("record",pushMoneyList);
            status="success";
        }
        jsonObject.put("status",status);
        return jsonObject;
    }
    //计算提成
    @ApiOperation(value = "计算提成", notes = "计算提成")
    @PostMapping(value = "/calculateushmoney")
    @ResponseBody
    public JSONObject calculatePushMoneyAction(PushMoneyRequest pushMoneyRequest){
        JSONObject jsonObject = new JSONObject();
        // 提成ID
        String borrowNid = pushMoneyRequest.getBorrowNid();
        // 取得借款API表
        BorrowApicronVO apicron = this.pushMoneyManageService.getBorrowApicronBorrowNid(borrowNid);
        String status= Response.SUCCESS;
        if (apicron == null) {
            jsonObject.put("record","该项目不存在!");
            status = Response.FAIL;
        }
        if (GetterUtil.getInteger(apicron.getWebStatus()) == 1) {
            jsonObject.put("record","该标的提成已经计算完成!");
            status = Response.SUCCESS;
        }
        int cnt = -1;
        try {
            // 发提成处理
            cnt = this.pushMoneyManageService.insertTenderCommissionRecord(apicron.getId(), pushMoneyRequest);
        } catch (Exception e) {
            jsonObject.put("record","提成计算失败,请重新操作!");
            status = Response.FAIL;
        }

        if (cnt >= 0) {
            jsonObject.put("record","提成计算成功！");
            status = Response.SUCCESS;
        } else {
            jsonObject.put("record","提成计算失败,请重新操作!");
            status = Response.FAIL;
        }
        jsonObject.put("status",status);
        return jsonObject;
    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param pushMoneyRequest
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "直投提成管理", notes = "直投提成管理记录导出")
    @PostMapping(value = "/exportpushmoney")
    public void exportExcel(PushMoneyRequest pushMoneyRequest, HttpServletResponse response){
        // 表格sheet名称
        String sheetName = "直投提成管理";
        // 文件名称
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 需要输出的结果列表
        List<PushMoneyVO> pushMoneyVOList =pushMoneyManageService.findPushMoneyList(pushMoneyRequest);
        String[] titles = new String[] { "序号", "项目编号", "项目标题", "融资期限", "融资金额", "提成总额", "放款时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (pushMoneyVOList != null && pushMoneyVOList.size() > 0) {
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < pushMoneyVOList.size(); i++) {
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
                    PushMoneyVO pushMoney = pushMoneyVOList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {// 序号
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 项目编号
                        cell.setCellValue(pushMoney.getBorrowNid());
                    } else if (celLength == 2) {// 项目标题
                        cell.setCellValue(pushMoney.getBorrowName());
                    } else if (celLength == 3) {// 融资期限
                        if ("endday".equals(pushMoney.getBorrowStyle())) {
                            cell.setCellValue(pushMoney.getBorrowPeriod()+"天");
                        } else {
                            cell.setCellValue(pushMoney.getBorrowPeriod()+"个月");
                        }
                    }else if (celLength == 4) {// 融资金额
                        cell.setCellValue(pushMoney.getAccount());
                    } else if (celLength == 5) {// 提成总额
                        cell.setCellValue(pushMoney.getCommission());
                    }else if (celLength == 6) {// 放款时间
                        cell.setCellValue(pushMoney.getRecoverLastTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
