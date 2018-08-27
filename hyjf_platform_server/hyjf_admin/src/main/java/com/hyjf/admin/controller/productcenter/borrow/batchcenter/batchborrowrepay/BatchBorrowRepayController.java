package com.hyjf.admin.controller.productcenter.borrow.batchcenter.batchborrowrepay;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BatchBorrowRecoverService;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.admin.BatchBorrowRepayBankInfoVO;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;


/**
 * @Auther:yangchangwei
 * @Date:2018/7/12
 * @Description:
 */
@Api(value = "产品中心-批次中心-批次还款",tags ="产品中心-批次中心-批次还款")
@RestController
@RequestMapping("/hyjf-admin/batchBorrowRepay")
public class BatchBorrowRepayController extends BaseController{

    private static final String NAME_CLASS = "REPAY_STATUS";

    private static final String PERMISSIONS = "HjhDebtCredit";

    @Autowired
    private BatchBorrowRecoverService batchBorrowRecoverService;

    @ApiOperation(value = "批次中心-批次还款页面初始化", notes = "页面初始化")
    @PostMapping(value = "/batchBorrowRepayInit")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @ResponseBody
    public JSONObject batchBorrowRepayInit() {
        JSONObject jsonObject = batchBorrowRecoverService.initPage(NAME_CLASS);
        BatchBorrowRecoverRequest request = new BatchBorrowRecoverRequest();
        JSONObject borrowRepayList = querybatchBorrowRepayList(request);
        if(borrowRepayList != null){
            List<BatchBorrowRecoverVo> listAccountDetail = (List<BatchBorrowRecoverVo>) borrowRepayList.get(LIST);
            if(listAccountDetail != null && listAccountDetail.size() > 0){
                jsonObject.put("批次还款列表","listAccountDetail");
                jsonObject.put("listAccountDetail",listAccountDetail);
                jsonObject.put("hjhDebtCreditVoListTotal",borrowRepayList.get(TRCORD));
            }
        }
        return jsonObject;
    }

    @ApiOperation(value = "批次中心-批次还款页面列表显示", notes = "页面列表显示")
    @PostMapping(value = "/querybatchBorrowRepayList")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @ResponseBody
    public JSONObject querybatchBorrowRepayList(@RequestBody BatchBorrowRecoverRequest request) {
        request.setApiType(1);
        request.setNameClass(NAME_CLASS);
        JSONObject jsonObject = batchBorrowRecoverService.queryBatchBorrowRecoverList(request);
        return jsonObject;
    }


    @ApiOperation(value = "批次中心-批次还款页面查询交易批次明细", notes = "查询交易批次明细")
    @PostMapping(value = "/querybatchBorrowRepayBankInfoList")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @ApiImplicitParam(name = "apicronID",value = "任务ID")
    public JSONObject querybatchBorrowRepayBankInfoList(@RequestBody String apicronID) {
        JSONObject jsonObject;
        List<BatchBorrowRepayBankInfoVO> resultList = batchBorrowRecoverService.queryBatchBorrowRepayBankInfoList(apicronID);
        if (null != resultList) {
            jsonObject = this.success(String.valueOf(resultList.size()), resultList);
        } else {
            jsonObject = this.fail("暂无符合条件数据");
        }
        return jsonObject;
    }

    @ApiOperation(value = "批次中心-批次还款页面导出功能", notes = "导出功能")
    @PostMapping(value = "/exportBatchBorrowRepayList")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    @ResponseBody
    public JSONObject exportBatchBorrowRepayList(@RequestBody BatchBorrowRecoverRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        // 表格sheet名称
        String sheetName = "批次还款列表";

        request.setLimitStart(-1);
        JSONObject jsonObject = this.querybatchBorrowRepayList(request);

        if(FAIL.equals(jsonObject.get(STATUS))){
            this.fail("暂时没有符合条件的数据！");
        }
        List<BatchBorrowRecoverVo> recordList = (List<BatchBorrowRecoverVo>) jsonObject.get(LIST);
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 序号   借款编号    批次号 还款角色    还款用户名   当前还款期数  总期数 借款金额    应收服务费   应还款 已还款 总笔数 成功笔数    成功金额    失败笔数    失败金额    提交时间    更新时间    批次状态    银行回执说明
        String[] titles = new String[] {"序号 ","借款编号","资产来源","批次号","还款角色","还款用户名","当前还款期数","总期数","借款金额","还款服务费",
                "应还款","已还款","总笔数","成功笔数","成功金额","失败笔数","失败金额","提交时间","更新时间","批次状态","银行回执说明"};
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
                    BatchBorrowRecoverVo customize = recordList.get(i);

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
                    // 资产来源
                    else if (celLength == 2) {
                        cell.setCellValue(customize.getInstName());
                    }
                    // 批次号
                    else if (celLength == 3) {
                        cell.setCellValue(customize.getBatchNo());
                    }
                    // 还款角色
                    else if (celLength == 4) {
                        cell.setCellValue("0".equals(customize.getIsRepayOrgFlag())?"借款人":"担保机构");
                    }
                    // 还款用户名
                    else if (celLength == 5) {
                        cell.setCellValue(customize.getUserName());
                    }
                    // 当前还款期数
                    else if (celLength == 6) {
                        cell.setCellValue(customize.getPeriodNow());
                    }
                    // 总期数
                    else if (celLength == 7) {
                        cell.setCellValue(customize.getBorrowPeriod());
                    }
                    // 借款金额
                    else if (celLength == 8) {
                        cell.setCellValue(customize.getBorrowAccount().toString());
                    }
                    // 应收服务费
                    else if (celLength == 9) {
                        cell.setCellValue(customize.getBatchServiceFee().toString());
                    }
                    // 应还款
                    else if (celLength == 10) {
                        cell.setCellValue(customize.getBatchAmount().toString());
                    }
                    // 已还款
                    else if (celLength == 11) {
                        cell.setCellValue(customize.getSucAmount().toString());
                    }
                    // 总笔数
                    else if (celLength == 12) {
                        cell.setCellValue(customize.getBatchCounts());
                    }
                    // 成功笔数
                    else if (celLength == 13) {
                        cell.setCellValue(customize.getSucCounts());
                    }
                    // 成功金额
                    else if (celLength == 14) {
                        cell.setCellValue(customize.getSucAmount().toString());
                    }
                    // 失败笔数
                    else if (celLength == 15) {
                        cell.setCellValue(customize.getFailCounts());
                    }
                    //  失败金额
                    else if (celLength == 16) {
                        cell.setCellValue(customize.getFailAmount().toString());
                    }
                    // 提交时间
                    else if (celLength == 17) {
                        cell.setCellValue(customize.getCreateTime());
                    }
                    // 更新时间
                    else if (celLength == 18) {
                        cell.setCellValue(customize.getUpdateTime());
                    }
                    // 批次状态
                    else if (celLength == 19) {
                        cell.setCellValue(customize.getStatusStr());
                    }
                    // 银行回执说明
                    else if (celLength == 20) {
                        cell.setCellValue(customize.getData());
                    }

                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        return this.success();
    }






}
