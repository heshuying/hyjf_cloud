/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.config;

import com.google.common.collect.Maps;
import com.hyjf.admin.beans.request.BailConfigRequestBean;
import com.hyjf.admin.beans.response.BailConfigResponseBean;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BailConfigService;
import com.hyjf.admin.utils.ConvertUtils;
import com.hyjf.admin.utils.ExportExcel;
import com.hyjf.admin.utils.Page;
import com.hyjf.admin.utils.exportutils.DataSet2ExcelSXSSFHelper;
import com.hyjf.admin.utils.exportutils.IValueFormatter;
import com.hyjf.am.resquest.admin.BailConfigAddRequest;
import com.hyjf.am.resquest.admin.BailConfigRequest;
import com.hyjf.am.vo.admin.BailConfigCustomizeVO;
import com.hyjf.am.vo.admin.BailConfigInfoCustomizeVO;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigController, v0.1 2018/9/26 15:30
 */
@Api(value = "配置中心-合作额度配置", tags = "配置中心-合作额度配置")
@RestController
@RequestMapping("/hyjf-admin/bail_config")
public class BailConfigController extends BaseController {

    public static final String PERMISSIONS = "bailconfig";

    @Autowired
    BailConfigService bailConfigService;

    /**
     * 合作额度配置列表查询
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "合作额度配置列表查询", notes = "合作额度配置列表查询")
    @PostMapping(value = "/search")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BailConfigResponseBean> search(@RequestBody BailConfigRequest request) {

        BailConfigResponseBean bean = new BailConfigResponseBean();

        Integer count = bailConfigService.selectBailConfigCount(request);
        count = (count == null) ? 0 : count;
        bean.setTotal(count);
        //分页参数
        Page page = Page.initPage(request.getCurrPage(), request.getPageSize());
        page.setTotal(count);
        request.setLimitStart(page.getOffset());
        request.setLimitEnd(page.getLimit());
        if (count > 0) {
            List<BailConfigCustomizeVO> bailConfigCustomizeVOList = bailConfigService.selectRecordList(request);
            if (bailConfigCustomizeVOList == null || bailConfigCustomizeVOList.size() == 0) {
                return new AdminResult<>(FAIL, FAIL_DESC);
            }
            bean.setRecordList(bailConfigCustomizeVOList);
        }
        return new AdminResult(bean);
    }

    /**
     * 画面迁移-合作额度配置详情
     *
     * @param idStr
     * @return
     */
    @ApiOperation(value = "合作额度配置详情", notes = "合作额度配置详情")
    @GetMapping("/info/{idStr}")
    @ResponseBody
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    public AdminResult<BailConfigInfoCustomizeVO> info(@PathVariable String idStr) {
        // 用户ID
        Integer id = GetterUtil.getInteger(idStr);
        if (null == id || 0 == id) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        // 更新当前机构可用的还款方式并返回最新合作额度详情(更新查询分开事务、查询取不到最新更新的数据)
        BailConfigInfoCustomizeVO bailConfigInfoCustomizeVO = bailConfigService.updateSelectBailConfigById(id);
        if (null == bailConfigInfoCustomizeVO) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(bailConfigInfoCustomizeVO);
    }

    /**
     * 未配置合作额度的机构编号下拉框
     *
     * @return
     */
    @ApiOperation(value = "未配置合作额度的机构编号下拉框", notes = "未配置合作额度的机构编号下拉框")
    @GetMapping("/select_noused_inst_config_list")
    public AdminResult<List<DropDownVO>> selectNoUsedInstConfigList() {
        List<HjhInstConfigVO> hjhInstConfigVOList = bailConfigService.selectNoUsedInstConfigList();
        if (null == hjhInstConfigVOList || hjhInstConfigVOList.size() <= 0) {
            return new AdminResult<>(FAIL, "未查询到未配置合作额度的机构");
        }
        List<DropDownVO> dropDownVOList = ConvertUtils.convertListToDropDown(hjhInstConfigVOList, "instCode", "instName");
        AdminResult<List<DropDownVO>> result = new AdminResult<List<DropDownVO>>();
        result.setData(dropDownVOList);
        return result;
    }

