/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.activity;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.ActivityUserGuessService;
import com.hyjf.admin.service.ActivityUserRewardService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ActivityUserGuessResponse;
import com.hyjf.am.response.admin.ActivityUserRewardResponse;
import com.hyjf.am.resquest.admin.ActivityUserGuessRequest;
import com.hyjf.am.resquest.admin.ActivityUserRewardRequest;
import com.hyjf.am.vo.activity.ActivityUserRewardVO;
import com.hyjf.am.vo.admin.ActivityUserGuessVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author yaoyong
 * @version MayDayController, v0.1 2019/4/18 14:10
 */
@Api(tags = "活动中心-五一活动")
@RestController
@RequestMapping("/hyjf-admin/manager/activity/mayday")
public class MayDayController extends BaseController {

    public static final String PERMISSIONS = "activitylist";

    @Autowired
    private ActivityUserGuessService activityUserGuessService;
    @Autowired
    private ActivityUserRewardService activityUserRewardService;

    @ApiOperation(value = "活动竞猜列表", notes = "活动竞猜列表")
    @PostMapping("/list")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult getRecordList(@RequestBody ActivityUserGuessRequest request) {
        ActivityUserGuessResponse response = activityUserGuessService.getGuessList(request);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "奖励领取列表", notes = "奖励领取列表")
    @PostMapping("/rewardList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult getRewardList(@RequestBody ActivityUserRewardRequest rewardRequest) {
        ActivityUserRewardResponse response = activityUserRewardService.getRewardList(rewardRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }

    /**
     * 竞猜列表导出
     *
     * @param request
     * @param response
     * @param 
     */
    @ApiOperation(value = "竞猜列表导出", notes = "竞猜列表导出")
    @PostMapping("exportList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportGuessExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody ActivityUserGuessRequest activityUserGuessRequest) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "竞猜用户列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        activityUserGuessRequest.setPageSize(defaultRowMaxCount);
        activityUserGuessRequest.setCurrPage(1);

        ActivityUserGuessResponse rewardResponse = activityUserGuessService.getGuessList(activityUserGuessRequest);
        Integer totalCount = rewardResponse.getCount();

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
            activityUserGuessRequest.setPageSize(defaultRowMaxCount);
            activityUserGuessRequest.setCurrPage(i + 1);
            ActivityUserGuessResponse rewardResponse2 = activityUserGuessService.getGuessList(activityUserGuessRequest);
            if(null == rewardResponse2){
                rewardResponse2= new ActivityUserGuessResponse();
            }
            List<ActivityUserGuessVO> resultList = rewardResponse2.getResultList();
            if (!CollectionUtils.isEmpty(resultList)) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }


    /**
     * 奖励领取列表导出
     *
     * @param request
     * @param response
     * @param
     */
    @ApiOperation(value = "奖励领取列表导出", notes = "奖励领取列表导出")
    @PostMapping("exportRewardList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAwardExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody ActivityUserRewardRequest activityUserRewardRequest) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "奖励领取列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        activityUserRewardRequest.setPageSize(defaultRowMaxCount);
        activityUserRewardRequest.setCurrPage(1);

        ActivityUserRewardResponse rewardResponse = activityUserRewardService.getRewardList(activityUserRewardRequest);
        Integer totalCount = rewardResponse.getCount();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        } else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, rewardResponse.getResultList());
        }
        for (int i = 1; i < sheetCount; i++) {
            activityUserRewardRequest.setPageSize(defaultRowMaxCount);
            activityUserRewardRequest.setCurrPage(i + 1);
            ActivityUserRewardResponse rewardResponse2 = activityUserRewardService.getRewardList(activityUserRewardRequest);
            if(null == rewardResponse2){
                rewardResponse2= new ActivityUserRewardResponse();
            }
            List<ActivityUserRewardVO> resultList = rewardResponse2.getResultList();
            if (!CollectionUtils.isEmpty(resultList)) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
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

    private Map<String,String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName", "用户名");
        map.put("trueName", "姓名");
        map.put("rewardName", "奖励名称");
        map.put("rewardType", "奖励类型");
        map.put("sendType", "发放方式");
        map.put("sendStatus", "发放状态");
        map.put("getTime", "领取时间");
        map.put("createTime", "发放时间");
        return map;
    }

    private Map<String, String> buildMap1() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName", "用户名");
        map.put("trueName", "姓名");
        map.put("grade", "竞猜名次");
        map.put("reward", "奖励名称");
        map.put("style", "发放方式");
        return map;
    }
}
