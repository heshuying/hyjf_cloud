/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.InvestorDebtBean;
import com.hyjf.admin.beans.request.BorrowInvestDebtInfoRequest;
import com.hyjf.admin.beans.request.BorrowInvestRequestBean;
import com.hyjf.admin.beans.request.InvestorRequest;
import com.hyjf.admin.beans.request.PdfSignRequest;
import com.hyjf.admin.beans.response.BorrowInvestResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.AdminCommonService;
import com.hyjf.admin.service.BorrowInvestService;
import com.hyjf.admin.service.BorrowRegistService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowInvestController, v0.1 2018/7/10 9:06
 */
@Api(value = "产品中心-汇直投-出借明细", tags = "产品中心-汇直投-出借明细")
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
     * 出借明细初始化
     *
     * @return
     */
    @ApiOperation(value = "出借明细初始化", notes = "出借明细初始化")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    public AdminResult<BorrowInvestResponseBean> init() {
        BorrowInvestResponseBean responseBean = new BorrowInvestResponseBean();
        //还款方式
        List<DropDownVO> borrowStyleList = adminCommonService.selectBorrowStyleList();
        responseBean.setBorrowStyleList(borrowStyleList);
        //操作平台
        List<DropDownVO> clientList = adminCommonService.getParamNameList("CLIENT");
        responseBean.setClientList(clientList);
        //出借方式
        List<DropDownVO> investTypeList = adminCommonService.getParamNameList("INVEST_TYPE");
        responseBean.setInvestTypeList(investTypeList);
        // 资产来源
        List<DropDownVO> hjhInstConfigList = adminCommonService.selectHjhInstConfigList();
        responseBean.setHjhInstConfigList(hjhInstConfigList);
        //产品类型
        List<BorrowProjectTypeVO> borrowProjectTypeList = borrowRegistService.selectBorrowProjectList();
        responseBean.setBorrowProjectTypeList(ConvertUtils.convertListToDropDown(borrowProjectTypeList,"borrowCd","borrowName"));
        //债权结束状态
        List<DropDownVO> creditendStateList = adminCommonService.getParamNameList("CREDITEND_STATE");
        responseBean.setCreditendStateList(creditendStateList);
        return new AdminResult(responseBean);
    }

    /**
     * 出借明细列表查询
     *
     * @param borrowInvestRequestBean
     * @return
     */
    @ApiOperation(value = "出借明细列表查询/运营记录-出借明细", notes = "出借明细列表查询/运营记录-出借明细")
    @PostMapping("/search")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowInvestResponseBean> getBorrowInvestList(@RequestBody BorrowInvestRequestBean borrowInvestRequestBean) {
        //查询类赋值
        BorrowInvestRequest borrowInvestRequest = new BorrowInvestRequest();
        BeanUtils.copyProperties(borrowInvestRequestBean, borrowInvestRequest);
        BorrowInvestResponseBean responseBean = borrowInvestService.getBorrowInvestList(borrowInvestRequest);
        return new AdminResult(responseBean);
    }

    /**
     * 出借明细列表导出
     *
     * @param request
     * @param response
     * @param borrowInvestRequestBean
     * @throws Exception
     */
    /*@ApiOperation(value = "出借明细列表导出", notes = "出借明细列表导出")
    @PostMapping("/export_list")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportList(HttpServletRequest request, HttpServletResponse response, @RequestBody BorrowInvestRequestBean borrowInvestRequestBean) throws Exception {
        //查询类赋值
        BorrowInvestRequest borrowInvestRequest = new BorrowInvestRequest();
        BeanUtils.copyProperties(borrowInvestRequestBean, borrowInvestRequest);
        // 表格sheet名称
        String sheetName = "出借明细";

        List<BorrowInvestCustomizeVO> resultList = this.borrowInvestService.getExportBorrowInvestList(borrowInvestRequest);

        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;

        String[] titles = new String[]{"序号", "项目编号", "计划编号", "借款人ID", "借款人用户名", "借款标题", "项目类型", "借款期限", "年化利率", "还款方式", "出借订单号", "冻结订单号", "出借人用户名", "出借人ID", "出借人用户属性（当前）", "出借人所属一级分部（当前）", "出借人所属二级分部（当前）", "出借人所属团队（当前）", "推荐人（当前）", "推荐人ID（当前）", "推荐人姓名（当前）", "推荐人所属一级分部（当前）", "推荐人所属二级分部（当前）", "推荐人所属团队（当前）",
                "出借人用户属性（出借时）", "推荐人用户属性（出借时）", "推荐人（出借时）", "推荐人ID（出借时）", "一级分部（出借时）", "二级分部（出借时）", "团队（出借时）", "出借金额", "操作平台", "出借方式", "出借时间", "合同编号", "合同状态", "合同名称", "模版编号", "合同生成时间", "合同签署时间", "复投投标(是/否)"};
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
                    // 项目编号
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
                    // 出借利率
                    else if (celLength == 8) {
                        cell.setCellValue(record.getBorrowApr());
                    }
                    // 还款方式
                    else if (celLength == 9) {
                        cell.setCellValue(record.getBorrowStyleName());
                    }

                    // 出借订单号
                    else if (celLength == 10) {
                        cell.setCellValue(record.getTenderOrderNum());
                    }
                    // 冻结订单号
                    else if (celLength == 11) {
                        cell.setCellValue(record.getFreezeOrderNum());
                    }
                    // 出借人用户名
                    else if (celLength == 12) {
                        cell.setCellValue(record.getTenderUsername());
                    }
                    // 出借人ID
                    else if (celLength == 13) {
                        cell.setCellValue(record.getTenderUserId());
                    }
                    // 出借人用户属性（当前）
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
                    // 出借人所属一级分部（当前）
                    else if (celLength == 15) {
                        cell.setCellValue(record.getTenderRegionName());
                    }
                    // 出借人所属二级分部（当前）
                    else if (celLength == 16) {
                        cell.setCellValue(record.getTenderBranchName());
                    }
                    // 出借人所属团队（当前）
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
                    // 出借人用户属性（出借时）
                    else if (celLength == 24) {
                        cell.setCellValue(record.getTenderUserAttribute());
                    }
                    // 推荐人用户属性（出借时）
                    else if (celLength == 25) {
                        cell.setCellValue(
                                "0".equals(record.getTenderReferrerUserId()) ? "" : record.getInviteUserAttribute());
                    }
                    // 推荐人（出借时）
                    else if (celLength == 26) {
                        cell.setCellValue(record.getTenderReferrerUsername());
                    }
                    // 推荐人ID（出借时）
                    else if (celLength == 27) {
                        cell.setCellValue(
                                "0".equals(record.getTenderReferrerUserId()) ? "" : record.getTenderReferrerUserId());
                    }
                    // 一级分部（出借时）
                    else if (celLength == 28) {
                        cell.setCellValue(record.getDepartmentLevel1Name());
                    }
                    // 二级分部（出借时）
                    else if (celLength == 29) {
                        cell.setCellValue(record.getDepartmentLevel2Name());
                    }
                    // 团队（出借时）
                    else if (celLength == 30) {
                        cell.setCellValue(record.getTeamName());
                    }
                    // 出借金额
                    else if (celLength == 31) {
                        cell.setCellValue(record.getAccount());
                    }
                    // 操作平台
                    else if (celLength == 32) {
                        cell.setCellValue(record.getOperatingDeck());
                    }
                    // 出借方式
                    else if (celLength == 33) {
                        cell.setCellValue(record.getInvestType());
                    }
                    // 出借时间
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
    }*/


    /**
     * 出借明细列表导出
     *
     * @param request
     * @param response
     * @param borrowInvestRequestBean
     * @throws Exception
     */
    @ApiOperation(value = "出借明细列表导出", notes = "出借明细列表导出")
    @PostMapping("/export_list")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportList(HttpServletRequest request, HttpServletResponse response, @RequestBody BorrowInvestRequestBean borrowInvestRequestBean) throws Exception {

        //查询类赋值
        BorrowInvestRequest borrowInvestRequest = new BorrowInvestRequest();
        BeanUtils.copyProperties(borrowInvestRequestBean, borrowInvestRequest);

        if (StringUtils.isBlank(borrowInvestRequest.getTimeEndSrch()) || StringUtils.isBlank(borrowInvestRequest.getTimeStartSrch())) {
            return ;
        }

        // 用户是否具有组织机构查看权限
        String isOrganizationView = borrowInvestRequestBean.getIsOrganizationView();

        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "出借明细";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        Integer totalCount = this.borrowInvestService.countBorrowInvest(borrowInvestRequest);

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;

        Map<String, String> beanPropertyColumnMap = buildMap(isOrganizationView);
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {

            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }

        // 查询列表传入分页
        Paginator paginator;
        for (int i = 1; i <= sheetCount; i++) {
            //请求第一页5000条
            paginator = new Paginator(i, totalCount,defaultRowMaxCount);
            borrowInvestRequest.setLimitStart(paginator.getOffset());
            borrowInvestRequest.setLimitEnd(paginator.getLimit());

            List<BorrowInvestCustomizeVO> resultResponse2 = borrowInvestService.getExportBorrowInvestList(borrowInvestRequest);
            if (resultResponse2 != null && resultResponse2.size()> 0) {
                sheetNameTmp = sheetName + "_第" + (i) + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  resultResponse2);
            } else {
                break;
            }
        }

        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap(String isOrganizationView) {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("borrowNid", "项目编号");
        map.put("planNid", "智投编号");
        map.put("userId", "借款人ID");
        map.put("username", "借款人用户名");
//        map.put("borrowName", "借款标题");
        map.put("borrowProjectTypeName", "项目类型");
        map.put("borrowPeriod", "项目期限");
        map.put("borrowApr", "出借利率");
        map.put("borrowStyleName", "还款方式");
        map.put("tenderOrderNum", "出借订单号");
        map.put("freezeOrderNum", "冻结订单号");
        map.put("tenderUsername", "出借人用户名");
        map.put("tenderUserId", "出借人ID");
        map.put("tenderUserAttributeNow", "出借人用户属性（当前）");
        if (StringUtils.isNotBlank(isOrganizationView)) {
            map.put("tenderRegionName", "出借人所属一级分部（当前）");
            map.put("tenderBranchName", "出借人所属二级分部（当前）");
            map.put("tenderDepartmentName", "出借人所属团队（当前）");
        }
        map.put("referrerName", "推荐人（当前）");
        map.put("referrerUserId", "推荐人ID（当前）");
        map.put("referrerTrueName", "推荐人姓名（当前）");
        if (StringUtils.isNotBlank(isOrganizationView)) {
            map.put("referrerRegionName", "推荐人所属一级分部（当前）");
            map.put("referrerBranchName", "推荐人所属二级分部（当前）");
            map.put("referrerDepartmentName", "推荐人所属团队（当前）");
        }
        map.put("tenderUserAttribute", "出借人用户属性（出借时）");
        map.put("inviteUserAttribute", "推荐人用户属性（出借时）");
        map.put("tenderReferrerUsername", "推荐人（出借时）");
        //原先是在导出时处理，现在在service中处理了
        map.put("tenderReferrerUserId", "推荐人ID（出借时）");
        if (StringUtils.isNotBlank(isOrganizationView)) {
            map.put("departmentLevel1Name", "一级分部（出借时）");
            map.put("departmentLevel2Name", "二级分部（出借时）");
            map.put("teamName", "团队（出借时）");
        }
        map.put("account", "出借金额");
        map.put("operatingDeck", "操作平台");
        map.put("investType", "出借方式");
        map.put("createTime", "出借时间");
        map.put("contractNumber", "合同编号");
        map.put("contractStatus", "合同状态");
        map.put("contractName", "合同名称");
        map.put("templetId", "模版编号");
        map.put("contractCreateTime", "合同生成时间");
        map.put("contractSignTime", "合同签署时间");
        map.put("tenderType", "循环出借");
        map.put("utmSource1", "出借人渠道来源（出借时）");
        map.put("utmSource2", "出借人渠道来源（当前）");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter tenderUserAttributeNowAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String) object;
                if ("0".equals(value)) {
                    return "无主单";
                } else if ("1".equals(value)) {
                    return "有主单";
                } else if ("2".equals(value)) {
                    return "线下员工";
                } else if ("3".equals(value)) {
                    return "线上员工";
                }else{
                    return "";
                }
            }
        };

        IValueFormatter contractStatusAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String) object;
                if (StringUtils.isBlank(value)) {
                    return "初始";
                } else if ("0".equals(value)) {
                    return "初始";
                } else if ("1".equals(value)) {
                    return "生成成功";
                } else if ("2".equals(value)) {
                    return "签署成功";
                } else if ("3".equals(value)) {
                    return "下载成功";
                }else{
                    return "";
                }
            }
        };

        IValueFormatter tenderReferrerUserIdAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String) object;
                if (StringUtils.isBlank(value)) {
                    return "";
                } else if ("0".equals(value)) {
                    return "";
                }
                return value;
            }
        };

        IValueFormatter referrerUserIdAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String value = (String) object;
                if (StringUtils.isBlank(value)) {
                    return "";
                } else if ("0".equals(value)) {
                    return "";
                }
                return value;
            }
        };


        mapAdapter.put("tenderUserAttributeNow", tenderUserAttributeNowAdapter);
        mapAdapter.put("contractStatus", contractStatusAdapter);
        mapAdapter.put("referrerUserId", referrerUserIdAdapter);
        mapAdapter.put("tenderReferrerUserId", tenderReferrerUserIdAdapter);
        return mapAdapter;
    }



    /**
     * 出借人债权明细
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "出借人债权明细", notes = "出借人债权明细")
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
    @ApiImplicitParam(name = "nid", value = "出借订单号", required = true, dataType = "String", paramType = "path")
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
     * 重发协议
     *
     * @param investorRequest
     * @return
     */
    @ApiOperation(value = "重发协议", notes = "重发协议")
    @PostMapping("/resend_agreement")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT_AGREEMENT)
    public AdminResult resendAgreement(@RequestBody InvestorRequest investorRequest) {
        return borrowInvestService.resendAgreement(investorRequest);
    }

    /**
     * 运营记录-出借明细
     *
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "运营记录-出借明细", notes = "运营记录-出借明细")
    @PostMapping("/optaction_init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BorrowInvestResponseBean> optRecordTender(@RequestBody BorrowInvestRequestBean requestBean) {
        //查询类赋值
        BorrowInvestRequest borrowInvestRequest = new BorrowInvestRequest();
        BeanUtils.copyProperties(requestBean, borrowInvestRequest);
        BorrowInvestResponseBean responseBean = borrowInvestService.getBorrowInvestList(borrowInvestRequest);
        return new AdminResult(responseBean);
    }
}
