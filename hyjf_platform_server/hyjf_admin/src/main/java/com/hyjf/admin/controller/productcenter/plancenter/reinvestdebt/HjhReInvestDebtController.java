package com.hyjf.admin.controller.productcenter.plancenter.reinvestdebt;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.HjhReInvestDebtService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhReInvestDebtResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import com.hyjf.am.vo.trade.hjh.HjhReInvestDebtVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 复投承接债权
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划",tags ="产品中心-汇计划-资金计划-复投承接债权")
@RestController
@RequestMapping(value = "/hjhReInvestDebt")
public class HjhReInvestDebtController extends BaseController {

    @Autowired
    private HjhReInvestDebtService hjhReInvestDebtService;


    /**
     * 产品中心  -  汇计划 --  复投承接债权 -- 检索下拉框
     * @return
     */
    @ApiOperation(value = "复投承接债权检索下拉框", notes = "复投承接债权检索下拉框")
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    public JSONObject dropDownBox(){
        JSONObject jsonObject = new JSONObject();

        // 初始化承接方式List
        List<Object> undertakingMethodList = new ArrayList<>();

        // 初始化投资方式Map
        Map<String, Object> undertakingMethodMap = new HashedMap();
        Map<String, Object> undertakingMethodMap1 = new HashedMap();

        undertakingMethodMap.put("key", 0);
        undertakingMethodMap.put("value", "自动承接");
        undertakingMethodMap1.put("key", 1);
        undertakingMethodMap1.put("key", "手动承接");

        undertakingMethodList.add(undertakingMethodMap);
        undertakingMethodList.add(undertakingMethodMap1);

        // 初始化还款方式List
        List<Object> repaymentList = new ArrayList<>();

        // 初始化还款方式Map
        Map<String, Object> repaymentMap = new HashedMap();
        Map<String, Object> repaymentMap1 = new HashedMap();
        Map<String, Object> repaymentMap2 = new HashedMap();
        Map<String, Object> repaymentMap3 = new HashedMap();
        Map<String, Object> repaymentMap4 = new HashedMap();

        repaymentMap.put("key", "按天计息，到期还本还息");
        repaymentMap.put("value", "按天计息，到期还本还息");
        repaymentMap1.put("key", "按月计息，到期还本还息");
        repaymentMap1.put("value", "按月计息，到期还本还息");
        repaymentMap2.put("key", "等额本息");
        repaymentMap2.put("value", "等额本息");
        repaymentMap3.put("key", "等额本金");
        repaymentMap3.put("value", "等额本金");
        repaymentMap4.put("key", "先息后本");
        repaymentMap4.put("value", "先息后本");

        repaymentList.add(repaymentMap);
        repaymentList.add(repaymentMap1);
        repaymentList.add(repaymentMap2);
        repaymentList.add(repaymentMap3);
        repaymentList.add(repaymentMap4);

        // Map 集
        Map<String, Object> allMap = new HashedMap();
        allMap.put("undertakingMethodList", undertakingMethodList);
        allMap.put("repaymentList", repaymentList);

        jsonObject.put("status", BaseResult.SUCCESS);
        jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
        jsonObject.put("data", allMap);

        return jsonObject;
    }

    /**
     * 资金计划 - 复投债权列表
     * @param request
     * @return
     */
    @ApiOperation(value = "资金计划", notes = "复投债权列表")
    @PostMapping(value = "/hjhReInvestDebtList")
    public AdminResult<ListResult<HjhReInvestDebtVO>> hjhReInvestDebtList(@RequestBody HjhReInvestDebtRequest request) {

        if (StringUtils.isEmpty(request.getDate())) {
            return new AdminResult<>(FAIL, "Date不能为空!");
        }

        if (StringUtils.isEmpty(request.getPlanNid())) {
            return new AdminResult<>(FAIL, "PlanNid不能为空!");
        }

        // 初始化返回List
        List<HjhReInvestDebtVO> returnList = null;

        HjhReInvestDebtResponse response = hjhReInvestDebtService.hjhReInvestDebtList(request);

        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }

