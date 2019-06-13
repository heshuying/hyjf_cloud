package com.hyjf.admin.controller.repair.bankdebtend;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.exception.BankCreditEndService;
import com.hyjf.admin.utils.Page;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.BorrowRepayInfoCurrentExportResponse;
import com.hyjf.am.resquest.admin.BorrowRepayInfoCurrentRequest;
import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.resquest.trade.BankCreditEndUpdateRequest;
import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
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
 * 异常中心-结束债权异常处理
 * @author hesy
 * @version BankCreditEndController, v0.1 2018/7/12 16:55
 */
@Api(value = "异常中心-结束债权异常处理", tags = "异常中心-结束债权异常处理")
@RestController
@RequestMapping("/hyjf-admin/exception/creditend")
public class BankCreditEndExceptionController extends BaseController {
    @Autowired
    BankCreditEndService bankCreditEndService;

    @Autowired
    AdminCommonService adminCommonService;

    /** 权限 */
    public static final String PERMISSIONS = "bankdebtend";
    /**
     * 结束债权列表
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "结束债权列表", notes = "结束债权列表")
    @PostMapping("/list")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<Map<String,Object>> getList(@RequestBody BankCreditEndListRequest requestBean){
        Map<String,Object> resultMap = new HashMap<>();
        //债权结束状态
        List<DropDownVO> creditendStateList = adminCommonService.getParamNameList("CREDITEND_STATE");
        resultMap.put("creditendStateList",creditendStateList);
        //结束债权类型
        List<DropDownVO> creditendTypeList = adminCommonService.getParamNameList("CREDITEND_TYPE");
        resultMap.put("creditendTypeList",creditendTypeList);

        Integer count = bankCreditEndService.getCreditEndCount(requestBean);
        Page page = Page.initPage(requestBean.getCurrPage(), requestBean.getPageSize());
        page.setTotal(count);
        requestBean.setLimitStart(page.getOffset());
        requestBean.setLimitEnd(page.getLimit());

        List<BankCreditEndVO> recordList = bankCreditEndService.getCreditEndList(requestBean);
        resultMap.put("list",recordList);
        resultMap.put("count",count);

        return new AdminResult<Map<String,Object>>(resultMap);
    }

    /**
     * 结束债权(新)同步
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "结束债权(新)同步", notes = "结束债权(新)同步")
    @PostMapping("/update_frombank")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateFromBank(@RequestBody BankCreditEndUpdateRequest requestBean){
        logger.info("结束债权(新)同步, requestBean: " + JSON.toJSONString(requestBean));
        //请求参数校验
        boolean checkResult = bankCreditEndService.checkForUpdate(requestBean);
        if(!checkResult){
            logger.error("结束债权(新)同步请求参数错误");
            return new AdminResult<>(FAIL, "请求参数错误");
        }

        List<BankCallBean> queryList = bankCreditEndService.queryBatchDetails(requestBean);
        if(queryList == null || queryList.isEmpty()){
            logger.error("结束债权(新)同步，请求银行接口失败返回null");
            return new AdminResult<>(FAIL, "请求银行接口失败");
        }

        boolean updateResult = bankCreditEndService.updateBankCreditEndFromBankQuery(queryList);
        if(!updateResult){
            logger.error("结束债权(新)同步，更新数据库失败");
            return new AdminResult<>(FAIL, "更新数据库失败");
        }

        return new AdminResult();
    }

    /**
     * 结束债权(新)更新为初始状态
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "结束债权(新)更新为初始状态", notes = "结束债权(新)更新为初始状态")
    @PostMapping("/update_forinitial")
    @AuthorityAnnotation(key = PERMISSIONS,value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult updateForInitial(@RequestBody BankCreditEndUpdateRequest requestBean){
        logger.info("结束债权(新)更新为初始状态，requestBean：" + JSON.toJSONString(requestBean));
        //请求参数校验
        boolean checkResult = bankCreditEndService.checkForUpdateInitial(requestBean);
        if(!checkResult){
            logger.error("结束债权(新)更新为初始状态，请求参数错误");
            return new AdminResult<>(FAIL, "请求参数错误");
        }

        BankCreditEndVO creditEndVO = bankCreditEndService.getCreditEndByOrderId(requestBean.getOrderId());
        if(creditEndVO == null){
            logger.error("结束债权(新)更新为初始状态,没有查到记录");
            return new AdminResult<>(FAIL, "更新状态失败，没有记录");
        }

        boolean updateResult = bankCreditEndService.updateCreditEndForInitial(creditEndVO);
        if(!updateResult){
            logger.error("结束债权(新)更新为初始状态,更新数据库失败");
            return new AdminResult<>(FAIL, "更新数据库失败");
        }

        return new AdminResult();

    }

    /**
     * 画面初始化
     *
     * @param request
     */
    @ApiOperation(value = "结束债权列表导出", notes = "结束债权列表导出")
    @PostMapping(value = "/export")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BankCreditEndListRequest requestBean) throws UnsupportedEncodingException {
        logger.info("结束债权导出-start, requestBean:{}", JSON.toJSONString(requestBean));

        // 不加条件不能导出
        if(StringUtils.isBlank(requestBean.getBorrowNidSrch()) && StringUtils.isBlank(requestBean.getBorrowUserNameSrch()) && StringUtils.isBlank(requestBean.getCommitTimeStartSrch())
                && StringUtils.isBlank(requestBean.getCommitTimeEndSrch())){
            logger.error("批次结束债权导出没有选择条件，导出终止");
            return;
        }

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "批次结束债权";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        requestBean.setPageSize(defaultRowMaxCount);
        requestBean.setCurrPage(1);
        // 查询

        Integer totalCount = bankCreditEndService.getCreditEndCount(requestBean);
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
                List<BankCreditEndVO> recordList = bankCreditEndService.getCreditEndList(requestBean);
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList);
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("orderId","结束债权订单号");
        map.put("orgOrderId","出借/承接订单号");
        map.put("creditEndType","债权结束类型");
        map.put("borrowNid","项目编号");
        map.put("batchNo","批次号");
        map.put("status","批次状态");
        map.put("username","借款人用户名");
        map.put("tenderUsername","出借人用户名");
        map.put("authCode","债权银行授权码");
        map.put("stateDesc","债权结束状态");
        map.put("retmsg","批次错误描述");
        map.put("failmsg","债权错误描述");
        map.put("commitTime","发起时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter creditEndTypeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                if (null!=object) {
                    Integer endType = (Integer)object;
                    if(endType == 1){
                        return "还款";
                    }else if(endType == 2){
                        return "散标债转";
                    }else if(endType == 3){
                        return "智投债转";
                    }else{
                        return "";
                    }
                }
                return null;
            }
        };
        IValueFormatter statusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                if (null!=object) {
                    Integer status = (Integer)object;
                    if(status == 0){
                        return "初始";
                    }else if(status == 1){
                        return "待请求";
                    }else if(status == 2){
                        return "请求成功";
                    }else if(status == 3){
                        return "请求失败";
                    }else if(status == 4){
                        return "校验成功";
                    }else if(status == 5){
                        return "业务全部成功";
                    }else if(status == 10){
                        return "校验失败";
                    }else if(status == 11){
                        return "业务部分成功";
                    }else if(status == 12){
                        return "业务失败";
                    }else{
                        return "";
                    }
                }
                return null;
            }
        };
        mapAdapter.put("creditEndType", creditEndTypeAdapter);
        mapAdapter.put("status", statusAdapter);
        return mapAdapter;
    }
}
