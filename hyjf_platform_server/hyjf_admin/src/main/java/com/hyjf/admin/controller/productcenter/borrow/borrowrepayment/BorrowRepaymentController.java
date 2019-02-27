package com.hyjf.admin.controller.productcenter.borrow.borrowrepayment;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.admin.beans.BorrowRepaymentBean;
import com.hyjf.admin.beans.DelayRepayInfoBean;
import com.hyjf.admin.beans.RepayInfoBean;
import com.hyjf.admin.beans.request.BorrowRepaymentPlanRequestBean;
import com.hyjf.admin.beans.request.BorrowRepaymentRequestBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.BorrowRepaymentService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.Page;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.admin.AdminBorrowRepaymentResponse;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentRequest;
import com.hyjf.am.vo.admin.BorrowRepaymentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentPlanCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BorrowRecoverController, v0.1 2018/7/2 10:13
 */
@Api(value = "产品中心-汇直投-还款信息",tags ="产品中心-汇直投-还款信息")
@RestController
@RequestMapping("/hyjf-admin/borrow/borrowrepayment")
public class BorrowRepaymentController extends BaseController {
    @Autowired
    private BorrowRepaymentService borrowRepaymentService;

    @Autowired
    private AdminCommonService adminCommonService;
    /** 查看权限 */
    public static final String PERMISSIONS = "borrowrepayment";
    /**
     * 画面初始化
     *
     * @param request
     */
    @ApiOperation(value = "还款信息", notes = "还款信息页面查询初始化")
    @PostMapping(value = "/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowRepaymentBean> searchAction(HttpServletRequest request, @RequestBody @Valid BorrowRepaymentRequestBean form) {


        BorrowRepaymentRequest copyForm=new BorrowRepaymentRequest();
        BeanUtils.copyProperties(form, copyForm);
        BorrowRepaymentBean bean = borrowRepaymentService.searchBorrowRepayment(copyForm);
        List<DropDownVO> repayTypeList =  this.adminCommonService.selectBorrowStyleList();
        bean.setRepayTypeList(repayTypeList);
        // 资金来源
        List<HjhInstConfigVO> hjhInstConfigList = this.borrowRepaymentService.selectHjhInstConfigByInstCode("-1");
        bean.setHjhInstConfigList(ConvertUtils.convertListToDropDown(hjhInstConfigList,"instCode","instName"));
        AdminResult<BorrowRepaymentBean> result=new AdminResult<BorrowRepaymentBean> ();
        result.setData(bean);
        return result;
    }


    /**
     * 延期画面初始化
     *
     * @param request
     * @return 标签配置列表
     */
    @ApiOperation(value = "延期画面初始化", notes = "延期页面查询初始化")
    @PostMapping(value = "/initDelayRepayAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @ApiImplicitParam(name = "borrowNid",value = "项目编号")
    public AdminResult<DelayRepayInfoBean> initDelayRepayAction(HttpServletRequest request,@RequestBody Map map) {
        String borrowNid = (String) map.get("borrowNid");
        DelayRepayInfoBean bean=borrowRepaymentService.getDelayRepayInfo(borrowNid);
        AdminResult<DelayRepayInfoBean> result=new AdminResult<DelayRepayInfoBean> ();
        result.setData(bean);
        return result;
    }

    /**
     * 延期
     *
     * @param request
     * @return 标签配置列表
     */
    @ApiOperation(value = "延期", notes = "延期")
    @PostMapping(value = "/delayRepayAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "borrowNid",value = "项目编号"),
            @ApiImplicitParam(name = "delayDays",value = "延期天数"),
            @ApiImplicitParam(name = "repayTime",value = "放款时间")
    })
    public AdminResult<DelayRepayInfoBean> delayRepayAction(HttpServletRequest request ,@RequestBody Map map) {
        AdminResult<DelayRepayInfoBean>  result=new AdminResult<DelayRepayInfoBean> ();
        result.setStatus(AdminResult.FAIL);
        result.setStatusDesc(AdminResult.FAIL_DESC);
        String borrowNid = (String) map.get("borrowNid");
        String delayDays = (String) map.get("delayDays");
        String repayTime = (String) map.get("repayTime");
        DelayRepayInfoBean bean=borrowRepaymentService.updateBorrowRepayDelayDays( borrowNid,delayDays,repayTime);
        result.setStatus(AdminResult.SUCCESS);
        result.setStatusDesc(AdminResult.SUCCESS_DESC);
        result.setData(bean);
        return result;
    }