    /**
     * 添加合作额度配置
     *
     * @param request
     * @param requestBean
     * @return
     */
    @ApiOperation(value = "添加合作额度配置", notes = "添加合作额度配置")
    @PostMapping("/insert_bail_config")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_ADD)
    public AdminResult insertBailConfig(HttpServletRequest request, @RequestBody BailConfigRequestBean requestBean) {

        BailConfigAddRequest bailConfigAddRequest = new BailConfigAddRequest();
        BeanUtils.copyProperties(requestBean, bailConfigAddRequest);
        // 获取当前添加机构的编号
        String instCode = bailConfigAddRequest.getInstCode();

        // 获取操作人id
        AdminSystemVO adminSystemVO = this.getUser(request);
        String loginUserId = adminSystemVO.getId();
        if (StringUtils.isNotBlank(loginUserId)) {
            bailConfigAddRequest.setCreateUserId(Integer.parseInt(loginUserId));
        }
        bailConfigAddRequest.setCreateTime(new Date());

        // 发标额度上限
//        bailConfigAddRequest.setPushMarkLine(bailConfigAddRequest.getBailTatol().multiply(new BigDecimal("100")).divide(new BigDecimal(bailConfigAddRequest.getBailRate()), 2, BigDecimal.ROUND_DOWN));
        // 发标额度余额（默认为上限）
//        bailConfigAddRequest.setRemainMarkLine(bailConfigAddRequest.getPushMarkLine());

        // 查询周期内发标已发额度
        BigDecimal sendedAccountByCycBD = BigDecimal.ZERO;
        String sendedAccountByCyc = this.bailConfigService.selectSendedAccountByCyc(bailConfigAddRequest);
        if (StringUtils.isNotBlank(sendedAccountByCyc)) {
            sendedAccountByCycBD = new BigDecimal(sendedAccountByCyc);
        }
        bailConfigAddRequest.setCycLoanTotal(sendedAccountByCycBD);

        boolean isInset = bailConfigService.insertBailConfig(bailConfigAddRequest);
        if (isInset) {
            // 根据还款方式更新合作额度还款方式验证的有效性
//            isInset = this.bailConfigService.updateBailInfoDelFlg(instCode);
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        // 更新info表失败
        if (!isInset) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        // 日推标上限记录到redis
//        RedisUtils.set(RedisConstants.DAY_MARK_LINE + instCode, bailConfigAddRequest.getDayMarkLine().toString());
        // 月推标上限记录到redis
//        RedisUtils.set(RedisConstants.MONTH_MARK_LINE + instCode, bailConfigAddRequest.getMonthMarkLine().toString());
        // 日额度累计redis(日累计不存再的情况初始化)
        if (!RedisUtils.exists(RedisConstants.DAY_MARK_ACCUMULATE + instCode)) {
            RedisUtils.set(RedisConstants.DAY_MARK_ACCUMULATE + instCode, "0");
        }
        return new AdminResult<>();
    }

    /**
     * 更新合作额度配置
     *
     * @param request
     * @param requestBean
     * @return
     */
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_MODIFY)
    @ApiOperation(value = "更新合作额度配置", notes = "更新合作额度配置")
    @PostMapping("/update_bail_config")
    public AdminResult updateBailConfig(HttpServletRequest request, @RequestBody BailConfigRequestBean requestBean) {

        BailConfigAddRequest bailConfigAddRequest = new BailConfigAddRequest();
        BeanUtils.copyProperties(requestBean, bailConfigAddRequest);
        // 获取当前添加机构的编号
        String instCode = bailConfigAddRequest.getInstCode();

        // 获取操作人id
        AdminSystemVO adminSystemVO = this.getUser(request);
        String loginUserId = adminSystemVO.getId();
        if (StringUtils.isNotBlank(loginUserId)) {
            bailConfigAddRequest.setUpdateUserId(Integer.parseInt(loginUserId));
        }
        bailConfigAddRequest.setUpdateTime(new Date());

        boolean isInset = bailConfigService.updateBailConfig(bailConfigAddRequest);
        if (isInset) {
            // 根据还款方式更新合作额度还款方式验证的有效性
//            isInset = this.bailConfigService.updateBailInfoDelFlg(instCode);
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        // 更新info表失败
        if (!isInset) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        // 日推标上限记录到redis
//        RedisUtils.set(RedisConstants.DAY_MARK_LINE + instCode, bailConfigAddRequest.getDayMarkLine().toString());
        // 月推标上限记录到redis
//        RedisUtils.set(RedisConstants.MONTH_MARK_LINE + instCode, bailConfigAddRequest.getMonthMarkLine().toString());
        return new AdminResult<>();
    }


    /**
     * 删除合作额度配置
     *
     * @param request
     * @param id
     * @return
     */
    @ApiOperation(value = "删除合作额度配置", notes = "删除合作额度配置")
    @PostMapping("/delete_bail_config")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_DELETE)
    public AdminResult deleteBailConfig(HttpServletRequest request, Integer id) {
        BailConfigAddRequest bailConfigAddRequest = new BailConfigAddRequest();
        bailConfigAddRequest.setId(id);
        // 获取操作人id
        AdminSystemVO adminSystemVO = this.getUser(request);
        String loginUserId = adminSystemVO.getId();
        if (StringUtils.isNotBlank(loginUserId)) {
            bailConfigAddRequest.setUpdateUserId(Integer.parseInt(loginUserId));
        }
        bailConfigAddRequest.setUpdateTime(new Date());
        boolean isInset = this.bailConfigService.deleteBailConfig(bailConfigAddRequest);
        // 删除info表失败
        if (!isInset) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>();
    }

    /**
     * 下拉联动
     *
     * @param instCode
     * @return
     */
    @ApiOperation(value = "下拉联动", notes = "下拉联动")
    @GetMapping("/instcode_change_action/{instCode}")
    @ResponseBody
    public AdminResult<BailConfigInfoCustomizeVO> instCodeChangeAction(@PathVariable String instCode) {

        BailConfigInfoCustomizeVO hjhBailConfigInfoCustomize = new BailConfigInfoCustomizeVO();
        hjhBailConfigInfoCustomize.setEndDEL(1);
        hjhBailConfigInfoCustomize.setEnddayDEL(1);
        hjhBailConfigInfoCustomize.setMonthDEL(1);
        hjhBailConfigInfoCustomize.setEndmonthDEL(1);
        hjhBailConfigInfoCustomize.setPrincipalDEL(1);
        hjhBailConfigInfoCustomize.setSeasonDEL(1);
        hjhBailConfigInfoCustomize.setEndmonthsDEL(1);

        // 获取当前机构可用还款方式
        List<String> repayMethodList = this.bailConfigService.selectRepayMethod(instCode);
        if (repayMethodList != null && repayMethodList.size() > 0) {
            for (String repayMethod : repayMethodList) {
                if ("end".equals(repayMethod)) {
                    hjhBailConfigInfoCustomize.setEndDEL(0);
                }
                if ("endday".equals(repayMethod)) {
                    hjhBailConfigInfoCustomize.setEnddayDEL(0);
                }
                if ("month".equals(repayMethod)) {
                    hjhBailConfigInfoCustomize.setMonthDEL(0);
                }
                if ("endmonth".equals(repayMethod)) {
                    hjhBailConfigInfoCustomize.setEndmonthDEL(0);
                }
                if ("principal".equals(repayMethod)) {
                    hjhBailConfigInfoCustomize.setPrincipalDEL(0);
                }
                if ("season".equals(repayMethod)) {
                    hjhBailConfigInfoCustomize.setSeasonDEL(0);
                }
                if ("endmonths".equals(repayMethod)) {
                    hjhBailConfigInfoCustomize.setEndmonthsDEL(0);
                }
            }
        } else {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult<>(hjhBailConfigInfoCustomize);
    }


    public void exportAccountsExcel(@RequestBody BailConfigRequest request, HttpServletResponse response) throws Exception {
        // 表格sheet名称
        String sheetName = "合作额度配置列表";

        List<BailConfigCustomizeVO> recordList = this.bailConfigService.selectRecordList(request);
        String fileName = null;
        try {
            fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + CustomConstants.EXCEL_EXT;
        } catch (UnsupportedEncodingException e) {
            logger.error("转码错误....", e);
        }

        String[] titles = new String[]{"序号", "资产来源", "日推标额度", "月推标额度", "未用额度是否累计", "授信周期",
                "保证金比例", "保证金金额", "新增授信额度", "在贷余额额度", "还款授信方式:等额本息", "还款授信方式:按月计息，到期还本还息",
                "还款授信方式:先息后本", "还款授信方式:按天计息，到期还本息", "还款授信方式:等额本金"};

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (recordList != null && recordList.size() > 0) {

            int sheetCount = 1;
            int rowNum = 0;

            for (int i = 0; i < recordList.size(); i++) {
                rowNum++;
                if (i != 0 && i % 60000 == 0) {
                    sheetCount++;
                    sheet = ExportExcel
                            .createHSSFWorkbookTitle(workbook, titles, (sheetName + "_第" + sheetCount + "页"));
                    rowNum = 1;
                }

                // 新建一行
                Row row = sheet.createRow(rowNum);
                // 循环数据
                for (int celLength = 0; celLength < titles.length; celLength++) {
                    BailConfigInfoCustomizeVO record = this.bailConfigService.updateSelectBailConfigById(recordList.get(i).getId());
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    // 序号
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 资产来源
                    else if (celLength == 1) {
                        cell.setCellValue(record.getInstName());
                    }
                    // 日推标额度
                    else if (celLength == 2) {
                        cell.setCellValue(record.getDayMarkLine().toString());
                    }
                    // 月推标额度
                    else if (celLength == 3) {
                        cell.setCellValue(record.getMonthMarkLine().toString());
                    }
                    // 未用额度是否累计
                    else if (celLength == 4) {
                        cell.setCellValue(record.getIsAccumulate() == 0 ? "否" : "是");
                    }
                    // 授信周期
                    else if (celLength == 5) {
                        cell.setCellValue(record.getTimestart() + "至" + record.getTimeend());
                    }
                    // 保证金比例
                    else if (celLength == 6) {
                        cell.setCellValue(record.getBailRate() + "%");
                    }
                    // 保证金金额
                    else if (celLength == 7) {
                        cell.setCellValue(record.getBailTatol().toString());
                    }
                    // 新增授信额度
                    else if (celLength == 8) {
                        cell.setCellValue(record.getNewCreditLine().toString());
                    }
                    // 在贷余额额度
                    else if (celLength == 9) {
                        cell.setCellValue(record.getLoanCreditLine().toString());
                    }
                    // 还款授信方式 等额本息
                    else if (celLength == 10) {
                        StringBuffer sb = new StringBuffer();
                        if (record.getMonthNCL() == 1) {
                            sb.append("新增授信+");
                        }
                        if (record.getMonthLCL() == 1) {
                            sb.append("在贷授信+");
                        }
                        if (record.getMonthRCT() == 0) {
                            sb.append("到期回滚");
                        } else if (record.getMonthRCT() == 1) {
                            sb.append("分期回滚");
                        } else if (record.getMonthRCT() == 2) {
                            sb.append("不回滚");
                        }
                        cell.setCellValue(sb.toString());
                    }
                    // 还款授信方式 按月计息，到期还本还息
                    else if (celLength == 11) {
                        StringBuffer sb = new StringBuffer();
                        if (record.getEndNCL() == 1) {
                            sb.append("新增授信+");
                        }
                        if (record.getEndLCL() == 1) {
                            sb.append("在贷授信+");
                        }
                        if (record.getEndRCT() == 0) {
                            sb.append("到期回滚");
                        } else if (record.getEndRCT() == 1) {
                            sb.append("分期回滚");
                        } else if (record.getEndRCT() == 2) {
                            sb.append("不回滚");
                        }
                        cell.setCellValue(sb.toString());
                    }
                    // 还款授信方式 先息后本
                    else if (celLength == 12) {
                        StringBuffer sb = new StringBuffer();
                        if (record.getEndmonthNCL() == 1) {
                            sb.append("新增授信+");
                        }
                        if (record.getEndmonthLCL() == 1) {
                            sb.append("在贷授信+");
                        }
                        if (record.getEndmonthRCT() == 0) {
                            sb.append("到期回滚");
                        } else if (record.getEndmonthRCT() == 1) {
                            sb.append("分期回滚");
                        } else if (record.getEndmonthRCT() == 2) {
                            sb.append("不回滚");
                        }
                        cell.setCellValue(sb.toString());
                    }
                    // 还款授信方式 按天计息，到期还本息
                    else if (celLength == 13) {
                        StringBuffer sb = new StringBuffer();
                        if (record.getEnddayNCL() == 1) {
                            sb.append("新增授信+");
                        }
                        if (record.getEnddayLCL() == 1) {
                            sb.append("在贷授信+");
                        }
                        if (record.getEnddayRCT() == 0) {
                            sb.append("到期回滚");
                        } else if (record.getEnddayRCT() == 1) {
                            sb.append("分期回滚");
                        } else if (record.getEnddayRCT() == 2) {
                            sb.append("不回滚");
                        }
                        cell.setCellValue(sb.toString());
                    }
                    // 还款授信方式 等额本金
                    else if (celLength == 14) {
                        StringBuffer sb = new StringBuffer();
                        if (record.getPrincipalNCL() == 1) {
                            sb.append("新增授信+");
                        }
                        if (record.getPrincipalLCL() == 1) {
                            sb.append("在贷授信+");
                        }
                        if (record.getPrincipalRCT() == 0) {
                            sb.append("到期回滚");
                        } else if (record.getPrincipalRCT() == 1) {
                            sb.append("分期回滚");
                        } else if (record.getPrincipalRCT() == 2) {
                            sb.append("不回滚");
                        }
                        cell.setCellValue(sb.toString());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
    /**
     * 保证金配置列表导出
     *
     * @param request
     * @param response
     * @param request
     */
    @ApiOperation(value = "合作额度配置列表导出", notes = "合作额度配置列表导出")
    @PostMapping("/export_account_detail_excel")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    public void exportToExcel(HttpServletRequest requestt,@RequestBody BailConfigRequest request, HttpServletResponse response) throws Exception {
        //sheet默认最大行数
        int defaultRowMaxCount = Integer.valueOf(systemConfig.getDefaultRowMaxCount());
        // 表格sheet名称
        String sheetName = "合作额度配置列表";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) +  CustomConstants.EXCEL_EXT;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        DataSet2ExcelSXSSFHelper helper = new DataSet2ExcelSXSSFHelper();

        //请求第一页5000条
        request.setPageSize(defaultRowMaxCount);
        

        Integer totalCount = bailConfigService.selectBailConfigCount(request);

        int sheetCount = (totalCount % defaultRowMaxCount) == 0 ? totalCount / defaultRowMaxCount : totalCount / defaultRowMaxCount + 1;
        Map<String, String> beanPropertyColumnMap = buildMap();
        Map<String, IValueFormatter> mapValueAdapter = buildValueAdapter();
        String sheetNameTmp = "";

        for (int i = 1; i <= sheetCount; i++) {
        	request.setCurrPage(i);
			sheetNameTmp = sheetName + "_第" + (i) + "页";
			helper.export(workbook, sheetNameTmp, beanPropertyColumnMap, mapValueAdapter, this.bailConfigService.selectRecordList(request));
        }
        DataSet2ExcelSXSSFHelper.write2Response(requestt, response, fileName, workbook);
    }

    private Map<String, String> buildMap() {
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("instName", "资产来源");
//        map.put("bailTatol", "保证金金额");
//        map.put("bailRate", "保证金比例");
//        map.put("newCreditLine", "新增授信额度");
//        map.put("loanCreditLine", "在贷授信额度");
        map.put("newCreditLine", "合作额度");
        map.put("dayMarkLine", "日推标额度");
        map.put("monthMarkLine", "月推标额度");
//        map.put("pushMarkLine", "发标额度上限");
//        map.put("loanMarkLine", "发标已发额度");
//        map.put("remainMarkLine", "发标额度余额");
//        map.put("repayedCapital", "已还本金");
        map.put("cycLoanTotal", "周期内发标额度");
        return map;
    }
    private Map<String, IValueFormatter> buildValueAdapter() {
        Map<String, IValueFormatter> mapAdapter = Maps.newHashMap();
        /*IValueFormatter isAccumulateAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                String isAccumulateAdapter = (String) object;
                if("0".equals(isAccumulateAdapter)) {
                	return "否";
                }else {
                	return "是";
                }

            }
        };
        IValueFormatter bailRateAdapter = new IValueFormatter() {
            @Override
            public String format(Object object) {
                Integer bailRate = (Integer) object;
                return bailRate+"%";
            }
        };

        mapAdapter.put("isAccumulateAdapter", isAccumulateAdapter);
        mapAdapter.put("bailRate", bailRateAdapter);*/
        return mapAdapter;
    }
}
