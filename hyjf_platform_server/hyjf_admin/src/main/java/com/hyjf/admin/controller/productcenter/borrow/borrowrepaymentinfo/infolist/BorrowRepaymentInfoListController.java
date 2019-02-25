package com.hyjf.admin.controller.productcenter.borrow.borrowrepaymentinfo.infolist;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.BorrowRepaymentInfoListBean;
import com.hyjf.admin.beans.request.BorrowRepaymentInfoListRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowRepaymentInfoListService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoListRequset;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoListController, v0.1 2018/7/10 9:30
 */

@Api(value = "产品中心-汇直投-还款明细列表",tags ="产品中心-汇直投-还款明细列表")
@RestController
@RequestMapping("/hyjf-admin/borrow/borrowrepaymentinfo/infolist")
public class BorrowRepaymentInfoListController {
    @Autowired
    private BorrowRepaymentInfoListService borrowRepaymentInfoListService;

    @Autowired
    SystemConfig systemConfig;
    /**
     * 画面初始化
     *
     * @param request
     */
    @ApiOperation(value = "还款明细列表", notes = "还款明细列表页面查询初始化")
    @PostMapping(value = "/searchAction")
    public AdminResult<BorrowRepaymentInfoListBean> searchAction(HttpServletRequest request, @RequestBody @Valid BorrowRepaymentInfoListRequestBean form) {
        BorrowRepaymentInfoListRequset copyForm=new BorrowRepaymentInfoListRequset();
        BeanUtils.copyProperties(form, copyForm);
        BorrowRepaymentInfoListBean bean = borrowRepaymentInfoListService.selectBorrowRepaymentInfoListList(copyForm);
        List<HjhInstConfigVO> hjhInstConfigList = this.borrowRepaymentInfoListService.selectHjhInstConfigByInstCode("-1");
        // 资金来源
        bean.setHjhInstConfigList(ConvertUtils.convertListToDropDown(hjhInstConfigList,"instCode","instName"));
        AdminResult<BorrowRepaymentInfoListBean> result=new AdminResult<BorrowRepaymentInfoListBean> ();
        result.setData(bean);
        return result;
    }


