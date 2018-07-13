package com.hyjf.admin.controller.productcenter.borrow.batchcenter.batchborrowrecover;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BatchBorrowRecoverService;
import com.hyjf.am.response.admin.BatchBorrowRecoverBankInfoReponse;
import com.hyjf.am.response.admin.BatchBorrowRecoverReponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.admin.BorrowRecoverBankInfoVo;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/7
 * @Description: 批次中心-批次放款
 */
@Api(value = "批次中心-批次放款")
@RestController
@RequestMapping("/hyjf-admin/batchBorrowRecover")
public class BatchBorrowRecoverController extends BaseController{

    @Autowired
    private BatchBorrowRecoverService batchBorrowRecoverService;

    public static final String NAME_CLASS = "REPAY_STATUS";


    @ApiOperation(value = "批次中心-批次放款页面初始化", notes = "页面初始化")
    @PostMapping(value = "/batchBorrowRecoverInit")
    @ResponseBody
    public JSONObject batchBorrowRecoverInit() {
        JSONObject jsonObject = null;
        return jsonObject;
    }

    @ApiOperation(value = "批次中心-批次放款页面列表显示", notes = "页面列表显示")
    @PostMapping(value = "/querybatchBorrowRecoverList")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @ResponseBody
    public JSONObject querybatchBorrowRecoverList(@RequestBody BatchBorrowRecoverRequest request) {
        JSONObject jsonObject = null;
        request.setApiType(0);
        request.setNameClass(NAME_CLASS);
        BatchBorrowRecoverReponse batchBorrowRecovertReponse = batchBorrowRecoverService.queryBatchBorrowRecoverList(request);
        List<BatchBorrowRecoverVo> batchBorrowRecoverVoList = new ArrayList();
        if (null != batchBorrowRecovertReponse) {
            List<BatchBorrowRecoverVo> listAccountDetail = batchBorrowRecovertReponse.getResultList();
            Integer recordCount = batchBorrowRecovertReponse.getRecordTotal();
            if (null != listAccountDetail && listAccountDetail.size() > 0) {
                batchBorrowRecoverService.queryBatchCenterStatusName(listAccountDetail,"REVERIFY_STATUS");
                batchBorrowRecoverVoList.addAll(listAccountDetail);
            }
            if (null != batchBorrowRecoverVoList) {
                BatchBorrowRecoverVo sumVo = batchBorrowRecoverService.queryBatchCenterListSum(request);
                jsonObject = this.success(String.valueOf(recordCount), batchBorrowRecoverVoList);
                jsonObject.put("sumObject",sumVo);
            } else {
                jsonObject = this.fail("暂无符合条件数据");
            }
        }
        return jsonObject;
    }

    @ApiOperation(value = "批次中心-批次放款页面查询交易批次明细", notes = "查询交易批次明细")
    @PostMapping(value = "/querybatchBorrowRecoverBankInfoList")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @ResponseBody
    public JSONObject querybatchBorrowRecoverBankInfoList(@RequestBody String apicronID) {
        JSONObject jsonObject = null;
        BatchBorrowRecoverBankInfoReponse batchBorrowRecovertBankInfoReponse = batchBorrowRecoverService.queryBatchBorrowRecoverBankInfoList(apicronID);
        if (null != batchBorrowRecovertBankInfoReponse) {
            List<BorrowRecoverBankInfoVo> resultList = batchBorrowRecovertBankInfoReponse.getResultList();
            if (null != resultList) {
                jsonObject = this.success(String.valueOf(resultList.size()), resultList);
            } else {
                jsonObject = this.fail("暂无符合条件数据");
            }
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
        // 表格sheet名称
        String sheetName = "批次放款列表";

        request.setLimitStart(-1);
        BatchBorrowRecoverReponse batchBorrowRecovertReponse = batchBorrowRecoverService.queryBatchBorrowRecoverList(request);

        List<BatchBorrowRecoverVo> recordList = batchBorrowRecovertReponse.getResultList();
        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
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
