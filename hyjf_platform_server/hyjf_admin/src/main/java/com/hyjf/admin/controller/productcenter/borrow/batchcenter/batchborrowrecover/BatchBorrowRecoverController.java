package com.hyjf.admin.controller.productcenter.borrow.batchcenter.batchborrowrecover;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BatchBorrowRecoverService;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.admin.BorrowRecoverBankInfoVo;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description: 批次中心-批次放款
 */
@Api(value = "Admin产品中心-批次中心-批次放款",description="Admin产品中心-批次中心-批次放款")
@RestController
@RequestMapping("/hyjf-admin/batchBorrowRecover")
public class BatchBorrowRecoverController extends BaseController{

    @Autowired
    private BatchBorrowRecoverService batchBorrowRecoverService;

    public static final String NAME_CLASS = "REVERIFY_STATUS";


    @ApiOperation(value = "批次中心-批次放款页面初始化", notes = "页面初始化")
    @PostMapping(value = "/batchBorrowRecoverInit")
    @ResponseBody
    public JSONObject batchBorrowRecoverInit() {
        JSONObject jsonObject = batchBorrowRecoverService.initPage(NAME_CLASS);

        return jsonObject;
    }

    @ApiOperation(value = "批次中心-批次放款页面列表显示", notes = "页面列表显示")
    @PostMapping(value = "/querybatchBorrowRecoverList")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @ResponseBody
    public JSONObject querybatchBorrowRecoverList(@RequestBody BatchBorrowRecoverRequest request) {
        JSONObject jsonObject;
        request.setApiType(0);
        request.setNameClass(NAME_CLASS);
        jsonObject = batchBorrowRecoverService.queryBatchBorrowRecoverList(request);
        return jsonObject;
    }

    @ApiOperation(value = "批次中心-批次放款页面查询交易批次明细", notes = "查询交易批次明细")
    @PostMapping(value = "/querybatchBorrowRecoverBankInfoList")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @ResponseBody
    @ApiImplicitParam(name = "apicronID",value = "任务ID")
    public JSONObject querybatchBorrowRecoverBankInfoList(@RequestBody String apicronID) {
        JSONObject jsonObject;
        List<BorrowRecoverBankInfoVo> resultList= batchBorrowRecoverService.queryBatchBorrowRecoverBankInfoList(apicronID);
        if (null != resultList) {
            jsonObject = this.success(String.valueOf(resultList.size()), resultList);
        } else {
            jsonObject = this.fail("暂无符合条件数据");
        }
        return jsonObject;
    }


    @ApiOperation(value = "批次中心-批次放款页面导出功能", notes = "导出功能")
    @PostMapping(value = "/exportBatchBorrowRecoverList")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @ResponseBody
    public JSONObject exportBatchBorrowRecoverList(@RequestBody BatchBorrowRecoverRequest request,HttpServletResponse response){
        String sheetName = "批次放款列表";

        request.setLimitStart(-1);
        JSONObject jsonObject = this.querybatchBorrowRecoverList(request);
        if(FAIL.equals(jsonObject.get(STATUS))){
            this.fail("暂时没有符合条件的数据！");
        }
        List<BatchBorrowRecoverVo> recordList = (List<BatchBorrowRecoverVo>) jsonObject.get(LIST);

        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[] { "序号","借款编号","资产来源","批次号", "借款金额","放款服务费","应放款","已放款","总笔数","成功笔数","失败笔数","更新时间","批次状态"};
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
                    BatchBorrowRecoverVo  customize= recordList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 借款编号
                    else if (celLength == 1) {
                        cell.setCellValue(customize.getBorrowNid());
                    }
                    /*----------add by LSY START---------------------*/
                    // 借款编号
                    else if (celLength == 2) {
                        cell.setCellValue(customize.getInstName());
                    }
                    /*----------add by LSY END---------------------*/
                    // 批次号
                    else if (celLength == 3) {
                        cell.setCellValue(customize.getBatchNo());
                    }
                    // 借款金额
                    else if (celLength == 4) {
                        cell.setCellValue(customize.getBorrowAccount().toString());
                    }
                    // 应收服务费
                    else if (celLength == 5) {
                        cell.setCellValue(customize.getBatchServiceFee().toString());
                    }
                    // 应放款
                    else if (celLength == 6) {
                        cell.setCellValue(customize.getBatchAmount().toString());
                    }
                    // 已放款
                    else if (celLength == 7) {
                        cell.setCellValue(customize.getSucAmount().toString());
                    }
                    // 总笔数）
                    else if (celLength == 8) {
                        cell.setCellValue(customize.getBatchCounts());
                    }
                    // 成功笔数
                    else if (celLength == 9) {
                        cell.setCellValue(customize.getSucCounts());
                    }
                    // 失败笔数
                    else if (celLength == 10) {
                        cell.setCellValue(customize.getFailCounts());
                    }
                    // 更新时间
                    else if (celLength == 11) {
                        cell.setCellValue(customize.getUpdateTime());
                    }
                    // 批次状态
                    else if (celLength == 12) {
                        cell.setCellValue(customize.getStatusStr());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        return this.success();
    }


}
