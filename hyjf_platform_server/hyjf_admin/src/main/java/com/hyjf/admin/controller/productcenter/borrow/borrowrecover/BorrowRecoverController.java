package com.hyjf.admin.controller.productcenter.borrow.borrowrecover;

import com.hyjf.admin.beans.BorrowRecoverBean;
import com.hyjf.admin.beans.request.BorrowRecoverRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowRecoverService;
import com.hyjf.am.resquest.admin.BorrowRecoverRequest;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BorrowRecoverController, v0.1 2018/7/2 10:13
 */
@Api(value = "产品中心-汇直投-放款明细",tags = "产品中心-汇直投-放款明细")
@RestController
@RequestMapping("/hyjf-admin/borrow/borrowrecover")
public class BorrowRecoverController extends BaseController {
    @Autowired
    private BorrowRecoverService borrowRecoverService;
    /** 查看权限 */
    public static final String PERMISSIONS = "borrowrecover";
    /**
     * 画面初始化
     *
     * @param request
     * @return 标签配置列表
     */
    @ApiOperation(value = "放款明细", notes = "放款明细页面查询初始化")
    @PostMapping(value = "/searchAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowRecoverBean> searchAction(HttpServletRequest request, @RequestBody @Valid BorrowRecoverRequestBean form) {


        BorrowRecoverRequest copyForm=new BorrowRecoverRequest();
        BeanUtils.copyProperties(form, copyForm);
        BorrowRecoverBean bean = borrowRecoverService.searchBorrowRecover(copyForm);
        Map<String, String> map=CacheUtil.getParamNameMap("LOAN_STATUS");
        bean.setLoanStarusList(map);
        // 资金来源
        List<HjhInstConfigVO> hjhInstConfigList = this.borrowRecoverService.selectHjhInstConfigByInstCode("-1");
        bean.setHjhInstConfigList(hjhInstConfigList);
        AdminResult<BorrowRecoverBean> result=new AdminResult<BorrowRecoverBean> ();
        result.setData(bean);
        return result;
    }


