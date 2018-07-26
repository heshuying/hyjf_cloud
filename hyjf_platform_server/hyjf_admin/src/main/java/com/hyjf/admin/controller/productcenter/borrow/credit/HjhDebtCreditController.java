package com.hyjf.admin.controller.productcenter.borrow.credit;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.BorrowRegistExceptionService;
import com.hyjf.admin.service.HjhDebtCreditService;
import com.hyjf.am.response.admin.HjhDebtCreditReponse;
import com.hyjf.am.resquest.admin.HjhDebtCreditListRequest;
import com.hyjf.am.vo.admin.HjhDebtCreditVo;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther:yangchangwei
 * @Date:2018/7/3
 * @Description: 汇计划-转让记录
 */
@Api(value = "Admin端产品中心-汇计划-转让记录",description="Admin端产品中心-汇计划-转让记录")
@RestController
@RequestMapping("/hyjf-admin/hjhDebtCredit")
public class HjhDebtCreditController extends BaseController{


    @Autowired
    private HjhDebtCreditService hjhDebtCreditService;

    @Autowired
    private BorrowRegistExceptionService borrowRegistExceptionService;

    @ApiOperation(value = "汇计划-转让记录页面初始化", notes = "页面初始化")
    @PostMapping(value = "/hjhDebtCreditInit")
    @ResponseBody
    public JSONObject hjhDebtCreditInit() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",SUCCESS);
        List<BorrowStyleVO> styleVOList = borrowRegistExceptionService.selectBorrowStyleList();
        if(styleVOList != null && styleVOList.size() > 0){
            jsonObject.put("还款方式列表","borrowStyleList");
            jsonObject.put("borrowStyleList",styleVOList);
        }else {
            jsonObject.put("status",FAIL);
            jsonObject.put("msg","获取还款方式列表失败！");
        }
        //转让状态
        List<ParamNameVO> hjhDebtCreditStatus = hjhDebtCreditService.getParamNameList(CustomConstants.HJH_DEBT_CREDIT_STATUS);
        if(hjhDebtCreditStatus != null && hjhDebtCreditStatus.size() > 0){
            jsonObject.put("转让状态列表","hjhDebtCreditStatus");
            jsonObject.put("hjhDebtCreditStatus",hjhDebtCreditStatus);
        }else {
            jsonObject.put("status",FAIL);
            jsonObject.put("msg","获取转让状态列表失败！");
        }
        //汇计划债转还款状态
        List<ParamNameVO> hjhDebtRepayStatus = hjhDebtCreditService.getParamNameList(CustomConstants.HJH_DEBT_REPAY_STATUS);
        if(hjhDebtRepayStatus != null && hjhDebtRepayStatus.size() > 0){
            jsonObject.put("还款状态列表","hjhDebtRepayStatus");
            jsonObject.put("hjhDebtRepayStatus",hjhDebtRepayStatus);
        }else {
            jsonObject.put("status",FAIL);
            jsonObject.put("msg","获取还款状态列表失败！");
        }
        return jsonObject;
    }

    @ApiOperation(value = "汇计划-转让记录页面列表显示", notes = "页面列表显示")
    @PostMapping(value = "/queryHjhDebtCreditDetail")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @ResponseBody
    public JSONObject queryHjhDebtCreditDetail(@RequestBody HjhDebtCreditListRequest request) {
        JSONObject jsonObject = null;
        HjhDebtCreditReponse hjhDebtCreditReponse = hjhDebtCreditService.queryHjhDebtCreditList(request);
        List<HjhDebtCreditVo> hjhDebtCreditVoList = new ArrayList<HjhDebtCreditVo>();
        if (null != hjhDebtCreditReponse) {
            List<HjhDebtCreditVo> listAccountDetail = hjhDebtCreditReponse.getResultList();
            Integer recordCount = hjhDebtCreditReponse.getRecordTotal();
            if (null != listAccountDetail && listAccountDetail.size() > 0) {
                hjhDebtCreditVoList.addAll(listAccountDetail);
            }
            if (null != hjhDebtCreditVoList) {
                hjhDebtCreditService.queryHjhDebtCreditListStatusName(hjhDebtCreditVoList);
                jsonObject = this.success(String.valueOf(recordCount), hjhDebtCreditVoList);
            } else {
                jsonObject = this.fail("暂无符合条件数据");
            }
        }else{
            jsonObject = this.fail("暂无符合条件数据");
        }
        return jsonObject;
    }

    @ApiOperation(value = "汇计划-转让记录页面导出列表", notes = "页面列表导出")
    @PostMapping(value = "/exportHjhDebtCreditDetail")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功")
    })
    @ResponseBody
    public JSONObject exportHjhDebtCreditDetail(@RequestBody HjhDebtCreditListRequest request,HttpServletResponse response) {

        request.setCurrPage(-1);
        HjhDebtCreditReponse hjhDebtCreditReponse = hjhDebtCreditService.queryHjhDebtCreditList(request);

        List<HjhDebtCreditVo> resultList = hjhDebtCreditReponse.getResultList();
        hjhDebtCreditService.queryHjhDebtCreditListStatusName(resultList);
        // 表格sheet名称
        String sheetName = "汇计划转让记录";

        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        String[] titles = new String[] { "序号", "出让人计划编号", "出让人计划订单号", "清算后计划编号", "出让人", "债转编号", "原项目编号", "原项目收益率", "还款方式", "债权本金","债权价值", "预计实际收益率", "已转让本金", "垫付利息", /*"清算手续费率", "实际服务费",*/"在途资金", "出让人实际到账金额", "实际清算时间", "转让状态", "还款状态","项目总期数 ","清算时所在期数","当期应还款时间" };
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
                    HjhDebtCreditVo debtCredit = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 出让人计划编号
                    else if (celLength == 1) {
                        cell.setCellValue(debtCredit.getPlanNid());
                    }
                    // 出让人计划订单号
                    else if (celLength == 2) {
                        cell.setCellValue(debtCredit.getPlanOrderId());
                    }
                    // 清算后计划编号
                    else if (celLength == 3) {
                        cell.setCellValue(debtCredit.getPlanNidNew());
                    }
                    // 出让人
                    else if (celLength == 4) {
                        cell.setCellValue(debtCredit.getUserName());
                    }
                    // 债转编号
                    else if (celLength == 5) {
                        cell.setCellValue(debtCredit.getCreditNid());
                    }
                    // 原项目编号
                    else if (celLength == 6) {
                        cell.setCellValue(debtCredit.getBorrowNid());
                    }
                    // 原项目收益率
                    else if (celLength == 7) {
                        cell.setCellValue(debtCredit.getBorrowApr()+"%");
                    }
                    // 还款方式
                    else if (celLength == 8) {
                        cell.setCellValue(debtCredit.getRepayStyleName());
                    }
                    // 债权本金
                    else if (celLength == 9) {
                        cell.setCellValue(debtCredit.getCreditCapital());
                    }
                    // 债权价值
                    else if (celLength == 10) {
                        cell.setCellValue(debtCredit.getLiquidationFairValue());
                    }
                    // 预计实际收益率
                    else if (celLength == 11) {
                        cell.setCellValue(debtCredit.getActualApr()+"%");
                    }
                    // 已转让本金
                    else if (celLength == 12) {
                        cell.setCellValue(debtCredit.getAssignCapital());
                    }
                    // 垫付利息
                    else if (celLength == 13) {
                        cell.setCellValue(debtCredit.getAssignAdvanceInterest());
                    }
                    // 在途资金
                    else if (celLength == 14) {
                        cell.setCellValue(0);
                    }
                    // 出让人实际到账金额
                    else if (celLength == 15) {
                        cell.setCellValue(debtCredit.getAccountReceive());
                    }
                    // 实际清算时间
                    else if (celLength == 16) {
                        cell.setCellValue(debtCredit.getLiquidatesTime());
                    }
                    // 转让状态
                    else if (celLength == 17) {
                        cell.setCellValue(debtCredit.getCreditStatusName());
                    }
                    // 还款状态
                    else if (celLength == 18) {
                        cell.setCellValue(debtCredit.getRepayStatusName());
                    }
                    // 项目总期数
                    else if (celLength == 19) {
                        cell.setCellValue(debtCredit.getBorrowPeriod());
                    }
                    // 清算时所在期数
                    else if (celLength == 20) {
                        cell.setCellValue(debtCredit.getLiquidatesPeriod());
                    }
                    // 当期应还款时间
                    else if (celLength == 21) {
                        cell.setCellValue(debtCredit.getRepayNextTime());
                    }

                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
        return this.success();
    }


}
