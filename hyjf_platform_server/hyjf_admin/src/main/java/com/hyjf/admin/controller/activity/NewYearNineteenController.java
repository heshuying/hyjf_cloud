package com.hyjf.admin.controller.activity;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.NewYearNineteenService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.NewYearActivityResponse;
import com.hyjf.am.resquest.admin.NewYearNineteenRequestBean;
import com.hyjf.am.vo.admin.NewYearActivityVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
    public AdminResult<ListResult<NewYearActivityVO>> init(@RequestBody NewYearNineteenRequestBean newYearNineteenRequestBean) {
        NewYearActivityResponse response = newYearNineteenService.selectInvestList(newYearNineteenRequestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        List<NewYearActivityVO> resultList = response.getResultList();
        return new AdminResult<ListResult<NewYearActivityVO>>(ListResult.build(resultList, response.getTotal()));
    }





    /**
     * @Description 数据导出--还款计划
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "累计年化出借金额", notes = "带条件导出EXCEL")
    @RequestMapping(value = "/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportToExcel(HttpServletRequest request, HttpServletResponse response, NewYearNineteenRequestBean newYearNineteenRequestBean) throws Exception {

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
            List<NewYearActivityVO> resultList = newYearActivityResponse.getResultList();
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
        map.put("userName","账户名");
        map.put("trueName","姓名");
        map.put("investAmount","累计年化出借金额（元）");
        map.put("reward","奖励名称");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter borrowPeriodAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return (String)object;
            }
        };
        return mapAdapter;
    }
}
