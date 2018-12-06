package com.hyjf.admin.controller.productcenter.plancenter.plancapital;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.HjhPlanCapitalRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlanCapitalService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import com.hyjf.am.vo.trade.HjhPlanCapitalVO;
import com.hyjf.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 产品中心 --> 汇计划 --> 资金计划
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划",tags ="产品中心-汇计划-资金计划")
@RestController
@RequestMapping(value = "/hyjf-admin/plancapital")
public class PlanCapitalController extends BaseController {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private PlanCapitalService planCapitalService;

    /**
     * 计划资金 列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "资金计划列表", notes = "资金计划列表")
    @PostMapping(value = "/init")
    public AdminResult<ListResult<HjhPlanCapitalVO>> init(@RequestBody HjhPlanCapitalRequestBean requestBean){

        HjhPlanCapitalRequest hjhPlanCapitalRequest = new HjhPlanCapitalRequest();
        BeanUtils.copyProperties(requestBean, hjhPlanCapitalRequest);

        //初始化时时间不能为空
        if (StringUtils.isNotBlank(requestBean.getDateFromSrch()) && StringUtils.isNotBlank(requestBean.getDateToSrch())){
//            return new AdminResult<>(FAIL, "日期开始或日期结束不能为空!");

            try{
                Date timeStart = dateFormat.parse(requestBean.getDateFromSrch());
                Date timeEnd = dateFormat.parse(requestBean.getDateToSrch());

                if (timeStart.getTime() > timeEnd.getTime()){
                    return new AdminResult<>(FAIL, "结束时间应大于等于开始时间!");
                }
            }catch (ParseException e){
                return new AdminResult<>(FAIL, e.getMessage());
            }
        }

        // 初始化返回list
        List<HjhPlanCapitalVO> returnList = new ArrayList<>();

        // 获取结果集
        HjhPlanCapitalResponse planCapitalResponse = planCapitalService.getPlanCapitalList(hjhPlanCapitalRequest);

        if (planCapitalResponse == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        if (!Response.isSuccess(planCapitalResponse)){
            return new AdminResult<>(FAIL, planCapitalResponse.getMessage());
        }

        if (CollectionUtils.isNotEmpty(planCapitalResponse.getResultList())){
            returnList = CommonUtils.convertBeanList(planCapitalResponse.getResultList(), HjhPlanCapitalVO.class);
            return new AdminResult<ListResult<HjhPlanCapitalVO>>(ListResult.build2(returnList, planCapitalResponse.getCount(), planCapitalResponse.getSumHjhPlanCapitalVO()));
        }else {
            return new AdminResult<ListResult<HjhPlanCapitalVO>>(ListResult.build(returnList, 0));
        }
    }

    /**
     * 根据业务需求导出相应的表格 此处暂时为可用情况 缺陷： 1.无法指定相应的列的顺序， 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "资金计划列表", notes = "资金计划列表导出")
    @PostMapping(value = "/exportExcel")
    public void exportExcel(@ModelAttribute HjhPlanCapitalRequestBean requestBean, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HjhPlanCapitalRequest hjhPlanCapitalRequest = new HjhPlanCapitalRequest();
        BeanUtils.copyProperties(requestBean, hjhPlanCapitalRequest);

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "资金计划";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        hjhPlanCapitalRequest.setPageSize(defaultRowMaxCount);
        hjhPlanCapitalRequest.setCurrPage(1);
        // 需要输出的结果列表
        List<HjhPlanCapitalVO> resultList = null;
        HjhPlanCapitalResponse resultResponse = planCapitalService.getPlanCapitalList(hjhPlanCapitalRequest);
        if (resultResponse.getCount() > 0){
            resultList = CommonUtils.convertBeanList(resultResponse.getResultList(), HjhPlanCapitalVO.class);
        }


        Integer totalCount = resultResponse.getCount();

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

            hjhPlanCapitalRequest.setPageSize(defaultRowMaxCount);
            hjhPlanCapitalRequest.setCurrPage(i + 1);
            HjhPlanCapitalResponse resultResponse2 = planCapitalService.getPlanCapitalList(hjhPlanCapitalRequest);
            if (resultResponse2 != null && resultResponse2.getResultList().size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultResponse2.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("date", "日期");
        map.put("planNid", "智投编号");
        map.put("planName", "智投名称");
        map.put("lockPeriodView", "服务回报期限");
        map.put("reinvestAccount", "复投总额（元）");
        map.put("creditAccount", "债转总额（元）");

        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter dateAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Date value = (Date) object;
                return GetDate.dateToString2(value);
            }
        };

        IValueFormatter reinvestAccountAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal value = (BigDecimal) object;
                return CustomConstants.DF_FOR_VIEW.format(value);
            }
        };

        IValueFormatter creditAccountAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                BigDecimal value = (BigDecimal) object;
                return CustomConstants.DF_FOR_VIEW.format(value);
            }
        };

        mapAdapter.put("date", dateAdapter);
        mapAdapter.put("reinvestAccount", reinvestAccountAdapter);
        mapAdapter.put("creditAccount", creditAccountAdapter);
        return mapAdapter;
    }

}