    /**
     * @Description 数据导出--还款计划
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
//    @ApiOperation(value = "数据导出--还款详情导出数据", notes = "带条件导出EXCEL")
//    @PostMapping(value = "/exportAction")
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowRepaymentInfoListRequestBean form) throws Exception {
        BorrowRepaymentInfoListRequset copyForm=new BorrowRepaymentInfoListRequset();
        BeanUtils.copyProperties(form, copyForm);

        // 表格sheet名称
        String sheetName = "还款详情导出数据";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 查询
        List<BorrowRepaymentInfoListCustomizeVO> resultList = this.borrowRepaymentInfoListService.selectExportBorrowRepaymentInfoListList(copyForm);
        // 列头
        String[] titles = new String[] { "项目编号", "资产来源", "借款人ID","借款人用户名", "借款标题", "项目类型", "借款期限",
                "出借利率", "借款金额", "借到金额", "还款方式", "还款期数", "出借人用户名", "出借人ID", "出借金额", "应还本金",
                "应还利息", "应还本息", "还款服务费", "提前天数", "少还利息", "延期天数", "延期利息", "逾期天数", "逾期利息",
                "应还总额", "还款订单号", "实还总额", "还款状态", "实际还款日期", "应还日期" };
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if ( resultList!= null && resultList.size() > 0) {

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
                    BorrowRepaymentInfoListCustomizeVO record = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 项目编号
                    if (celLength == 0) {
                        cell.setCellValue(record.getBorrowNid());
                    }
                    // 资产来源
                    if (celLength == 1) {
                        cell.setCellValue(record.getInstName());
                    }
                    // 借款人ID
                    else if (celLength == 2) {
                        cell.setCellValue(record.getUserId());
                    }
                    // 借款人用户名
                    else if (celLength == 3) {
                        cell.setCellValue(record.getBorrowUserName());
                    }
                    // 借款标题
                    else if (celLength == 4) {
                        cell.setCellValue(record.getBorrowName());
                    }
                    // 项目类型
                    else if (celLength == 5) {
                        cell.setCellValue(record.getProjectTypeName());
                    }
                    // 借款期限
                    else if (celLength == 6) {
                        cell.setCellValue(record.getBorrowPeriod() + "个月");
                    }
                    // 出借利率
                    else if (celLength == 7) {
                        cell.setCellValue(record.getBorrowApr() + "%");
                    }
                    // 借款金额
                    else if (celLength == 8) {
                        cell.setCellValue("".equals(record.getBorrowAccount()) ? 0 : Double.valueOf(record.getBorrowAccount()));
                    }
                    // 借到金额
                    else if (celLength == 9) {
                        cell.setCellValue("".equals(record.getBorrowAccountYes()) ? 0 : Double.valueOf(record.getBorrowAccountYes()));
                    }
                    // 还款方式
                    else if (celLength == 10) {
                        cell.setCellValue(record.getRepayType());
                    }
                    // 还款期数
                    else if (celLength == 11) {
                        cell.setCellValue("第" + record.getRecoverPeriod() + "期");
                    }
                    // 出借人用户名
                    else if (celLength == 12) {
                        cell.setCellValue(record.getRecoverUserName());
                    }
                    // 出借人ID
                    else if (celLength == 13) {
                        cell.setCellValue(record.getRecoverUserId());
                    }
                    // 出借金额
                    else if (celLength == 14) {
                        cell.setCellValue("".equals(record.getRecoverTotal()) ? 0 : Double.valueOf(record.getRecoverTotal()));
                    }
                    // 应还本金
                    else if (celLength == 15) {
                        cell.setCellValue("".equals(record.getRecoverCapital()) ? 0 : Double.valueOf(record.getRecoverCapital()));
                    }
                    // 应还利息
                    else if (celLength == 16) {
                        cell.setCellValue("".equals(record.getRecoverInterest()) ? 0 : Double.valueOf(record.getRecoverInterest()));
                    }
                    // 应还本息
                    else if (celLength == 17) {
                        cell.setCellValue("".equals(record.getRecoverAccount()) ? 0 : Double.valueOf(record.getRecoverAccount()));
                    }
                    // 管理费
                    else if (celLength == 18) {
                        cell.setCellValue("".equals(record.getRecoverFee()) ? 0 : Double.valueOf(record.getRecoverFee()));
                    }
                    // 提前天数
                    else if (celLength == 19) {
                        cell.setCellValue(record.getChargeDays());
                    }
                    // 少还利息
                    else if (celLength == 20) {
                        cell.setCellValue(StringUtils.isNotBlank(record.getChargeInterest()) ? record.getChargeInterest() : "0");
                    }
                    // 延期天数
                    else if (celLength == 21) {
                        cell.setCellValue(record.getDelayDays());
                    }
                    // 延期利息
                    else if (celLength == 22) {
                        cell.setCellValue(StringUtils.isNotBlank(record.getDelayInterest()) ? record.getDelayInterest() : "0");
                    }
                    // 逾期天数
                    else if (celLength == 23) {
                        cell.setCellValue(record.getLateDays());
                    }
                    // 逾期利息
                    else if (celLength == 24) {
                        cell.setCellValue(StringUtils.isNotBlank(record.getLateInterest()) ? record.getLateInterest() : "0");
                    }
                    // 应还总额
                    else if (celLength == 25) {
                        cell.setCellValue(StringUtils.isNotBlank(record.getRecoverAccount()) ? record.getRecoverAccount() : "0");
                    }
                    // 还款订单号
                    else if (celLength == 26) {
                        cell.setCellValue(record.getNid());
                    }
                    // 实还总额
                    else if (celLength == 27) {
                        cell.setCellValue(StringUtils.isNotBlank(record.getRecoverAccountYes()) ? record.getRecoverAccountYes() : "0");
                    }
                    // 还款状态
                    else if (celLength == 28) {
                        cell.setCellValue(record.getRepayType());
                    }
                    // 实际还款日期
                    else if (celLength == 29) {
                        cell.setCellValue(record.getRecoverActionTime());
                    }
                    // 应还日期
                    else if (celLength == 30) {
                        cell.setCellValue(record.getRecoverLastTime());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

    /**
     * @Description 数据导出--还款计划
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "数据导出--还款详情导出数据", notes = "带条件导出EXCEL")
    @PostMapping(value = "/exportAction")
    public void exportToExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowRepaymentInfoListRequestBean form) throws Exception {

        BorrowRepaymentInfoListRequset copyForm=new BorrowRepaymentInfoListRequset();
        BeanUtils.copyProperties(form, copyForm);

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "还款详情导出数据";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        copyForm.setPageSize(defaultRowMaxCount);
        copyForm.setCurrPage(1);
        // 查询
        List<BorrowRepaymentInfoListCustomizeVO> resultList = this.borrowRepaymentInfoListService.selectExportBorrowRepaymentInfoListList(copyForm);
        Integer totalCount = resultList.size();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList);
        }
        for (int i = 1; i < sheetCount; i++) {

            copyForm.setPageSize(defaultRowMaxCount);
            copyForm.setCurrPage(i+1);
            List<BorrowRepaymentInfoListCustomizeVO> resultList2 = this.borrowRepaymentInfoListService.selectExportBorrowRepaymentInfoListList(copyForm);
            if (resultList2 != null && resultList2.size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultList2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("borrowNid","项目编号");
        map.put("instName","资产来源");
        map.put("userId","借款人ID");
        map.put("borrowUserName","借款人用户名");
        map.put("borrowName","借款标题");
        map.put("projectTypeName","项目类型");
        map.put("borrowPeriod","借款期限");
        map.put("borrowApr","出借利率");
        map.put("borrowAccount","借款金额");
        map.put("borrowAccountYes","借到金额");
        map.put("repayType","还款方式");
        map.put("recoverPeriod","还款期数");
        map.put("recoverUserName","出借人用户名");
        map.put("recoverUserId","出借人ID");
        map.put("recoverTotal","出借金额");
        map.put("recoverCapital","应还本金");
        map.put("recoverInterest","应还利息");
        map.put("recoverAccountCopy","应还本息");
        map.put("recoverFee","还款服务费");
        map.put("chargeDays","提前天数");
        map.put("chargeInterest","少还利息");
        map.put("delayDays","延期天数");
        map.put("delayInterest","延期利息");
        map.put("lateDays","逾期天数");
        map.put("lateInterest","逾期利息");
        map.put("recoverAccount","应还总额");
        map.put("nid","还款订单号");
        map.put("recoverAccountYes","实还总额");
        map.put("status","还款状态");
        map.put("recoverActionTime","实际还款日期");
        map.put("recoverLastTime","应还日期");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter borrowPeriodAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return object+"";
            }
        };
        IValueFormatter borrowAprAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return object + "%";
            }
        };
        IValueFormatter valueFormatAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return object==null||"".equals(object) ? "0" : object.toString();
            }
        };
        IValueFormatter recoverPeriodAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return "第" + object + "期";
            }
        };
        IValueFormatter statusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                if (null!=object) {
                    return"0".equals(object) ? "还款中" : "已还款";
                }
                return null;
            }
        };

        mapAdapter.put("borrowNid",valueFormatAdapter);
        mapAdapter.put("instName",valueFormatAdapter);
        mapAdapter.put("userId",valueFormatAdapter);
        mapAdapter.put("borrowUserName",valueFormatAdapter);
        mapAdapter.put("borrowName",borrowPeriodAdapter);
        mapAdapter.put("projectTypeName",valueFormatAdapter);
        mapAdapter.put("borrowPeriod",valueFormatAdapter);
        mapAdapter.put("borrowApr",borrowAprAdapter);
        mapAdapter.put("borrowAccount", valueFormatAdapter);
        mapAdapter.put("borrowAccountYes", valueFormatAdapter);
        mapAdapter.put("repayType",valueFormatAdapter);
        mapAdapter.put("recoverPeriod", recoverPeriodAdapter);
        mapAdapter.put("recoverUserName",valueFormatAdapter);
        mapAdapter.put("recoverUserId",valueFormatAdapter);
        mapAdapter.put("recoverTotal", valueFormatAdapter);
        mapAdapter.put("recoverCapital", valueFormatAdapter);
        mapAdapter.put("recoverInterest", valueFormatAdapter);
        mapAdapter.put("recoverAccountCopy", valueFormatAdapter);
        mapAdapter.put("recoverFee", valueFormatAdapter);
        mapAdapter.put("chargeDays",valueFormatAdapter);
        mapAdapter.put("chargeInterest", valueFormatAdapter);
        mapAdapter.put("delayDays",valueFormatAdapter);
        mapAdapter.put("delayInterest", valueFormatAdapter);
        mapAdapter.put("lateDays",valueFormatAdapter);
        mapAdapter.put("lateInterest", valueFormatAdapter);
        mapAdapter.put("recoverAccount", valueFormatAdapter);
        mapAdapter.put("nid",valueFormatAdapter);
        mapAdapter.put("recoverAccountYes", valueFormatAdapter);
        mapAdapter.put("status", statusAdapter);
        mapAdapter.put("recoverActionTime",valueFormatAdapter);
        mapAdapter.put("recoverLastTime",valueFormatAdapter);


        return mapAdapter;
    }
}
