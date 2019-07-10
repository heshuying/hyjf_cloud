/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.pointsshop.duiba.points;

import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.pointsshop.duiba.points.DuibaPointsListService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.DuibaPointsResponse;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.resquest.admin.DuibaPointsRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 兑吧积分明细表
 *
 * @author PC-LIUSHOUYI
 * @version DuibaPointsListController, v0.1 2019/5/29 9:46
 */
@Api(value = "积分商城-兑吧积分账户明细", tags = "积分商城-兑吧积分账户明细")
@RestController
@RequestMapping("/hyjf-admin/duiba/pointslist")
public class DuibaPointsListController extends BaseController {

    @Autowired
    DuibaPointsListService duibaPointsListService;

    private static final String PERMISSIONS = "dbpoints";

    @ApiOperation(value = "兑吧积分账户明细查询", notes = "兑吧积分账户明细查询")
    @PostMapping("/selectDuibaPointsList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<DuibaPointsResponse> selectDuibaPointsList(@RequestBody DuibaPointsRequest requestBean) {
        DuibaPointsResponse response = duibaPointsListService.selectDuibaPointsList(requestBean);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        // 积分类型
        List<ParamNameVO> integralTypeList = duibaPointsListService.getParamNameList(CustomConstants.INTEGRAL_TYPE);
        if (null != integralTypeList && integralTypeList.size() > 0) {
            response.setIntegralTypeList(integralTypeList);
        }
        // 积分业务名称
        List<ParamNameVO> integralBusinessNameList = duibaPointsListService.getParamNameList(CustomConstants.INTEGRAL_BUSINESS_NAME);
        if (null != integralBusinessNameList && integralBusinessNameList.size() > 0) {
            response.setIntegralBusinessNameList(integralBusinessNameList);
        }
        // 审核状态
        List<ParamNameVO> auditStatusList = duibaPointsListService.getParamNameList(CustomConstants.AUDIT_STATUS);
        if (null != auditStatusList && auditStatusList.size() > 0) {
            response.setAuditStatusList(auditStatusList);
        }
        // 操作类型
        List<ParamNameVO> operationTypeList = duibaPointsListService.getParamNameList(CustomConstants.OPERATION_TYPE);
        if (null != operationTypeList && operationTypeList.size() > 0) {
            response.setOperationTypeList(operationTypeList);
        }
        return new AdminResult<DuibaPointsResponse>(response);
    }

    /**
     * 兑吧积分账户明细列表导出
     *
     * @param request
     * @param response
     * @param response
     */
    @ApiOperation(value = "兑吧积分账户明细列表导出", notes = "兑吧积分账户明细列表导出")
    @PostMapping("/exportDuibaPointsList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportToExcel(HttpServletRequest request, @RequestBody DuibaPointsRequest requestBean, HttpServletResponse response) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "积分明细表导出";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) +  CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        //请求第一页5000条
        requestBean.setPageSize(defaultRowMaxCount);

        DuibaPointsResponse duibaPointsResponse = this.duibaPointsListService.selectDuibaPointsList(requestBean);

        Integer totalCount = duibaPointsResponse.getRecordTotal();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = "";

        for (int i = 1; i <= sheetCount; i++) {
            requestBean.setCurrPage(i);
            sheetNameTmp = sheetName + "_第" + (i) + "页";
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, duibaPointsResponse.getResultList());
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        // 序号、账户名、姓名、积分业务名称、积分数、类型、发生时间
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("userName", "账户名");
        map.put("trueName", "姓名");
        map.put("businessNameStr", "积分业务名称");
        map.put("points", "积分数");
        map.put("typeStr", "类型");
        map.put("createTime", "发生时间");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
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
        return mapAdapter;
    }
}
