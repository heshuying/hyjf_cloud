package com.hyjf.admin.controller.productcenter.borrow.borrowrepaymentinfo;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.BorrowRepaymentInfoBean;
import com.hyjf.admin.beans.request.BorrowRepaymentInfoRequsetBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.config.SystemConfig;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowRepaymentInfoService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoController, v0.1 2018/7/7 14:14
 */
@Api(value = "产品中心-汇直投-还款明细",tags ="产品中心-汇直投-还款明细" )
@RestController
@RequestMapping("/hyjf-admin/borrow/borrowrepaymentinfo")
public class BorrowRepaymentInfoController extends BaseController {

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private BorrowRepaymentInfoService borrowRepaymentInfoService;
    /** 查看权限 */
    public static final String PERMISSIONS = "borrowRepayMentInfo";
    /**
     * 画面初始化
     *
     * @param request
     */
    @ApiOperation(value = "还款明细", notes = "还款明细页面查询初始化")
    @PostMapping(value = "/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowRepaymentInfoBean> searchAction(HttpServletRequest request, @RequestBody @Valid BorrowRepaymentInfoRequsetBean form) {
        BorrowRepaymentInfoRequset copyForm=new BorrowRepaymentInfoRequset();
        BeanUtils.copyProperties(form, copyForm);
        BorrowRepaymentInfoBean bean = borrowRepaymentInfoService.selectBorrowRepaymentInfoListForView(copyForm);
        // 资金来源
        List<HjhInstConfigVO> hjhInstConfigList = this.borrowRepaymentInfoService.selectHjhInstConfigByInstCode("-1");
        bean.setHjhInstConfigList(ConvertUtils.convertListToDropDown(hjhInstConfigList,"instCode","instName"));
        AdminResult<BorrowRepaymentInfoBean> result=new AdminResult<BorrowRepaymentInfoBean> ();
        result.setData(bean);
        return result;
    }


