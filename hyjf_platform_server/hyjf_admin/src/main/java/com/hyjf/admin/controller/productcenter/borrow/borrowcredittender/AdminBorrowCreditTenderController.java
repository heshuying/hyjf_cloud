package com.hyjf.admin.controller.productcenter.borrow.borrowcredittender;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.BorrowCreditRepayResultBean;
import com.hyjf.admin.beans.BorrowCreditTenderBean;
import com.hyjf.admin.beans.BorrowCreditTenderResultBean;
import com.hyjf.admin.beans.request.BorrowCreditTenderInfoRequest;
import com.hyjf.admin.beans.request.BorrowCreditTenderPDFSignReq;
import com.hyjf.admin.beans.request.BorrowCreditTenderRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowCreditTenderService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.AdminCreditTenderResponse;
import com.hyjf.am.vo.admin.BorrowCreditTenderVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(value = "产品中心-汇转让-承接信息" , tags = "产品中心-汇转让-承接信息")
@RestController
@RequestMapping("/hyjf-admin/borrow/creditTender")
public class AdminBorrowCreditTenderController extends BaseController {


    @Autowired
    private BorrowCreditTenderService borrowCreditTenderService;

    public static final String PERMISSIONS = "credittender";


    /**
     * com.hyjf.admin.manager.borrow.credittender.CreditTenderController.searchAction()
     * @author zhangyk
     * @date 2018/8/29 10:17
     */
    @ApiOperation(value = "承接信息", notes = "承接信息")
    @PostMapping("/getList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    @ResponseBody
    public Object  getBorrowCreditTenderList(@RequestBody BorrowCreditTenderRequest request){
        AdminResult result = borrowCreditTenderService.getCreditTenderList(request);
        return result;
    }

    @ApiOperation(value = "结束债权", notes = "结束债权")
    @GetMapping("/creditEnd/{orderId}")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_CHULI)
    @ResponseBody
    public Object creditEnd(@PathVariable String orderId){
        AdminResult result = new AdminResult();
        logger.info("【结束债权】orderId:" + orderId);
        if(StringUtils.isBlank(orderId)){
            result.setStatus(AdminResult.FAIL);
            result.setStatusDesc("请求参数错误");
            return result;
        }

        Response saveResponse = borrowCreditTenderService.doCreditEnd(orderId);
        if(saveResponse == null || !"0".equals(saveResponse.getRtn())){
            result.setStatus(AdminResult.FAIL);
            result.setStatusDesc(saveResponse.getMessage());
            return result;
        }

        return result;
    }

