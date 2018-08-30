/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import com.hyjf.admin.beans.InvestorDebtBean;
import com.hyjf.admin.beans.request.BorrowInvestDebtInfoRequest;
import com.hyjf.admin.beans.request.BorrowInvestRequestBean;
import com.hyjf.admin.beans.request.InvestorRequest;
import com.hyjf.admin.beans.request.PdfSignRequest;
import com.hyjf.admin.beans.response.BorrowInvestResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.BorrowInvestService;
import com.hyjf.admin.service.BorrowRegistService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wangjun
 * @version BorrowInvestController, v0.1 2018/7/10 9:06
 */
@Api(value = "产品中心-汇直投-投资明细", tags = "产品中心-汇直投-投资明细")
@RestController
@RequestMapping("/hyjf-admin/borrow_invest")
public class BorrowInvestController extends BaseController {
    @Autowired
    BorrowInvestService borrowInvestService;

    @Autowired
    BorrowRegistService borrowRegistService;

    @Autowired
    AdminCommonService adminCommonService;

    /**
     * 权限
     */
    public static final String PERMISSIONS = "borrowinvest";

    /**
     * 投资明细初始化
     *
     * @param borrowInvestRequestBean
     * @return
     */
    @ApiOperation(value = "投资明细初始化", notes = "投资明细初始化")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowInvestResponseBean> init(@RequestBody BorrowInvestRequestBean borrowInvestRequestBean) {
        //查询类赋值
        BorrowInvestRequest borrowInvestRequest = new BorrowInvestRequest();
        BeanUtils.copyProperties(borrowInvestRequestBean, borrowInvestRequest);
        //初始化只获取10天数据
        borrowInvestRequest.setTimeStartSrch(GetDate.date2Str(GetDate.getTodayBeforeOrAfter(-10), new SimpleDateFormat("yyyy-MM-dd")));
        BorrowInvestResponseBean responseBean = borrowInvestService.getBorrowInvestList(borrowInvestRequest);
        //还款方式
        List<DropDownVO> borrowStyleList = adminCommonService.selectBorrowStyleList();
        responseBean.setBorrowStyleList(borrowStyleList);
        //操作平台
        List<DropDownVO> clientList = adminCommonService.getParamNameList("CLIENT");
        responseBean.setClientList(clientList);
        //投资方式
        List<DropDownVO> investTypeList = adminCommonService.getParamNameList("INVEST_TYPE");
        responseBean.setInvestTypeList(investTypeList);
        // 资产来源
        List<DropDownVO> hjhInstConfigList = adminCommonService.selectHjhInstConfigList();
        responseBean.setHjhInstConfigList(hjhInstConfigList);
        //产品类型
        List<BorrowProjectTypeVO> borrowProjectTypeList = borrowRegistService.selectBorrowProjectList();
        responseBean.setBorrowProjectTypeList(ConvertUtils.convertListToDropDown(borrowProjectTypeList,"borrowCd","borrowName"));
        return new AdminResult(responseBean);
    }

    /**
     * 投资明细列表查询
     *
     * @param borrowInvestRequestBean
     * @return
     */
    @ApiOperation(value = "投资明细列表查询/运营记录-投资明细", notes = "投资明细列表查询/运营记录-投资明细")
    @PostMapping("/search")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    public AdminResult<BorrowInvestResponseBean> getBorrowInvestList(@RequestBody BorrowInvestRequestBean borrowInvestRequestBean) {
        //查询类赋值
        BorrowInvestRequest borrowInvestRequest = new BorrowInvestRequest();
        BeanUtils.copyProperties(borrowInvestRequestBean, borrowInvestRequest);
        BorrowInvestResponseBean responseBean = borrowInvestService.getBorrowInvestList(borrowInvestRequest);
        return new AdminResult(responseBean);
    }

    /**
     * 投资明细列表导出
     *
     * @param request
     * @param response
     * @param borrowInvestRequestBean
     * @throws Exception
     */
    @ApiOperation(value = "投资明细列表导出", notes = "投资明细列表导出")
    @PostMapping("/export_list")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportList(HttpServletRequest request, HttpServletResponse response, @RequestBody BorrowInvestRequestBean borrowInvestRequestBean) throws Exception {
        //查询类赋值
        BorrowInvestRequest borrowInvestRequest = new BorrowInvestRequest();
        BeanUtils.copyProperties(borrowInvestRequestBean, borrowInvestRequest);
        // 表格sheet名称
        String sheetName = "投资明细";

        List<BorrowInvestCustomizeVO> resultList = this.borrowInvestService.getExportBorrowInvestList(borrowInvestRequest);

        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        String[] titles = new String[]{"序号", "借款编号", "计划编号", "借款人ID", "借款人用户名", "借款标题", "项目类型", "借款期限", "年化利率", "还款方式", "投资订单号", "冻结订单号", "投资人用户名", "投资人ID", "投资人用户属性（当前）", "投资人所属一级分部（当前）", "投资人所属二级分部（当前）", "投资人所属团队（当前）", "推荐人（当前）", "推荐人ID（当前）", "推荐人姓名（当前）", "推荐人所属一级分部（当前）", "推荐人所属二级分部（当前）", "推荐人所属团队（当前）",
                "投资人用户属性（投资时）", "推荐人用户属性（投资时）", "推荐人（投资时）", "推荐人ID（投资时）", "一级分部（投资时）", "二级分部（投资时）", "团队（投资时）", "投资金额", "操作平台", "投资方式", "投资时间", "合同编号", "合同状态", "合同名称", "模版编号", "合同生成时间", "合同签署时间", "复投投资(是/否)"};
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
                    BorrowInvestCustomizeVO record = resultList.get(i);

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
                        cell.setCellValue(record.getUsername());
                    }
                    // 借款标题
                    else if (celLength == 5) {
                        cell.setCellValue(record.getBorrowName());
                    }
                    // 项目类型
                    else if (celLength == 6) {
                        cell.setCellValue(record.getBorrowProjectTypeName());
                    }
                    // 借款期限
                    else if (celLength == 7) {
                        cell.setCellValue(record.getBorrowPeriod());
                    }
                    // 年化收益
                    else if (celLength == 8) {
                        cell.setCellValue(record.getBorrowApr());
                    }
                    // 还款方式
                    else if (celLength == 9) {
                        cell.setCellValue(record.getBorrowStyleName());
                    }

