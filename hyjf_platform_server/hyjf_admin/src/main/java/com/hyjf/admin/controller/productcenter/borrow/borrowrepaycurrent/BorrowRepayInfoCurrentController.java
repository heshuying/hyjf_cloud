package com.hyjf.admin.controller.productcenter.borrow.borrowrepaycurrent;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowRepayInfoCurrentService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowRepayInfoCurrentExportResponse;
import com.hyjf.am.response.admin.BorrowRepayInfoCurrentResponse;
import com.hyjf.am.resquest.admin.BorrowRepayInfoCurrentRequest;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 当前债权还款明细
 * @author hesy
 */
@Api(value = "产品中心-汇直投-当前债权还款明细",tags ="产品中心-汇直投-当前债权还款明细")
@RestController
@RequestMapping("/hyjf-admin/borrow/repayinfocurrent")
public class BorrowRepayInfoCurrentController extends BaseController {
    /** 查看权限 */
    public static final String PERMISSIONS = "repayinfocurrent";

    @Autowired
    private BorrowRepayInfoCurrentService borrowRepayInfoCurrentService;

    /**
     * 画面初始化
     *
     * @param request
     */
    @ApiOperation(value = "当前债权还款明细页面数据", notes = "当前债权还款明细页面数据")
    @PostMapping(value = "/searchAction")
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<Map<String,Object>> searchAction(HttpServletRequest request, @RequestBody @Valid BorrowRepayInfoCurrentRequest requestBean) {
        logger.info("当前债权还款明细-start, requestBean:{}", JSON.toJSONString(requestBean));
        AdminResult<Map<String,Object>> response = new AdminResult<>();
        Map<String,Object> dataMap = new HashMap<>();

        //请求参数校验
        if(requestBean.getCurrPage() <= 0){
            requestBean.setCurrPage(1);
        }
        if(requestBean.getPageSize() <= 0){
            requestBean.setPageSize(10);
        }
        //borrowNid为必须传的参数
//        if(StringUtils.isBlank(requestBean.getBorrowNid())){
//            response.setStatus(AdminResult.FAIL);
//            response.setStatusDesc("请求参数错误，borrowNid为空");
//            return response;
//        }

        //请求原子层获取当前债权还款信息数据
        BorrowRepayInfoCurrentResponse amResponse = borrowRepayInfoCurrentService.getRepayInfoCurrentData(requestBean);
        if(amResponse == null || !Response.SUCCESS.equals(amResponse.getRtn())){
            response.setStatus(AdminResult.FAIL);
            response.setStatusDesc(amResponse.getMessage());
            return response;
        }

        dataMap.put("recordList", amResponse.getResultList());
        dataMap.put("sumInfo", amResponse.getSumInfo());
        response.setData(dataMap);
        response.setTotalCount(amResponse.getCount());
        logger.info("当前债权还款明细-end, response:{}", JSON.toJSONString(response));
        return response;
    }