    /**
     * 迁移到还款画面
     *
     *
     *
     * @param request
     * @return 标签配置列表
     */
    @ApiOperation(value = "还款画面初始化", notes = "还款页面查询初始化")
    @PostMapping(value = "/initRepayAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @ApiImplicitParam(name = "borrowNid",value = "项目编号")
    public AdminResult<RepayInfoBean> moveRepayAction(HttpServletRequest request, @RequestBody Map map) {
        String borrowNid = (String) map.get("borrowNid");
        RepayInfoBean bean=borrowRepaymentService.getRepayInfo(borrowNid);
        AdminResult<RepayInfoBean> result=new AdminResult<RepayInfoBean> ();
        result.setData(bean);
        return result;
    }

    /**
     * @Description 数据导出--还款计划
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "数据导出--还款计划", notes = "带条件导出EXCEL")
    @PostMapping(value = "/exportRepayClkAct")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportRepayClkAct(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowRepaymentPlanRequestBean form) throws Exception {
        BorrowRepaymentPlanRequest copyForm=new BorrowRepaymentPlanRequest();
        logger.info("请求参数:" +JSONObject.toJSON(form));
        BeanUtils.copyProperties(form, copyForm);
        if(form.getVerifyTimeStartSrch() != null&&!"".equals(form.getVerifyTimeStartSrch())) {
            String date;
            date = form.getVerifyTimeStartSrch();
            copyForm.setVerifyTimeStartSrch(date);
        }
        if(form.getVerifyTimeEndSrch() != null&&!"".equals(form.getVerifyTimeEndSrch())) {
            String date;
            date = form.getVerifyTimeEndSrch();
            copyForm.setVerifyTimeEndSrch(date);
        }

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "还款计划导出数据";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        //请求第一页5000条
        copyForm.setLimitStart(1);
        copyForm.setLimitEnd(defaultRowMaxCount);
        // 查询
        AdminBorrowRepaymentResponse resultList = this.borrowRepaymentService.exportRepayClkActBorrowRepaymentInfoList(copyForm);

        Integer totalCount = resultList.getTotal();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList.getBorrowRepaymentPlanList());
        }
        for (int i = 1; i < sheetCount; i++) {
            Page page = Page.initPage(i+1, defaultRowMaxCount);
            copyForm.setLimitStart(page.getOffset());
            copyForm.setLimitEnd(page.getLimit());
            // 查询
            List<BorrowRepaymentPlanCustomizeVO> resultList2 = this.borrowRepaymentService.exportRepayClkActBorrowRepaymentInfoList(copyForm).getBorrowRepaymentPlanList();
            if (resultList2 != null && resultList2.size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultList2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("borrowNid","项目编号");
        map.put("instName","资产来源");
        map.put("userId","借款人ID");
        map.put("borrowUserName","借款人用户名");
//        map.put("borrowName","借款标题");
        map.put("projectTypeName","项目类型");
        map.put("borrowPeriod","借款期限");

        map.put("borrowApr","出借利率");
        map.put("borrowAccount","借款金额");
        map.put("borrowAccountYes","借到金额");
        map.put("repayType","还款方式");
        map.put("repayPeriod","还款期数");
        map.put("repayCapital","应还本金");
        map.put("repayInterest","应还利息");
        map.put("repayAccount","应还本息");
        map.put("repayFee","还款服务费");
        map.put("tiqiantianshu","提前天数");
        map.put("shaohuanlixi","少还利息");
        map.put("yanqitianshu","延期天数");
        map.put("yanqilixi","延期利息");
        map.put("yuqitianshu","逾期天数");
        map.put("yuqilixi","逾期利息");
        map.put("yinghuanzonge","应还总额");
        map.put("shihuanzonge","实还总额");
        map.put("status","还款状态");
        map.put("repayActionTime","实际还款日期");
        map.put("repayLastTime","应还日期");
        map.put("repayAccountCapitalWait","剩余待还本金");
        map.put("repayAccountInterestWait","剩余待还利息");
        map.put("repayMoneySource","还款来源");
        map.put("verifyTime","初审时间");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter borrowAprAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return object + "%";
            }
        };
        IValueFormatter valueFormatAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return object==null||"".equals(object) ? "0" : object.toString();
            }
        };
        IValueFormatter repayPeriodAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return "第" + object + "期";
            }
        };
        IValueFormatter statusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                if (null!=object) {
                    return"0".equals(object) ? "还款中" : "已还款";
                }
                return null;
            }
        };
        mapAdapter.put("borrowApr", borrowAprAdapter);
        mapAdapter.put("borrowAccount", valueFormatAdapter);
        mapAdapter.put("borrowAccountYes", valueFormatAdapter);
        mapAdapter.put("repayPeriod", repayPeriodAdapter);
        mapAdapter.put("repayCapital", valueFormatAdapter);
        mapAdapter.put("repayInterest", valueFormatAdapter);
        mapAdapter.put("repayAccount", valueFormatAdapter);
        mapAdapter.put("repayFee", valueFormatAdapter);
        mapAdapter.put("shaohuanlixi", valueFormatAdapter);
        mapAdapter.put("yanqilixi", valueFormatAdapter);
        mapAdapter.put("yuqilixi", valueFormatAdapter);
        mapAdapter.put("yinghuanzonge", valueFormatAdapter);
        mapAdapter.put("shihuanzonge", valueFormatAdapter);
        mapAdapter.put("status", statusAdapter);
        return mapAdapter;
    }

    /**
     * @Description 数据导出--还款信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "数据导出--还款信息", notes = "带条件导出EXCEL")
    @PostMapping(value = "/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowRepaymentRequestBean form) throws Exception {
        BorrowRepaymentRequest copyForm=new BorrowRepaymentRequest();
        BeanUtils.copyProperties(form, copyForm);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "还款信息导出数据";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();


        // 查询


        
        Integer totalCount = this.borrowRepaymentService.countBorrowRepayment(copyForm);

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = exportBuildMap();
        Map<String, IValueFormatter> mapValueAdapter = exportBuildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        for (int i = 1; i <= sheetCount; i++) {
            Page page = Page.initPage(i, defaultRowMaxCount);
            	page.setTotal(totalCount);
                copyForm.setLimitStart(page.getOffset());
                copyForm.setLimitEnd(page.getLimit());
            // 查询
            List<BorrowRepaymentCustomizeVO> resultList2 = this.borrowRepaymentService.selectBorrowRepaymentList(copyForm);
            if (resultList2 != null && resultList2.size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultList2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }


    private Map<String, String> exportBuildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("borrowNid","项目编号");
        map.put("instName","资产来源");
        map.put("planNid","智投编号");
        map.put("userId","借款人ID");
        map.put("borrowUserName","借款人用户名");
        map.put("borrowName","项目名称");
        map.put("projectTypeName","项目类型");
        map.put("partner","合作机构");
        map.put("borrowPeriod","借款期限");
        map.put("borrowApr","出借利率");
        map.put("borrowAccount","借款金额");
        map.put("borrowAccountYes","借到金额");
        map.put("repayType","还款方式");
        map.put("repayAccountCapital","应还本金");
        map.put("repayAccountInterest","应还利息");
        map.put("repayAccountAll","应还本息");
        map.put("repayFee","还款服务费");
        map.put("repayAccountCapitalYes","已还本金");
        map.put("repayAccountInterestYes","已还利息");
        map.put("repayAccountYes","已还本息");
        map.put("repayAccountCapitalWait","剩余待还本金");
        map.put("repayAccountInterestWait","剩余待还利息");
        map.put("repayAccountWait","未还本息");
        map.put("status","还款状态");
        map.put("repayLastTime","到期日");
        map.put("repayNextTime","下期还款日");
        map.put("repayMoneySource","还款来源");
        map.put("repayActionTime","实际还款时间");
        map.put("repayOrgUserName" ,"担保机构用户名");
        map.put("createUserName","添加标的人员");
        map.put("registUserName","备案人员");
        return map;
    }
    private Map<String, IValueFormatter> exportBuildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter borrowAprAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return object + "%";
            }
        };
        IValueFormatter valueFormatAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return object == null || "".equals(object) ? "0" : object.toString();
            }
        };
        IValueFormatter statusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                if (null != object) {
                    return "0".equals(object) ? "还款中" : "已还款";
                }
                return null;
            }
        };
        mapAdapter.put("borrowApr", borrowAprAdapter);
        mapAdapter.put("borrowAccount", valueFormatAdapter);
        mapAdapter.put("borrowAccountYes", valueFormatAdapter);
        mapAdapter.put("repayAccountCapital", valueFormatAdapter);
        mapAdapter.put("repayAccountInterest", valueFormatAdapter);
        mapAdapter.put("repayAccountAll", valueFormatAdapter);
        mapAdapter.put("repayFee", valueFormatAdapter);
        mapAdapter.put("repayAccountCapitalYes", valueFormatAdapter);
        mapAdapter.put("repayAccountInterestYes", valueFormatAdapter);
        mapAdapter.put("repayAccountYes", valueFormatAdapter);
        mapAdapter.put("repayAccountCapitalWait", valueFormatAdapter);
        mapAdapter.put("repayAccountInterestWait", valueFormatAdapter);
        mapAdapter.put("repayAccountWait", valueFormatAdapter);
        mapAdapter.put("status", statusAdapter);
        return mapAdapter;
    }
}