    /**
     * 带条件导出
     * 1.无法指定相应的列的顺序，
     * 2.无法配置，excel文件名，excel sheet名称
     * 3.目前只能导出一个sheet
     * 4.列的宽度的自适应，中文存在一定问题
     * 5.根据导出的业务需求最好可以在导出的时候输入起止页码，因为在大数据量的情况下容易造成卡顿
     * @param request
     * @return 带条件导出
     */
    @ApiOperation(value = "放款明细导出", notes = "带条件导出EXCEL")
    @PostMapping(value = "/exportAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportAction(HttpServletRequest request, HttpServletResponse response, @RequestBody @Valid BorrowRecoverRequestBean form) throws Exception {
        BorrowRecoverRequest copyForm=new BorrowRecoverRequest();
        BeanUtils.copyProperties(form, copyForm);
        // 表格sheet名称
        String sheetName = "放款明细";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, "UTF-8") + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 查询
        List<BorrowRecoverCustomizeVO> resultList = this.borrowRecoverService.exportBorrowRecoverList(copyForm);
        // 列头
        String[] titles = new String[] { "序号", "借款编号", "资产来源", "计划编号", "借款人ID", "借款人用户名", "是否受托支付", "受托支付用户名", "借款标题", "项目类型", "借款期限", "年化收益", "还款方式", "投资订单号", "放款订单号","合作机构编号",
                "投资人用户名", "投资人ID", "投资时间", "投资金额", "应放款金额", "放款服务费", "实际放款金额", "实收服务费", "放款状态", "放款时间" ,
                "投资人用户属性（投资时）", "推荐人用户属性（投资时）", "推荐人（投资时）", "推荐人ID（投资时）", "一级分部（投资时）", "二级分部（投资时）", "团队（投资时）"};
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
                    BorrowRecoverCustomizeVO record = resultList.get(i);

                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);

                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 借款编号
                    else if (celLength == 1) {
                        cell.setCellValue(record.getBorrowNid());
                    }
                    /*--------------add by LSY START-------------------*/
                    else if (celLength == 2) {
                        cell.setCellValue(record.getInstName());
                    }
                    /*--------------add by LSY END-------------------*/
                    // 计划编号
                    else if (celLength == 3) {
                        cell.setCellValue(record.getPlanNid());
                    }
                    // 借款人ID
                    else if (celLength == 4) {
                        cell.setCellValue(record.getUserId());
                    }
                    // 借款人用户名
                    else if (celLength == 5) {
                        cell.setCellValue(record.getUsername());
                    }
                    // 是否受托支付 2017.11.7新增
                    else if (celLength == 6) {
                        Integer isStzf = record.getEntrustedFlg();
                        String isStzf_str="否";
                        if (isStzf != null && isStzf - 1 == 0) {
                            isStzf_str = "是";
                        }
                        cell.setCellValue(isStzf_str);
                    }
                    // 受托支付用户名 2017.11.7新增
                    else if (celLength == 7) {
                        cell.setCellValue(record.getEntrustedUserName());
                    }
                    // 借款标题
                    else if (celLength == 8) {
                        cell.setCellValue(record.getBorrowName());
                    }
                    // 项目类型
                    else if (celLength == 9) {
                        cell.setCellValue(record.getBorrowProjectTypeName());
                    }
                    // 借款期限
                    else if (celLength == 10) {
                        cell.setCellValue(record.getBorrowPeriod());
                    }
                    // 年化收益
                    else if (celLength == 11) {
                        cell.setCellValue(record.getBorrowApr());
                    }
                    // 还款方式
                    else if (celLength == 12) {
                        cell.setCellValue(record.getBorrowStyleName());
                    }
                    // 投资订单号
                    else if (celLength == 13) {
                        cell.setCellValue(record.getOrderNum());
                    }
                    // 投资订单号
                    else if (celLength == 14) {
                        cell.setCellValue(record.getLoanOrdid());
                    }
                    // 合作机构编号
                    else if (celLength == 15) {
                        cell.setCellValue(record.getInstCode());
                    }
                    // 投资人用户名
                    else if (celLength == 16) {
                        cell.setCellValue(record.getTenderUsername());
                    }
                    // 投资人ID
                    else if (celLength == 17) {
                        cell.setCellValue(record.getTenderUserId());
                    }
                    // 投资时间
                    else if (celLength == 18) {
                        cell.setCellValue(record.getCreateTime());
                    }
                    // 投资金额
                    else if (celLength == 19) {
                        cell.setCellValue(record.getAccount());
                    }
                    // 应放款金额
                    else if (celLength == 20) {
                        cell.setCellValue(record.getAccountYes());
                    }
                    // 应收服务费
                    else if (celLength == 21) {
                        cell.setCellValue(record.getLoanFee());
                    }
                    // 实际放款金额
                    else if (celLength == 22) {
                        cell.setCellValue(record.getRecoverPrice());
                    }
                    // 实收服务费
                    else if (celLength == 23) {
                        cell.setCellValue(record.getServicePrice());
                    }
                    // 放款状态
                    else if (celLength == 24) {
                        cell.setCellValue(record.getIsRecover());
                    }
                    // 放款时间
                    else if (celLength == 25) {
                        cell.setCellValue(record.getTimeRecover());
                    }
                    // 投资人用户属性（投资时）
                    else if (celLength == 26) {
                        cell.setCellValue(record.getTenderUserAttribute());
                    }
                    // 推荐人用户属性（投资时）
                    else if (celLength == 27) {
                        cell.setCellValue(record.getInviteUserAttribute());
                    }
                    // 推荐人（投资时）
                    else if (celLength == 28) {
                        cell.setCellValue(record.getTenderReferrerUsername());
                    }
                    // 推荐人ID（投资时）
                    else if (celLength == 29) {
                        cell.setCellValue(record.getTenderReferrerUserId());
                    }
                    // 一级分部（投资时）
                    else if (celLength == 30) {
                        cell.setCellValue(record.getDepartmentLevel1Name());
                    }
                    // 二级分部（投资时）
                    else if (celLength == 31) {
                        cell.setCellValue(record.getDepartmentLevel2Name());
                    }
                    // 团队（投资时）
                    else if (celLength == 32) {
                        cell.setCellValue(record.getTeamName());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}