    /**
     * 画面初始化
     *
     * @param request
     */
    @ApiOperation(value = "当前债权还款明细导出数据", notes = "当前债权还款明细导出数据")
    @PostMapping(value = "/exportAction")
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response,  @RequestBody @Valid BorrowRepayInfoCurrentRequest requestBean) throws UnsupportedEncodingException {
        logger.info("当前债权还款明细导出-start, requestBean:{}", JSON.toJSONString(requestBean));
        Map<String,Object> dataMap = new HashMap<>();

        //borrowNid为必须传的参数
        if(StringUtils.isBlank(requestBean.getBorrowNid()) && StringUtils.isBlank(requestBean.getTenderOrderId()) && StringUtils.isBlank(requestBean.getRepayTimeStart()) && StringUtils.isBlank(requestBean.getRepayedTimeStart())){
            return;
        }

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "当前债权还款明细";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        requestBean.setPageSize(defaultRowMaxCount);
        requestBean.setCurrPage(1);
        // 查询

        Integer totalCount = borrowRepayInfoCurrentService.getRepayInfoCurrentExportCount(requestBean.getBorrowNid());
        requestBean.setCount(totalCount);
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            for (int i = 1; i <= sheetCount; i++) {
                requestBean.setPageSize(defaultRowMaxCount);
                requestBean.setCurrPage(i);
                // 查询
                BorrowRepayInfoCurrentExportResponse amResponse = borrowRepayInfoCurrentService.getRepayInfoCurrentExportData(requestBean);
                if (amResponse != null && Response.SUCCESS.equals(amResponse.getRtn())) {
                    sheetNameTmp = sheetName + "_第" + (i) + "页";
                    helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  amResponse.getResultList());
                } else {
                    break;
                }
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("tenderOrdid","出借订单号");
        map.put("assignOrdid","承接订单号");
        map.put("repayOrdid","还款订单号");
        map.put("borrowNid","项目编号");
        map.put("planNid","智投编号");
        map.put("instName","资产来源");
        map.put("borrowUserId","借款人ID");
        map.put("borrowUserName","借款人用户名");
        map.put("borrowName","项目名称");
        map.put("projectTypeName","项目类型");
        map.put("borrowPeriod","项目期限");
        map.put("borrowApr","出借利率");
        map.put("borrowAccount","借款金额");
        map.put("borrowAccountYes","借到金额");
        map.put("repayType","还款方式");
        map.put("recoverUserName","出借人用户名");
        map.put("recoverUserId","出借人ID");

        map.put("recoverUserAttribute","出借人用户属性（当时）");
        map.put("recoverRegionName","出借人所属一级分部（当时）");
        map.put("recoverBranchName","出借人所属二级分部（当时）");
        map.put("recoverDepartmentName","出借人所属团队（当时）");
        map.put("referrerName","推荐人用户名（当时）");
        map.put("referrerRegionName","推荐人所属一级分部（当时）");
        map.put("referrerBranchName","推荐人所属二级分部（当时）");
        map.put("referrerDepartmentName","推荐人所属团队（当时）");
        map.put("recoverTotal","出借金额");
        map.put("amountHold","持有金额");
        map.put("recoverCapital","应回本金");

        map.put("recoverInterest","应回利息");
        map.put("recoverAccount","应回本息");
        map.put("recoverFee","还款服务费");
        map.put("recoverLastTime","到期日");
        map.put("period","回款期数");
        map.put("borrowPeriod","总期数");
        map.put("recoverCapitalPeriod","当期应回本金");
        map.put("recoverInterestPeriod","当期应回利息");
        map.put("recoverAccountPeriod","当期应回本息");
        map.put("recoverFeePeriod","当期还款服务费");
        map.put("chargeDays","当期提前天数");
        map.put("chargeInterestReduce","提前还款减息");
        map.put("chargePenaltyInterest","提前还款违约金");
        map.put("lateDays","逾期天数");
        map.put("lateInterest","逾期违约金");
        map.put("recoverCapitalYesPeriod","当期实回本金");
        map.put("recoverInterestYesPeriod","当期实回利息");
        map.put("recoverAccountYesPeriod","当期实还总额");
        map.put("recoverFeeYesPeriod","当期实还管理费");
        map.put("recoverCapitalWait","剩余待回本金");
        map.put("recoverInterestWait","剩余待回利息");
        map.put("recoverAccountWait","剩余待回本息");
        map.put("repayUserName","当期还款人");
        map.put("recoverStatus","还款状态");
        map.put("autoRepay","平台还款方式");
        map.put("repayMoneySource","还款来源");
        map.put("recoverCapital","发起人");
        map.put("recoverTime","应还日期");
        map.put("repayActionTime","实际回款时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter entrustedFlgAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String isStzf_str="否";
                if (object != null && Integer.parseInt(object.toString()) - 1 == 0) {
                    isStzf_str = "是";
                }
                return isStzf_str;
            }
        };
        mapAdapter.put("entrustedFlg", entrustedFlgAdapter);
        return mapAdapter;
    }
}
