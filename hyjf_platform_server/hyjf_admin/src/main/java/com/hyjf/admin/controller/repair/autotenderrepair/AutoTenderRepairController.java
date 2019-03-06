/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.repair.autotenderrepair;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.AutoTenderExceptionRequestBean;
import com.hyjf.admin.beans.request.BorrowInvestRequestBean;
import com.hyjf.admin.beans.request.TenderExceptionSolveRequestBean;
import com.hyjf.admin.beans.response.BorrowInvestResponseBean;
import com.hyjf.admin.beans.vo.AdminBorrowInvestCustomizeVO;
import com.hyjf.admin.beans.vo.AdminPlanAccedeListVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AutoTenderExceptionService;
import com.hyjf.admin.service.BorrowInvestService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AutoTenderExceptionResponse;
import com.hyjf.am.resquest.admin.AssetExceptionRequest;
import com.hyjf.am.resquest.admin.AutoTenderExceptionRequest;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.vo.admin.AdminPlanAccedeListCustomizeVO;
import com.hyjf.am.vo.admin.AssetExceptionCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
 * @author nixiaoling
 * @version AutoTenderExceptionController, v0.1 2018/7/12 10:27
 */
@Api(value = "异常中心-汇计划出借异常",tags = "异常中心-汇计划出借异常")
@RestController
@RequestMapping("/hyjf-admin/autotenderrepair")
public class AutoTenderRepairController extends BaseController {

    @Autowired
    private AutoTenderExceptionService autoTenderExceptionService;
    @Autowired
    private BorrowInvestService borrowInvestService;

    private static final String PERMISSIONS = "accedelist";


    @ApiOperation(value = "汇计划出借异常列表显示", notes = "汇计划出借异常列表显示")
    @PostMapping(value = "/selectAccedeRecordList")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<AdminPlanAccedeListVO>> selectAccedeRecordList(@RequestBody AutoTenderExceptionRequestBean autoTenderExceptionRequestBean) {
        AutoTenderExceptionRequest autoTenderExceptionRequest = new AutoTenderExceptionRequest();
        BeanUtils.copyProperties(autoTenderExceptionRequestBean, autoTenderExceptionRequest);
        AutoTenderExceptionResponse autoTenderExceptionResponse = autoTenderExceptionService.selectAccedeRecordList(autoTenderExceptionRequest);
        if (autoTenderExceptionResponse == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(autoTenderExceptionResponse)) {
            return new AdminResult<>(FAIL, autoTenderExceptionResponse.getMessage());

        }
        List<AdminPlanAccedeListVO> listVOList = new ArrayList<AdminPlanAccedeListVO>();
        List<AdminPlanAccedeListCustomizeVO> listCustomizeVOS  =autoTenderExceptionResponse.getResultList();
        if(null!=listCustomizeVOS&&listCustomizeVOS.size()>0){
            listVOList = CommonUtils.convertBeanList(listCustomizeVOS,AdminPlanAccedeListVO.class);
        }
        return new AdminResult<ListResult<AdminPlanAccedeListVO>>(ListResult.build(listVOList, autoTenderExceptionResponse.getCount()));
    }

