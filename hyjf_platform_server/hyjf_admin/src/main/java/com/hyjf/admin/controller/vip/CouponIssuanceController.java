/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.vip;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.CouponCheckService;
import com.hyjf.admin.service.CouponConfigService;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponConfigCustomizeResponse;
import com.hyjf.am.response.trade.CouponConfigExportCustomizeResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigExportCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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
 * @author yaoyong
 * @version CouponIssuanceController, v0.1 2018/7/5 10:05
 */
@Api(tags = "VIP中心-优惠券发行")
@RestController
@RequestMapping("/hyjf-admin/coupon/issuance")
public class CouponIssuanceController extends BaseController {
    /**
     * 权限名称
     */
    private static final String PERMISSIONS = "couponconfig";
    @Autowired
    CouponConfigService couponConfigService;
    @Autowired
    CouponCheckService couponCheckService;

    @ApiOperation(value = "页面初始化", notes = "页面初始化")
    @PostMapping("/init")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<CouponConfigCustomizeResponse> init(@RequestBody CouponConfigRequest request) {
        CouponConfigCustomizeResponse ccr = couponConfigService.getRecordList(request);
        List<String> couponStatus = new ArrayList<>();
        couponStatus.add("待审核");
        couponStatus.add("已发行");
        couponStatus.add("审核不通过");
        List<ParamNameVO> couponType = couponCheckService.getParamNameList("COUPON_TYPE");
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        ccr.setCouponStatus(couponStatus);
        ccr.setCouponTypes(couponType);

        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());

        }
        return new AdminResult<>(ccr);
    }


    @ApiOperation(value = "详情页面", notes = "详情页面")
    @RequestMapping(value = "/infoAction", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    public AdminResult updateCouponConfig(@RequestBody CouponConfigRequest request) {
        CouponConfigResponse response = new CouponConfigResponse();
        CouponConfigVO configVO = new CouponConfigVO();
        //操作平台
        List<ParamNameVO> clients = couponCheckService.getParamNameList("CLIENT");
        //有效期类型
        List<ParamNameVO> expType = couponCheckService.getParamNameList("COUPON_EXP_TYPE");
        //项目类型
        List<BorrowProjectTypeVO> projectTypes = couponConfigService.getCouponProjectTypeList();
        response.setClients(clients);
        response.setExpType(expType);
        response.setProjectTypes(projectTypes);

        // 被选中操作平台
        List<String> selectedClientList = new ArrayList<String>();
        // 被选中项目类型
        List<String> selectedProjectList = new ArrayList<String>();
        // 被选中操作平台表示用
        StringBuffer selectedClientDisplayBuffer = new StringBuffer();
        // 被选中项目类别表示用
        StringBuffer selectedProjectDisplayBuffer = new StringBuffer();

        if (request.getId() != null) {
            response = couponConfigService.getCouponConfig(request);
            if (response != null) {
                configVO = response.getResult();
                if (configVO.getAuditUser() != null) {
                    String userName = couponConfigService.getAdminInfoByUserId(configVO.getAuditUser());
                    configVO.setAuditUser(userName);
                }
                // 如果审核时间不为空
                if (configVO.getAudittime() != null) {
                    configVO.setAudittime(configVO.getAudittime());
                }
                if (null != configVO.getExpirationdate()) {
                    configVO.setExpirationdate(configVO.getExpirationdate());
                }
                // 被选中操作平台
                String clientSed[] = StringUtils.split(configVO.getCouponSystem(),
                        ",");
                for (int i = 0; i < clientSed.length; i++) {
                    selectedClientList.add(clientSed[i]);
                    if ("-1".equals(clientSed[i])) {
                        selectedClientDisplayBuffer.append("全部平台");
                        break;
                    } else {
                        for (ParamNameVO paramName : clients) {
                            if (clientSed[i].equals(paramName.getNameCd())) {
                                if (i != 0 && selectedClientDisplayBuffer.length() != 0) {
                                    selectedClientDisplayBuffer.append("/");
                                }
                                selectedClientDisplayBuffer.append(paramName.getName());

                            }
                        }
                    }
                }

                String selectedClientDisplay = selectedClientDisplayBuffer
                        .toString();
                selectedClientDisplay = StringUtils.removeEnd(
                        selectedClientDisplay, "/");
                response.setSelectedClientDisplay(selectedClientDisplay);

                // 被选中项目类型    新逻辑 pcc20160715
                String projectSed[] = StringUtils.split(configVO.getProjectType(),
                        ",");
                String selectedProjectDisplay = "";
                if (configVO.getProjectType().indexOf("-1") != -1) {
                    selectedProjectDisplay = "所有散标/新手/智投项目";
                } else {
                    selectedProjectDisplayBuffer.append("所有");
                    for (String project : projectSed) {
                        if ("1".equals(project)) {
                            selectedProjectDisplayBuffer.append("散标/");
                        }
                        if ("2".equals(project)) {
                            selectedProjectDisplayBuffer.append("");
                        }
                        if ("3".equals(project)) {
                            selectedProjectDisplayBuffer.append("新手/");
                        }
                        if ("4".equals(project)) {
                            selectedProjectDisplayBuffer.append("");
                        }
                        if ("5".equals(project)) {
                            selectedProjectDisplayBuffer.append("");
                        }
                        if ("6".equals(project)) {
                            selectedProjectDisplayBuffer.append("智投/");
                        }

                    }
                    selectedProjectDisplay = selectedProjectDisplayBuffer
                            .toString();
                    selectedProjectDisplay = StringUtils.removeEnd(
                            selectedProjectDisplay, "/");
                    selectedProjectDisplay = selectedProjectDisplay + "项目";
                }
                response.setSelectedProjectDisplay(selectedProjectDisplay);
            }
            if (response == null) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            if (!Response.isSuccess(response)) {
                return new AdminResult<>(FAIL, response.getMessage());
            }
            return new AdminResult<>(response);
        } else {
            configVO.setExpirationDate(GetDate.getNowTime10());
            response.setResult(configVO);
        }
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "保存修改信息", notes = "保存修改信息")
    @PostMapping("/saveAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    public AdminResult saveCouponConfig(@RequestBody CouponConfigRequest request) {
        if (request.getAuditUser() != null) {
            Integer userId = couponConfigService.getUserId(request.getAuditUser());
            request.setAuditUser(String.valueOf(userId));
        }
        CouponConfigResponse ccr = couponConfigService.saveCouponConfig(request);
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "发行优惠券", notes = "发行优惠券")
    @PostMapping("/insertAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertAction(@RequestBody CouponConfigRequest couponConfigRequest, HttpServletRequest request) {
        logger.info("添加优惠券入参：{}", JSON.toJSONString(request));
        AdminSystemVO user = getUser(request);
        String userId = user.getId();
        Integer tenderQuotaType = couponConfigRequest.getTenderQuotaType();
        if (tenderQuotaType != null) {
            if (tenderQuotaType == 0 || tenderQuotaType == 2) {
                couponConfigRequest.setTenderQuotaMin(100);
                couponConfigRequest.setTenderQuotaMax(1000000);
            }
        }
        if (couponConfigRequest.getCouponType() != 1) {
            couponConfigRequest.setAddFlag(0);
            couponConfigRequest.setRepayTimeConfig(1);
        }
        couponConfigRequest.setCreateUserId(Integer.parseInt(userId));
        couponConfigRequest.setUpdateUserId(Integer.parseInt(userId));
        CouponConfigResponse ccr = couponConfigService.insertAction(couponConfigRequest);
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());
        }
        return new AdminResult<>();
    }


    @ApiOperation(value = "删除优惠券信息", notes = "删除优惠券信息")
    @RequestMapping(value = "/deleteAction/{id}", method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteAction(@PathVariable String id) {
        CouponConfigRequest couponConfigRequest = new CouponConfigRequest();
        couponConfigRequest.setId(id);
        CouponConfigResponse ccr = couponConfigService.deleteAction(couponConfigRequest);
        if (ccr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccr)) {
            return new AdminResult<>(FAIL, ccr.getMessage());
        }
        return new AdminResult<>();
    }


    @ApiOperation(value = "审核页面信息", notes = "审核页面信息")
    @RequestMapping(value = "/auditInfo/{id}", method = RequestMethod.GET)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_AUDIT)
    public AdminResult auditInfo(@PathVariable String id) {
        CouponConfigRequest ccfr = new CouponConfigRequest();

        //操作平台
        List<ParamNameVO> clients = couponCheckService.getParamNameList("CLIENT");
        //有效期类型
        List<ParamNameVO> expType = couponCheckService.getParamNameList("COUPON_EXP_TYPE");
        //项目类型
        List<BorrowProjectTypeVO> projectTypes = couponConfigService.getCouponProjectTypeList();

        // 被选中操作平台
        List<String> selectedClientList = new ArrayList<String>();
        // 被选中项目类型
        List<String> selectedProjectList = new ArrayList<String>();
        // 被选中操作平台表示用
        StringBuffer selectedClientDisplayBuffer = new StringBuffer();
        // 被选中项目类别表示用
        StringBuffer selectedProjectDisplayBuffer = new StringBuffer();

        ccfr.setId(id);
        CouponConfigResponse response = couponConfigService.getAuditInfo(ccfr);
        if (response != null) {
            CouponConfigVO configVO = response.getResult();
            if (configVO.getAuditUser() != null) {
                String userName = couponConfigService.getAdminInfoByUserId(configVO.getAuditUser());
                configVO.setAuditUser(userName);
            }
            // 如果审核时间不为空
            if (configVO.getAudittime() != null) {
                configVO.setAudittime(configVO.getAudittime());
            }
            if (null != configVO.getExpirationdate()) {
                configVO.setExpirationdate(configVO.getExpirationdate());
            }
            // 被选中操作平台
            String clientSed[] = StringUtils.split(configVO.getCouponSystem(),
                    ",");
            for (int i = 0; i < clientSed.length; i++) {
                selectedClientList.add(clientSed[i]);
                if ("-1".equals(clientSed[i])) {
                    selectedClientDisplayBuffer.append("全部平台");
                    break;
                } else {
                    for (ParamNameVO paramName : clients) {
                        if (clientSed[i].equals(paramName.getNameCd())) {
                            if (i != 0 && selectedClientDisplayBuffer.length() != 0) {
                                selectedClientDisplayBuffer.append("/");
                            }
                            selectedClientDisplayBuffer.append(paramName.getName());

                        }
                    }
                }
            }

            String selectedClientDisplay = selectedClientDisplayBuffer
                    .toString();
            selectedClientDisplay = StringUtils.removeEnd(
                    selectedClientDisplay, "/");
            response.setSelectedClientDisplay(selectedClientDisplay);

            // 被选中项目类型    新逻辑 pcc20160715
            String projectSed[] = StringUtils.split(configVO.getProjectType(),
                    ",");
            String selectedProjectDisplay = "";
            if (configVO.getProjectType().indexOf("-1") != -1) {
                selectedProjectDisplay = "所有散标/新手/智投项目";
            } else {
                selectedProjectDisplayBuffer.append("所有");
                for (String project : projectSed) {
                    if ("1".equals(project)) {
                        selectedProjectDisplayBuffer.append("散标/");
                    }
                    if ("2".equals(project)) {
                        selectedProjectDisplayBuffer.append("");
                    }
                    if ("3".equals(project)) {
                        selectedProjectDisplayBuffer.append("新手/");
                    }
                    if ("4".equals(project)) {
                        selectedProjectDisplayBuffer.append("");
                    }
                    if ("5".equals(project)) {
                        selectedProjectDisplayBuffer.append("");
                    }
                    if ("6".equals(project)) {
                        selectedProjectDisplayBuffer.append("智投/");
                    }

                }
                selectedProjectDisplay = selectedProjectDisplayBuffer
                        .toString();
                selectedProjectDisplay = StringUtils.removeEnd(
                        selectedProjectDisplay, "/");
                selectedProjectDisplay = selectedProjectDisplay + "项目";
            }
            response.setSelectedProjectDisplay(selectedProjectDisplay);
        }
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }


    @ApiOperation(value = "保存审核信息", notes = "保存审核信息")
    @PostMapping("/auditUpdateAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_AUDIT)
    public AdminResult updateAudit(HttpServletRequest request, @RequestBody CouponConfigRequest couponConfigRequest) {
        AdminSystemVO user = getUser(request);
        String userId = user.getId();
        couponConfigRequest.setAuditUser(userId);
        CouponConfigResponse ccfr = couponConfigService.updatrAudit(couponConfigRequest);
        if (ccfr == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(ccfr)) {
            return new AdminResult<>(FAIL, ccfr.getMessage());
        }
        return new AdminResult<>();
    }

    @ApiOperation(value = "检查编号唯一性", notes = "检查编号唯一性")
    @PostMapping("/checkAction")
    @AuthorityAnnotation(key = PERMISSIONS, value = {ShiroConstants.PERMISSION_ADD, ShiroConstants.PERMISSION_MODIFY})
    public AdminResult checkAction(@RequestBody CouponConfigRequest request) {
        Integer couponQuantity = request.getCouponQuantity();
        // 优惠券编号
        String couponCode = request.getCouponCode();
        CouponUserResponse response = couponConfigService.getIssueNumber(couponCode);
        if (response.getCount() > couponQuantity) {
            String message = "修改数量不能小于已发放数量，已发放" + response.getCount() + "张";
            response.setMessage(message);
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }


    //@ApiOperation(value = "导出", notes = "导出")
    //@RequestMapping(value = "/export", method = RequestMethod.POST)
    /*public void exportAction(HttpServletResponse response, @RequestBody CouponConfigRequest request) throws Exception {
        //表格sheet名称
        String sheetName = "优惠券发行列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        String[] titles = new String[]{"序号", "优惠券名称", "优惠券编号", "优惠券类型", "优惠券面值",
                "发行数量", "已发放数量", "有效期", "使用范围-操作平台", "使用范围-项目类型",
                "使用范围-出借金额", "使用范围-项目期限", "状态", "发行时间"};
        //操作平台
        List<ParamNameVO> clients = this.couponCheckService.getParamNameList("CLIENT");
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        CouponConfigExportCustomizeResponse exportCustomizeResponse = couponConfigService.getExportConfigList(request);
        if (null != exportCustomizeResponse) {
            List<CouponConfigExportCustomizeVO> configVOS = exportCustomizeResponse.getResultList();
            if (!CollectionUtils.isEmpty(configVOS)) {
                int sheetCount = 1;
                int rowNum = 0;
                for (int i = 0; i < configVOS.size(); i++) {
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
                        CouponConfigExportCustomizeVO config = configVOS.get(i);
                        // 创建相应的单元格
                        Cell cell = row.createCell(celLength);
                        if (celLength == 0) {// 序号
                            cell.setCellValue(i + 1);
                        } else if (celLength == 1) {
                            cell.setCellValue(config.getCouponName());
                        } else if (celLength == 2) {
                            cell.setCellValue(config.getCouponCode());
                        } else if (celLength == 3) {
                            if ("1".equals(config.getCouponType())) {
                                cell.setCellValue("体验金");
                            } else if ("2".equals(config.getCouponType())) {
                                cell.setCellValue("加息券");
                            } else {
                                cell.setCellValue("代金券");
                            }
                        } else if (celLength == 4) {
                            if ("2".equals(config.getCouponType())) {
                                cell.setCellValue(config.getCouponQuota() + "%");
                            } else if ("1".equals(config.getCouponType()) || "3".equals(config.getCouponType())) {
                                cell.setCellValue(config.getCouponQuota() + "元");
                            }
                        } else if (celLength == 5) {
                            cell.setCellValue(config.getCouponQuantity());
                        } else if (celLength == 6) {
                            cell.setCellValue(config.getIssueNumber());
                        } else if (celLength == 7) {
                            cell.setCellValue(config.getExpirationDate());
                        } else if (celLength == 8) {
                            //被选中操作平台
                            String clientString = "";
                            //被选中操作平台
                            String clientSed[] = StringUtils.split(config.getCouponSystem(), ",");
                            for (int j = 0; j < clientSed.length; j++) {
                                if ("-1".equals(clientSed[j])) {
                                    clientString = clientString + "不限";
                                    break;
                                } else {
                                    for (ParamNameVO paramName : clients) {
                                        if (clientSed[j].equals(paramName.getNameCd())) {
                                            if (j != 0 && clientString.length() != 0) {
                                                clientString = clientString + "、";
                                            }
                                            clientString = clientString + paramName.getName();
                                        }
                                    }
                                }
                            }
                            cell.setCellValue(clientString);
                        } else if (celLength == 9) {
                            // 被选中项目类别表示用
                            StringBuffer selectedProjectDisplayBuffer = new StringBuffer();
                            // 被选中项目类型   新逻辑 pcc20160715
                            String projectSed[] = StringUtils.split(config.getProjectType(),
                                    ",");
                            String selectedProjectDisplay = "";
                            if (config.getProjectType().indexOf("-1") != -1) {
                                selectedProjectDisplay = "所有汇直投/汇消费/新手汇/尊享汇/汇添金/汇计划项目";
                            } else {
                                selectedProjectDisplayBuffer.append("所有");
                                for (String project : projectSed) {
                                    if ("1".equals(project)) {
                                        selectedProjectDisplayBuffer.append("汇直投/");
                                    }
                                    if ("2".equals(project)) {
                                        selectedProjectDisplayBuffer.append("汇消费/");
                                    }
                                    if ("3".equals(project)) {
                                        selectedProjectDisplayBuffer.append("新手汇/");
                                    }
                                    if ("4".equals(project)) {
                                        selectedProjectDisplayBuffer.append("尊享汇/");
                                    }
                                    if ("5".equals(project)) {
                                        selectedProjectDisplayBuffer.append("汇添金/");
                                    }
                                    if ("6".equals(project)) {
                                        selectedProjectDisplayBuffer.append("汇计划/");
                                    }

                                }
                                selectedProjectDisplay = selectedProjectDisplayBuffer
                                        .toString();
                                selectedProjectDisplay = StringUtils.removeEnd(
                                        selectedProjectDisplay, "/");
                                selectedProjectDisplay = selectedProjectDisplay + "项目";
                            }
                            cell.setCellValue(selectedProjectDisplay);
                        } else if (celLength == 10) {
                            cell.setCellValue(config.getTenderQuota());
                        } else if (celLength == 11) {
                            cell.setCellValue(config.getProjectExpirationType());
                        } else if (celLength == 12) {
                            cell.setCellValue(config.getStatus());
                        } else if (celLength == 13) {
                            cell.setCellValue(config.getAddTime());
                        }
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }*/

    /**
     * 导出excel
     *
     * @param couponConfigRequest
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "导出", notes = "导出")
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportActionCc(HttpServletRequest request, HttpServletResponse response, @RequestBody CouponConfigRequest couponConfigRequest) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "优惠券发行列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xlsx";
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();
        couponConfigRequest.setLimitFlg(true);

        // 导出列表总数
        Integer totalCount = couponConfigService.getCountForExport(couponConfigRequest);
        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = sheetName + "_第1页";
        if (totalCount == 0) {
            helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, new ArrayList());
        }
        //设置默认5000条/页
        couponConfigRequest.setPageSize(defaultRowMaxCount);
        couponConfigRequest.setExportCount(totalCount);
        for (int i = 1; i <= sheetCount; i++) {
            couponConfigRequest.setPageSize(defaultRowMaxCount);
            couponConfigRequest.setCurrPage(i);
            CouponConfigExportCustomizeResponse exportCustomizeResponse = couponConfigService.getExportConfigList(couponConfigRequest);
            if (exportCustomizeResponse != null && exportCustomizeResponse.getResultList().size()> 0) {
                //实现多个参数判断返回问题
                for(CouponConfigExportCustomizeVO couponConfigExportCustomizeVO : exportCustomizeResponse.getResultList()){
                    if ("2".equals(couponConfigExportCustomizeVO.getCouponType())) {
                        couponConfigExportCustomizeVO.setCouponQuota(couponConfigExportCustomizeVO.getCouponQuota() + "%");
                    } else if ("1".equals(couponConfigExportCustomizeVO.getCouponType()) || "3".equals(couponConfigExportCustomizeVO.getCouponType())) {
                        couponConfigExportCustomizeVO.setCouponQuota(couponConfigExportCustomizeVO.getCouponQuota() + "元");
                    }
                }
                sheetNameTmp = sheetName + "_第" + i + "页";
                helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter,  exportCustomizeResponse.getResultList());
            } else {
                break;
            }
        }
        DataSet2ExcelSXSSFHelper.write2Response(request, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("couponName", "优惠券名称");
        map.put("couponCode", "优惠券编号");
        map.put("couponType", "优惠券类型");
        map.put("couponQuota", "优惠券面值");
        map.put("couponQuantity", "发行数量");
        map.put("issueNumber", "已发放数量");
        map.put("expirationDate", "有效期");
        map.put("couponSystem", "使用范围-操作平台");
        map.put("projectType", "使用范围-项目类型");
        map.put("tenderQuota", "使用范围-出借金额");
        map.put("projectExpirationType", "使用范围-项目期限");
        map.put("status", "状态");
        map.put("addTime", "发行时间");
        return map;
    }

    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        IValueFormatter couponTypeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String couponType = (String) object;
                if ("1".equals(couponType)) {
                    return ("体验金");
                } else if ("2".equals(couponType)) {
                    return ("加息券");
                } else {
                    return ("代金券");
                }
            }
        };

        IValueFormatter couponSystemAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                //操作平台
                List<ParamNameVO> clients = couponCheckService.getParamNameList("CLIENT");
                String couponSystem = (String) object;
                //被选中操作平台
                String clientString = "";
                //被选中操作平台
                String clientSed[] = StringUtils.split(couponSystem, ",");
                for (int j = 0; j < clientSed.length; j++) {
                    if ("-1".equals(clientSed[j])) {
                        clientString = clientString + "不限";
                        break;
                    } else {
                        for (ParamNameVO paramName : clients) {
                            if (clientSed[j].equals(paramName.getNameCd())) {
                                if (j != 0 && clientString.length() != 0) {
                                    clientString = clientString + "、";
                                }
                                clientString = clientString + paramName.getName();
                            }
                        }
                    }
                }
                return clientString;
            }
        };

        IValueFormatter projectTypeAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String projectType = (String) object;
                // 被选中项目类别表示用
                StringBuffer selectedProjectDisplayBuffer = new StringBuffer();
                // 被选中项目类型   新逻辑 pcc20160715
                String projectSed[] = StringUtils.split(projectType,
                        ",");
                String selectedProjectDisplay = "";
                if (projectType.indexOf("-1") != -1) {
                    selectedProjectDisplay = "所有散标/新手/智投项目";
                } else {
                    selectedProjectDisplayBuffer.append("所有");
                    for (String project : projectSed) {
                        if ("1".equals(project)) {
                            selectedProjectDisplayBuffer.append("散标/");
                        }
                        if ("2".equals(project)) {
                            selectedProjectDisplayBuffer.append("");
                        }
                        if ("3".equals(project)) {
                            selectedProjectDisplayBuffer.append("新手/");
                        }
                        if ("4".equals(project)) {
                            selectedProjectDisplayBuffer.append("");
                        }
                        if ("5".equals(project)) {
                            selectedProjectDisplayBuffer.append("");
                        }
                        if ("6".equals(project)) {
                            selectedProjectDisplayBuffer.append("智投/");
                        }

                    }
                    selectedProjectDisplay = selectedProjectDisplayBuffer
                            .toString();
                    selectedProjectDisplay = StringUtils.removeEnd(
                            selectedProjectDisplay, "/");
                    selectedProjectDisplay = selectedProjectDisplay + "项目";
                }
                return selectedProjectDisplay;
            }
        };

        mapAdapter.put("couponType", couponTypeAdapter);
        mapAdapter.put("couponSystem", couponSystemAdapter);
        mapAdapter.put("projectType", projectTypeAdapter);
        return mapAdapter;
    }
}