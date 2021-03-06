package com.hyjf.admin.controller.activity;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.NewYearNineteenService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NewYearActivityResponse;
import com.hyjf.am.response.admin.NewYearActivityRewardResponse;
import com.hyjf.am.resquest.admin.NewYearNineteenRequestBean;
import com.hyjf.am.vo.admin.NewYearActivityRewardVO;
import com.hyjf.am.vo.admin.NewYearActivityVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xiehuili on 2019/3/25.
 */
@Api(tags = "活动中心-春节活动")
@RestController
@RequestMapping("/hyjf-admin/manager/activity/newyearnineteen2019")
public class NewYearNineteenController extends BaseController {

    /**
     * 权限关键字
     */
    public static final String PERMISSIONS = "activitylist";
    @Autowired
    private NewYearNineteenService newYearNineteenService;
    /**
     * 累计年化出借金额列表 画面初始化
     * @param newYearNineteenRequestBean
     * @return
     */
    @ApiOperation(value = "累计年化出借金额列表", notes = "累计年化出借金额列表")
    @PostMapping("/investInit")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult init(@RequestBody NewYearNineteenRequestBean newYearNineteenRequestBean) {
        NewYearActivityResponse response = newYearNineteenService.selectInvestList(newYearNineteenRequestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<NewYearActivityResponse>(response);
    }





    /**
     * @Description 累计年化出借金额
     * @Author xiehuili
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "导出累计年化出借金额", notes = "带条件导出EXCEL")
    @PostMapping(value = "/exportExcelAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportToExcel(HttpServletRequest request, HttpServletResponse response,@RequestBody NewYearNineteenRequestBean newYearNineteenRequestBean) throws Exception {

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "累计年化出借金额导出";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        newYearNineteenRequestBean.setPageSize(defaultRowMaxCount);
        newYearNineteenRequestBean.setCurrPage(1);
        // 查询
        NewYearActivityResponse newYearActivityResponse = newYearNineteenService.selectInvestList(newYearNineteenRequestBean);
        if(null == newYearActivityResponse){
            newYearActivityResponse = new NewYearActivityResponse();
        }
        Integer totalCount = newYearActivityResponse.getTotal();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, newYearActivityResponse.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {

            newYearNineteenRequestBean.setPageSize(defaultRowMaxCount);
            newYearNineteenRequestBean.setCurrPage(i+1);
            NewYearActivityResponse newYearActivityResponse2 = newYearNineteenService.selectInvestList(newYearNineteenRequestBean);
            if(null == newYearActivityResponse2){
                newYearActivityResponse2 = new NewYearActivityResponse();
            }
            List<NewYearActivityVO> resultList = newYearActivityResponse2.getResultList();
            if (!CollectionUtils.isEmpty(resultList)) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName", "账户名");
        map.put("trueName", "姓名");
        map.put("investAmount", "累计年化出借金额（元）");
        map.put("reward", "奖励名称");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter borrowPeriodAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return (String) object;
            }
        };
        return mapAdapter;
    }


    /**
     * 奖励明细列表  画面初始化
     *
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "奖励明细列表", notes = "奖励明细列表")
    @PostMapping("/awardInit")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<ListResult<NewYearActivityRewardVO>> awardInit(@RequestBody NewYearNineteenRequestBean requestBean) {
        NewYearActivityRewardResponse response = newYearNineteenService.selectAwardList(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<NewYearActivityRewardVO> resultList = response.getResultList();
        return new AdminResult<>(ListResult.build(resultList, response.getTotal()));
    }

    /**
     * 奖励明细修改发放状态  页面初试化
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "修改状态详情", notes = "修改状态详情")
    @RequestMapping(value = "/awardInfo/{id}", method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult awardInfo(@PathVariable String id) {
        NewYearNineteenRequestBean request = new NewYearNineteenRequestBean();
        request.setId(Integer.parseInt(id));
        NewYearActivityRewardResponse response = newYearNineteenService.selectAwardInfo(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult(response.getResult());
    }

    /**
     * 修改奖励明细发放状态
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "修改发放状态", notes = "修改发放状态")
    @PostMapping("/updateStatus")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateStatus(@RequestBody NewYearNineteenRequestBean request) {
        BooleanResponse response = newYearNineteenService.updateStatus(request);
        if (!response.getResultBoolean()) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult();
    }


    /**
     * 奖励明细列表导出
     *
     * @param request
     * @param response
     * @param newYearNineteenRequestBean
     */
    @ApiOperation(value = "奖励明细列表导出", notes = "奖励明细列表导出")
    @PostMapping("exportAwardAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAwardExcel(HttpServletRequest request, HttpServletResponse response,@RequestBody NewYearNineteenRequestBean newYearNineteenRequestBean) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "奖励明细导出";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        newYearNineteenRequestBean.setPageSize(defaultRowMaxCount);
        newYearNineteenRequestBean.setCurrPage(1);

        NewYearActivityRewardResponse rewardResponse = newYearNineteenService.selectAwardList(newYearNineteenRequestBean);
        Integer totalCount = rewardResponse.getTotal();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap1();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        } else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, rewardResponse.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {
            newYearNineteenRequestBean.setPageSize(defaultRowMaxCount);
            newYearNineteenRequestBean.setCurrPage(i + 1);
            NewYearActivityRewardResponse rewardResponse2 = newYearNineteenService.selectAwardList(newYearNineteenRequestBean);
            if(null == rewardResponse2){
                rewardResponse2= new NewYearActivityRewardResponse();
            }
            List<NewYearActivityRewardVO> resultList = rewardResponse2.getResultList();
            if (!CollectionUtils.isEmpty(resultList)) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap1() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("reward", "奖励名称");
        map.put("distributionMethod", "发放方式");
        map.put("userName", "账户名");
        map.put("trueName", "姓名");
        map.put("recipientName", "收件人姓名");
        map.put("recipientMobile", "收件人手机号");
        map.put("recipientAddress", "收件地址");
        map.put("status", "发放状态");
        map.put("getTime", "获得时间");
        map.put("releaseTime", "发放时间");
        return map;
    }
}
