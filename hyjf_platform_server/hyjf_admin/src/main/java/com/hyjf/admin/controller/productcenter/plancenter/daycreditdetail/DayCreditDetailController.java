package com.hyjf.admin.controller.productcenter.plancenter.daycreditdetail;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.DayCreditDetailRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.BaseResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.DayCreditDetailService;
import com.hyjf.admin.utils.ExportExcel;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DayCreditDetailResponse;
import com.hyjf.am.resquest.admin.DayCreditDetailRequest;
import com.hyjf.am.vo.trade.hjh.DayCreditDetailVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 转让详情
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划-转让详情",tags ="产品中心-汇计划-资金计划-转让详情")
@RestController
@RequestMapping(value = "/hyjf-admin/dayCreditDetail")
public class DayCreditDetailController extends BaseController {

    @Autowired
    private DayCreditDetailService dayCreditDetailService;

    // 权限名称 (子页面.未添加为菜单,无需设置权限)
    public static final String PERMISSIONS = "hjhdebtcredit";

    /**
     * 产品中心  -  汇计划 --  转让详情 -- 检索下拉框
     * @return
     */
    @ApiOperation(value = "转让详情检索下拉框", notes = "转让详情权检索下拉框")
    @RequestMapping(value = "/dropDownBox", method = RequestMethod.GET)
    public JSONObject dropDownBox(){
        JSONObject jsonObject = new JSONObject();

        // 转让状态 List
        List<Object> transferStatusList = new ArrayList<>();

        Map<String, Object> transferStatusMap = new HashedMap();
        Map<String, Object> transferStatusMap1 = new HashedMap();
        Map<String, Object> transferStatusMap2 = new HashedMap();
        Map<String, Object> transferStatusMap3 = new HashedMap();

        transferStatusMap.put("key", 0);
        transferStatusMap.put("value", "承接中");
        transferStatusMap1.put("key", 1);
        transferStatusMap1.put("value", "部分承接");
        transferStatusMap2.put("key", 2);
        transferStatusMap2.put("value", "完全承接");
        transferStatusMap3.put("key", 3);
        transferStatusMap3.put("value", "承接终止");

        transferStatusList.add(transferStatusMap);
        transferStatusList.add(transferStatusMap1);
        transferStatusList.add(transferStatusMap2);
        transferStatusList.add(transferStatusMap3);

        // 还款方式 List
        List<Object> repaymentList = new ArrayList<>();

        // 还款方式Map
        Map<String, Object> repaymentMap = new HashedMap();
        Map<String, Object> repaymentMap1 = new HashedMap();
        Map<String, Object> repaymentMap2 = new HashedMap();
        Map<String, Object> repaymentMap3 = new HashedMap();

        repaymentMap.put("key", "month");
        repaymentMap.put("value", "等额本息");
        repaymentMap1.put("key", "end");
        repaymentMap1.put("value", "按月计息，到期还本还息");
        repaymentMap2.put("key", "endmonth");
        repaymentMap2.put("value", "先息后本");
        repaymentMap3.put("key", "endday");
        repaymentMap3.put("value", "按天计息，到期还本息");

        repaymentList.add(repaymentMap);
        repaymentList.add(repaymentMap1);
        repaymentList.add(repaymentMap2);
        repaymentList.add(repaymentMap3);

        // Map 集
        Map<String, Object> allMap = new HashedMap();
        allMap.put("transferStatusList", transferStatusList);
        allMap.put("repaymentList", repaymentList);

        jsonObject.put("status", BaseResult.SUCCESS);
        jsonObject.put("statusDesc", BaseResult.SUCCESS_DESC);
        jsonObject.put("data", allMap);

        return jsonObject;
    }