    /**
     * @Description 数据导出--还款计划
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
//    @ApiOperation(value = "数据导出--还款明细", notes = "带条件导出EXCEL")
//    @PostMapping(value = "/exportAction")
//    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowRepaymentInfoRequsetBean form) throws Exception {
        BorrowRepaymentInfoRequset copyForm=new BorrowRepaymentInfoRequset();
        BeanUtils.copyProperties(form, copyForm);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if(copyForm.getYesTimeStartSrch() != null&&!"".equals(copyForm.getYesTimeStartSrch())) {
            Date date;
            try {
                date = simpleDateFormat.parse(copyForm.getYesTimeStartSrch());

                copyForm.setYesTimeStartSrch(String.valueOf(date.getTime()/1000));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }

        }
        if(copyForm.getYesTimeEndSrch() != null&&!"".equals(copyForm.getYesTimeStartSrch())) {
            Date date;
            try {
                date = simpleDateFormat.parse(copyForm.getYesTimeEndSrch());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, 1);

                copyForm.setYesTimeEndSrch(String.valueOf(cal.getTime().getTime()/1000));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }

        }
        // 表格sheet名称
        String sheetName = "还款明细导出数据";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 查询
        List<BorrowRepaymentInfoCustomizeVO> resultList = this.borrowRepaymentInfoService.selectBorrowRepaymentList(copyForm);
        // 列头
        String[] titles = new String[] { "项目编号", "资产来源", "智投编号", "借款人ID", "借款人用户名", "借款标题", "项目类型",
                "借款期限", "出借利率", "借款金额", "借到金额", "还款方式", "出借人用户名", "出借人ID", "出借人用户属性（当前）",
                "出借人所属一级分部（当前）", "出借人所属二级分部（当前）", "出借人所属团队（当前）", "推荐人用户名（当前）",
                "推荐人姓名（当前）", "推荐人所属一级分部（当前）", "推荐人所属二级分部（当前）", "推荐人所属团队（当前）", "出借金额",
                "应还本金","应还利息", "应还本息", "还款服务费", "已还本金", "已还利息", "已还本息", "未还本金", "未还利息", "未还本息",
                "还款状态", "最后还款日","实际还款时间", "还款冻结订单号"};
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");

        if (resultList != null && resultList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < resultList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }
                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    BorrowRepaymentInfoCustomizeVO record = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 项目编号计划编号
                    if (celLength == 0) {
                        cell.setCellValue(record.getBorrowNid());
                    }
                    /*----------add by LSY START---------------------*/
                    // 资产来源
                    else if (celLength == 1) {
                        cell.setCellValue(record.getInstName());
                    }
                    /*----------add by LSY END---------------------*/
                    // 计划编号
                    else if (celLength == 2) {
                        cell.setCellValue(record.getPlanNid());
                    }
                    // 借款人ID
                    else if (celLength == 3) {
                        cell.setCellValue(record.getUserId());
                    }
                    // 借款人用户名
                    else if (celLength == 4) {
                        cell.setCellValue(record.getBorrowUserName());
                    }
                    // 借款标题
                    else if (celLength == 5) {
                        cell.setCellValue(record.getBorrowName());
                    }
                    // 项目类型
                    else if (celLength == 6) {
                        cell.setCellValue(record.getProjectTypeName());
                    }
                    // 借款期限
                    else if (celLength == 7) {
                        if("endday".equals(record.getBorrowStyle())) {
                            cell.setCellValue(record.getBorrowPeriod() + "天");
                        }else{
                            cell.setCellValue(record.getBorrowPeriod() + "个月");
                        }
                    }
                    // 出借利率
                    else if (celLength == 8) {
                        cell.setCellValue(record.getBorrowApr() + "%");
                    }
                    // 借款金额
                    else if (celLength == 9) {
                        cell.setCellValue(
                                "".equals(record.getBorrowAccount()) ? 0 : Double.valueOf(record.getBorrowAccount()));
                    }
                    // 借到金额
                    else if (celLength == 10) {
                        cell.setCellValue("".equals(record.getBorrowAccountYes()) ? 0
                                : Double.valueOf(record.getBorrowAccountYes()));
                    }
                    // 还款方式
                    else if (celLength == 11) {
                        cell.setCellValue(record.getRepayType());
                    }
                    // 出借人用户名
                    else if (celLength == 12) {
                        cell.setCellValue(record.getRecoverUserName());
                    }
                    // 出借人ID
                    else if (celLength == 13) {
                        cell.setCellValue(record.getRecoverUserId());
                    }
                    // 出借人用户属性（当前）
                    else if (celLength == 14) {
                        if ("0".equals(record.getRecoverUserAttribute())) {
                            cell.setCellValue("无主单");
                        } else if ("1".equals(record.getRecoverUserAttribute())) {
                            cell.setCellValue("有主单");
                        } else if ("2".equals(record.getRecoverUserAttribute())) {
                            cell.setCellValue("线下员工");
                        } else if ("3".equals(record.getRecoverUserAttribute())) {
                            cell.setCellValue("线上员工");
                        }
                    }
                    // 出借人所属一级分部（当前）
                    else if (celLength == 15) {
                        cell.setCellValue(record.getRecoverRegionName());
                    }
                    // 出借人所属二级分部（当前）
                    else if (celLength == 16) {
                        cell.setCellValue(record.getRecoverBranchName());
                    }
                    // 出借人所属团队（当前）
                    else if (celLength == 17) {
                        cell.setCellValue(record.getRecoverDepartmentName());
                    }
                    // 推荐人用户名（当前）
                    else if (celLength == 18) {
                        cell.setCellValue(record.getReferrerName());
                    }
                    // 推荐人姓名（当前）
                    else if (celLength == 19) {
                        cell.setCellValue(record.getReferrerTrueName());
                    }
                    // 推荐人所属一级分部（当前）
                    else if (celLength == 20) {
                        cell.setCellValue(record.getReferrerRegionName());
                    }
                    // 推荐人所属二级分部（当前）
                    else if (celLength == 21) {
                        cell.setCellValue(record.getReferrerBranchName());
                    }
                    // 推荐人所属团队（当前）
                    else if (celLength == 22) {
                        cell.setCellValue(record.getReferrerDepartmentName());
                    }
                    // 出借金额
                    else if (celLength == 23) {
                        cell.setCellValue(
                                "".equals(record.getRecoverTotal()) ? 0 : Double.valueOf(record.getRecoverTotal()));
                    }
                    // 应还本金
                    else if (celLength == 24) {
                        cell.setCellValue(
                                "".equals(record.getRecoverCapital()) ? 0 : Double.valueOf(record.getRecoverCapital()));
                    }
                    // 应还利息
                    else if (celLength == 25) {
                        cell.setCellValue("".equals(record.getRecoverInterest()) ? 0
                                : Double.valueOf(record.getRecoverInterest()));
                    }
                    // 应还本息
                    else if (celLength == 26) {
                        cell.setCellValue(
                                "".equals(record.getRecoverAccount()) ? 0 : Double.valueOf(record.getRecoverAccount()));
                    }
                    // 管理费
                    else if (celLength == 27) {
                        cell.setCellValue(
                                "".equals(record.getRecoverFee()) ? 0 : Double.valueOf(record.getRecoverFee()));
                    }
                    // 已还本金
                    else if (celLength == 28) {
                        cell.setCellValue("".equals(record.getRecoverCapitalYes()) ? 0
                                : Double.valueOf(record.getRecoverCapitalYes()));
                    }
                    // 已还利息
                    else if (celLength == 29) {
                        cell.setCellValue("".equals(record.getRecoverInterestYes()) ? 0
                                : Double.valueOf(record.getRecoverInterestYes()));
                    }
                    // 已还本息
                    else if (celLength == 30) {
                        cell.setCellValue("".equals(record.getRecoverAccountYes()) ? 0
                                : Double.valueOf(record.getRecoverAccountYes()));
                    }
                    // 未还本金
                    else if (celLength == 31) {
                        cell.setCellValue("".equals(record.getRecoverCapitalWait()) ? 0
                                : Double.valueOf(record.getRecoverCapitalWait()));
                    }
                    // 未还利息
                    else if (celLength == 32) {
                        cell.setCellValue("".equals(record.getRecoverInterestWait()) ? 0
                                : Double.valueOf(record.getRecoverInterestWait()));
                    }
                    // 未还本息
                    else if (celLength == 33) {
                        cell.setCellValue("".equals(record.getRecoverAccountWait()) ? 0
                                : Double.valueOf(record.getRecoverAccountWait()));
                    }
                    // 还款状态
                    else if (celLength == 34) {
                        if (StringUtils.isNotEmpty(record.getStatus())) {
                            cell.setCellValue("0".equals(record.getStatus()) ? "还款中" : "已还款");
                        }
                    }
                    // 最后还款日
                    else if (celLength == 35) {
                        cell.setCellValue(record.getRecoverLastTime());
                    }else if (celLength == 36) {
                        cell.setCellValue(record.getRepayActionTime());
                    }
                    // 还款冻结订单号
                    else if (celLength == 37){
                        cell.setCellValue(StringUtils.isBlank(record.getFreezeOrderId())?"":record.getFreezeOrderId());
//                        cell.setCellValue(StringUtils.isBlank(record.getFreezeOrderId())?"":record.getFreezeOrderId());
                    }

                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

