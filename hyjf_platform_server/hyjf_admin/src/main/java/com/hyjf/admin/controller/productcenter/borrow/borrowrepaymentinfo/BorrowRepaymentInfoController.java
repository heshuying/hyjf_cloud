package com.hyjf.admin.controller.productcenter.borrow.borrowrepaymentinfo;

import com.hyjf.admin.beans.BorrowRepaymentInfoBean;
import com.hyjf.admin.beans.request.BorrowRepaymentInfoRequsetBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowRepaymentInfoService;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoCustomizeVO;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoController, v0.1 2018/7/7 14:14
 */
@Api(value = "产品中心-汇直投-还款明细",tags ="产品中心-汇直投-还款明细" )
@RestController
@RequestMapping("/hyjf-admin/borrow/borrowrepaymentinfo")
public class BorrowRepaymentInfoController extends BaseController {


    @Autowired
    private BorrowRepaymentInfoService borrowRepaymentInfoService;
    /** 查看权限 */
    public static final String PERMISSIONS = "borrowrepayment";
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
        List<HjhInstConfigVO> hjhInstConfigList = this.borrowRepaymentInfoService.selectHjhInstConfigByInstCode("-1");
        bean.setHjhInstConfigList(hjhInstConfigList);
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
    @ApiOperation(value = "数据导出--还款明细", notes = "带条件导出EXCEL")
    @PostMapping(value = "/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
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
                e.printStackTrace();
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
                e.printStackTrace();
            }

        }
        // 表格sheet名称
        String sheetName = "还款明细导出数据";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 查询
        List<BorrowRepaymentInfoCustomizeVO> resultList = this.borrowRepaymentInfoService.selectBorrowRepaymentList(copyForm);
        // 列头
        String[] titles = new String[] { "借款编号", "资产来源", "计划编号", "借款人ID", "借款人用户名", "借款标题", "项目类型",
                "借款期限", "年化收益", "借款金额", "借到金额", "还款方式", "投资人用户名", "投资人ID", "投资人用户属性（当前）",
                "投资人所属一级分部（当前）", "投资人所属二级分部（当前）", "投资人所属团队（当前）", "推荐人用户名（当前）",
                "推荐人姓名（当前）", "推荐人所属一级分部（当前）", "推荐人所属二级分部（当前）", "推荐人所属团队（当前）", "投资金额",
                "应还本金","应还利息", "应还本息", "还款服务费", "已还本金", "已还利息", "已还本息", "未还本金", "未还利息", "未还本息",
                "还款状态", "最后还款日","实际还款时间"};
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
                    // 借款编号计划编号
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
                    // 年化收益
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
                    // 投资人用户名
                    else if (celLength == 12) {
                        cell.setCellValue(record.getRecoverUserName());
                    }
                    // 投资人ID
                    else if (celLength == 13) {
                        cell.setCellValue(record.getRecoverUserId());
                    }
                    // 投资人用户属性（当前）
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
                    // 投资人所属一级分部（当前）
                    else if (celLength == 15) {
                        cell.setCellValue(record.getRecoverRegionName());
                    }
                    // 投资人所属二级分部（当前）
                    else if (celLength == 16) {
                        cell.setCellValue(record.getRecoverBranchName());
                    }
                    // 投资人所属团队（当前）
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
                    // 投资金额
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

                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
