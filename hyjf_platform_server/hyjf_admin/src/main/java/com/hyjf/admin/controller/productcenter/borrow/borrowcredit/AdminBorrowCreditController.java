package com.hyjf.admin.controller.productcenter.borrow.borrowcredit;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.BorrowCreditListResultBean;
import com.hyjf.admin.beans.request.BorrowCreditRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowCreditService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.user.UserPayAuthResponse;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * admin: 汇直投：债权转让
 * @author zhangyk
 * @date 2018/7/9 14:59
 */

@Api(value = "产品中心-汇转让-债权转让", tags = "产品中心-汇转让-债权转让")
@RestController
@RequestMapping("/hyjf-admin/borrow/credit")
public class AdminBorrowCreditController {


    @Autowired
    private BorrowCreditService borrowCreditService;

    @Autowired
    public SystemConfig systemConfig;

    public static final String PERMISSIONS = "borrowcredit";


    /**
     * 债权转让列表
     * @author zhangyk
     * 原接口：com.hyjf.admin.manager.borrow.borrowcredit.BorrowCreditController.search()
     * @date 2018/8/7 9:36
     */
    @ApiOperation(value = "债权转让", notes = "债权转让")
    @PostMapping("/getList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    @ResponseBody
    public Object  getBorrowCreditList(@RequestBody BorrowCreditRequest request){
        AdminResult result = borrowCreditService.getBorrowCreditList(request);
        return result;
    }

    /**
     * 债权转让导出
     * @author zhangyk
     * @date 2018/8/7 9:36
     */
    @ApiOperation(value = "债权转让导出", notes = "债权转让导出")
    @PostMapping("/exportData")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    @ResponseBody
    public void  exportBorrowCreditList(@RequestBody BorrowCreditRequest request, HttpServletRequest servletRequest, HttpServletResponse response) throws UnsupportedEncodingException {
        int totalCount = borrowCreditService.selectBorrowCreditCount(request);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        defaultRowMaxCount = 3;//todo 测试导出
        String sheetName = "债权转让列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            //请求第一页5000条
            request.setPageSize(defaultRowMaxCount);
            request.setCurrPage(i);
            AdminResult borrowCreditList = borrowCreditService.getBorrowCreditList(request);
            if (borrowCreditList != null) {
                BorrowCreditListResultBean data = (BorrowCreditListResultBean) borrowCreditList.getData();
                if (data != null && data.getRecordList().size() > 0) {
                    sheetNameTmp = sheetName + "_第" + (i) + "页";
                    helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  data.getRecordList());
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(servletRequest, response, fileName, workbook);
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        return mapAdapter;
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("creditNid","债转编号");
        map.put("bidNid","项目编号");
        map.put("userName","用户名");
        map.put("creditCapital","债权本金");
        map.put("creditCapitalPrice","转让本金");
        map.put("creditDiscount","折让率");
        map.put("creditPrice","转让价格");
        map.put("creditCapitalAssigned","已转让金额");
        map.put("addTime","发布时间");
        map.put("repayLastTime","还款时间");
        map.put("creditStatusName","转让状态");
        map.put("client","发布平台");
        return map;
    }

    /**
     * 债权转让明细
     * @author zhangyk
     * @date 2018/8/7 9:36
     */
    @ApiOperation(value = "债权转让明细", notes = "债权转让明细")
    @PostMapping("/infoDetail")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    @ResponseBody
    public Object getCreditInfoDetail(@RequestBody BorrowCreditRequest request){
       AdminResult result =  borrowCreditService.getBorrowInfoList(request);
       return result;
    }

    /**
     * 取消债权转让
     * @author zhangyk
     * @date 2018/8/7 9:36
     */
    @ApiOperation(value = "取消债权转让", notes = "取消债权转让")
    @PostMapping("/cancel")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_CANCEL)
    @ResponseBody
    public Object creditCalcel(@RequestBody BorrowCreditRequest request){
        AdminResult result =  borrowCreditService.cancelCredit(request);
        return result;
    }


    @ApiOperation(value = "转让状态下拉选" , notes = "转让状态下拉选")
    @PostMapping("/creditStatusList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @ResponseBody
    public Object getCreditStatusList(){
        AdminResult result =  borrowCreditService.getCreditStatusList();
        return result;
    }



}