                    // 投资订单号
                    else if (celLength == 10) {
                        cell.setCellValue(record.getTenderOrderNum());
                    }
                    // 冻结订单号
                    else if (celLength == 11) {
                        cell.setCellValue(record.getFreezeOrderNum());
                    }
                    // 投资人用户名
                    else if (celLength == 12) {
                        cell.setCellValue(record.getTenderUsername());
                    }
                    // 投资人ID
                    else if (celLength == 13) {
                        cell.setCellValue(record.getTenderUserId());
                    }
                    // 投资人用户属性（当前）
                    else if (celLength == 14) {
                        if ("0".equals(record.getTenderUserAttributeNow())) {
                            cell.setCellValue("无主单");
                        } else if ("1".equals(record.getTenderUserAttributeNow())) {
                            cell.setCellValue("有主单");
                        } else if ("2".equals(record.getTenderUserAttributeNow())) {
                            cell.setCellValue("线下员工");
                        } else if ("3".equals(record.getTenderUserAttributeNow())) {
                            cell.setCellValue("线上员工");
                        }
                    }
                    // 投资人所属一级分部（当前）
                    else if (celLength == 15) {
                        cell.setCellValue(record.getTenderRegionName());
                    }
                    // 投资人所属二级分部（当前）
                    else if (celLength == 16) {
                        cell.setCellValue(record.getTenderBranchName());
                    }
                    // 投资人所属团队（当前）
                    else if (celLength == 17) {
                        cell.setCellValue(record.getTenderDepartmentName());
                    }
                    // 推荐人（当前）
                    else if (celLength == 18) {
                        cell.setCellValue(record.getReferrerName());
                    }
                    // 推荐人ID（当前）
                    else if (celLength == 19) {
                        cell.setCellValue("0".equals(record.getReferrerUserId()) ? "" : record.getReferrerUserId());
                    }
                    // 推荐人姓名（当前）
                    else if (celLength == 20) {
                        cell.setCellValue(record.getReferrerTrueName());
                    }
                    // 推荐人所属一级分部（当前）
                    else if (celLength == 21) {
                        cell.setCellValue(record.getReferrerRegionName());
                    }
                    // 推荐人所属二级分部（当前）
                    else if (celLength == 22) {
                        cell.setCellValue(record.getReferrerBranchName());
                    }
                    // 推荐人所属团队（当前）
                    else if (celLength == 23) {
                        cell.setCellValue(record.getReferrerDepartmentName());
                    }
                    // 投资人用户属性（投资时）
                    else if (celLength == 24) {
                        cell.setCellValue(record.getTenderUserAttribute());
                    }
                    // 推荐人用户属性（投资时）
                    else if (celLength == 25) {
                        cell.setCellValue(
                                "0".equals(record.getTenderReferrerUserId()) ? "" : record.getInviteUserAttribute());
                    }
                    // 推荐人（投资时）
                    else if (celLength == 26) {
                        cell.setCellValue(record.getTenderReferrerUsername());
                    }
                    // 推荐人ID（投资时）
                    else if (celLength == 27) {
                        cell.setCellValue(
                                "0".equals(record.getTenderReferrerUserId()) ? "" : record.getTenderReferrerUserId());
                    }
                    // 一级分部（投资时）
                    else if (celLength == 28) {
                        cell.setCellValue(record.getDepartmentLevel1Name());
                    }
                    // 二级分部（投资时）
                    else if (celLength == 29) {
                        cell.setCellValue(record.getDepartmentLevel2Name());
                    }
                    // 团队（投资时）
                    else if (celLength == 30) {
                        cell.setCellValue(record.getTeamName());
                    }
                    // 投资金额
                    else if (celLength == 31) {
                        cell.setCellValue(record.getAccount());
                    }
                    // 操作平台
                    else if (celLength == 32) {
                        cell.setCellValue(record.getOperatingDeck());
                    }
                    // 投资方式
                    else if (celLength == 33) {
                        cell.setCellValue(record.getInvestType());
                    }
                    // 投资时间
                    else if (celLength == 34) {
                        cell.setCellValue(record.getCreateTime());
                    }
                    // 合同编号
                    else if (celLength == 35) {
                        cell.setCellValue(StringUtils.isBlank(record.getContractNumber()) ? "" : record.getContractNumber());
                    }
                    // 合同状态
                    else if (celLength == 36) {
                        if (StringUtils.isBlank(record.getContractStatus())) {
                            cell.setCellValue("初始");
                        } else if (StringUtils.isNotBlank(record.getContractStatus()) && "0".equals(record.getContractStatus())) {
                            cell.setCellValue("初始");
                        } else if (StringUtils.isNotBlank(record.getContractStatus()) && "1".equals(record.getContractStatus())) {
                            cell.setCellValue("生成成功");
                        } else if (StringUtils.isNotBlank(record.getContractStatus()) && "2".equals(record.getContractStatus())) {
                            cell.setCellValue("签署成功");
                        } else if (StringUtils.isNotBlank(record.getContractStatus()) && "3".equals(record.getContractStatus())) {
                            cell.setCellValue("下载成功");
                        }
                    }
                    // 合同名称
                    else if (celLength == 37) {
                        cell.setCellValue(StringUtils.isBlank(record.getContractName()) ? "" : record.getContractName());
                    }
                    // 模版编号
                    else if (celLength == 38) {
                        cell.setCellValue(StringUtils.isBlank(record.getTempletId()) ? "" : record.getTempletId());
                    }
                    // 合同生成时间
                    else if (celLength == 39) {
                        cell.setCellValue(StringUtils.isBlank(record.getContractCreateTime()) ? "" : record.getContractCreateTime());
                    }
                    // 合同签署时间
                    else if (celLength == 40) {
                        cell.setCellValue(StringUtils.isBlank(record.getContractSignTime()) ? "" : record.getContractSignTime());
                    } else if (celLength == 41) {
                        cell.setCellValue(record.getTenderType());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }

