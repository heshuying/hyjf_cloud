/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.SmsCountRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.SmsCountService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.admin.SmsCountCustomizeResponse;
import com.hyjf.am.resquest.user.SmsCountRequest;
import com.hyjf.am.vo.admin.SmsCountCustomizeVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author fq
 * @version SmsCountController, v0.1 2018/8/17 9:14
 */
@Api(tags = "消息中心-短信统计")
@RestController
@RequestMapping("/hyjf-admin/message/smsCount")
public class SmsCountController extends BaseController {
    @Autowired
    private SmsCountService smsCountService;

    @ApiOperation(value = "根据条件查询短信统计", notes = "根据条件查询短信统计")
    @PostMapping("/sms_count_list")
    public AdminResult smsCountList(@RequestBody SmsCountRequestBean request) {
        SmsCountRequest smsCountCustomize = new SmsCountRequest();
        if (StringUtils.isNotEmpty(request.getPost_time_begin())) {
            //int begin = GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.getDayStart(form.getPost_time_begin()));
            smsCountCustomize.setPost_time_begin(request.getPost_time_begin());
        }
        if (StringUtils.isNotEmpty(request.getPost_time_end())) {
            //int end = GetDate.strYYYYMMDDHHMMSS2Timestamp2(GetDate.getDayEnd(form.getPost_time_end()));
            smsCountCustomize.setPost_time_end(request.getPost_time_end());
        }

        if (request.getCurrPage() >0 && request.getPageSize() >0) {
            smsCountCustomize.setLimitStart((request.getCurrPage() - 1) * (request.getPageSize()));
            smsCountCustomize.setLimitEnd(request.getPageSize());
        }

        // 部门
        if (Validator.isNotNull(request.getCombotreeListSrch())) {

            String[] combotreeListSrch = request.getCombotreeListSrch();
            if (Arrays.asList(combotreeListSrch).contains("-10086")) {

                //将-10086转换为 0 , 0=部门为 ‘其他’
                for (int i = 0; i < combotreeListSrch.length; i++) {
                    String st = combotreeListSrch[i];
                    if (("-10086").equals(st)) {
                        combotreeListSrch[i] = "0";
                    }
                }
            }
            smsCountCustomize.setCombotreeListSrch(combotreeListSrch);
        }
        SmsCountCustomizeResponse response = smsCountService.querySmsCountList(smsCountCustomize);

        return new AdminResult(response);
    }