    /**
     * @Description 数据导出--还款计划
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @ApiOperation(value = "数据导出--还款明细", notes = "带条件导出EXCEL")
    @PostMapping(value = "/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportToExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowRepaymentInfoRequsetBean form) throws Exception {

        BorrowRepaymentInfoRequset copyForm=new BorrowRepaymentInfoRequset();
        BeanUtils.copyProperties(form, copyForm);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 是否具有组织机构查看权限
        String isOrganizationView = form.getIsOrganizationView();

        if(copyForm.getYesTimeStartSrch() != null&&!"".equals(copyForm.getYesTimeStartSrch())) {
            Date date;
            try {
                date = simpleDateFormat.parse(copyForm.getYesTimeStartSrch());

                copyForm.setYesTimeStartSrch(String.valueOf(date.getTime()/1000));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }

        }
        if(copyForm.getYesTimeEndSrch() != null&&!"".equals(copyForm.getYesTimeStartSrch())) {
            Date date;
            try {
                date = simpleDateFormat.parse(copyForm.getYesTimeEndSrch());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, 1);

                copyForm.setYesTimeEndSrch(String.valueOf(cal.getTime().getTime()/1000));
            } catch (ParseException e) {
                logger.error(e.getMessage());
            }

        }

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "原始债权还款明细数据";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        // 查询
//        List<BorrowRepaymentInfoCustomizeVO> resultList = this.borrowRepaymentInfoService.selectBorrowRepaymentList(copyForm);
//        Integer totalCount = resultList.size();
        Integer totalCount = borrowRepaymentInfoService.countBorrowRepaymentInfo(copyForm);

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap(isOrganizationView);
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }else {
            for (int i = 1; i <= sheetCount; i++) {
                copyForm.setPageSize(defaultRowMaxCount);
                copyForm.setCurrPage(i);
                List<BorrowRepaymentInfoCustomizeVO> resultList2 = this.borrowRepaymentInfoService.selectBorrowRepaymentList(copyForm);
                if (resultList2 != null && resultList2.size() > 0) {
                    sheetNameTmp = sheetName + "_第" + (i) + "页";
                    helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, resultList2);
                } else {
                    break;
                }
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

//    private Map<String, String> buildMap(String isOrganizationView) {
//        Map<String, String> map = Maps.newLinkedHashMap();
//        map.put("borrowNid","项目编号");
//        map.put("instName","资产来源");
//        map.put("planNid","智投编号");
//        map.put("userId","借款人ID");
//        map.put("borrowUserName","借款人用户名");
//        map.put("borrowName","借款标题");
//        map.put("projectTypeName","项目类型");
//        map.put("borrowPeriod","项目期限");
//        map.put("borrowApr","出借利率");
//        map.put("borrowAccount","借款金额");
//        map.put("borrowAccountYes","借到金额");
//        map.put("repayType","还款方式");
//        map.put("recoverUserName","出借人用户名");
//        map.put("recoverUserId","出借人ID");
//        map.put("recoverUserAttribute","出借人用户属性（当前）");
//        if (StringUtils.isNotBlank(isOrganizationView)) {
//            map.put("recoverRegionName", "出借人所属一级分部（当前）");
//            map.put("recoverBranchName", "出借人所属二级分部（当前）");
//            map.put("recoverDepartmentName", "出借人所属团队（当前）");
//        }
//        map.put("referrerName","推荐人用户名（当前）");
//        map.put("referrerTrueName","推荐人姓名（当前）");
//        if (StringUtils.isNotBlank(isOrganizationView)) {
//            map.put("referrerRegionName", "推荐人所属一级分部（当前）");
//            map.put("referrerBranchName", "推荐人所属二级分部（当前）");
//            map.put("referrerDepartmentName", "推荐人所属团队（当前）");
//        }
//        map.put("recoverTotal","出借金额");
//        map.put("recoverCapital","应回本金");
//        map.put("recoverInterest","应回利息");
//        map.put("recoverAccount","应回本息");
//        map.put("recoverFee","还款服务费");
//        map.put("recoverCapitalYes","已回本金");
//        map.put("recoverInterestYes","已回利息");
//        map.put("recoverAccountYes","已回本息");
//        map.put("recoverCapitalWait","剩余待回本金");
//        map.put("recoverInterestWait","剩余待回利息");
//        map.put("recoverAccountWait","剩余待回本息");
//        map.put("status","回款状态");
//        map.put("recoverLastTime","到期日");
//        map.put("repayActionTime","实际回款时间");
//        map.put("freezeOrderId","还款冻结订单号");
//        return map;
//    }
    private Map<String, String> buildMap(String isOrganizationView) {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("nid","出借订单号");
        map.put("repayOrdid","还款订单号");
        map.put("borrowNid","项目编号");
        map.put("instName","资产来源");
        map.put("planNid","智投编号");
        map.put("userId","借款人ID");
        map.put("borrowUserName","借款人用户名");
        map.put("borrowName","项目名称");
        map.put("projectTypeName","项目类型");
        map.put("borrowPeriodExcl","项目期限");
        map.put("borrowApr","出借利率");
        map.put("borrowAccount","借款金额");
        map.put("borrowAccountYes","借到金额");
        map.put("repayType","还款方式");
        map.put("recoverUserName","出借人用户名");
        map.put("recoverUserId","出借人ID");
        map.put("recoverUserAttribute","出借人用户属性（当时）");
        if (StringUtils.isNotBlank(isOrganizationView)) {
            map.put("recoverRegionName", "出借人所属一级分部（当时）");
            map.put("recoverBranchName", "出借人所属二级分部（当时）");
            map.put("recoverDepartmentName", "出借人所属团队（当时）");
        }
        map.put("referrerName","推荐人用户名（当时）");
        map.put("referrerTrueName","推荐人姓名（当时）");
        if (StringUtils.isNotBlank(isOrganizationView)) {
            map.put("referrerRegionName", "推荐人所属一级分部（当前）");
            map.put("referrerBranchName", "推荐人所属二级分部（当前）");
            map.put("referrerDepartmentName", "推荐人所属团队（当前）");
        }
        map.put("recoverTotal","出借金额");
       // map.put("recoverTotal","持有金额");
        map.put("repayAccountCapital","应回本金");
        map.put("repayAccountInterest","应回利息");
        map.put("repayAccountAll","应回本息");
        map.put("borrowManager","还款服务费");
        map.put("recoverPeriod","还款期次");
        map.put("recoverCapital","当期应回本金");
        map.put("recoverInterest","当期应回利息");
        map.put("recoverAccount","当期应回本息");
        map.put("recoverFee","当期还款服务费");
        map.put("chargeDays","当期提前天数");
        map.put("jianxi","当期提前还款减息");
        map.put("chargeInterest","提前还款违约金");
        map.put("lateDays","逾期天数");
        map.put("lateInterest","逾期违约金");
        map.put("recoverCapitalYes","当期已回本金");
        map.put("recoverInterestYes","当期已回利息");
        map.put("recoverAccountYes","当期已回本息");
        map.put("recoverCapitalWait","当期剩余待回本金");
        map.put("recoverInterestWait","当期剩余待回利息");
        map.put("recoverAccountWait","当期剩余待回本息");
        map.put("repayMoneySource","还款来源");
        map.put("repayUsername","当期还款人");
        map.put("status","还款状态");
        map.put("autoRepay","平台还款方式");
        map.put("submitter","发起人");
        map.put("recoverLastTime","应还日期");
        map.put("repayActionTime","实还时间");
     //   map.put("freezeOrderId","还款冻结订单号");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
//        IValueFormatter borrowStyleAdapter = new IValueFormatter() {
//            @Override
//            public String format(Object object) {
//                if("endday".equals(object)) {
//                    return object+ "天";
//                }else{
//                    return object+"个月";
//                }
//            }
//        };
        IValueFormatter borrowAprAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return object + "%";
            }
        };
        IValueFormatter valueFormatAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                return "".equals(object) ? "0" : object.toString();
            }
        };
        IValueFormatter recoverUserAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                if ("0".equals(object)) {
                    return "无主单";
                } else if ("1".equals(object)) {
                    return "有主单";
                } else if ("2".equals(object)) {
                    return "线下员工";
                } else if ("3".equals(object)) {
                    return "线上员工";
                }
                return null;
            }
        }; IValueFormatter statusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                if (null!=object) {
                	if("0".equals(object) ) {
                		return "还款中";
                	}else if ("1".equals(object) ) {
                		return "已还款";
                	}else {
                		  return"还款失败";
                	}
                
                }
                return null;
            }
        };
       // mapAdapter.put("borrowPeriod", borrowStyleAdapter);
        mapAdapter.put("borrowApr", borrowAprAdapter);
        mapAdapter.put("borrowAccount", valueFormatAdapter);
        mapAdapter.put("borrowAccountYes", valueFormatAdapter);
        mapAdapter.put("recoverTotal", valueFormatAdapter);
        mapAdapter.put("recoverCapital", valueFormatAdapter);
        mapAdapter.put("recoverInterest", valueFormatAdapter);
        mapAdapter.put("recoverAccount", valueFormatAdapter);
        mapAdapter.put("recoverFee", valueFormatAdapter);
        mapAdapter.put("recoverCapitalYes", valueFormatAdapter);
        mapAdapter.put("recoverInterestYes", valueFormatAdapter);
        mapAdapter.put("recoverAccountYes", valueFormatAdapter);
        mapAdapter.put("recoverCapitalWait", valueFormatAdapter);
        mapAdapter.put("recoverInterestWait", valueFormatAdapter);
        mapAdapter.put("recoverAccountWait", valueFormatAdapter);
        mapAdapter.put("recoverUserAttribute", recoverUserAdapter);
        mapAdapter.put("status", statusAdapter);
        return mapAdapter;
    }
}