    /**
     * 投资人债权明细
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "投资人债权明细", notes = "投资人债权明细")
    @PostMapping("/debt_info")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DEBTCHECK)
    public AdminResult<BorrowInvestResponseBean> debtInfo(@RequestBody BorrowInvestDebtInfoRequest request) {
        InvestorDebtBean investorDebtBean = new InvestorDebtBean();
        BeanUtils.copyProperties(request, investorDebtBean);
        return borrowInvestService.debtInfo(investorDebtBean);
    }

    /**
     * PDF脱敏图片预览
     *
     * @param nid
     * @return
     */
    @ApiOperation(value = "PDF脱敏图片预览", notes = "PDF脱敏图片预览")
    @ApiImplicitParam(name = "nid", value = "投资订单号", required = true, dataType = "String", paramType = "path")
    @GetMapping("/pdf_preview/{nid}")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_PDF_PREVIEW)
    public AdminResult<BorrowInvestResponseBean> pdfPreview(@PathVariable String nid) {
        return borrowInvestService.pdfPreview(nid);
    }

    /**
     * PDF签署
     *
     * @param pdfSignRequest
     * @return
     */
    @ApiOperation(value = "PDF签署", notes = "PDF签署")
    @PostMapping("/pdf_sign")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_PDF_SIGN)
    public AdminResult pdfSign(@RequestBody PdfSignRequest pdfSignRequest) {
        InvestorDebtBean investorDebtBean = new InvestorDebtBean();
        BeanUtils.copyProperties(pdfSignRequest, investorDebtBean);
        return borrowInvestService.pdfSign(investorDebtBean);
    }

    /**
     * 发送协议
     *
     * @param investorRequest
     * @return
     */
    @ApiOperation(value = "发送协议", notes = "发送协议")
    @PostMapping("/send_agreement")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT_AGREEMENT)
    public AdminResult sendAgreement(@RequestBody InvestorRequest investorRequest) {
        return borrowInvestService.sendAgreement(investorRequest);
    }

    /**
     * 运营记录-投资明细
     *
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "运营记录-投资明细", notes = "运营记录-投资明细")
    @PostMapping("/optaction_init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowInvestResponseBean> optRecordTender(@RequestBody BorrowInvestRequestBean requestBean) {
        //查询类赋值
        BorrowInvestRequest borrowInvestRequest = new BorrowInvestRequest();
        BeanUtils.copyProperties(requestBean, borrowInvestRequest);
        // 如果是从原始标的跳转过来，不默认时间，否则默认最近10天
        if (!"1".equals(requestBean.getIsOptFlag())) {
            borrowInvestRequest.setTimeStartSrch(GetDate.date2Str(GetDate.getTodayBeforeOrAfter(-10), new SimpleDateFormat("yyyy-MM-dd")));
        }
        BorrowInvestResponseBean responseBean = borrowInvestService.getBorrowInvestList(borrowInvestRequest);
        return new AdminResult(responseBean);
    }
}