    /**
     /**
     * 取得部门信息
     *
     * @param
     * @return
     */
    @ApiOperation(value = "取得部门信息", notes = "取得部门信息")
    @GetMapping(value = "/get_crm_department_list")
    public JSONObject getCrmDepartmentListAction() {
        // 部门
        String[] list = new String[]{};
      /*  if (Validator.isNotNull(form.getIds())) {
            if (form.getIds().contains(StringPool.COMMA)) {
                list = form.getIds().split(StringPool.COMMA);
            } else {
                list = new String[]{form.getIds()};
            }
        }*/

        JSONArray ja = this.smsCountService.getCrmDepartmentList(list);
        if (ja != null) {

            //在部门树中加入 0=部门（其他）,因为前端不能显示id=0,就在后台将0=其他转换为-10086=其他
            JSONObject jo = new JSONObject();

            jo.put("value", "-10086");
          /*  jo.put("text", "其他");
            jo.put("value", -10086);*/
          /*  jo.put("parentid", 0);
            jo.put("parentname", "");*/
            jo.put("title", "其他");
//            jo.put("listorder", 0);
            JSONArray array = new JSONArray();
            jo.put("key", UUID.randomUUID());
            jo.put("children", array);
           /* if (Validator.isNotNull(list) && ArrayUtils.contains(list, String.valueOf(-10086))) {
                JSONObject selectObj = new JSONObject();
                selectObj.put("selected", true);
                // selectObj.put("opened", true);
                jo.put("state", selectObj);
            }*/

            ja.add(jo);
            JSONObject ret= new JSONObject();
            ret.put("data", ja);
            ret.put("status", "000");
            ret.put("statusDesc", "成功");
            return ret;
        }

        return new JSONObject();
    }

//    /**
//     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
//     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
//     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
//     *
//     * @param request
//     * @param response
//     * @throws Exception
//     */
//    @ApiOperation(value = "导出功能", notes = "导出功能")
//    @PostMapping(value = "/export1")
//    public void exportExcel1(@RequestBody SmsCountRequestBean form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        //查询短信单价配置
//        String configMoney = CacheUtil.getParamName("SMS_COUNT_PRICE", "PRICE");
//        DecimalFormat decimalFormat = new DecimalFormat("0.000");
//        // 表格sheet名称
//        String sheetName = "短信统计列表";
//        // 文件名称
//        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
//        // 需要输出的结果列表
//        SmsCountRequest smsCountCustomize = new SmsCountRequest();
//        if (StringUtils.isNotEmpty(form.getPost_time_begin())) {
//            smsCountCustomize.setPost_time_begin(form.getPost_time_begin());
//        }
//        if (StringUtils.isNotEmpty(form.getPost_time_end())) {
//            smsCountCustomize.setPost_time_end(form.getPost_time_end());
//        }
//
//        // 部门
//        if (Validator.isNotNull(form.getCombotreeListSrch())) {
//
//            String[] combotreeListSrch = form.getCombotreeListSrch();
//            if (Arrays.asList(combotreeListSrch).contains("-10086")) {
//
//                //将-10086转换为 0 , 0=部门为 ‘其他’
//                for (int i = 0; i < combotreeListSrch.length; i++) {
//                    String st = combotreeListSrch[i];
//                    if (("-10086").equals(st)) {
//                        combotreeListSrch[i] = "0";
//                    }
//                }
//            }
//            smsCountCustomize.setCombotreeListSrch(combotreeListSrch);
//        }
//        List<SmsCountCustomizeVO> listSms = smsCountService.querySmsCountList(smsCountCustomize).getResultList();
//        //短信总条数+总费用
//        Integer smsNumber = 0;
//        BigDecimal smsMoney = BigDecimal.ZERO;
//        if (!CollectionUtils.isEmpty(listSms)) {
//            for (SmsCountCustomizeVO vo : listSms) {
//                smsNumber += vo.getSmsNumber();
//                smsMoney = smsMoney.add(new BigDecimal(configMoney).multiply(new BigDecimal(vo.getSmsNumber())));
//            }
//        }
//
//        String[] titles = new String[]{"序号", "分公司", "数量(条)", "费用(元)", "时间"};
//        // 声明一个工作薄
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        // 生成一个表格
//        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
//
//        if (listSms != null && listSms.size() > 0) {
//
//            int sheetCount = 1;
//            int rowNum = 0;
//
//            for (int i = 0; i < listSms.size(); i++) {
//                rowNum++;
//                if (i != 0 && i % 60000 == 0) {
//                    sheetCount++;
//                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
//                    rowNum = 1;
//                }
//
//                // 新建一行
//                Row row = sheet.createRow(rowNum);
//                // 循环数据
//                for (int celLength = 0; celLength < titles.length; celLength++) {
//                    SmsCountCustomizeVO sms = listSms.get(i);
//                    // 创建相应的单元格
//                    Cell cell = row.createCell(celLength);
//                    if (celLength == 0) {// 序号
//                        cell.setCellValue(i + 1);
//                    } else if (celLength == 1) { // 分公司
//                        cell.setCellValue(sms.getDepartmentName());
//                    } else if (celLength == 2) { // 数量(条)
//                        cell.setCellValue(sms.getSmsNumber());
//                    } else if (celLength == 3) { // 费用(元)
//                        sms.setSmsMoney(decimalFormat.format(new BigDecimal(configMoney).multiply(new BigDecimal(sms.getSmsNumber()))));
//                        cell.setCellValue(sms.getSmsMoney());
//                    } else if (celLength == 4) {// 时间
//                        cell.setCellValue(sms.getPosttime());
//                    }
//                }
//            }
//
//            //总条数
//            String[] sumSmsCount = new String[]{"总条数", "", String.valueOf(smsNumber), decimalFormat.format(smsMoney), ""};
//            Row rowTow = sheet.createRow(rowNum + 1);
//            for (int celLength = 0; celLength < sumSmsCount.length; celLength++) {
//                // 创建相应的单元格
//                Cell cell = rowTow.createCell(celLength);
//                cell.setCellValue(sumSmsCount[celLength]);
//            }
//        }
//        // 导出
//        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
//    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出功能", notes = "导出功能")
    @PostMapping(value = "/export")
    public void exportExcel(@RequestBody SmsCountRequestBean form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //查询短信单价配置
//        String configMoney = CacheUtil.getParamName("SMS_COUNT_PRICE", "PRICE");
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "短信统计列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        // 需要输出的结果列表
        SmsCountRequest smsCountCustomize = new SmsCountRequest();
        if (StringUtils.isNotEmpty(form.getPost_time_begin())) {
            smsCountCustomize.setPost_time_begin(form.getPost_time_begin());
        }
        if (StringUtils.isNotEmpty(form.getPost_time_end())) {
            smsCountCustomize.setPost_time_end(form.getPost_time_end());
        }

        // 部门
        if (Validator.isNotNull(form.getCombotreeListSrch())) {

            String[] combotreeListSrch = form.getCombotreeListSrch();
            if (Arrays.asList(combotreeListSrch).contains("-10086")) {

                //将-10086转换为 0 , 0=部门为 ‘其他’
                for (int i = 0; i < combotreeListSrch.length; i++) {
                    String st = combotreeListSrch[i];
                    if (("-10086").equals(st)) {
                        combotreeListSrch[i] = "0";
                    }
                }
            }
            smsCountCustomize.setCombotreeListSrch(combotreeListSrch);
        }
        //请求第一页5000条
        smsCountCustomize.setPageSize(defaultRowMaxCount);
        smsCountCustomize.setCurrPage(1);
        smsCountCustomize.setLimitStart(-1);
        Integer totalCount = smsCountService.getSmsCountForExport(smsCountCustomize);

        //短信总条数+总费用
        Integer smsNumber = 0;
        BigDecimal smsMoney = BigDecimal.ZERO;

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            String[] sumSmsCount = new String[]{"总条数", "", String.valueOf(smsNumber), decimalFormat.format(smsMoney), ""};
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList(),sumSmsCount);
        }
        for (int i = 1; i <= sheetCount; i++) {
            //每页的总条数重置
            smsNumber = 0;
            smsMoney = BigDecimal.ZERO;
            smsCountCustomize.setPageSize(defaultRowMaxCount);
            smsCountCustomize.setCurrPage(i);
            smsCountCustomize.setLimitStart(-1);
            List<SmsCountCustomizeVO> listSms = smsCountService.querySmsCountList(smsCountCustomize).getResultList();
            if (listSms != null && listSms.size()> 0) {
                for (SmsCountCustomizeVO vo : listSms) {
                    smsNumber += vo.getSmsNumber();
//                    smsMoney = smsMoney.add(new BigDecimal(configMoney).multiply(new BigDecimal(vo.getSmsNumber())));
                    smsMoney =smsMoney.add( new BigDecimal(vo.getSmsMoney()));
                }
                //每页最后一行
                String[] sumSmsCount = new String[]{"总条数", "", String.valueOf(smsNumber), decimalFormat.format(smsMoney), ""};
                sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  listSms, sumSmsCount);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("departmentName","分公司");
        map.put("smsNumber","数量(条)");
        map.put("smsFee","费用(元)");
        map.put("posttime","时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
//        IValueFormatter decimalFormat = new IValueFormatter() {
//            //查询短信单价配置
//            String configMoney = CacheUtil.getParamName("SMS_COUNT_PRICE", "PRICE");
//            DecimalFormat decimalFormat = new DecimalFormat("0.000");
//            @Override
//            public String format(Object object) {
//                return  decimalFormat.format(new BigDecimal(configMoney).multiply(new BigDecimal(object.toString())));
//            }
//        };
//        mapAdapter.put("smsFee", decimalFormat);
        return mapAdapter;
    }
}