    @ApiOperation(value = "承接信息导出", notes = "承接信息导出")
    @PostMapping("/exportData")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    @ResponseBody
    public void exportBorrowCreditTender( @RequestBody BorrowCreditTenderRequest request, HttpServletRequest servletRequest, HttpServletResponse response) throws UnsupportedEncodingException {
        int totalCount = borrowCreditTenderService.selectBorrowCreditTenderCount(request);
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        String sheetName = "汇转让-承接信息";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            export(request, workbook, sheetNameTmp, new ArrayList<>());
        }
        for (int i = 1; i <= sheetCount; i++) {
            //请求第一页5000条
            request.setPageSize(defaultRowMaxCount);
            request.setCurrPage(i);
            AdminCreditTenderResponse borrowCreditList = borrowCreditTenderService.getCreditTenderResponse(request);
            if (borrowCreditList != null) {
                List<BorrowCreditTenderVO> data = borrowCreditList.getResultList();
                if (!CollectionUtils.isEmpty(data)) {
                    sheetNameTmp = sheetName + "_第" + (i) + "页";
                    export(request, workbook, sheetNameTmp,data);
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(servletRequest, response, fileName, workbook);
    }

    /**
     * 导出
     * @param workbook
     * @param sheetNameTmp
     * @param recordList
     */
    private void export(BorrowCreditTenderRequest request, SXSSFWorkbook workbook, String sheetNameTmp, List<BorrowCreditTenderVO> recordList) {
        String[] titles = null;
        if (StringUtils.isNotBlank(request.getIsOrganizationView())){
            titles = new String[]{"序号", "订单号", "债转编号", "项目编号", "出让人", "出让人当前的推荐人的用户名", "出让人当前的推荐人的用户属性", "出让人当前的推荐人的分公司", "出让人当前的推荐人的部门", "出让人当前的推荐人的团队",
                    "出让人承接时的推荐人的用户名", "出让人承接时的推荐人的用户属性", "出让人承接时的推荐人的分公司", "出让人承接时的推荐人的部门", "出让人承接时的推荐人的团队",
                    "承接人", "承接人当前的推荐人的用户名", "承接人当前的推荐人的用户属性", "承接人当前的推荐人的分公司", "承接人当前的推荐人的部门", "承接人当前的推荐人的团队",
                    "承接人承接时的推荐人的用户名", "承接人承接时的推荐人的用户属性", "承接人承接时的推荐人的分公司", "承接人承接时的推荐人的部门", "承接人承接时的推荐人的团队",
                    "承接本金", "折让率", "认购价格", "垫付利息", "债转服务费", "实付金额", "承接平台", "承接时间","债权结束状态"};
        }else {
            titles = new String[]{"序号", "订单号", "债转编号", "项目编号", "出让人", "出让人当前的推荐人的用户名", "出让人当前的推荐人的用户属性",
                    "出让人承接时的推荐人的用户名", "出让人承接时的推荐人的用户属性",
                    "承接人", "承接人当前的推荐人的用户名", "承接人当前的推荐人的用户属性",
                    "承接人承接时的推荐人的用户名", "承接人承接时的推荐人的用户属性",
                    "承接本金", "折让率", "认购价格", "垫付利息", "债转服务费", "实付金额", "承接平台", "承接时间","债权结束状态"};
        }
        // 生成一个表格
        SXSSFSheet sheet = workbook.createSheet(sheetNameTmp);
        int rowNum = 0;
        Row row = sheet.createRow(0);
        for (int celLength = 0; celLength < titles.length; celLength++) {
            // 创建相应的单元格
            Cell cell = row.createCell(celLength);
            cell.setCellValue(titles[celLength]);
        }
        for (int i = 0; i < recordList.size(); i++) {
            Row columnValueRow = sheet.createRow(i + 1);
            // 循环数据
            for (int celLength = 0; celLength < titles.length; celLength++) {
                BorrowCreditTenderVO borrowCommonCustomize = recordList.get(i);

                // 创建相应的单元格
                Cell cell = columnValueRow.createCell(celLength);
                if (StringUtils.isNotBlank(request.getIsOrganizationView())){
                    if (celLength == 0) {
                        cell.setCellValue(rowNum++ + 1);
                    }
                    // 订单号
                    else if (celLength == 1) {
                        cell.setCellValue(borrowCommonCustomize.getAssignNid());
                    }
                    // 债转编号
                    else if (celLength == 2) {
                        cell.setCellValue(borrowCommonCustomize.getCreditNid());
                    }
                    // 项目编号
                    else if (celLength == 3) {
                        cell.setCellValue(borrowCommonCustomize.getBidNid());
                    }
                    // 出让人用户名
                    else if (celLength == 4) {
                        cell.setCellValue(borrowCommonCustomize.getCreditUserName());
                    }
                    // 出让人推荐人信息---start
                    // 出让人当前
                    else if (celLength == 5) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getCreditUserName());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRecommendNameCredit());
                        }

                    }
                    else if (celLength == 6) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getRecommendAttrCreditSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRecommendAttrCredit());
                        }

                    }
                    else if (celLength == 7) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getRegionNameCreditSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRegionNameCredit());
                        }

                    }
                    else if (celLength == 8) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getBranchNameCreditSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getBranchNameCredit());
                        }

                    }
                    else if (celLength == 9) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getDepartmentNameCreditSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getDepartmentNameCredit());
                        }

                    }

                    //出让人承接
                    else if (celLength == 10) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserCreditName());
                    }
                    else if (celLength == 11) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserCreditAttribute());
                    }
                    else if (celLength == 12) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserCreditRegionName());
                    }
                    else if (celLength == 13) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserCreditBranchName());
                    }
                    else if (celLength == 14) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserCreditDepartmentName());
                    }
                    // 出让人推荐人信息---end
                    // 承接人用户名
                    else if (celLength == 15) {
                        cell.setCellValue(borrowCommonCustomize.getUserName());
                    }
                    // 承接人推荐人信息---start
                    // 承接人当前
                    else if (celLength == 16) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getUserName());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRecommendName());
                        }

                    }
                    else if (celLength == 17) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getRecommendAttrSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRecommendAttr());
                        }

                    }
                    else if (celLength == 18) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getRegionNameSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRegionName());
                        }

                    }
                    else if (celLength == 19) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getBranchNameSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getBranchName());
                        }

                    }
                    else if (celLength == 20) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getDepartmentNameSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getDepartmentName());
                        }

                    }

                    // 承接人承接时
                    else if (celLength == 21) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserName());
                    }
                    else if (celLength == 22) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserAttribute());
                    }
                    else if (celLength == 23) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUseRegionname());
                    }
                    else if (celLength == 24) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserBranchname());
                    }
                    else if (celLength == 25) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserDepartmentName());
                    }
                    // 承接人承接时推荐人信息---end
                    // 承接本金
                    else if (celLength == 26) {
                        cell.setCellValue(borrowCommonCustomize.getAssignCapital());
                    }
                    // 折让率
                    else if (celLength == 27) {
                        cell.setCellValue(borrowCommonCustomize.getCreditDiscount());
                    }
                    // 认购价格
                    else if (celLength == 28) {
                        cell.setCellValue(borrowCommonCustomize.getAssignPrice());
                    }
                    // 垫付利息
                    else if (celLength == 29) {
                        cell.setCellValue(borrowCommonCustomize.getAssignInterestAdvance());
                    }
                    // 服务费
                    else if (celLength == 30) {
                        cell.setCellValue(borrowCommonCustomize.getCreditFee());
                    }
                    // 实付金额
                    else if (celLength == 31) {
                        cell.setCellValue(borrowCommonCustomize.getAssignPay());
                    }
                    // 客户端
                    else if (celLength == 32) {
                        if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                &&borrowCommonCustomize.getClient().equals("0")) {
                            cell.setCellValue("pc");
                        }else if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                &&borrowCommonCustomize.getClient().equals("1")) {
                            cell.setCellValue("微信");
                        }else if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                &&borrowCommonCustomize.getClient().equals("2")) {
                            cell.setCellValue("android");
                        }else if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                &&borrowCommonCustomize.getClient().equals("3")) {
                            cell.setCellValue("ios");
                        }
                    }
                    // 承接时间
                    else if (celLength == 33) {
                        cell.setCellValue(borrowCommonCustomize.getAddTime());
                    }else if (celLength == 34) {
                        cell.setCellValue(borrowCommonCustomize.getStateDesc());
                    }
                }else {
                    if (celLength == 0) {
                        cell.setCellValue(rowNum++ + 1);
                    }
                    // 订单号
                    else if (celLength == 1) {
                        cell.setCellValue(borrowCommonCustomize.getAssignNid());
                    }
                    // 债转编号
                    else if (celLength == 2) {
                        cell.setCellValue(borrowCommonCustomize.getCreditNid());
                    }
                    // 项目编号
                    else if (celLength == 3) {
                        cell.setCellValue(borrowCommonCustomize.getBidNid());
                    }
                    // 出让人用户名
                    else if (celLength == 4) {
                        cell.setCellValue(borrowCommonCustomize.getCreditUserName());
                    }
                    // 出让人推荐人信息---start
                    // 出让人当前
                    else if (celLength == 5) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getCreditUserName());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRecommendNameCredit());
                        }

                    }
                    else if (celLength == 6) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrCreditSelf()) && (borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrCreditSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getRecommendAttrCreditSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRecommendAttrCredit());
                        }

                    }
                    //出让人承接
                    else if (celLength == 7) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserCreditName());
                    }
                    else if (celLength == 8) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserCreditAttribute());
                    }
                    // 出让人推荐人信息---end
                    // 承接人用户名
                    else if (celLength == 9) {
                        cell.setCellValue(borrowCommonCustomize.getUserName());
                    }
                    // 承接人推荐人信息---start
                    // 承接人当前
                    else if (celLength == 10) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getUserName());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRecommendName());
                        }

                    }
                    else if (celLength == 11) {
                        if(StringUtils.isNotBlank(borrowCommonCustomize.getRecommendAttrSelf()) && (borrowCommonCustomize.getRecommendAttrSelf().equals("线上员工") || borrowCommonCustomize.getRecommendAttrSelf().equals("线下员工"))){
                            cell.setCellValue(borrowCommonCustomize.getRecommendAttrSelf());
                        }else{
                            cell.setCellValue(borrowCommonCustomize.getRecommendAttr());
                        }

                    }
                    // 承接人承接时
                    else if (celLength == 12) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserName());
                    }
                    else if (celLength == 13) {
                        cell.setCellValue(borrowCommonCustomize.getInviteUserAttribute());
                    }
                    // 承接人承接时推荐人信息---end
                    // 承接本金
                    else if (celLength == 14) {
                        cell.setCellValue(borrowCommonCustomize.getAssignCapital());
                    }
                    // 折让率
                    else if (celLength == 15) {
                        cell.setCellValue(borrowCommonCustomize.getCreditDiscount());
                    }
                    // 认购价格
                    else if (celLength == 16) {
                        cell.setCellValue(borrowCommonCustomize.getAssignPrice());
                    }
                    // 垫付利息
                    else if (celLength == 17) {
                        cell.setCellValue(borrowCommonCustomize.getAssignInterestAdvance());
                    }
                    // 服务费
                    else if (celLength == 18) {
                        cell.setCellValue(borrowCommonCustomize.getCreditFee());
                    }
                    // 实付金额
                    else if (celLength == 19) {
                        cell.setCellValue(borrowCommonCustomize.getAssignPay());
                    }
                    // 客户端
                    else if (celLength == 20) {
                        if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                &&borrowCommonCustomize.getClient().equals("0")) {
                            cell.setCellValue("pc");
                        }else if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                &&borrowCommonCustomize.getClient().equals("1")) {
                            cell.setCellValue("微信");
                        }else if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                &&borrowCommonCustomize.getClient().equals("2")) {
                            cell.setCellValue("android");
                        }else if (borrowCommonCustomize.getClient()!=null&&!borrowCommonCustomize.getClient().equals("")
                                &&borrowCommonCustomize.getClient().equals("3")) {
                            cell.setCellValue("ios");
                        }
                    }
                    // 承接时间
                    else if (celLength == 21) {
                        cell.setCellValue(borrowCommonCustomize.getAddTime());
                    }else if (celLength == 22) {
                        cell.setCellValue(borrowCommonCustomize.getStateDesc());
                    }
                }
            }
        }

    }

    @ApiOperation(value = "查看债权人债权信息", notes = "查看债权人债权信息")
    @PostMapping("/getCreditUserInfo")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_QUERY_INVEST_DEBT_VIEW)
    @ResponseBody
    public Object  getCreditUserInfo(@RequestBody BorrowCreditTenderInfoRequest request){
        AdminResult result =  borrowCreditTenderService.getCreditUserInfo(request);
        return result;
    }


    /**
     * 原接口：com.hyjf.admin.manager.borrow.credittender.CreditTenderController.pdfSignAction()
     * @author zhangyk
     * @date 2018/8/31 10:08
     */
    @ApiOperation(value = "PDF签署", notes = "PDF签署")
    @PostMapping("/pdfSign")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_PDF_SIGN)
    @ResponseBody
    public Object  pdfSign(@RequestBody BorrowCreditTenderPDFSignReq reqBean,HttpServletRequest req){
        AdminSystemVO adminSystemVO = this.getUser(req);
        AdminResult result =  borrowCreditTenderService.pdfSign(reqBean,adminSystemVO);
        return result;
    }


    @ApiOperation(value = "脱敏图片预览", notes = "脱敏图片预览")
    @PostMapping("/pdfPreviewAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_PDF_PREVIEW)
    @ResponseBody
    public Object pdfPreviewAction(@RequestBody BorrowCreditTenderPDFSignReq req){
        AdminResult result = borrowCreditTenderService.pdfPreview(req);
        return result;
    }

}