    /**
     * 出借明细画面初始化
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "出借明细列表显示", notes = "出借明细列表显示")
    @PostMapping(value = "/tenderInfoAction")
    @ResponseBody
    public AdminResult<ListResult<AdminBorrowInvestCustomizeVO>> tenderInfoAction(HttpServletRequest request, @RequestBody BorrowInvestRequestBean borrowInvestRequestBean) {
        BorrowInvestRequest borrowInvestRequest = new BorrowInvestRequest();
        BeanUtils.copyProperties(borrowInvestRequestBean, borrowInvestRequest);
        BorrowInvestResponseBean responseBean = borrowInvestService.getBorrowInvestList(borrowInvestRequest);
        if (responseBean == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        List<AdminBorrowInvestCustomizeVO> adminBorrowInvestCustomizeVOList = responseBean.getRecordList();
        return new AdminResult<ListResult<AdminBorrowInvestCustomizeVO>>(ListResult.build(adminBorrowInvestCustomizeVOList, responseBean.getTotal()));
    }

    /**
     * 汇计划加入明细异常处理
     * @return
     */
    @ApiOperation(value = "异常处理", notes = "异常处理")
    @PostMapping(value = "/tenderRepairAction")
    @ResponseBody
    public AdminResult tenderRepairAction(@RequestBody TenderExceptionSolveRequestBean tenderExceptionSolveRequestBean){
        String returnMsg = autoTenderExceptionService.tenderExceptionAction(tenderExceptionSolveRequestBean);
        if(StringUtils.isNotBlank(returnMsg)){
            AdminResult adminResult = new AdminResult<>(FAIL, returnMsg);
            return adminResult;
        }
        return new AdminResult<>();
    }


    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody AutoTenderExceptionRequestBean autoTenderExceptionRequestBean) throws Exception {
        AutoTenderExceptionRequest autoTenderExceptionRequest = new AutoTenderExceptionRequest();
        BeanUtils.copyProperties(autoTenderExceptionRequestBean, autoTenderExceptionRequest);
        autoTenderExceptionRequest.setLimitFlg(true);
        // 表格sheet名称
        String sheetName = "加入明细";
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        AutoTenderExceptionResponse autoTenderExceptionResponse = autoTenderExceptionService.selectAccedeRecordList(autoTenderExceptionRequest);
        List<AdminPlanAccedeListCustomizeVO> resultList  = new ArrayList<AdminPlanAccedeListCustomizeVO>();
        if(null!=autoTenderExceptionResponse){
            resultList = autoTenderExceptionResponse.getResultList();
        }
        String[] titles = new String[] { "序号","智投订单号", "智投编号","服务回报期限", "用户名", "授权服务金额",
                "已出借金额(元)","待还总额(元) ","待还本金(元) ","待还利息(元) ","操作平台","订单状态",  "开始计息时间", "授权服务时间" };
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
                    AdminPlanAccedeListCustomizeVO planAccedeDetail = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 智投订单号
                    else if (celLength == 1) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getPlanOrderId()) ? StringUtils.EMPTY : planAccedeDetail.getPlanOrderId());
                    }
                    // 智投编号
                    else if (celLength == 2) {
                        cell.setCellValue(planAccedeDetail.getDebtPlanNid());
                    }
                    // 服务回报期限
                    else if (celLength == 3) {
                        String dateUnit="";
                        if (planAccedeDetail.getIsMonth()==0){
                            dateUnit="天";
                        }else if(planAccedeDetail.getIsMonth()==1){
                            dateUnit="个月";
                        }
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getDebtLockPeriod()) ? StringUtils.EMPTY : planAccedeDetail.getDebtLockPeriod() + dateUnit);
                    }
                    // 用户名
                    else if (celLength == 4) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getUserName()) ? StringUtils.EMPTY : planAccedeDetail.getUserName());
                    }
                    // 加入金额
                    else if (celLength == 5) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getAccedeAccount()) ? StringUtils.EMPTY : planAccedeDetail.getAccedeAccount());
                    }
                    // 已出借金额
                    else if (celLength == 6) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getAlreadyInvest()) ? StringUtils.EMPTY : planAccedeDetail.getAlreadyInvest());
                    }
                    // 待收总额
                    else if (celLength == 7) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getWaitTotal()) ? StringUtils.EMPTY : planAccedeDetail.getWaitTotal());
                    }
                    // 待收本金
                    else if (celLength == 8) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getWaitCaptical()) ? StringUtils.EMPTY : planAccedeDetail.getWaitCaptical());
                    }
                    // 待还利息
                    else if (celLength == 9) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getWaitInterest()) ? StringUtils.EMPTY : planAccedeDetail.getWaitInterest());
                    }

                    // 平台
                    else if (celLength == 10) {
                        if ("0".equals(planAccedeDetail.getPlatform())) {
                            cell.setCellValue("PC");
                        } else if ("1".equals(planAccedeDetail.getPlatform())) {
                            cell.setCellValue("微官网");
                        } else if ("2".equals(planAccedeDetail.getPlatform())) {
                            cell.setCellValue("android");
                        } else if ("3".equals(planAccedeDetail.getPlatform())) {
                            cell.setCellValue("ios");
                        }
                    }
                    // 订单状态
                    else if (celLength == 11) {
                        if (0 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
                            cell.setCellValue("自动投标中");
                        } else if (2 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
                            cell.setCellValue("自动投标成功");
                        } else if (3 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
                            cell.setCellValue("锁定中");
                        } else if (5 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
                            cell.setCellValue("退出中");
                        } else if (7 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
                            cell.setCellValue("已退出");
                        } else if (99 == Integer.parseInt(planAccedeDetail.getOrderStatus())) {
                            cell.setCellValue("自动出借异常");
                        } else{
                            cell.setCellValue(planAccedeDetail.getOrderStatus());
                        }
                    }
                    // 计息时间
                    else if (celLength == 12) {
                        if (StringUtils.isNotEmpty(planAccedeDetail.getCountInterestTime())) {
                            cell.setCellValue(planAccedeDetail.getCountInterestTime());
                        }
                    }
                    // 加入时间
                    else if (celLength == 13) {
                        if (StringUtils.isNotEmpty(planAccedeDetail.getCreateTime())) {
                            cell.setCellValue(planAccedeDetail.getCreateTime());
                        }
                    }
                    // 预期年化
                    else if (celLength == 14) {
                        cell.setCellValue(StringUtils.isEmpty(planAccedeDetail.getExpectApr()) ? StringUtils.EMPTY : planAccedeDetail.getExpectApr() + "%");
                    }
                    // 用户属性（当前）
                    else if (celLength == 15) {
                        if ("0".equals(planAccedeDetail.getUserAttribute())) {
                            cell.setCellValue("无主单");
                        } else if ("1".equals(planAccedeDetail.getUserAttribute())) {
                            cell.setCellValue("有主单");
                        } else if ("2".equals(planAccedeDetail.getUserAttribute())) {
                            cell.setCellValue("线下员工");
                        } else if ("3".equals(planAccedeDetail.getUserAttribute())) {
                            cell.setCellValue("线上员工");
                        }
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
    @ApiOperation(value = "汇计划出借异常记录列表导出", notes = "汇计划出借异常录列表导出")
    @PostMapping(value = "/exportregist")
	 public void exportToExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody AutoTenderExceptionRequestBean autoTenderExceptionRequestBean) throws Exception {
	        //sheet默认最大行数
	        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
	        // 表格sheet名称
	        String sheetName = "加入明细";
	        // 文件名称
	        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
	        // 声明一个工作薄
	        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
	        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
	        AutoTenderExceptionRequest autoTenderExceptionRequest = new AutoTenderExceptionRequest();
	        BeanUtils.copyProperties(autoTenderExceptionRequestBean, autoTenderExceptionRequest);
	        autoTenderExceptionRequest.setLimitFlg(true);
	        AutoTenderExceptionResponse autoTenderExceptionResponse = autoTenderExceptionService.selectAccedeRecordList(autoTenderExceptionRequest);
	        List<AdminPlanAccedeListCustomizeVO> resultList  = new ArrayList<AdminPlanAccedeListCustomizeVO>();
	        if(null!=autoTenderExceptionResponse){
	            resultList = autoTenderExceptionResponse.getResultList();
	        }
	        
	        Integer totalCount = resultList.size();

	        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
	        Map<String, String> beanPropertyColumnMap = buildMap();
	        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
	        String sheetNameTmp = sheetName + "_第1页";
	        if (totalCount == 0) {
	        	
	            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
	        }else {
	            // 当前下载数据超过一页上限
	            if(defaultRowMaxCount < resultList.size()) {
                    helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList.subList(0, defaultRowMaxCount));
                    for (int i = 1; i < sheetCount; i++) {
                        sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                        int intLongestSize = defaultRowMaxCount * (i + 1);
                        if(intLongestSize>resultList.size()){
                            intLongestSize = resultList.size();
                        }
                        helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList.subList(defaultRowMaxCount * i, intLongestSize));
                    }
                } else {
                 helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList.subList(0, resultList.size()));
             }
         }
	        
	        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
	    }
	    private Map<String, String> buildMap() {
	        Map<String, String> map = Maps.newLinkedHashMap();
	        map.put("planOrderId", "智投订单号");
	        map.put("debtPlanNid", "智投编号");
	        map.put("debtLockPeriodExcel", "服务回报期限");
	        map.put("userName", "用户名");
	        map.put("accedeAccount", "授权服务金额");
	        map.put("alreadyInvest", "已出借金额(元)");
	        /*map.put("waitTotal", "待还总额(元)");
	        map.put("waitCaptical", "待还本金(元)");
	        map.put("waitInterest", "待还利息(元)");*/
	        map.put("platform", "操作平台");
	        map.put("orderStatus", "订单状态");
	        map.put("countInterestTime", "开始计息时间");
            map.put("createTime", "授权服务时间");
            map.put("borrowNid", "项目编号");
            map.put("isLast", "是否标的的最后一笔投资/承接");
            map.put("respCode", "返回状态码");
            map.put("respDesc", "返回错误信息");
	        return map;
	    }
	    private Map<String, IValueFormatter> buildValueAdapter() {
	        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
	        IValueFormatter platformAdapter = new IValueFormatter() {
	            @Override
	            public String format(Object object) {
	            	String platform = (String) object;
	                   if ("0".equals(platform)) {
                           return "PC";
                       } else if ("1".equals(platform)) {
                    	   return"微官网";
                       } else if ("2".equals(platform)) {
                    	   return"android";
                       } else if ("3".equals(platform)) {
                    	   return"ios";
                       }else {
                    	   return "";
                       }
	             
	            }
	        };
	        IValueFormatter orderStatusAdapter = new IValueFormatter() {
	            @Override
	            public String format(Object object) {
	            	String orderStatus = (String) object;
                    if (0 == Integer.parseInt(orderStatus)) {
                        return"自动投标中";
                    } else if (2 == Integer.parseInt(orderStatus)) {
                        return"自动投标成功";
                    } else if (3 == Integer.parseInt(orderStatus)) {
                        return"锁定中";
                    } else if (5 == Integer.parseInt(orderStatus)) {
                        return"退出中";
                    } else if (7 == Integer.parseInt(orderStatus)) {
                        return"已退出";
                    } else if (99 == Integer.parseInt(orderStatus)) {
                        return"自动出借异常";
                    } else{
                        return orderStatus;
                    }
	             
	            }
	        };
            IValueFormatter isLastAdapter = new IValueFormatter() {
                @Override
                public String format(Object object) {
                    String isLast = (String) object;
                    if (0 == Integer.parseInt(isLast)) {
                        return"非最后一笔";
                    } else if (1 == Integer.parseInt(isLast)) {
                        return"最后一笔";
                    } else{
                        return isLast;
                    }

                }
            };
	        mapAdapter.put("platform", platformAdapter);
            mapAdapter.put("orderStatus", orderStatusAdapter);
            mapAdapter.put("isLast", isLastAdapter);
	        return mapAdapter;
	    }
}
