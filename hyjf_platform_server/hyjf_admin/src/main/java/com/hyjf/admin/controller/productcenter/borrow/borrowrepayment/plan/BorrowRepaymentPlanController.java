package com.hyjf.admin.controller.productcenter.borrow.borrowrepayment.plan;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.BorrowRepaymentPlanBean;
import com.hyjf.admin.beans.request.BorrowRepaymentPlanRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowRepaymentService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.BorrowRepaymentRequest;
import com.hyjf.am.vo.admin.BorrowRepaymentPlanCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
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
@Api(value = "产品中心-汇直投-还款信息还款计划",tags ="产品中心-汇直投-还款信息还款计划")
@RestController
@RequestMapping("/hyjf-admin/borrow/borrowrepayment/plan")
public class BorrowRepaymentPlanController extends BaseController {
    @Autowired
    private BorrowRepaymentService borrowRepaymentService;

    /** 查看权限 */
    public static final String PERMISSIONS = "borrowrepayment";
    /**
     * 画面初始化
     *
     * @param request
     */
    @ApiOperation(value = "还款信息还款计划", notes = "还款信息还款计划页面查询初始化")
    @PostMapping(value = "/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowRepaymentPlanBean> searchAction(HttpServletRequest request, @RequestBody @Valid BorrowRepaymentPlanRequestBean form) {


        BorrowRepaymentRequest copyForm=new BorrowRepaymentRequest();
        BeanUtils.copyProperties(form, copyForm);
        BorrowRepaymentPlanBean bean = borrowRepaymentService.searchBorrowRepaymentPlan(copyForm);

        // 资金来源
        List<HjhInstConfigVO> hjhInstConfigList = this.borrowRepaymentService.selectHjhInstConfigByInstCode("-1");
        bean.setHjhInstConfigList(ConvertUtils.convertListToDropDown(hjhInstConfigList,"instCode","instName"));
        AdminResult<BorrowRepaymentPlanBean> result=new AdminResult<BorrowRepaymentPlanBean> ();
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
    @PostMapping(value = "/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowRepaymentPlanRequestBean form) throws Exception {
        BorrowRepaymentRequest copyForm=new BorrowRepaymentRequest();
        BeanUtils.copyProperties(form, copyForm);
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
        copyForm.setPageSize(defaultRowMaxCount);
        copyForm.setCurrPage(1);
        // 查询
        List<BorrowRepaymentPlanCustomizeVO> recordList = this.borrowRepaymentService.selectBorrowRepaymentPlanList(copyForm);
        Integer totalCount = recordList.size();

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, recordList);
        }
        for (int i = 1; i < sheetCount; i++) {

            copyForm.setPageSize(defaultRowMaxCount);
            copyForm.setCurrPage(i+1);
            List<BorrowRepaymentPlanCustomizeVO> recordList2 = this.borrowRepaymentService.selectBorrowRepaymentPlanList(copyForm);
            if (recordList2 != null && recordList2.size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i + 1) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  recordList2);
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("borrowNid","项目编号");
        //智投编号planNid
        map.put("planNid","智投编号");
        map.put("instName","资产来源");
        map.put("userId","借款人ID");
        map.put("borrowUserName","借款人用户名");
      //受托支付收款人用户名 
        map.put("entrustedUserName","受托支付收款人用户名");
        map.put("repayOrgName","担保人");
        map.put("borrowName","项目名称");
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
        map.put("shaohuanlixi","提前还款减息");
        //提前还款违约金 shihuanzonge
        map.put("shihuanzonge","提前还款违约金");
//        map.put("yanqitianshu","延期天数");
//        map.put("yanqilixi","延期利息");
        map.put("yuqitianshu","逾期天数");
        map.put("yuqilixi","逾期违约金");
        map.put("repayAccount","应还总额");
        //实还本金 repayAccountCapitalYes
        //实还利息 repayAccountInterestYes
        //实还服务费 yihuanfuwufei
        map.put("repayAccountCapitalYes","实还本金");
        map.put("repayAccountInterestYes","实还利息");
        map.put("yihuanfuwufei","实还服务费");
        map.put("yinghuanzonge","实还总额");
        map.put("extraYieldRepayStatus","还款状态");
        map.put("repayActionTime","实际还款日期");
        map.put("repayLastTime","应还日期");
        map.put("repayMoneySource","还款来源");
        map.put("userName","还款人");
        map.put("submitter","发起人");
        map.put("verifyTime","发标时间");
        map.put("borrowFullTime","满标时间");
        map.put("recoverLastTime","放款时间");
        map.put("repayAccountCapitalWait","剩余待还本金");
        map.put("repayAccountInterestWait","剩余待还利息");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter borrowPeriodAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return object+"个月";
            }
        };
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
		IValueFormatter timeAdapter = new IValueFormatter() {
			@Override
			public String format(Object object) {
				String value = (String) object;
				return GetDate.timestamptoNUMStrYYYYMMDDHHMMSS(Integer.valueOf(value));
			}
		};
        mapAdapter.put("borrowPeriod", borrowPeriodAdapter);
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
       // mapAdapter.put("status", statusAdapter);
        mapAdapter.put("repayAccountCapitalWait",valueFormatAdapter);
        mapAdapter.put("repayAccountInterestWait",valueFormatAdapter);
        mapAdapter.put("verifyTime",timeAdapter);
        mapAdapter.put("borrowFullTime",timeAdapter);
        mapAdapter.put("recoverLastTime",timeAdapter);
        return mapAdapter;
    }

}
