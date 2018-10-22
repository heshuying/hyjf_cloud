/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.finance.web;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.WebBean;
import com.hyjf.admin.beans.response.WebsiteResponse;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.WebsiteService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.AccountTradeVO;
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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version WebsiteController, v0.1 2018/7/6 9:43
 */
@Api(value = "资金中心-平台账户-网站收支", tags ="资金中心-平台账户-网站收支")
@RestController
@RequestMapping("/hyjf-admin/finance/web")
public class WebsiteController extends BaseController {

    @Autowired
    WebsiteService websiteService;

    @Autowired
    SystemConfig systemConfig;

    /** 查看权限 */
    public static final String PERMISSIONS = "web";

    /**
     * 网站收支 列表
     * @param
     * @param form
     * @return
     */
    @ApiOperation(value = "网站收支列表",notes = "网站收支列表")
    @PostMapping(value = "/weblist")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult init(@RequestBody WebBean form) {
        AccountWebListVO accountWebList = new AccountWebListVO();
        BeanUtils.copyProperties(form, accountWebList);
        WebsiteResponse websiteResponse = new WebsiteResponse();
        //交易类型列表
        List<AccountTradeVO> trades= this.websiteService.selectTradeTypes();
        AccountWebListResponse response = websiteService.queryAccountWebList(accountWebList);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>();
        }
        //交易金额总计
        String sumAccount = this.websiteService.selectBorrowInvestAccount(accountWebList);
        if(response == null||response.getRecordTotal()==0) {
            sumAccount = "0.00";
        }
        if (null!=response){
            websiteResponse.setTotal(response.getRecordTotal());
            websiteResponse.setAccountWebList(response.getResultList());
        }
        websiteResponse.setTradeList(trades);
        websiteResponse.setSumAccount(sumAccount);
        return new AdminResult(websiteResponse);
    }


    /**
     * 查询条件
     * @param form
     * @return
     */
    @ApiOperation(value = "网站收支-查询条件",notes = "网站收支-查询条件")
    @PostMapping(value = "/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult initWithQ(@RequestBody WebBean form) {
        AccountWebListVO accountWebList = new AccountWebListVO();
        BeanUtils.copyProperties(form, accountWebList);
        WebsiteResponse websiteResponse = new WebsiteResponse();
        //交易类型列表
        List<AccountTradeVO> trades= this.websiteService.selectTradeTypes();
        AccountWebListResponse response = websiteService.queryAccountWebList(accountWebList);
        if (!Response.isSuccess(response)) {
            return new AdminResult<>();
        }
        //交易金额总计
        String sumAccount = this.websiteService.selectBorrowInvestAccount(accountWebList);
        if(response == null||response.getRecordTotal()==0) {
            sumAccount = "0.00";
        }
        if (null!=response){
            websiteResponse.setTotal(response.getRecordTotal());
            websiteResponse.setAccountWebList(response.getResultList());
        }
        websiteResponse.setTradeList(trades);
        websiteResponse.setSumAccount(sumAccount);
        return new AdminResult(websiteResponse);
    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * 导出网站收支列表
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出网站收支列表")
    @PostMapping(value = "/exportWeblistExcel")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportWeblistExcel(HttpServletResponse response, @RequestBody WebBean form) throws Exception {
        AccountWebListVO accountWebList = new AccountWebListVO();
        BeanUtils.copyProperties(form, accountWebList);
        // 表格sheet名称
        String sheetName = "网站收支";
        // 取得数据
        accountWebList.setPageSize(-1);
        //设置默认查询时间
        if(StringUtils.isEmpty(form.getStartDate())){
            accountWebList.setStartDate(GetDate.getDate("yyyy-MM-dd"));
        }
        if(StringUtils.isEmpty(form.getEndDate())){
            accountWebList.setEndDate(GetDate.getDate("yyyy-MM-dd"));
        }
        AccountWebListResponse accountWebListResponse = websiteService.queryAccountWebList(accountWebList);
        List<AccountWebListVO> recordList = accountWebListResponse.getResultList();
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[] { "序号", "订单号", "分公司", "分部", "团队", "用户名", "姓名", "收支类型", "交易金额", "交易类型", "说明", "发生时间" };
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
                    AccountWebListVO bean = recordList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 订单号
                    else if (celLength == 1) {
                        cell.setCellValue(bean.getOrdid());
                    }
                    // 大区
                    else if (celLength == 2) {
                        cell.setCellValue(bean.getRegionName());
                    }
                    // 分公司
                    else if (celLength == 3) {
                        cell.setCellValue(bean.getBranchName());
                    }
                    // 部门
                    else if (celLength == 4) {
                        cell.setCellValue(bean.getDepartmentName());
                    }
                    // 用户名
                    else if (celLength == 5) {
                        cell.setCellValue(bean.getUsername());
                    }
                    // 姓名
                    else if (celLength == 6) {
                        cell.setCellValue(bean.getTruename());
                    }
                    // 收支类型
                    else if (celLength == 7) {
                        cell.setCellValue("1".equals(bean.getTrade()) ?"收入":"支出");
                    }
                    // 交易金额
                    else if (celLength == 8) {
                        cell.setCellValue(bean.getAmount() == null ? "0.00" : bean.getAmount().toString());
                    }
                    // 交易类型
                    else if (celLength == 9) {
                        cell.setCellValue(bean.getTradeType());
                    }
                    // 说明
                    else if (celLength == 10) {
                        cell.setCellValue(bean.getRemark());
                    }
                    // 发生时间
                    else if (celLength == 11) {
                        logger.debug("CreateTime...:"+bean.getCreateTime());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        if(bean.getCreateTime()!=null){
                            Date data = new Date(bean.getCreateTime()*1000L);
                            cell.setCellValue(sdf.format(data));
                        }else {
                            cell.setCellValue("");
                        }

                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

    @ApiOperation(value = "平台转账-导出excel",notes = "平台转账-导出excel")
    @PostMapping(value = "/platformtransferlist")
    public void exportPlatformTransferList(HttpServletRequest request, HttpServletResponse response, @RequestBody WebBean form) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "网站收支";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        AccountWebListVO accountWebList = new AccountWebListVO();
        BeanUtils.copyProperties(form, accountWebList);

        accountWebList.setCurrPage(1);
        accountWebList.setPageSize(defaultRowMaxCount);
        AccountWebListResponse accountWebListResponse = websiteService.queryAccountWebList(accountWebList);
        Integer totalCount = accountWebListResponse.getRecordTotal();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        if (totalCount == 0) {
            String sheetNameTmp = sheetName + "_第1页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            accountWebList.setPageSize(defaultRowMaxCount);
            accountWebList.setCurrPage(i);
            AccountWebListResponse webListResponse = websiteService.queryAccountWebList(accountWebList);
            List<AccountWebListVO> record = webListResponse.getResultList();
            if (record != null && record.size()> 0) {
                String sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  record);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("orderid", "订单号");
        map.put("regionName", "分公司");
        map.put("branchName", "分部");
        map.put("departmentName", "团队");
        map.put("username", "用户名");
        map.put("truename", "姓名");
        map.put("trade", "收支类型");
        map.put("amount", "交易金额");
        map.put("tradeType", "交易类型");
        map.put("remark", "说明");
        map.put("createTime", "发生时间");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter tradeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String trade = (String) object;
                return "1".equals(trade) ?"收入":"支出";
            }
        };
        IValueFormatter amountAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Double amount = (Double) object;
                return amount == null ? "0.00" : amount.toString();
            }
        };
        IValueFormatter createTimeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer createTime = (Integer) object;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if(createTime != null){
                    Date data = new Date(createTime*1000L);
                    return sdf.format(data);
                }else {
                    return "";
                }
            }
        };
        mapAdapter.put("trade", tradeAdapter);
        mapAdapter.put("amount", amountAdapter);
        mapAdapter.put("createTime", createTimeAdapter);
        return mapAdapter;
    }


    /**
     * 余额查询
     * @param
     * @return
     */
    @ApiOperation(value = "余额查询",notes = "余额查询")
    @PostMapping(value = "/yueSearchAction")
    public AdminResult yueSearch() {
        // 取得客户编号
        String companyId = systemConfig.getMerCustId();
        // 取得公司账户余额
        double companyYue = websiteService.getCompanyYuE(companyId);
        return new AdminResult(companyYue);
    }

}
