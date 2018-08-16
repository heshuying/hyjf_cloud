package com.hyjf.admin.controller.productcenter.borrow.borrowrepayment;

import com.hyjf.admin.beans.BorrowRepaymentBean;
import com.hyjf.admin.beans.DelayRepayInfoBean;
import com.hyjf.admin.beans.request.BorrowRepaymentPlanRequestBean;
import com.hyjf.admin.beans.request.BorrowRepaymentRequestBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.BorrowRepaymentService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentRequest;
import com.hyjf.am.vo.admin.BorrowRepaymentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentPlanCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

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
        List<DropDownVO> repayTypeList = this.adminCommonService.selectBorrowStyleList();
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
    public AdminResult<DelayRepayInfoBean> initDelayRepayAction(HttpServletRequest request, @PathVariable  String borrowNid) {
        DelayRepayInfoBean bean= new DelayRepayInfoBean();
        bean=borrowRepaymentService.getDelayRepayInfo(borrowNid);
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
    public AdminResult<DelayRepayInfoBean> delayRepayAction(HttpServletRequest request, @PathVariable  String borrowNid,@PathVariable  String delayDays,@PathVariable  String repayTime) {
        DelayRepayInfoBean bean=borrowRepaymentService.updateBorrowRepayDelayDays( borrowNid,delayDays,repayTime);
        AdminResult<DelayRepayInfoBean>  result=new AdminResult<DelayRepayInfoBean> ();
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
        BeanUtils.copyProperties(form, copyForm);
        // 表格sheet名称
        String sheetName = "还款计划导出数据";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 查询
        List<BorrowRepaymentPlanCustomizeVO> resultList = this.borrowRepaymentService.exportRepayClkActBorrowRepaymentInfoList(copyForm);
        // 列头
        String[] titles = new String[] { "借款编号","资产来源" , "借款人ID", "借款人用户名", "借款标题",
                "项目类型", "借款期限", "年化收益", "借款金额", "借到金额",
                "还款方式", "还款期次", "应还本金", "应还利息", "应还本息",
                "还款服务费", "提前天数", "少还利息", "延期天数", "延期利息",
                "逾期天数", "逾期利息", "应还总额", "实还总额", "还款状态",
                "实际还款日期", "应还日期","还款来源","发布时间"};
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
                    BorrowRepaymentPlanCustomizeVO record = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 借款编号
                    if (celLength == 0) {
                        cell.setCellValue(record.getBorrowNid());
                    }
                    // 资产来源
                    else if(celLength == 1) {
                        cell.setCellValue(record.getInstName());
                    }
                    // 借款人ID
                    else if (celLength == 2) {
                        cell.setCellValue(record.getUserId());
                    }
                    // 借款人用户名
                    else if (celLength == 3) {
                        cell.setCellValue(record.getBorrowUserName());
                    }
                    // 借款标题
                    else if (celLength == 4) {
                        cell.setCellValue(record.getBorrowName());
                    }
                    // 项目类型
                    else if (celLength == 5) {
                        cell.setCellValue(record.getProjectTypeName());
                    }
                    // 借款期限
                    else if (celLength == 6) {
                        cell.setCellValue(record.getBorrowPeriod());
                    }
                    // 年化收益
                    else if (celLength == 7) {
                        cell.setCellValue(record.getBorrowApr() + "%");
                    }
                    // 借款金额
                    else if (celLength == 8) {
                        cell.setCellValue("".equals(record.getBorrowAccount()) ? 0 : Double.valueOf(record.getBorrowAccount()));
                    }
                    // 借到金额
                    else if (celLength == 9) {
                        cell.setCellValue("".equals(record.getBorrowAccountYes()) ? 0 : Double.valueOf(record.getBorrowAccountYes()));
                    }
                    // 还款方式
                    else if (celLength == 10) {
                        cell.setCellValue(record.getRepayType());
                    }
                    // 还款期次
                    else if (celLength == 11) {
                        cell.setCellValue("第" + record.getRepayPeriod() + "期");
                    }
                    // 应还本金
                    else if (celLength == 12) {
                        cell.setCellValue("".equals(record.getRepayCapital()) ? 0 : Double.valueOf(record.getRepayCapital()));
                    }
                    // 应还利息
                    else if (celLength == 13) {
                        cell.setCellValue("".equals(record.getRepayInterest()) ? 0 : Double.valueOf(record.getRepayInterest()));
                    }
                    // 应还本息
                    else if (celLength == 14) {
                        cell.setCellValue("".equals(record.getRepayAccount()) ? 0 : Double.valueOf(record.getRepayAccount()));
                    }
                    // 应收管理费
                    else if (celLength == 15) {
                        cell.setCellValue("".equals(record.getRepayFee()) ? 0 : Double.valueOf(record.getRepayFee()));
                    }
                    // 提前天数
                    else if (celLength == 16) {
                        cell.setCellValue(record.getTiqiantianshu());
                    }
                    // 少还利息
                    else if (celLength == 17) {
                        cell.setCellValue("".equals(record.getShaohuanlixi()) ? 0 : Double.valueOf(record.getShaohuanlixi()));
                    }
                    // 延期天数
                    else if (celLength == 18) {
                        cell.setCellValue(record.getYanqitianshu());
                    }
                    // 延期利息
                    else if (celLength == 19) {
                        cell.setCellValue("".equals(record.getYanqilixi()) ? 0 : Double.valueOf(record.getYanqilixi()));
                    }
                    // 逾期天数
                    else if (celLength == 20) {
                        cell.setCellValue(record.getYuqitianshu());
                    }
                    // 逾期利息
                    else if (celLength == 21) {
                        cell.setCellValue("".equals(record.getYuqilixi()) ? 0 : Double.valueOf(record.getYuqilixi()));
                    }
                    // 应还总额
                    else if (celLength == 22) {
                        cell.setCellValue("".equals(record.getYinghuanzonge()) ? 0 : Double.valueOf(record.getYinghuanzonge()));
                    }
                    // 实还总额
                    else if (celLength == 23) {
                        cell.setCellValue("".equals(record.getShihuanzonge()) ? 0 : Double.valueOf(record.getShihuanzonge()));
                    }
                    // 还款状态
                    else if (celLength == 24) {
                        if (StringUtils.isNotEmpty(record.getStatus())) {
                            cell.setCellValue("0".equals(record.getStatus()) ? "还款中" : "已还款");
                        }
                    }
                    // 实际还款日
                    else if (celLength == 25) {
                        cell.setCellValue(record.getRepayActionTime());
                    }
                    // 应还日期
                    else if (celLength == 26) {
                        cell.setCellValue(record.getRepayLastTime());
                    }
                    // 还款来源
                    else if(celLength == 27) {
                        cell.setCellValue(record.getRepayMoneySource());
                    }
                    // 应还日期
                    else if (celLength == 28) {
                        cell.setCellValue(record.getVerifyTime());
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
    @ApiOperation(value = "数据导出--还款信息", notes = "带条件导出EXCEL")
    @PostMapping(value = "/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowRepaymentRequestBean form) throws Exception {
        BorrowRepaymentRequest copyForm=new BorrowRepaymentRequest();
        BeanUtils.copyProperties(form, copyForm);
        // 表格sheet名称
        String sheetName = "还款信息导出数据";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 查询
        List<BorrowRepaymentCustomizeVO> resultList = this.borrowRepaymentService.selectBorrowRepaymentList(copyForm);
        // 列头
        String[] titles = new String[] { "借款编号", "资产来源", "计划编号", "借款人ID", "借款人用户名", "借款标题", "项目类型",
                "合作机构", "借款期限", "年化收益", "借款金额", "借到金额", "还款方式", "应还本金", "应还利息", "应还本息",
                "还款服务费", "已还本金", "已还利息", "已还本息", "未还本金", "未还利息","未还本息", "还款状态", "最后还款日",
                "下期还款日", "还款来源", "实际还款时间" ,"垫付机构用户名","添加标的人员","备案人员"};
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
                    BorrowRepaymentCustomizeVO record = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 借款编号
                    if (celLength == 0) {
                        cell.setCellValue(record.getBorrowNid());
                    }
                    /*-----add by LSY START-------------------------*/
                    // 资产来源
                    else if (celLength == 1) {
                        cell.setCellValue(record.getInstName());
                    }
                    /*-----add by LSY END-------------------------*/
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
                    // 合作机构
                    else if(celLength == 7){
                        cell.setCellValue(record.getPartner());
                    }
                    // 借款期限
                    else if (celLength == 8) {
                        cell.setCellValue(record.getBorrowPeriod());
                    }
                    // 年化收益
                    else if (celLength == 9) {
                        cell.setCellValue(record.getBorrowApr() + "%");
                    }
                    // 借款金额
                    else if (celLength == 10) {
                        cell.setCellValue(
                                "".equals(record.getBorrowAccount()) ? 0 : Double.valueOf(record.getBorrowAccount()));
                    }
                    // 借到金额
                    else if (celLength == 11) {
                        cell.setCellValue("".equals(record.getBorrowAccountYes()) ? 0
                                : Double.valueOf(record.getBorrowAccountYes()));
                    }
                    // 还款方式
                    else if (celLength == 12) {
                        cell.setCellValue(record.getRepayType());
                    }
                    // 应还本金
                    else if (celLength == 13) {
                        cell.setCellValue("".equals(record.getRepayAccountCapital()) ? 0
                                : Double.valueOf(record.getRepayAccountCapital()));
                    }
                    // 应还利息
                    else if (celLength == 14) {
                        cell.setCellValue("".equals(record.getRepayAccountInterest()) ? 0
                                : Double.valueOf(record.getRepayAccountInterest()));
                    }
                    // 应还本息
                    else if (celLength == 15) {
                        cell.setCellValue("".equals(record.getRepayAccountAll()) ? 0
                                : Double.valueOf(record.getRepayAccountAll()));
                    }
                    // 管理费
                    else if (celLength == 16) {
                        cell.setCellValue("".equals(record.getRepayFee()) ? 0 : Double.valueOf(record.getRepayFee()));
                    }
                    // 已还本金
                    else if (celLength == 17) {
                        cell.setCellValue("".equals(record.getRepayAccountCapitalYes()) ? 0
                                : Double.valueOf(record.getRepayAccountCapitalYes()));
                    }
                    // 已还利息
                    else if (celLength == 18) {
                        cell.setCellValue("".equals(record.getRepayAccountInterestYes()) ? 0
                                : Double.valueOf(record.getRepayAccountInterestYes()));
                    }
                    // 已还本息
                    else if (celLength == 19) {
                        cell.setCellValue("".equals(record.getRepayAccountYes()) ? 0
                                : Double.valueOf(record.getRepayAccountYes()));
                    }
                    // 未还本金
                    else if (celLength == 20) {
                        cell.setCellValue("".equals(record.getRepayAccountCapitalWait()) ? 0
                                : Double.valueOf(record.getRepayAccountCapitalWait()));
                    }
                    // 未还利息
                    else if (celLength == 21) {
                        cell.setCellValue("".equals(record.getRepayAccountInterestWait()) ? 0
                                : Double.valueOf(record.getRepayAccountInterestWait()));
                    }
                    // 未还本息
                    else if (celLength == 22) {
                        cell.setCellValue("".equals(record.getRepayAccountWait()) ? 0
                                : Double.valueOf(record.getRepayAccountWait()));
                    }
                    // 还款状态
                    else if (celLength == 23) {
                        if (StringUtils.isNotEmpty(record.getStatus())) {
                            cell.setCellValue("0".equals(record.getStatus()) ? "还款中" : "已还款");
                        }
                    }
                    // 最后还款日
                    else if (celLength == 24) {
                        cell.setCellValue(record.getRepayLastTime());
                    }
                    // 下期还款日
                    else if (celLength == 25) {
                        cell.setCellValue(record.getRepayNextTime());
                    }
                    // 还款来源
                    else if (celLength == 26) {
                        cell.setCellValue(record.getRepayMoneySource());
                    }
                    // 实际还款时间
                    else if (celLength == 27) {
                        cell.setCellValue(record.getRepayActionTime());
                    }
                    // 垫付机构用户名
                    else if (celLength == 28) {
                        cell.setCellValue(record.getRepayOrgUserName());
                    }
                    // 账户操作人
                    else if (celLength == 29) {
                        cell.setCellValue(record.getCreateUserName());
                    }
                    // 备案人员 
                    else if (celLength == 30) {
                        cell.setCellValue(record.getRegistUserName());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