    /**
     * 资金计划 - 汇计划按天转让记录列表
     * @param request
     * @return
     */
    @ApiOperation(value = "资金计划", notes = "汇计划按天转让记录列表")
    @PostMapping(value = "/hjhDayCreditDetailList")
    public AdminResult<ListResult<DayCreditDetailVO>> init(@RequestBody DayCreditDetailRequestBean request){
        logger.info("汇计划按天转让记录列表请求开始....");
        DayCreditDetailRequest copyRequest = new DayCreditDetailRequest();
        BeanUtils.copyProperties(request, copyRequest);

        //默认应传入清算日期和转让人planNid
        if (StringUtils.isEmpty(request.getDate())){
            return new AdminResult<>(FAIL, "清算日期不能为空!");
        }
        if (StringUtils.isEmpty(request.getPlanNid())){
            return new AdminResult<>(FAIL, "转让人PlanNid不能为空!");
        }

        //初始化返回List
        List<DayCreditDetailVO> returnList = new ArrayList<>();

        //类表查询
        DayCreditDetailResponse response = this.dayCreditDetailService.hjhDayCreditDetailList(copyRequest);

        if (response == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(response)){
            return new AdminResult<>(FAIL, response.getMessage());
        }

        logger.info("汇计划按天转让记录列表请求结束....");
        if (CollectionUtils.isNotEmpty(response.getResultList())){
            returnList = CommonUtils.convertBeanList(response.getResultList(), DayCreditDetailVO.class);
            return new AdminResult<ListResult<DayCreditDetailVO>>(ListResult.build2(returnList, response.getCount(), response.getSumDayCreditDetailVO()));
        }else {
            return new AdminResult<ListResult<DayCreditDetailVO>>(ListResult.build(returnList, 0));
        }
    }

    /**
     * 资金计划转让记录 - 按日转让导出
     * @param request
     * @param response
     * @param planCreditBean
     * @throws Exception
     */
    @ApiOperation(value = "资金计划 - 转让详情导出", notes = "资金计划转让详情列表导出")
    @PostMapping(value = "/exportExcel")
    public void exportAction(HttpServletRequest request, HttpServletResponse response, DayCreditDetailRequestBean planCreditBean) throws Exception {

        // 表格sheet名称
        String sheetName = "智投服务按日转让记录";

        DayCreditDetailRequest copyRequest = new DayCreditDetailRequest();
        BeanUtils.copyProperties(planCreditBean, copyRequest);

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());

        //请求第一页5000条
        copyRequest.setPageSize(defaultRowMaxCount);
        copyRequest.setCurrPage(1);
        //初始化返回List
        List<DayCreditDetailVO> resultList = new ArrayList<>();

        DayCreditDetailResponse responseList = this.dayCreditDetailService.hjhDayCreditDetailList(copyRequest);
        if (CollectionUtils.isNotEmpty(responseList.getResultList())){
            resultList = CommonUtils.convertBeanList(responseList.getResultList(), DayCreditDetailVO.class);
        }
        String fileName = URLEncoder.encode(sheetName,CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        String[] titles = new String[] { "序号", "出让人智投编号", "出让人智投订单号", "清算后智投编号", "出让人", "债转编号", "原项目编号", "还款方式", "债权本金（元）", "债权价值（元）", "已转让本金（元）", "垫付利息（元）", "转让状态", "项目期数", "实际清算时间", "债转结束时间"};
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
                    DayCreditDetailVO data = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    } else if (celLength == 1) {// 出让人计划编号
                        cell.setCellValue(data.getPlanNid());
                    } else if (celLength == 2) {// 出让人计划订单号
                        cell.setCellValue(data.getPlanOrderId());
                    } else if (celLength == 3) {// 清算后计划编号
                        cell.setCellValue(data.getPlanNidNew());
                    } else if (celLength == 4) {// 出让人
                        cell.setCellValue(data.getUserName());
                    } else if (celLength == 5) {// 债转编号
                        cell.setCellValue(data.getCreditNid());
                    } else if (celLength == 6) {// 原项目编号
                        cell.setCellValue(data.getBorrowNid());
                    } else if (celLength == 7) {// 还款方式
                        cell.setCellValue(data.getRepayStyleName());
                    } else if (celLength == 8) {// 债权本金（元）
                        cell.setCellValue(data.getCreditCapital().toString());
                    } else if (celLength == 9) {// 债权价值（元）
                        cell.setCellValue(data.getLiquidationFairValue().toString());
                    } else if (celLength == 10) {// 已转让本金（元）
                        cell.setCellValue(data.getAssignCapital().toString());
                    } else if (celLength == 11) {// 垫付利息（元）
                        cell.setCellValue(data.getAssignAdvanceInterest().toString());
                    } else if (celLength == 12) {// 转让状态
                        cell.setCellValue(data.getCreditStatusName());
                    } else if (celLength == 13) {// 项目总期数
                        cell.setCellValue(data.getLiquidatesPeriod()+"/"+data.getBorrowPeriod());
                    } /*else if (celLength == 14) {// 清算时所在期数
						cell.setCellValue(data.getLiquidatesPeriod());
					}*/ else if (celLength == 14) {// 实际清算时间
                        cell.setCellValue(data.getLiquidatesTime());
                    } else if (celLength == 15) {// 债转结束时间
                        cell.setCellValue(data.getEndTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