        if (CollectionUtils.isNotEmpty(response.getResultList())) {
            returnList = CommonUtils.convertBeanList(response.getResultList(), HjhReInvestDebtVO.class);
            return new AdminResult<ListResult<HjhReInvestDebtVO>>(ListResult.build(returnList, response.getCount()));
        } else {
            return new AdminResult<ListResult<HjhReInvestDebtVO>>(ListResult.build(returnList, 0));
        }
    }

    /**
     * 导出资金明细列表
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "复投承接债权导出", notes = "复投承接债权列表导出")
    @PostMapping(value = "/exportAction")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, HjhReInvestDebtRequest requestBean) throws Exception {
        // 表格sheet名称
        String sheetName = "资金明细";

        // currPage<0 为全部,currPage>0 为具体某一页
        requestBean.setCurrPage(-1);

//        HjhReInvestDebtCustomize hjhReInvestDebtCustomize = new HjhReInvestDebtCustomize();
//        hjhReInvestDebtCustomize.setAssignPlanOrderIdSrch(requestBean.getAssignPlanOrderIdSrch());
//        hjhReInvestDebtCustomize.setAssignPlanNidSrch(requestBean.getAssignPlanNidSrch());
//        hjhReInvestDebtCustomize.setAssignOrderIdSrch(requestBean.getAssignOrderIdSrch());
//        hjhReInvestDebtCustomize.setUserNameSrch(requestBean.getUserNameSrch());
//        hjhReInvestDebtCustomize.setCreditUserNameSrch(requestBean.getCreditUserNameSrch());
//        hjhReInvestDebtCustomize.setCreditNidSrch(requestBean.getCreditNidSrch());
//        hjhReInvestDebtCustomize.setBorrowNidSrch(requestBean.getBorrowNidSrch());
//        hjhReInvestDebtCustomize.setAssignTypeSrch(requestBean.getAssignTypeSrch());
//        hjhReInvestDebtCustomize.setBorrowStyleSrch(requestBean.getBorrowStyleSrch());
//        hjhReInvestDebtCustomize.setPlanNid(requestBean.getPlanNid());
//        hjhReInvestDebtCustomize.setDate(requestBean.getDate());
        // 取得数据
        List<HjhReInvestDebtVO> returnList = null;
        HjhReInvestDebtResponse reInvestDebtResponse = this.hjhReInvestDebtService.hjhReInvestDebtList(requestBean);

        if (reInvestDebtResponse.getCount() > 0){
            returnList = CommonUtils.convertBeanList(reInvestDebtResponse.getResultList(), HjhReInvestDebtVO.class);
        }

        String fileName = sheetName + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";

        String[] titles = new String[] { "序号","计划订单号","承接计划编号","承接订单号","承接人","出让人","债权编号","原项目编号","还款方式","承接本金","垫付利息","实际支付金额","承接方式","项目总期数","承接时所在期数","承接时间" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (returnList != null && returnList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < returnList.size(); i++) {
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

                    HjhReInvestDebtVO hjhReInvestDebt = returnList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 计划订单号
                    else if (celLength == 1) {
                        cell.setCellValue(hjhReInvestDebt.getAssignPlanOrderId());
                    }
                    // 承接计划编号
                    else if (celLength == 2) {
                        cell.setCellValue(hjhReInvestDebt.getAssignPlanNid());
                    }
                    // 承接订单号
                    else if (celLength == 3) {
                        cell.setCellValue(hjhReInvestDebt.getAssignOrderId());
                    }
                    // 承接人
                    else if (celLength == 4) {
                        cell.setCellValue(hjhReInvestDebt.getUserName());
                    }
                    // 出让人
                    else if (celLength == 5) {
                        cell.setCellValue(hjhReInvestDebt.getCreditUserName());
                    }
                    // 债权编号
                    else if (celLength == 6) {
                        cell.setCellValue(hjhReInvestDebt.getCreditNid());
                    }
                    // 原项目编号
                    else if (celLength == 7) {
                        cell.setCellValue(hjhReInvestDebt.getBorrowNid());
                    }
                    // 还款方式
                    else if (celLength == 8) {
                        cell.setCellValue(hjhReInvestDebt.getBorrowStyle());
                    }
                    // 承接本金
                    else if (celLength == 9) {
                        cell.setCellValue(hjhReInvestDebt.getAssignCapital());
                    }
                    // 垫付利息
                    else if (celLength == 10) {
                        cell.setCellValue(hjhReInvestDebt.getAssignInterestAdvance());
                    }
                    // 实际支付金额
                    else if (celLength == 11) {
                        cell.setCellValue(hjhReInvestDebt.getAssignPay());
                    }
                    // 承接方式
                    else if (celLength == 12) {
                        cell.setCellValue(hjhReInvestDebt.getAssignType());
                    }
                    // 项目总期数
                    else if (celLength == 13) {
                        cell.setCellValue(hjhReInvestDebt.getBorrowPeriod());
                    }
                    // 承接时所在期数
                    else if (celLength == 14) {
                        cell.setCellValue(hjhReInvestDebt.getAssignPeriod());
                    }
                    // 承接时间
                    else if (celLength == 15) {
                        cell.setCellValue(hjhReInvestDebt.getAssignTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);

    }

}
