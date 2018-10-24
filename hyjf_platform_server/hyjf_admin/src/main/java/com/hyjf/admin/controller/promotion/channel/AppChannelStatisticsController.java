/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.promotion.channel;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ExportExcel;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.AdminUtmReadPermissionsService;
import com.hyjf.admin.service.promotion.AppChannelStatisticsService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.app.AppChannelStatisticsResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.vo.config.AdminSystemVO;
import com.hyjf.am.vo.config.AdminUtmReadPermissionsVO;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.StringPool;
import com.hyjf.common.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @author yaoyong
 * @version AppChannelStatisticsController, v0.1 2018/9/21 9:24
 */
@Api(tags = "推广中心-app渠道统计")
@RestController
@RequestMapping("/hyjf-admin/promotion/app")
public class AppChannelStatisticsController extends BaseController {

    @Autowired
    private AppChannelStatisticsService appChannelStatisticsService;
    @Autowired
    private AdminUtmReadPermissionsService adminUtmReadPermissionsService;

    @ApiOperation(value = "页面初始化", notes = "页面初始化")
    @RequestMapping(value = "/channelStatistics", method = RequestMethod.POST)
    public AdminResult init(HttpServletRequest request, @RequestBody AppChannelStatisticsRequest statisticsRequest) {
        AdminResult result = new AdminResult();
        //获取后天用户登录id
        AdminSystemVO adminSystemVO = getUser(request);
        String userId = adminSystemVO.getId();

        // 根据用户Id查询渠道账号管理
        AdminUtmReadPermissionsVO permissionsVO = adminUtmReadPermissionsService.selectAdminUtmReadPermissions(userId);
        if (permissionsVO != null) {
            statisticsRequest.setUtmIds(permissionsVO.getUtmIds());
        }
        // 渠道
        String[] utmIds = new String[]{};
        if (Validator.isNotNull(statisticsRequest.getUtmIds())) {
            if (statisticsRequest.getUtmIds().contains(StringPool.COMMA)) {
                utmIds = statisticsRequest.getUtmIds().split(StringPool.COMMA);
                statisticsRequest.setUtmIdsSrch(utmIds);
            } else {
                utmIds = new String[]{statisticsRequest.getUtmIds()};
                statisticsRequest.setUtmIdsSrch(utmIds);
            }
        }
        AppChannelStatisticsResponse response = appChannelStatisticsService.searchList(statisticsRequest);
        if (response == null) {
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        if (!Response.isSuccess(response)) {
            return new AdminResult<>(FAIL, response.getMessage());
        }
        return new AdminResult<>(response);
    }

    @ApiOperation(value = "导出", notes = "导出")
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void export(HttpServletResponse response, HttpServletRequest request, @RequestBody AppChannelStatisticsRequest statisticsRequest) throws UnsupportedEncodingException {
        //获取后天用户登录id
        AdminSystemVO adminSystemVO = getUser(request);
        String userId = adminSystemVO.getId();
        // 根据用户Id查询渠道账号管理
        AdminUtmReadPermissionsVO permissionsVO = adminUtmReadPermissionsService.selectAdminUtmReadPermissions(userId);
        if (permissionsVO != null) {
            statisticsRequest.setUtmIds(permissionsVO.getUtmIds());
        }
        // 表格sheet名称
        String sheetName = "app渠道统计";
        // 文件名称
        String fileName = URLEncoder.encode(sheetName, CustomConstants.UTF8) + StringPool.UNDERLINE + GetDate.getServerDateTime(8, new Date()) + ".xls";
        // 渠道
        String[] utmIds = new String[]{};
        if (Validator.isNotNull(statisticsRequest.getUtmIds())) {
            if (statisticsRequest.getUtmIds().contains(StringPool.COMMA)) {
                utmIds = statisticsRequest.getUtmIds().split(StringPool.COMMA);
                statisticsRequest.setUtmIdsSrch(utmIds);
            } else {
                utmIds = new String[]{statisticsRequest.getUtmIds()};
                statisticsRequest.setUtmIdsSrch(utmIds);
            }
        }
        AppChannelStatisticsResponse statisticsResponse = appChannelStatisticsService.exportList(statisticsRequest);
        String[] titles = new String[]{"序号", "渠道", "访问数", "注册数", "注册数(无主单)", "开户数", "开户数(无主单)", "开户数(PC)", "开户数(iOS)", "开户数(Android)", "开户数(微官网)", "投资人数", "投资人数(无主单)", "投资人数(PC)", "投资人数(iOS)", "投资人数(Android)", "投资人数(微官网)", "累计充值", "累计充值(无主单)", "累计投资", "累计投资(无主单)", "汇直投投资金额", "汇消费投资金额", "汇天利投资金额",
                "汇添金投资金额", "汇金理财投资金额", "汇转让投资金额"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = ExportExcel.createHSSFWorkbookTitle(workbook, titles, sheetName + "_第1页");
        if (null != statisticsResponse) {
            List<AppChannelStatisticsVO> voList = statisticsResponse.getResultList();
            int sheetCount = 1;
            int rowNum = 0;
            for (int i = 0; i < voList.size(); i++) {
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
                    AppChannelStatisticsVO statisticsVO = voList.get(i);
                    // 创建相应的单元格
                    Cell cell = row.createCell(celLength);
                    if (celLength == 0) {
                        cell.setCellValue(i + 1);
                    }
                    // 平台
                    else if (celLength == 1) {
                        cell.setCellValue(statisticsVO.getChannelName());
                    }
                    // 访问数
                    else if (celLength == 2) {
                        cell.setCellValue(statisticsVO.getVisitCount());
                    }
                    // 注册数
                    else if (celLength == 3) {
                        cell.setCellValue(statisticsVO.getRegisterCount());
                    }
                    // 注册数（无主单）
                    else if (celLength == 4) {
                        cell.setCellValue(statisticsVO.getRegisterAttrCount().doubleValue());
                    }
                    // 开户数
                    else if (celLength == 5) {
                        cell.setCellValue(statisticsVO.getOpenAccountCount());
                    }
                    // 开户数（无主单）
                    else if (celLength == 6) {
                        cell.setCellValue(statisticsVO.getOpenAccountAttrCount());
                    }
                    // 开户数(PC)
                    else if (celLength == 7) {
                        cell.setCellValue(statisticsVO.getAccountNumberPc());
                    }
                    // 开户数(iOS)
                    else if (celLength == 8) {
                        cell.setCellValue(statisticsVO.getAccountNumberIos());
                    }
                    // 开户数(Android)
                    else if (celLength == 9) {
                        cell.setCellValue(statisticsVO.getAccountNumberAndroid());
                    }
                    // 开户数(微信)
                    else if (celLength == 10) {
                        cell.setCellValue(statisticsVO.getAccountNumberWechat());
                    }

                    // 投资人数
                    else if (celLength == 11) {
                        cell.setCellValue(statisticsVO.getInvestNumber());
                    }
                    // 投资人数(无主单)
                    else if (celLength == 12) {
                        cell.setCellValue(statisticsVO.getInvestAttrNumber());
                    }
                    // 投资人数（PC）
                    else if (celLength == 13) {
                        cell.setCellValue(statisticsVO.getTenderNumberPc());
                    }
                    // 投资人数(iOS)
                    else if (celLength == 14) {
                        cell.setCellValue(statisticsVO.getTenderNumberIos());
                    }
                    // 投资人数(Android)
                    else if (celLength == 15) {
                        cell.setCellValue(statisticsVO.getTenderNumberAndroid());
                    }
                    // 投资人数(微官网)
                    else if (celLength == 16) {
                        cell.setCellValue(statisticsVO.getTenderNumberWechat());
                    }
                    // 累计充值
                    else if (celLength == 17) {
                        cell.setCellValue(statisticsVO.getCumulativeCharge().doubleValue());
                    }
                    // 累计充值(无主单)
                    else if (celLength == 18) {
                        cell.setCellValue(statisticsVO.getCumulativeAttrCharge().doubleValue());
                    }
                    // 累计投资
                    else if (celLength == 19) {
                        cell.setCellValue(statisticsVO.getCumulativeInvest().doubleValue());
                    }
                    // 累计投资(无主单)
                    else if (celLength == 20) {
                        cell.setCellValue(statisticsVO.getCumulativeAttrInvest().doubleValue());
                    }
                    // 汇直投投资金额
                    else if (celLength == 21) {
                        cell.setCellValue(statisticsVO.getHztInvestSum().doubleValue());
                    }
                    // 汇消费投资金额
                    else if (celLength == 22) {
                        cell.setCellValue(statisticsVO.getHxfInvestSum().doubleValue());
                    }
                    // 汇天利投资金额
                    else if (celLength == 23) {
                        cell.setCellValue(statisticsVO.getHtlInvestSum().doubleValue());
                    }
                    // 汇添金投资金额
                    else if (celLength == 24) {
                        cell.setCellValue(statisticsVO.getHtjInvestSum().doubleValue());
                    }
                    // 汇金理财投资金额
                    else if (celLength == 25) {
                        cell.setCellValue(statisticsVO.getRtbInvestSum().doubleValue());
                    }
                    // 汇转让投资金额
                    else if (celLength == 26) {
                        cell.setCellValue(statisticsVO.getHzrInvestSum().doubleValue());
                    }
                }
            }
        }
        // 导出
        ExportExcel.writeExcelFile(response, workbook, titles, fileName);
    }
}